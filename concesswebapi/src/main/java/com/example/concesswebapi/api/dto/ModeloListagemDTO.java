package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Modelo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloListagemDTO {

    private Long id;
    private String nome;
    private String nomeFabricante;

    public static ModeloListagemDTO create(Modelo modelo) {
        ModelMapper modelMapper = new ModelMapper();
        ModeloListagemDTO dto = modelMapper.map(modelo, ModeloListagemDTO.class);
        dto.nomeFabricante = modelo.getFabricante().getNome();

        return dto;
    }
}
