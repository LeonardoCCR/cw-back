package com.example.concesswebapi.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class PessoaFisica extends Pessoa{

    private String nome;
    private String cpf;
}
