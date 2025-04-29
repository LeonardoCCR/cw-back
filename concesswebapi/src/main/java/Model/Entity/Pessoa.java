package Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String uf;
}
