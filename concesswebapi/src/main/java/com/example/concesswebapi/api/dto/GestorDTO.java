package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GestorDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;

    private String telefone1;
    private String telefone2;
    private String email1;
    private String email2;

    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String uf;

    public static GestorDTO create( Gestor gestor){

        ModelMapper modelMapper = new ModelMapper();
        GestorDTO dto = modelMapper.map(gestor, GestorDTO.class);
        return dto;
    }
}
