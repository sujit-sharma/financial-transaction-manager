package com.sujit.reconciliationwebapp.util.channel;

import com.sujit.reconciliationwebapp.model.Transaction;
import com.sujit.reconciliationwebapp.util.parser.Parser;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemChannel implements Channel {
  final Parser parser;
  final File fileName;

  public FileSystemChannel(Parser parser, File fileName) {
    this.parser = parser;
    this.fileName = fileName;
  }

  @Override
  public List<Transaction> read() {
    List<Transaction> transactions = null;
    try (InputStreamReader reader = new FileReader(fileName)) {
      transactions = parser.parse(reader);
    } catch (IOException ioException) {
      Logger.getGlobal()
          .log(
              Level.SEVERE,
              ioException,
              () -> String.format("An Exception Occurs on reading %s", ioException.getMessage()));
    }
    return transactions;
  }

  @Override
  public void writeLine(String transaction) {
    try (FileWriter writer = new FileWriter(fileName, true)) {
      parser.transfer(transaction, writer);
    } catch (IOException ioException) {
      Logger.getGlobal()
          .log(
              Level.SEVERE,
              ioException,
              () -> String.format("An Exception Occurs on writing %s", ioException.getMessage()));
    }
  }
}
