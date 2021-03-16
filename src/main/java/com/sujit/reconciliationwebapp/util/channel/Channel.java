package com.sujit.reconciliationwebapp.util.channel;

import com.sujit.reconciliationwebapp.model.Transaction;

import java.util.List;

public interface Channel {
  List<Transaction> read();

  void writeLine(String transaction);
}
