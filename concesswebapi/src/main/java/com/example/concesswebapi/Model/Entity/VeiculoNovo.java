package com.example.concesswebapi.Model.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Data

public class VeiculoNovo extends Veiculo {
}
