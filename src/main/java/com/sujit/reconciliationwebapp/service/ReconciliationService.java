package com.sujit.reconciliationwebapp.service;

import com.sujit.reconciliationwebapp.constraint.DaoType;
import com.sujit.reconciliationwebapp.dao.ReconciliationDAO;
import com.sujit.reconciliationwebapp.dao.ReconciliationDAOImpl;
import com.sujit.reconciliationwebapp.dto.MatchingDto;
import com.sujit.reconciliationwebapp.dto.MissingOrMismatchingDto;
import com.sujit.reconciliationwebapp.exception.IllegalFileFormatException;
import com.sujit.reconciliationwebapp.model.FileInfo;
import com.sujit.reconciliationwebapp.model.Transaction;
import com.sujit.reconciliationwebapp.repository.FileInfoRepository;
import com.sujit.reconciliationwebapp.util.ReconciliationUtils;
import com.sujit.reconciliationwebapp.util.channel.FileSystemChannel;
import com.sujit.reconciliationwebapp.util.parser.ApacheCsvParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReconciliationService {
    private final FileInfoRepository fileInfoRepository;
    private final ReconciliationUtils reconciliationUtils;

    private List<Transaction> sourceList;
    private List<Transaction> targetList;
    Map<DaoType, List<Object>> result;

    public void saveFileSystemInfo(FileInfo fileInfo ) {
        log.info("saving file information on database");
        fileInfoRepository.save(fileInfo);
    }

    public static final String COMMA = ",";

    public Map<DaoType, List<Object>> reconcile() {
        result = new LinkedHashMap<>();
        arrangeDataThenApplyReconciliation();
        for (Iterator<Transaction> sourceItr = sourceList.listIterator(); sourceItr.hasNext(); ) {
            Transaction sourceTrans = sourceItr.next();

            for (Iterator<Transaction> targetItr = targetList.listIterator(); targetItr.hasNext(); ) {
                Transaction targetTrans = targetItr.next();
                if (sourceTrans.getTransId().equals(targetTrans.getTransId())) {
                    if (sourceTrans.isMatched(targetTrans)) {
                        addTransBasedOnType(DaoType.MATCHING, sourceTrans, null);
                        sourceItr.remove();
                    } else {
                        addTransBasedOnType(DaoType.MISMATCHING, sourceTrans, true);
                        sourceItr.remove();
                        addTransBasedOnType(DaoType.MISMATCHING, targetTrans, false);
                    }
                    targetItr.remove();
                }
            }
        }
        separatesMissingTransaction();
        return result;
    }

    private void separatesMissingTransaction() {
        sourceList.forEach(
                missingSourceTrans -> addTransBasedOnType(DaoType.MISSING, missingSourceTrans,true));
        targetList.forEach(
                missingTargetTrans -> addTransBasedOnType(DaoType.MISSING, missingTargetTrans, false));
    }
    private void addTransBasedOnType(DaoType daoType,  Transaction transaction, Boolean isSource ) {
        List<Object> transactionListDto = result.getOrDefault(daoType, new ArrayList<>());
        MatchingDto matchingDto = null;
        MissingOrMismatchingDto missingOrMismatchingDto = null;
        if(daoType == DaoType.MATCHING) {
            matchingDto = new MatchingDto();
            transactionListDto.add(setAttributeToTransfer(matchingDto, transaction));
        }
        else {
            missingOrMismatchingDto = new MissingOrMismatchingDto();
            missingOrMismatchingDto.setFoundIn(isSource? "SOURCE": "TARGET");
            transactionListDto.add(setAttributeToTransfer( missingOrMismatchingDto, transaction));
        }
        result.put(daoType, transactionListDto);
    }

    private MatchingDto setAttributeToTransfer(MatchingDto matchingDto, Transaction transaction) {
        matchingDto.setTransactionId(transaction.getTransId());
        matchingDto.setAmount(transaction.getAmount());
        matchingDto.setCurrencyCode(transaction.getCurrencyCode());
        matchingDto.setDate(transaction.getDate());
        return matchingDto;

    }

    public static String toAmount(Double amount) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        String pattern = format.toPattern();
        String newPattern = pattern.replace("\u00A4", "").replace(",", "").trim();
        DecimalFormat decimalFormat = new DecimalFormat(newPattern);
        return decimalFormat.format(amount);
    }
    public void arrangeDataThenApplyReconciliation() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FileInfo fileInfo =  fileInfoRepository.findFirstByUsername(user.toString()).get();
        List<String> fileUrlList = fileInfo.getFileUrls();
        if(fileUrlList.size() > 1) {
            String source = fileUrlList.get(fileUrlList.size() - 1);
            String target = fileUrlList.get(fileUrlList.size() - 2);
            try {
                sourceList = reconciliationUtils.accessData(source);
                targetList = reconciliationUtils.accessData(target);
            } catch (IllegalFileFormatException illegalFileFormatException) {
                log.error("An IllegalFileFormatException occurs" + illegalFileFormatException.getMessage());
            } catch (Exception Exception) {
                log.info("An Exception occurs while accessing file" + Exception.getMessage());
            }
        }
    }
}
