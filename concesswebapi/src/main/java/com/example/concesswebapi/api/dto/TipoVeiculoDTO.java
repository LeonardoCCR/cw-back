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
public class TipoVeiculoDTO {

    private String tipo;
    private MotoDTO moto;
    private CarroDTO carro;

    public TipoVeiculo converter(TipoVeiculoDTO dto, TipoVeiculo tipoVeiculo) {

        tipoVeiculo.setTipo(dto.getTipo());
        return tipoVeiculo;
    }

    public static TipoVeiculoDTO create(TipoVeiculo tipoVeiculo) {

        TipoVeiculoDTO dto = new TipoVeiculoDTO();
        dto.tipo = tipoVeiculo.getTipo();

        if (tipoVeiculo instanceof Carro) {
            CarroDTO carroDTO = CarroDTO.create((Carro) tipoVeiculo);
            dto.setCarro(carroDTO);
        } else {
            MotoDTO motoDTO = MotoDTO.create((Moto) tipoVeiculo);
            dto.setMoto(motoDTO);
        }

        return dto;
    }
}
