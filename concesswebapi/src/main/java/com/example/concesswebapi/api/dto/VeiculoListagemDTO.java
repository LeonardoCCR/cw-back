package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoListagemDTO {

    private Long id;
    private String tipo;
    private String chassi;
    private String nomeModelo;
    private String nomeFabricante;
    private BigDecimal precoAtual;
    private String condicao;
    private String nomeConcessionaria;

    public static VeiculoListagemDTO create(Veiculo veiculo)
    {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoListagemDTO dto = modelMapper.map(veiculo, VeiculoListagemDTO.class);
        dto.nomeModelo = veiculo.getModeloVeiculo().getModelo().getNome();
        dto.nomeFabricante = veiculo.getModeloVeiculo().getModelo().getFabricante().getNome();
        dto.nomeConcessionaria = veiculo.getConcessionaria().getRazaoSocial();
        dto.tipo = veiculo.getModeloVeiculo().getTipoVeiculo().getTipo();

        return dto;
    }
}
