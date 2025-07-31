package com.example.concesswebapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {
    private Long id;
    private String data;
    private String formaPag;
    private Double desconto;
    private Long clienteId;
    private Long vendedorId;
    private List<ItensVendaDTO> itens;
}