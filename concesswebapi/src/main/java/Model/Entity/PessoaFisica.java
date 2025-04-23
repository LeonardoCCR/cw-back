package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor


public abstract class PessoaFisica extends Pessoa{

    private String nome;
    private String cpf;
    private String login;
    private String senha;
}
