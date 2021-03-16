package com.sujit.reconciliationwebapp.util.parser;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sujit.dataformat.JsonTransaction;
import com.sujit.dataformat.Transaction;

import java.io.FileWriter;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


public class GoogleJsonParser implements Parser {

  @Override
  public List<Transaction> parse(InputStreamReader reader) {
    JsonTransaction[] jsonTransactions;
    Gson gson = new Gson();
    jsonTransactions = gson.fromJson(new JsonReader(reader), JsonTransaction[].class);

    return jsonObjectToTransactionAdapter(Arrays.asList(jsonTransactions));
  }

  @Override
  public void transfer(String line, FileWriter writer) {
    throw new UnsupportedOperationException("Method not Implemented Yet");
  }

  private List<Transaction> jsonObjectToTransactionAdapter(List<JsonTransaction> jsonTransactions) {
    List<Transaction> transactions = new LinkedList<>();
    jsonTransactions.forEach(
        jsonTransaction -> {
          Transaction transaction =
              new Transaction(
                  jsonTransaction.getReference(),
                  jsonTransaction.getAmount(),
                  jsonTransaction.getCurrencyCode(),
                  jsonTransaction.getPurpose(),
                  LocalDate.parse(
                      jsonTransaction.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
          transactions.add(transaction);
        });
    return transactions;
  }
}
