package com.example.concesswebapi.Model.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class PessoaJuridica extends Pessoa {

    private String razaoSocial;
    private String cnpj;
}
