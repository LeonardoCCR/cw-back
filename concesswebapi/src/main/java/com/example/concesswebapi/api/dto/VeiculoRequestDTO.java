package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoNovo;
import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VeiculoRequestDTO {

    private String chassi;
    private ArrayList<String> fotos;
    private Long concessionariaId;
    private BigDecimal precoAtual;
    private String cor;
    private String garantia;
    private String condicao;
    private ModeloVeiculoRequestDTO modeloVeiculo;
    private VeiculoUsadoRequestDTO veiculoUsado;

    public Veiculo converter(VeiculoRequestDTO dto) {

        Veiculo veiculo;

        if (dto.getVeiculoUsado() == null) {
            veiculo = new VeiculoNovo();
        } else {
            veiculo = new VeiculoUsado();
        }

        veiculo.setChassi(dto.getChassi());
        veiculo.setPrecoAtual(dto.getPrecoAtual());
        veiculo.setCor(dto.getCor());
        veiculo.setCondicao(dto.getCondicao());
        veiculo.setVendido(false);
        veiculo.setGarantia(dto.getGarantia());
        veiculo.setFotos(dto.getFotos());

        return veiculo;
    }
}
