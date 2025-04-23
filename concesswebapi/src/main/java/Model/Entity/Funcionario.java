package Model.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Funcionario extends PessoaFisica {

    private String cargo;
    private Empresa empresa;
}
