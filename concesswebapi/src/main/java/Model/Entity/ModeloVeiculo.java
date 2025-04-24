package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ModeloVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //carro == true; moto == false
    private boolean tipo;
    private String anoFabricacao;
    private float precoBase;
    private String fotoModelo;
    private boolean requerTestDrive;
    private Integer qtdEstoqueVenda;

    @ManyToOne
    private TipoVeiculo tipoVeiculo;

    @ManytoOne
    private Modelo modelo;

    private VeiculoUsado veiculoUsado;
}
