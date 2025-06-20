package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;
    private String dataNascimento;

    private String telefone1;
    private String telefone2;
    private String email1;
    private String email2;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String uf;

    public static ClienteDTO create( Cliente cliente){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cliente , ClienteDTO.class);
        //retorna o dto de cliente sem precisar intanciar uma variável dto
    }
}
