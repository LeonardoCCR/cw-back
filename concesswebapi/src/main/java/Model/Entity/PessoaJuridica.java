package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@MappedSuperclass

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class PessoaJuridica extends Pessoa {

    private String razaoSocial;
    private String cnpj;
}
