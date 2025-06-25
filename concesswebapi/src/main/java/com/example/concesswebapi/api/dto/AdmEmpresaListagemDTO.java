package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.AdmEmpresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmEmpresaListagemDTO {

    private Long id;
    private String nome;
    private String cpf;

    private String email1; //principais
    private String telefone1;


    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String uf;

    public static AdmEmpresaListagemDTO create(AdmEmpresa admEmpresa){

        ModelMapper modelMapper = new ModelMapper();
        AdmEmpresaListagemDTO dto = modelMapper.map(admEmpresa , AdmEmpresaListagemDTO.class);
        return dto;
    }
}
