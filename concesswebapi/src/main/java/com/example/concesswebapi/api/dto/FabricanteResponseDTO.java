package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FabricanteResponseDTO {

    private String nome;

    public static FabricanteResponseDTO create(Fabricante fabricante) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fabricante, FabricanteResponseDTO.class);
    }
}
