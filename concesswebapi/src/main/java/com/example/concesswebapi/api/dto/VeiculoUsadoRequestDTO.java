package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoNovo;
import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import com.example.concesswebapi.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoUsadoRequestDTO {

    private Double quilometragem;
    private String documentacao;
    private String manutencao;
    private String laudoVistoria;
    private String dataUltimaRevisao;
    private String sinistroAcidente;
    private String contatoProprietario;

    public VeiculoUsado converter(VeiculoUsadoRequestDTO dto, VeiculoUsado veiculo) {

        if(dto == null)
        {
            throw new RegraNegocioException("Informações de veículo usado são obrigatórias");
        }

        veiculo.setQuilometragem(dto.getQuilometragem());
        veiculo.setDataUltimaRevisao(dto.getDataUltimaRevisao());
        veiculo.setContatoProprietario(dto.getContatoProprietario());
        veiculo.setLaudoVistoria(dto.getLaudoVistoria());
        veiculo.setDocumentacao(dto.getDocumentacao());
        veiculo.setManutencao(dto.getManutencao());
        veiculo.setSinistroAcidente(dto.getSinistroAcidente());

        return veiculo;
    }
}
