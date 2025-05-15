package com.example.concesswebapi.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public abstract class Veiculo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chassi;
    private float precoAtual;
    private String cor;
    private String condicao;
    //vendido == true; em estoque == false
    private boolean vendido;
    private String garantia;
    private ArrayList<String> fotos;

    @ManyToOne
    private Concessionaria concessionaria;

    @ManyToOne
    private ModeloVeiculo modeloVeiculo;
}
