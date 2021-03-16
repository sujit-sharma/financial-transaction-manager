package com.sujit.reconciliationwebapp.dao;


import com.sujit.reconciliationwebapp.model.Transaction;

import java.util.List;

public interface ReconciliationDAO {
  List<Transaction> findAll();

  void saveRow(String transactions);
}
