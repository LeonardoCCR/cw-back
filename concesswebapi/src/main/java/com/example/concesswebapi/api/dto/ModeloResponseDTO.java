package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ModeloResponseDTO {

    private Long id;
    private String nome;
    private String nomeFabricante;

    public static ModeloResponseDTO create(Modelo modelo) {
        ModelMapper modelMapper = new ModelMapper();
        ModeloResponseDTO dto = modelMapper.map(modelo, ModeloResponseDTO.class);
        dto.nomeFabricante = modelo.getFabricante().getNome();

        return dto;
    }
}
