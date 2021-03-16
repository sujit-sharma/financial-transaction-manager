package com.sujit.reconciliationwebapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;

@Getter
@Setter
public class JsonTransaction {

  private final String date;
  private final String reference;
  private final Double amount;
  private final Currency currencyCode;
  private final String purpose;

  public JsonTransaction(
      String date, String reference, Double amount, Currency currencyCode, String purpose) {
    this.date = date;
    this.reference = reference;
    this.amount = amount;
    this.currencyCode = currencyCode;
    this.purpose = purpose;
  }

}
