package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Empresa extends PessoaJuridica {

    //não tem listas aqui, marco usa anotações @ManyToOne para referenciar 1 para n
    @ManyToOne
    private AdmEmpresa admEmpresa;
}
