package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItensVenda {


    private float descontoParcial;
    private Venda venda;
    private ModeloVeiculo modeloVeiculo;
    private Veiculo veiculo;
}
