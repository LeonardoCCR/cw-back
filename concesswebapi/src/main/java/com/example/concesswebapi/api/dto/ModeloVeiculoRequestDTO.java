package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.ModeloVeiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloVeiculoRequestDTO {

    private String anoFabricacao;
    private BigDecimal precoBase;
    private String fotoModelo;
    private Integer qtdEstoqueVenda;
    private Integer qtdEstoque;
    private String permiteTestDrive;
    private Long modeloId;
    private TipoVeiculoRequestDTO tipoVeiculo;

    public ModeloVeiculo converter(ModeloVeiculoRequestDTO dto) {
        ModeloVeiculo modeloVeiculo = new ModeloVeiculo();

        modeloVeiculo.setAnoFabricacao(dto.getAnoFabricacao());
        modeloVeiculo.setPrecoBase(dto.getPrecoBase());
        modeloVeiculo.setFotoModelo(dto.getFotoModelo());
        modeloVeiculo.setQtdEstoqueVenda(dto.getQtdEstoqueVenda());
        modeloVeiculo.setQtdEstoque(dto.getQtdEstoque());
        modeloVeiculo.setPermiteTestDrive(dto.getPermiteTestDrive());

        return modeloVeiculo;
    }
}
