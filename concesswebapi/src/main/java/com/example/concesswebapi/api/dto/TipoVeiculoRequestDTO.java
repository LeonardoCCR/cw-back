package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.TipoVeiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoVeiculoRequestDTO {

    private String tipo;
    private MotoRequestDTO moto;
    private CarroRequestDTO carro;

    public TipoVeiculo converter(TipoVeiculoRequestDTO dto, TipoVeiculo tipoVeiculo) {

        tipoVeiculo.setTipo(dto.getTipo());
        return tipoVeiculo;
    }
}
