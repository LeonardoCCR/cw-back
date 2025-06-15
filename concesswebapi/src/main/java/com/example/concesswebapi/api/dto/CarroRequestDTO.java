package com.example.concesswebapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarroRequestDTO {

    private String categoria;
    private String motorizacao;
    private Double potencia;
    private String transmissao;
}
