package com.example.concesswebapi.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ModeloVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String anoFabricacao;
    private BigDecimal precoBase;
    private String fotoModelo;
    private Integer qtdEstoqueVenda;
    private Integer qtdEstoque;
    private String permiteTestDrive;

    @ManyToOne
    private TipoVeiculo tipoVeiculo;

    @ManyToOne
    private Modelo modelo;
}
