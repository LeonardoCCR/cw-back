package com.example.concesswebapi.Model.Entity;


import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@ToString(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class Funcionario extends PessoaFisica {

    private String cargo;

    @ManyToOne
    private Empresa empresa;

}
