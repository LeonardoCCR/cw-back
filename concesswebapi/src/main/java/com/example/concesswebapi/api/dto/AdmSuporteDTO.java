package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.AdmSuporte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmSuporteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;

    private String email1;
    private String email2;
    private String telefone1;
    private String telefone2;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String uf;

    public static AdmSuporteDTO create(AdmSuporte admSuporte){
        AdmSuporteDTO dto = new AdmSuporteDTO();

        dto.setId(admSuporte.getId());
        dto.setEmail1(admSuporte.getEmail1());
        dto.setEmail2(admSuporte.getEmail2());
        dto.setTelefone1(admSuporte.getTelefone1());
        dto.setTelefone2(admSuporte.getTelefone2());
        dto.setLogradouro(admSuporte.getLogradouro());
        dto.setNumero(admSuporte.getNumero());
        dto.setComplemento(admSuporte.getComplemento());
        dto.setBairro(admSuporte.getBairro());
        dto.setCep(admSuporte.getCep());
        dto.setUf(admSuporte.getUf());

        dto.setNome(admSuporte.getNome());
        dto.setCpf(admSuporte.getCpf());
//        dto.setLogin(admSuporte.getLogin());
//        dto.setSenha(admSuporte.getSenha());

        return dto;
    }
}