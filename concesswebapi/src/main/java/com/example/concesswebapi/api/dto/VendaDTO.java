package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VendaDTO {

    private Long id;
    private String data;
    private String formaPag;
    private String descontoTotal;
    private String aprovada;
    private String cpfCliente;
    //private String nomeModelo;
    //private String chassi;
    //private String nomeConcessionaria;

    public static VendaDTO create (Venda venda) {
        ModelMapper modelMapper = new ModelMapper();
        VendaDTO dto = modelMapper.map(venda, VendaDTO.class);
        dto.cpfCliente = venda.getCliente().getCpf();
        //dto.nomeModelo
        //dto.chassi
        //dto.nomeConcessionaria

        return dto;
    }

}
