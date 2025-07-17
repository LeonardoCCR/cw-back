
package com.example.concesswebapi.Model.Entity;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Concessionaria extends PessoaJuridica {

    @ManyToOne
    private Empresa empresa;
}
