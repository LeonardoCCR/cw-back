package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    private String data;
    private String formaPag;
    private String descontoTotal;
    private String aprovada;

    private Vendedor vendedor;
    private Cliente cliente;
    private ModeloVeiculo modeloVeiculo;
}
