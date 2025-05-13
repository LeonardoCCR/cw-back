package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AgendamentoTestDriveDTO {

    private long id;
    private String dataAgendada;
    private String horaAgendada;
    private String dataEntegue;
    private String horaEntregue;

    private String modeloVeiculo;
    private String cpfCliente;

    public static AgendamentoTestDriveDTO create(AgendamentoTestDrive agendamentoTestDrive) {
        ModelMapper modelMapper = new ModelMapper();
        AgendamentoTestDriveDTO dto = modelMapper.map(agendamentoTestDrive, AgendamentoTestDriveDTO.class);
        dto.modeloVeiculo = agendamentoTestDrive.getVeiculo().getModeloVeiculo().getModelo().getNome();
        dto.cpfCliente = agendamentoTestDrive.getCliente().getCpf();

        return dto;
    }
}
