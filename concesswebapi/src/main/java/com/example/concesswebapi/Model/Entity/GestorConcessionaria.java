package com.example.concesswebapi.Model.Entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class GestorConcessionaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Gestor gestor;

    @ManyToOne
    private Concessionaria concessionaria;
}
