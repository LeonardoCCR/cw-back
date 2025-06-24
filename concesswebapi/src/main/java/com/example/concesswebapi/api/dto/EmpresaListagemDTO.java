package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaListagemDTO {

    private Long id;
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
    private String uf;
    private String cep;

    public static EmpresaListagemDTO create(Empresa empresa) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(empresa, EmpresaListagemDTO.class);
    }
}
