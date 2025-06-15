package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VeiculoResponseDTO {

    private Long id;
    private String chassi;
    private ArrayList<String> fotos;
    private String nomeModelo;
    private String nomeFabricante;
    private String nomeConcessionaria;
    private BigDecimal precoAtual;
    private String cor;
    private String garantia;
    private String condicao;
    private String anoFabricacao;
    private BigDecimal precoBase;
    private String fotoModelo;
    private Integer qtdEstoqueVenda;
    private Integer qtdEstoque;
    private String permiteTestDrive;
    private Double quilometragem;
    private String documentacao;
    private String manutencao;
    private String laudoVistoria;
    private String dataUltimaRevisao;
    private String numeroProprietario;
    private String sinistroAcidente;
    private String categoria;
    private String motorizacao;
    private Double potencia;
    private String transmissao;
    private String tipoMotor;
    private Integer cilindrada;
    private Integer qtdMarcha;
    private String tipoPartida;

    public static VeiculoResponseDTO create(Veiculo veiculo) {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoResponseDTO dto = modelMapper.map(veiculo, VeiculoResponseDTO.class);
        dto.nomeModelo = veiculo.getModeloVeiculo().getModelo().getNome();
        dto.nomeFabricante = veiculo.getModeloVeiculo().getModelo().getFabricante().getNome();
        dto.nomeConcessionaria = veiculo.getConcessionaria().getRazaoSocial();
        dto.anoFabricacao = veiculo.getModeloVeiculo().getAnoFabricacao();
        dto.precoBase = veiculo.getModeloVeiculo().getPrecoBase();
        dto.fotoModelo = veiculo.getModeloVeiculo().getFotoModelo();
        dto.qtdEstoqueVenda = veiculo.getModeloVeiculo().getQtdEstoqueVenda();
        dto.qtdEstoque = veiculo.getModeloVeiculo().getQtdEstoque();
        dto.permiteTestDrive = veiculo.getModeloVeiculo().getPermiteTestDrive();

        if (veiculo instanceof VeiculoUsado) {
            dto.quilometragem = ((VeiculoUsado) veiculo).getQuilometragem();
            dto.documentacao = ((VeiculoUsado) veiculo).getDocumentacao();
            dto.manutencao = ((VeiculoUsado) veiculo).getManutencao();
            dto.laudoVistoria = ((VeiculoUsado) veiculo).getLaudoVistoria();
            dto.dataUltimaRevisao = ((VeiculoUsado) veiculo).getDataUltimaRevisao();
            dto.sinistroAcidente = ((VeiculoUsado) veiculo).getSinistroAcidente();
        }

        TipoVeiculo tipoVeiculo = veiculo.getModeloVeiculo().getTipoVeiculo();

        if(tipoVeiculo instanceof Carro){
           dto.motorizacao = ((Carro) tipoVeiculo).getMotorizacao();
           dto.potencia = ((Carro) tipoVeiculo).getPotencia();
           dto.transmissao = ((Carro)tipoVeiculo).getTransmissao();
           dto.categoria = ((Carro) tipoVeiculo).getCategoria();
        }
        else if(tipoVeiculo instanceof Moto){
            dto.qtdMarcha = ((Moto) tipoVeiculo).getQtdMarcha();
            dto.tipoPartida = ((Moto) tipoVeiculo).getTipoPartida();
            dto.tipoMotor = ((Moto) tipoVeiculo).getTipoMotor();
            dto.cilindrada = ((Moto) tipoVeiculo).getCilindrada();
            dto.categoria = ((Moto) tipoVeiculo).getCategoria();
        }

        return dto;
    }
}
