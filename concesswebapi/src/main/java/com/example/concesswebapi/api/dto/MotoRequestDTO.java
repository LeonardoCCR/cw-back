package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Moto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoRequestDTO {

    private String tipoMotor;
    private Integer cilindrada;
    private Integer qtdMarcha;
    private String tipoPartida;
    private String categoria;

    public Moto converter(MotoRequestDTO dto) {
        Moto moto = new Moto();

        moto.setCategoria(dto.getCategoria());
        moto.setCilindrada(dto.getCilindrada());
        moto.setQtdMarcha(dto.getQtdMarcha());
        moto.setTipoMotor(dto.getTipoMotor());
        moto.setTipoPartida(dto.getTipoPartida());

        return moto;
    }
}
