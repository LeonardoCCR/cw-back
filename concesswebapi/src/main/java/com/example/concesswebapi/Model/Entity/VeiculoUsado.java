package com.example.concesswebapi.Model.Entity;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
