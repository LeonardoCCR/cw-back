package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import com.example.concesswebapi.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoUsadoDTO {

    private Double quilometragem;
    private String documentacao;
    private String manutencao;
    private String laudoVistoria;
    private String dataUltimaRevisao;
    private String sinistroAcidente;
    private String contatoProprietario;

    public VeiculoUsado converter(VeiculoUsadoDTO dto, VeiculoUsado veiculo) {

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

    public static VeiculoUsadoDTO create(VeiculoUsado veiculo) {

        VeiculoUsadoDTO dto = new VeiculoUsadoDTO();

        dto.quilometragem = veiculo.getQuilometragem();
        dto.documentacao = veiculo.getDocumentacao();
        dto.manutencao = veiculo.getManutencao();
        dto.laudoVistoria = veiculo.getLaudoVistoria();
        dto.dataUltimaRevisao = veiculo.getDataUltimaRevisao();
        dto.sinistroAcidente = veiculo.getSinistroAcidente();

        return dto;
    }
}
