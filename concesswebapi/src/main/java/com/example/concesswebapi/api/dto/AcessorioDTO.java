package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AcessorioDTO {

    private String descricao;

    public static AcessorioDTO create(Acessorio acessorio) {
        ModelMapper modelMapper = new ModelMapper();
        AcessorioDTO dto = modelMapper.map(acessorio, AcessorioDTO.class);
        dto.descricao = acessorio.getDescricao();

        return dto;
    }
}
