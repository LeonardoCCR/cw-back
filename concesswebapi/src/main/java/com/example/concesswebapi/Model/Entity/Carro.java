package com.example.concesswebapi.Model.Entity;

import com.example.concesswebapi.api.dto.CarroDTO;
import com.example.concesswebapi.api.dto.VeiculoUsadoDTO;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Carro extends TipoVeiculo {

    private String motorizacao;
    private String transmissao;
    private Double potencia;
    private String categoria;
}
