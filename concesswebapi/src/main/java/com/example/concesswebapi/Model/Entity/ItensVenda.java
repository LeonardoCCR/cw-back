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

    @Column(precision = 5, scale = 2)
    private BigDecimal descontoParcial;

    @ManyToOne
    private Venda venda;

    @ManyToOne
    private ModeloVeiculo modeloVeiculo;

    @OneToOne
    private Veiculo veiculo;

    @Transient
    public BigDecimal getValorComDesconto() {
        if (veiculo == null || veiculo.getPrecoAtual() == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal precoOriginal = veiculo.getPrecoAtual();
        if (descontoParcial == null || descontoParcial.compareTo(BigDecimal.ZERO) <= 0) {
            return precoOriginal;
        }
        BigDecimal fatorDesconto = BigDecimal.ONE.subtract(descontoParcial.divide(new BigDecimal("100")));
        return precoOriginal.multiply(fatorDesconto);
    }
}