package Model.Entity;

import java.util.ArrayList;

public class Veiculo {

    private String chassi;
    private float precoAtual;
    private String cor;
    //novo == true; usado == false
    private boolean condicao;
    //vendido == true; em estoque == false
    private boolean vendido;
    private String garantia;
    private ArrayList<String> fotos;
    private ModeloVeiculo modeloVeiculo;
}
