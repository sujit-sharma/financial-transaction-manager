package com.sujit.reconciliationwebapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Currency;

@Getter
@Setter
@Entity
public class Transaction {

  @Id
  private String transId;
  private String description;
  private Double amount;
  private Currency currencyCode;
  private String purpose;
  private LocalDate date;
  private Character transType;

  public Transaction() {}

  public Transaction(String transId, Double amount, Currency currencyCode, String purpose, LocalDate date) {
    this.transId = transId;
    this.amount = amount;
    this.currencyCode = currencyCode;
    this.purpose = purpose;
    this.date = date;
  }

  public Transaction(
      String transId,
      String description,
      Double amount,
      Currency currencyCode,
      String purpose,
      LocalDate date,
      Character transType) {
    this(transId, amount, currencyCode, purpose, date);
    this.description = description;
    this.transType = transType;
  }

  public boolean isMatched(Transaction targetTrans) {
    return this.getAmount().equals(targetTrans.getAmount())
        && this.getCurrencyCode().equals(targetTrans.getCurrencyCode())
        && this.getDate().equals(targetTrans.getDate());
  }
}
