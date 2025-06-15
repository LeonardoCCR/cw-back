package com.example.concesswebapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloRequestDTO {

    private String nome;
    private Long fabricanteId;
}
