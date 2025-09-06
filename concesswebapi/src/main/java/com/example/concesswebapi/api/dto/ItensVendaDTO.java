package com.example.concesswebapi.api.dto;

import java.math.BigDecimal;

public class ItensVendaDTO {
    private Long id;
    private Long modeloVeiculoId;
    private Long veiculoId;
    private BigDecimal descontoParcial;
    private Long vendaId;

    public ItensVendaDTO() {
    }

    public ItensVendaDTO(Long id, Long modeloVeiculoId, Long veiculoId, BigDecimal descontoParcial) {
        this.id = id;
        this.modeloVeiculoId = modeloVeiculoId;
        this.veiculoId = veiculoId;
        this.descontoParcial = descontoParcial;
    }

    public ItensVendaDTO(Long id, Long modeloVeiculoId, Long veiculoId, Long vendaId, BigDecimal descontoParcial) {
        this.id = id;
        this.modeloVeiculoId = modeloVeiculoId;
        this.veiculoId = veiculoId;
        this.vendaId = vendaId;
        this.descontoParcial = descontoParcial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModeloVeiculoId() {
        return modeloVeiculoId;
    }

    public void setModeloVeiculoId(Long modeloVeiculoId) {
        this.modeloVeiculoId = modeloVeiculoId;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public BigDecimal getDescontoParcial() {
        return descontoParcial;
    }

    public void setDescontoParcial(BigDecimal descontoParcial) {
        this.descontoParcial = descontoParcial;
    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }
}