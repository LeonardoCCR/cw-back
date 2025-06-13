package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AcessorioResponseDTO {

    private String descricao;

    public static AcessorioResponseDTO create(Acessorio acessorio) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(acessorio, AcessorioResponseDTO.class);
    }
}
