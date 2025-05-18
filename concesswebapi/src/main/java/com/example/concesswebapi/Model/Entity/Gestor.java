package com.example.concesswebapi.Model.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data

public class Gestor extends Funcionario{
}
