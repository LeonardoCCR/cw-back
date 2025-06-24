package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FabricanteDTO {

    private Long id;
    private String nome;

    public static FabricanteDTO create(Fabricante fabricante) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fabricante, FabricanteDTO.class);
    }
}
