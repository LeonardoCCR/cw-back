package com.example.concesswebapi.Model.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Empresa extends PessoaJuridica {

}
