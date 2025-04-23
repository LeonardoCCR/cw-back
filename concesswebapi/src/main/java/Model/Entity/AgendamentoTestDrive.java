package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoTestDrive {

    private String dataAgendada;
    private String horaAgendada;
    private String dataEntegue;
    private String horaEntregue;
    
    private Vendedor vendedor;
    private Cliente cliente;
    private Veiculo veiculo;
}
