package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.AdmEmpresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmEmpresaDTO {

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
    private String razaoSocialEmpresa;
    private Long empresaId;
    List<Long> concessionariasIds;


    public static AdmEmpresaDTO create(AdmEmpresa admEmpresa){

        ModelMapper modelMapper = new ModelMapper();
        AdmEmpresaDTO dto = modelMapper.map(admEmpresa , AdmEmpresaDTO.class);

        if (admEmpresa.getEmpresa() != null) {
            dto.razaoSocialEmpresa = admEmpresa.getEmpresa().getRazaoSocial();
            dto.empresaId = admEmpresa.getEmpresa().getId();
        }
        return dto;
    }
}
