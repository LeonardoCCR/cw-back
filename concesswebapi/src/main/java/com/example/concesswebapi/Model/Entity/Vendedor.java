package com.example.concesswebapi.Model.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Vendedor extends Funcionario{

    @ManyToOne
    private Concessionaria concessionaria;
}

