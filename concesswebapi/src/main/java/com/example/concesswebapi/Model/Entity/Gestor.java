package com.example.concesswebapi.Model.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Data

public class Gestor extends Funcionario{
}
