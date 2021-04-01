package com.sujit.reconciliationwebapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class UserActivity {

    @Id
    private String username;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<HitInformation> hitInformation;


}
