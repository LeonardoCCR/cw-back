package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne
    private ModeloVeiculo modeloVeiculo;
}
