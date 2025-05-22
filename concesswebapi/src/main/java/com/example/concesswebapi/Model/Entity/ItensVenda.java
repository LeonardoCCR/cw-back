package com.example.concesswebapi.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItensVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal descontoParcial;

    @ManyToOne
    private Venda venda;

    @ManyToOne
    private ModeloVeiculo modeloVeiculo;

    @OneToOne
    private Veiculo veiculo;
}
