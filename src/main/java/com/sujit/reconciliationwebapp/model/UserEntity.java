package com.sujit.reconciliationwebapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities;
}
