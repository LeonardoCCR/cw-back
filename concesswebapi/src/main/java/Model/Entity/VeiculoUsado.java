package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class VeiculoUsado extends Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float quilometragem;
    private String dataUltimaRevisao;
    private String contatoProprietario;
    private String laudoVistoria;
    private String documentacao;
    private String sinistroAcidente;

    @ManyToOne
    private ModeloVeiculo modeloVeiculo;
}
