package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.AdmEmpresa;
import com.example.concesswebapi.Model.Entity.Concessionaria;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

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


    public static ConcessionariaDTO create(Concessionaria concessionaria) {
        //create parcial, pois falta o admsEmpresaIds

        ModelMapper modelMapper = new ModelMapper();
        ConcessionariaDTO dto = modelMapper.map(concessionaria, ConcessionariaDTO.class);
        dto.empresaId = concessionaria.getEmpresa().getId();
        return dto;
    }

}
