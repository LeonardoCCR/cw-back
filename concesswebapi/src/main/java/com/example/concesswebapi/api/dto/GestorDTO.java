package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String complemento;
    private String bairro;
    private String cep;
    private String uf;
    private String cargo;
    private Long idEmpresa;

    public static GestorDTO create( Gestor gestor){
        GestorDTO dto = new GestorDTO();

        dto.setId(gestor.getId());
        dto.setNome(gestor.getNome());
        dto.setCpf(gestor.getCpf());
        //dto.setLogin(gestor.getLogin());
        //dto.setSenha(gestor.getSenha());

        dto.setTelefone1(gestor.getTelefone1());
        dto.setTelefone2(gestor.getTelefone2());
        dto.setEmail1(gestor.getEmail1());
        dto.setEmail2(gestor.getEmail2());

        dto.setLogradouro(gestor.getLogradouro());
        dto.setNumero(gestor.getNumero());
        dto.setComplemento(gestor.getComplemento());
        dto.setBairro(gestor.getBairro());
        dto.setCep(gestor.getCep());
        dto.setUf(gestor.getUf());

        dto.setCargo(gestor.getCargo());

        if (gestor.getEmpresa() != null) {
            dto.setIdEmpresa(gestor.getEmpresa().getId());
        }

        return dto;
    }
}