package Model.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Funcionario extends PessoaFisica {

    private String cargo;

    @ManyToOne
    private Empresa empresa;

}
