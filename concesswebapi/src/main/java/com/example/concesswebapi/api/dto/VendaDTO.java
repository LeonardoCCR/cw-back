package com.example.concesswebapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {
    private String data;
    private String formaPag;
    private Double desconto;
    private Long clienteId;
    private Long vendedorId;
}

