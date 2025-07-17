package com.example.concesswebapi.Model.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class VeiculoUsado extends Veiculo {

    private Double quilometragem;
    private String dataUltimaRevisao;
    private String laudoVistoria;
    private String documentacao;
    private String manutencao;
    private String sinistroAcidente;
    private String contatoProprietario; // O campo existe aqui.
}