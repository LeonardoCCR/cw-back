package com.example.concesswebapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItensVendaDTO {
    private Long id;
    private Long modeloVeiculoId;
    private Long veiculoId;
    private BigDecimal descontoParcial;
}