package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Carro;
import com.example.concesswebapi.Model.Entity.Moto;
import com.example.concesswebapi.Model.Entity.TipoVeiculo;
import com.example.concesswebapi.Model.Entity.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoDTO {

    private String tipoMotor;
    private Integer cilindrada;
    private Integer qtdMarcha;
    private String tipoPartida;
    private String categoria;

    public Moto converter(MotoDTO dto) {
        Moto moto = new Moto();

        moto.setCategoria(dto.getCategoria());
        moto.setCilindrada(dto.getCilindrada());
        moto.setQtdMarcha(dto.getQtdMarcha());
        moto.setTipoMotor(dto.getTipoMotor());
        moto.setTipoPartida(dto.getTipoPartida());

        return moto;
    }

    public static MotoDTO create(Moto moto) {

        MotoDTO dto = new MotoDTO();

        dto.qtdMarcha = moto.getQtdMarcha();
        dto.tipoPartida = moto.getTipoPartida();
        dto.tipoMotor = moto.getTipoMotor();
        dto.cilindrada = moto.getCilindrada();
        dto.categoria = moto.getCategoria();

        return dto;
    }
}
