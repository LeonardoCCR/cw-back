package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.ModeloVeiculo;
import com.example.concesswebapi.Model.Entity.TipoVeiculo;
import com.example.concesswebapi.Model.Entity.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloVeiculoDTO {

    private String anoFabricacao;
    private BigDecimal precoBase;
    private String fotoModelo;
    private Integer qtdEstoqueVenda;
    private Integer qtdEstoque;
    private String permiteTestDrive;
    private Long modeloId;
    private TipoVeiculoDTO tipoVeiculo;

    public ModeloVeiculo converter(ModeloVeiculoDTO dto) {
        ModeloVeiculo modeloVeiculo = new ModeloVeiculo();

        modeloVeiculo.setAnoFabricacao(dto.getAnoFabricacao());
        modeloVeiculo.setPrecoBase(dto.getPrecoBase());
        modeloVeiculo.setFotoModelo(dto.getFotoModelo());
        modeloVeiculo.setQtdEstoqueVenda(dto.getQtdEstoqueVenda());
        modeloVeiculo.setQtdEstoque(dto.getQtdEstoque());
        modeloVeiculo.setPermiteTestDrive(dto.getPermiteTestDrive());

        return modeloVeiculo;
    }

    public static ModeloVeiculoDTO create(Veiculo veiculo) {

        ModeloVeiculoDTO dto = new ModeloVeiculoDTO();

        dto.anoFabricacao = veiculo.getModeloVeiculo().getAnoFabricacao();
        dto.precoBase = veiculo.getModeloVeiculo().getPrecoBase();
        dto.fotoModelo = veiculo.getModeloVeiculo().getFotoModelo();
        dto.qtdEstoqueVenda = veiculo.getModeloVeiculo().getQtdEstoqueVenda();
        dto.qtdEstoque = veiculo.getModeloVeiculo().getQtdEstoque();
        dto.permiteTestDrive = veiculo.getModeloVeiculo().getPermiteTestDrive();
        dto.modeloId = veiculo.getModeloVeiculo().getModelo().getId();

        TipoVeiculo tipoVeiculo = veiculo.getModeloVeiculo().getTipoVeiculo();
        TipoVeiculoDTO tipoVeiculoDTO = TipoVeiculoDTO.create(tipoVeiculo);
        dto.setTipoVeiculo(tipoVeiculoDTO);

        return dto;
    }
}
