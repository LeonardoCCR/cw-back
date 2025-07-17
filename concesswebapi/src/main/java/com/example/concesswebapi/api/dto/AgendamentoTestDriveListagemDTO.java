package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.AgendamentoTestDrive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoTestDriveListagemDTO {

    private Long id;
    private String dataAgendada;
    private String horaAgendada;
    private String cpfCliente;
    private String modeloVeiculo;
    private String razaoSocialConcessionaria;

    public static AgendamentoTestDriveListagemDTO create(AgendamentoTestDrive agendamento) {
        ModelMapper modelMapper = new ModelMapper();
        AgendamentoTestDriveListagemDTO dto = modelMapper.map(agendamento, AgendamentoTestDriveListagemDTO.class);

        if (agendamento.getCliente() != null) {
            dto.setCpfCliente(agendamento.getCliente().getCpf());
        }
        if (agendamento.getVeiculo() != null && agendamento.getVeiculo().getModeloVeiculo() != null && agendamento.getVeiculo().getModeloVeiculo().getModelo() != null) {
            dto.setModeloVeiculo(agendamento.getVeiculo().getModeloVeiculo().getModelo().getNome());
        }
        if (agendamento.getVeiculo() != null && agendamento.getVeiculo().getConcessionaria() != null) {
            dto.setRazaoSocialConcessionaria(agendamento.getVeiculo().getConcessionaria().getRazaoSocial());
        }

        return dto;
    }
}