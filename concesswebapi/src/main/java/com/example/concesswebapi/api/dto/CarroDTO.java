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
public class CarroDTO {

    private String categoria;
    private String motorizacao;
    private Double potencia;
    private String transmissao;

    public static CarroDTO create(Carro carro) {

        CarroDTO dto = new CarroDTO();

        dto.motorizacao = carro.getMotorizacao();
        dto.potencia = carro.getPotencia();
        dto.transmissao = carro.getTransmissao();
        dto.categoria = carro.getCategoria();

        return dto;
    }
}
