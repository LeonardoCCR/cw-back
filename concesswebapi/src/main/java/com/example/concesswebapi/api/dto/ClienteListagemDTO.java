package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    public static ClienteListagemDTO create(Cliente cliente) {
        ClienteListagemDTO dto = new ClienteListagemDTO();

        dto.setId(cliente.getId());
        dto.setEmail1(cliente.getEmail1());
        dto.setTelefone1(cliente.getTelefone1());
        dto.setLogradouro(cliente.getLogradouro());
        dto.setNumero(cliente.getNumero());
        dto.setComplemento(cliente.getComplemento());
        dto.setBairro(cliente.getBairro());
        dto.setCep(cliente.getCep());
        dto.setUf(cliente.getUf());

        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());

        return dto;
    }
}