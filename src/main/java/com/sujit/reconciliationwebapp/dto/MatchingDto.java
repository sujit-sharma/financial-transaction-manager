package com.sujit.reconciliationwebapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Currency;

@Getter
@Setter
public class MatchingDto {

    protected String transactionId;
    protected Double amount;
    protected Currency currencyCode;
    protected LocalDate date;
}

