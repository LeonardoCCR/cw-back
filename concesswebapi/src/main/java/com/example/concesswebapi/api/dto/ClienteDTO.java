package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static ClienteDTO create(Cliente cliente){
        ClienteDTO dto = new ClienteDTO();

        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setLogin(cliente.getLogin());
        dto.setSenha(cliente.getSenha());
        dto.setDataNascimento(cliente.getDataNascimento());

        dto.setTelefone1(cliente.getTelefone1());
        dto.setTelefone2(cliente.getTelefone2());
        dto.setEmail1(cliente.getEmail1());
        dto.setEmail2(cliente.getEmail2());

        dto.setLogradouro(cliente.getLogradouro());
        dto.setNumero(cliente.getNumero());
        dto.setComplemento(cliente.getComplemento());
        dto.setBairro(cliente.getBairro());
        dto.setCep(cliente.getCep());
        dto.setUf(cliente.getUf());

        return dto;
    }
}