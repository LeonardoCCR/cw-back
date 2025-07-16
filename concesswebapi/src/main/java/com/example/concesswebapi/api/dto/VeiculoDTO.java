package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VeiculoDTO {

    private String chassi;
    private ArrayList<String> fotos;
    private Long concessionariaId;
    private BigDecimal precoAtual;
    private String cor;
    private String garantia;
    private String condicao;
    private VeiculoUsadoDTO veiculoUsado;
    private ModeloVeiculoDTO modeloVeiculo;
    //private List<Long> acessoriosIds;

    public Veiculo converter(VeiculoDTO dto) {

        Veiculo veiculo;

        if (dto.getVeiculoUsado() == null) {
            veiculo = new VeiculoNovo();
        } else {
            veiculo = new VeiculoUsado();
        }

        veiculo.setChassi(dto.getChassi());
        veiculo.setPrecoAtual(dto.getPrecoAtual());
        veiculo.setCor(dto.getCor());
        veiculo.setCondicao(dto.getCondicao());
        veiculo.setVendido(false);
        veiculo.setGarantia(dto.getGarantia());
        veiculo.setFotos(dto.getFotos());

        return veiculo;
    }

    public static VeiculoDTO create(Veiculo veiculo) {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoDTO dto = modelMapper.map(veiculo, VeiculoDTO.class);

        dto.concessionariaId = veiculo.getConcessionaria().getId();

        if (veiculo instanceof VeiculoUsado) {
           VeiculoUsadoDTO veiculoUsadoDTO = VeiculoUsadoDTO.create((VeiculoUsado) veiculo);
            dto.setVeiculoUsado(veiculoUsadoDTO);
        }

        ModeloVeiculoDTO modeloVeiculoDTO = ModeloVeiculoDTO.create(veiculo);
        dto.setModeloVeiculo(modeloVeiculoDTO);

        return dto;
    }
}
