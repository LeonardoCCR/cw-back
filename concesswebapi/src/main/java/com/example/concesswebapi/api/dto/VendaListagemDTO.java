package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaListagemDTO {

    private Long id;
    private String data;
    private String formaPag;
    private String cpfCliente;
    private String modelosVeiculos;
    private BigDecimal valorTotal;

    public static VendaListagemDTO create(Venda venda) {
        VendaListagemDTO dto = new VendaListagemDTO();
        dto.setId(venda.getId());
        dto.setData(venda.getData());
        dto.setFormaPag(venda.getFormaPag());

        if (venda.getCliente() != null) {
            dto.setCpfCliente(venda.getCliente().getCpf());
        }

        if (venda.getItens() != null && !venda.getItens().isEmpty()) {
            String modelos = venda.getItens().stream()
                    .map(item -> item.getModeloVeiculo().getModelo().getNome())
                    .collect(Collectors.joining(", "));
            dto.setModelosVeiculos(modelos);
        } else {
            dto.setModelosVeiculos("N/A");
        }

        dto.setValorTotal(venda.getValorTotal());

        return dto;
    }
}