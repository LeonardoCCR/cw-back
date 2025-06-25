package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendedorListagemDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String telefone1;

    private String email1;
    private String concessionaria;

    public static VendedorListagemDTO create(Vendedor vendedor){

        ModelMapper modelMapper = new ModelMapper();
        VendedorListagemDTO dto = modelMapper.map(vendedor, VendedorListagemDTO.class);
        dto.concessionaria = vendedor.getConcessionaria().getRazaoSocial();
        return dto;
    }
}
