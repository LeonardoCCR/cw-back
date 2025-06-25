package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Gestor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GestorListagemDTO {

    private Long id;
    private String nome;
    private String cpf;

    private String telefone1;
    private String email1;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String uf;

    public static GestorListagemDTO create(Gestor gestor){

        ModelMapper modelMapper = new ModelMapper();
        GestorListagemDTO dto = modelMapper.map(gestor, GestorListagemDTO.class);
        return dto;
    }
}