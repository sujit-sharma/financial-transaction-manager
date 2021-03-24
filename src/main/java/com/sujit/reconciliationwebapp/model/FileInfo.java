package com.sujit.reconciliationwebapp.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class FileInfo implements Serializable {

    @Id
    private String username;

    private String address;

    @ElementCollection
    private List<String> fileUrls;

}
