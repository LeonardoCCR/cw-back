package com.example.concesswebapi.Model.Entity;

import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class  Cliente extends PessoaFisica {

    private String dataNascimento;
}
