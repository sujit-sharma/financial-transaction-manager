package com.sujit.reconciliationwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Currency;

@Getter
@Setter
public class MissingOrMismatchingDto extends DataTransferDto {

    private String foundIn;
    private String transactionId;
    private Double amount;
    private Currency currencyCode;
    private LocalDate date;
}
