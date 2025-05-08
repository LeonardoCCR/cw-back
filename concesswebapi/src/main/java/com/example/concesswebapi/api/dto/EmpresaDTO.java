package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {

    private Long id;
    private String razaoSocial;
    private String cnpj;
    private Long idTelefone;
    private String telefone;
    private Long idEmail;
    private String email;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String uf;

    public static EmpresaDTO create(Empresa empresa) {
        ModelMapper modelMapper = new ModelMapper();
        EmpresaDTO dto = modelMapper.map(empresa, EmpresaDTO.class);
        //dto.email = empresa.getEmails().getEmail();
        //dto.telefone = empresa.getTelefone().getNumero();
        return dto;
    }

}
