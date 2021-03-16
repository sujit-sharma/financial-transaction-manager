package com.sujit.reconciliationwebapp.service;

import com.sujit.reconciliationwebapp.constraint.DaoType;
import com.sujit.reconciliationwebapp.dao.ReconciliationDAO;
import com.sujit.reconciliationwebapp.dao.ReconciliationDAOImpl;
import com.sujit.reconciliationwebapp.exception.IllegalFileFormatException;
import com.sujit.reconciliationwebapp.model.FileInfo;
import com.sujit.reconciliationwebapp.model.Transaction;
import com.sujit.reconciliationwebapp.repository.FileInfoRepository;
import com.sujit.reconciliationwebapp.util.ReconciliationUtils;
import com.sujit.reconciliationwebapp.util.channel.Channel;
import com.sujit.reconciliationwebapp.util.channel.FileSystemChannel;
import com.sujit.reconciliationwebapp.util.parser.ApacheCsvParser;
import com.sujit.reconciliationwebapp.util.parser.Parser;
import com.sujit.reconciliationwebapp.util.parser.ParserFactory;
import com.sujit.reconciliationwebapp.util.parser.ParserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReconciliationService {
    private final FileInfoRepository fileInfoRepository;
    private final ReconciliationUtils reconciliationUtils;

    private List<Transaction> sourceList;
    private List<Transaction> targetList;

    public void saveFileSystemInfo(FileInfo fileInfo ) {
        log.info("saving file information on database");
        fileInfoRepository.save(fileInfo);
    }

    public static final String COMMA = ",";

    public void reconcile() {
        arrangeDataThenApplyReconciliation();
        Map<DaoType, ReconciliationDAO> daoMap = getReconciliationDao();
        for (Iterator<Transaction> sourceItr = sourceList.listIterator(); sourceItr.hasNext(); ) {
            Transaction sourceTrans = sourceItr.next();

            for (Iterator<Transaction> targetItr = targetList.listIterator(); targetItr.hasNext(); ) {
                Transaction targetTrans = targetItr.next();
                if (sourceTrans.getTransId().equals(targetTrans.getTransId())) {
                    if (sourceTrans.isMatched(targetTrans)) {
                        daoMap.get(DaoType.MATCHING).saveRow(csvLine(sourceTrans, ""));
                        sourceItr.remove();
                    } else {
                        daoMap.get(DaoType.MISMATCHING).saveRow(csvLine(sourceTrans, "SOURCE"));
                        sourceItr.remove();
                        daoMap.get(DaoType.MISMATCHING).saveRow(csvLine(targetTrans, "TARGET"));
                    }
                    targetItr.remove();
                }
            }
        }
        sourceList.forEach(
                transaction -> daoMap.get(DaoType.MISSING).saveRow(csvLine(transaction, "SOURCE")));
        targetList.forEach(
                transaction -> daoMap.get(DaoType.MISSING).saveRow(csvLine(transaction, "TARGET")));
        Logger.getGlobal()
                .info(() -> String.format("Result files are available in directory %s ", destinationDir));
    }

    private Map<DaoType, ReconciliationDAO> getReconciliationDao() {
        Map<DaoType, ReconciliationDAO> daoMap = new LinkedHashMap<>(3);
        ReconciliationDAO matchingDao = createReconciliationDao("MatchingTransactions.csv");
        ReconciliationDAO mismatchingDao = createReconciliationDao("MismatchingTransactions.csv");
        ReconciliationDAO missingDao = createReconciliationDao("MissingTransactions.csv");
        matchingDao.saveRow("transaction id,amount,currency code,value date");
        mismatchingDao.saveRow("found in file,transaction id,amount,currency code,value date");
        missingDao.saveRow("found in file,transaction id,amount,currency code,value date");

        daoMap.put(DaoType.MATCHING, matchingDao);
        daoMap.put(DaoType.MISMATCHING, mismatchingDao);
        daoMap.put(DaoType.MISSING, missingDao);
        return daoMap;
    }

    private ReconciliationDAO createReconciliationDao(String fileName) {
        return new ReconciliationDAOImpl(
                new FileSystemChannel(new ApacheCsvParser(), new File(destinationDir, fileName)));
    }




    private String csvLine(Transaction transaction, String source) {
        String txnRow =
                transaction.getTransId()
                        + COMMA
                        + toAmount(transaction.getAmount())
                        + COMMA
                        + transaction.getCurrencyCode()
                        + COMMA
                        + transaction.getDate();
        return source != null && !source.trim().isEmpty() ? (source + COMMA + txnRow) : txnRow;
    }

    public static String toAmount(Double amount) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        String pattern = format.toPattern();
        String newPattern = pattern.replace("\u00A4", "").replace(",", "").trim();
        DecimalFormat decimalFormat = new DecimalFormat(newPattern);
        return decimalFormat.format(amount);
    }
    public void arrangeDataThenApplyReconciliation() {
        FileInfo fileInfo = fileInfoRepository.findAllByUserId(12L).get();
        List<String> fileUrlSet = fileInfo.getFileUrls();
        String source = fileUrlSet.get(fileUrlSet.size()-1);
        String target = fileUrlSet.get(fileUrlSet.size()-2);
        try {
            sourceList = reconciliationUtils.accessData(source);
            targetList = reconciliationUtils.accessData(target);
        }
        catch (IllegalFileFormatException illegalFileFormatException){
            log.error("An IllegalFileFormatException occures" + illegalFileFormatException.getMessage());
        }
        catch (Exception Exception) {
            log.info("An Exception occurs while accessing file" + Exception.getMessage());
        }
    }

    public Map<String, List<Object>> reconciliate() {

    }
}
