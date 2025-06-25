package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.AdmSuporte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmSuporteListagemDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;

    private String email1;
    private String telefone1;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String uf;

    public static AdmSuporteListagemDTO create(AdmSuporte admSuporte){
        ModelMapper modelMapper = new ModelMapper();
        AdmSuporteListagemDTO dto = modelMapper.map( admSuporte, AdmSuporteListagemDTO.class);
        return dto;
    }
}


