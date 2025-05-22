package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VeiculoDTO {

    private Long id;
    private String chassi;
    private ArrayList<String> fotos;
    private String nomeModelo;
    private String nomeFabricante;
    private String nomeConcessionaria;
    private float precoAtual;
    private String cor;
    private String garantia;
    private Double quilometragem;
    private String documentacao;
    private String manutencao;
    private String laudoVistoria;
    private String dataUltimaRevisao;
    private String categoria;
    private String motorizacao;
    private Double potencia;
    private String transmissao;
    private String tipoMotor;
    private float cilindrada;
    private Integer qtdMarcha;
    private String tipoPartida;
    private String sinistroAcidente;

    public static VeiculoDTO create(Veiculo veiculo) {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoDTO dto = modelMapper.map(veiculo, VeiculoDTO.class);
        dto.nomeModelo = veiculo.getModeloVeiculo().getModelo().getNome();
        dto.nomeFabricante = veiculo.getModeloVeiculo().getModelo().getFabricante().getNome();
        dto.nomeConcessionaria = veiculo.getConcessionaria().getRazaoSocial();
        dto.categoria = veiculo.getModeloVeiculo().getTipoVeiculo().getCategoria();

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
        }
        else if(tipoVeiculo instanceof Moto){
            dto.qtdMarcha = ((Moto) tipoVeiculo).getQtdMarcha();
            dto.tipoPartida = ((Moto) tipoVeiculo).getTipoPartida();
            dto.tipoMotor = ((Moto) tipoVeiculo).getTipoMotor();
            dto.cilindrada = ((Moto) tipoVeiculo).getCilindrada();
        }

        return dto;
    }
}
