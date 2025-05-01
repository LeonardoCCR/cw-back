package com.example.concesswebapi.Model.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class Funcionario extends PessoaFisica {

    private String cargo;

    @ManyToOne
    private Empresa empresa;

}
