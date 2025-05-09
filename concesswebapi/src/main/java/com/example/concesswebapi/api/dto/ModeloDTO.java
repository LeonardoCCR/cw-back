package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ModeloDTO {

    private String nome;
    private String nomeFabricante;

    public static ModeloDTO create(Modelo modelo) {
        ModelMapper modelMapper = new ModelMapper();
        ModeloDTO dto = modelMapper.map(modelo, ModeloDTO.class);
        dto.nome = modelo.getNome();
        dto.nomeFabricante = modelo.getFabricante().getNome();

        return dto;
    }
}
