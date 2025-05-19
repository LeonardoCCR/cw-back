package com.example.concesswebapi.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class VeiculoUsado extends Veiculo {

    private Double quilometragem;
    private String dataUltimaRevisao;
    private String contatoProprietario;
    private String laudoVistoria;
    private String documentacao;
    private String sinistroAcidente;
    private String manutencao;
}
