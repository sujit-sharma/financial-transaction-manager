package com.sujit.reconciliationwebapp.util;

import com.sujit.reconciliationwebapp.dao.ReconciliationDAO;
import com.sujit.reconciliationwebapp.dao.ReconciliationDAOImpl;
import com.sujit.reconciliationwebapp.exception.IllegalFileFormatException;
import com.sujit.reconciliationwebapp.model.Transaction;
import com.sujit.reconciliationwebapp.util.channel.Channel;
import com.sujit.reconciliationwebapp.util.channel.FileSystemChannel;
import com.sujit.reconciliationwebapp.util.parser.Parser;
import com.sujit.reconciliationwebapp.util.parser.ParserFactory;
import com.sujit.reconciliationwebapp.util.parser.ParserType;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Component
public class ReconciliationUtils {


    public List<Transaction> accessData(String filePath) {
        String[] parts = filePath.split("\\.");
        String fileFormat = parts[parts.length - 1];
        Optional<ParserType> parserType = ParserType.getByExtension(fileFormat);
        if (parserType.isEmpty()) {
            throw new IllegalFileFormatException("File format not supported");
        }

        Parser parser = ParserFactory.getParserByName(parserType.get());
        Channel sourceChannel = new FileSystemChannel(parser, new File(filePath));
        ReconciliationDAO sourceDao = new ReconciliationDAOImpl(sourceChannel);
        return sourceDao.findAll();
    }
}
