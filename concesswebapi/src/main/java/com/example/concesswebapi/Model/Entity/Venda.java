package com.example.concesswebapi.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;
    private String formaPag;
    private String descontoTotal;
    private String aprovada;

    @ManyToOne
    private Vendedor vendedor;

    @ManyToOne
    private Cliente cliente;


}
