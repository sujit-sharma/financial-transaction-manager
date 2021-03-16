package com.sujit.reconciliationwebapp.util.parser;


import com.sujit.reconciliationwebapp.model.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public interface Parser {
  List<Transaction> parse(InputStreamReader reader) throws IOException;

  void transfer(String line, FileWriter writer) throws IOException;
}
