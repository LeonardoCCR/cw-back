package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Cliente;
import org.modelmapper.ModelMapper;

public class ClienteListagemDTO {

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

    public static ClienteListagemDTO create(Cliente cliente){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cliente , ClienteListagemDTO.class);
        //retorna o dto de cliente sem precisar instanciar uma variável dto
    }
}
























