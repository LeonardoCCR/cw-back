package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaListagemDTO {

    private Long id;
    private String data;
    private String formaPag;
    private String desconto;
    private String aprovada;
    private String nomeCliente;
    private String cpfCliente;
    private String nomeVendedor;
    private String cpfVendedor;

    public static VendaListagemDTO create(Venda venda) {
        VendaListagemDTO dto = new VendaListagemDTO();

        dto.setId(venda.getId());
        dto.setData(venda.getData());
        dto.setFormaPag(venda.getFormaPag());
        dto.setDesconto(venda.getDescontoTotal() != null ? venda.getDescontoTotal().toString() : "0.0");
        dto.setAprovada(venda.getAprovada());

        if (venda.getCliente() != null) {
            dto.setNomeCliente(venda.getCliente().getNome());
            dto.setCpfCliente(venda.getCliente().getCpf());
        }

        if (venda.getVendedor() != null) {
            dto.setNomeVendedor(venda.getVendedor().getNome());
            dto.setCpfVendedor(venda.getVendedor().getCpf());
        }

        return dto;
    }
}

