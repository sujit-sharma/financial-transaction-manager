package com.sujit.reconciliationwebapp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MissingOrMismatchingDto extends MatchingDto {

    private String foundIn;
}
