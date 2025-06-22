package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Concessionaria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcessionariaResponseDTO {

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
    private String cep;
    private String uf;

    private String razaoSocialEmpresa;

    public static ConcessionariaResponseDTO create(Concessionaria concessionaria) {
        ModelMapper modelMapper = new ModelMapper();
        ConcessionariaResponseDTO dto = modelMapper.map(concessionaria, ConcessionariaResponseDTO.class);
        if (concessionaria.getEmpresa() != null) {
            dto.setRazaoSocialEmpresa(concessionaria.getEmpresa().getRazaoSocial());
        }
        return dto;
    }
}
