package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

import java.util.ArrayList;

public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chassi;
    private float precoAtual;
    private String cor;
    //novo == true; usado == false
    private boolean condicao;
    //vendido == true; em estoque == false
    private boolean vendido;
    private String garantia;
    private ArrayList<String> fotos;

    @ManyToOne
    private Concessionaria concessionaria;

    @ManyToOne
    private ModeloVeiculo modeloVeiculo;
}
