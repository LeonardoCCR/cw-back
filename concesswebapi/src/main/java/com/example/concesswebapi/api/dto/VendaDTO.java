package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Venda;
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
    private String nomeCliente;

    private Long idVendedor;
    private String nomeVendedor;

    public static VendaDTO create(Venda venda) {
        ModelMapper modelMapper = new ModelMapper();
        VendaDTO dto = modelMapper.map(venda, VendaDTO.class);

        if (venda.getCliente() != null) {
            dto.cpfCliente = venda.getCliente().getCpf();
            dto.nomeCliente = venda.getCliente().getNome();
        }

        if (venda.getVendedor() != null) {
            dto.idVendedor = venda.getVendedor().getId();
            dto.nomeVendedor = venda.getVendedor().getNome();
        }

        dto.descontoTotal = venda.getDescontoTotal() != null
                ? venda.getDescontoTotal().toString()
                : "0.0";

        return dto;
    }
}