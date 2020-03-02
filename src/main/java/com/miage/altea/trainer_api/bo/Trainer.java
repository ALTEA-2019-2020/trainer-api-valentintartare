package com.miage.altea.trainer_api.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Transactional
public class Trainer {

    @Id
    private String name;

    @Column
    private String password;

    @ElementCollection
    private List<Pokemon> team;

    public Trainer(String name) {
        this.name = name;
    }
}
