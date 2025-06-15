package com.example.concesswebapi.Model.Entity;

import lombok.*;

import javax.persistence.Entity;

@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Moto extends TipoVeiculo {

    private Integer qtdMarcha;
    private String tipoPartida;
    private String tipoMotor;
    private Integer cilindrada;
    private String categoria;
}
