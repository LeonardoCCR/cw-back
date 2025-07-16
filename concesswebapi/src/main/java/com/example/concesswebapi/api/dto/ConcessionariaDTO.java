package com.example.concesswebapi.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcessionariaDTO {
    private String razaoSocial;
    private String cnpj;

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

    private Long empresaId;
}
