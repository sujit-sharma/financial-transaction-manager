package com.sujit.reconciliationwebapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class HitInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;


    private String activity;

    private String requestIpAddr;

    private LocalDateTime date;
}
