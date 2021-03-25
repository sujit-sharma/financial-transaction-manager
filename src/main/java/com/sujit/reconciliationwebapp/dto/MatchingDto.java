package com.sujit.reconciliationwebapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Currency;

@Getter
@Setter
public class MatchingDto extends DataTransferDto {

    private String transactionId;
    private Double amount;
    private Currency currencyCode;
    private LocalDate date;
}
