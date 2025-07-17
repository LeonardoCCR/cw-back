package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.AgendamentoTestDrive;
import com.example.concesswebapi.Model.repository.AgendamentoTestDriveRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AgendamentoTestDriveService {

    private final AgendamentoTestDriveRepository repository;

    public AgendamentoTestDriveService(AgendamentoTestDriveRepository repository) {
        this.repository = repository;
    }

    public List<AgendamentoTestDrive> getAgendamentos() {
        return repository.findAll();
    }

    public Optional<AgendamentoTestDrive> getAgendamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public AgendamentoTestDrive salvar(AgendamentoTestDrive agendamento) {
        validar(agendamento);
        return repository.save(agendamento);
    }

    @Transactional
    public void excluir(AgendamentoTestDrive agendamento) {
        Objects.requireNonNull(agendamento.getId());
        repository.delete(agendamento);
    }

    private void validar(AgendamentoTestDrive agendamento) {
        if (agendamento.getCliente() == null) {
            throw new RegraNegocioException("Cliente não informado ou não encontrado.");
        }
        if (agendamento.getVeiculo() == null) {
            throw new RegraNegocioException("Veículo não informado ou não encontrado.");
        }
        if (agendamento.getDataAgendada() == null || agendamento.getDataAgendada().trim().isEmpty()) {
            throw new RegraNegocioException("Data agendada é obrigatória.");
        }
        if (agendamento.getHoraAgendada() == null || agendamento.getHoraAgendada().trim().isEmpty()) {
            throw new RegraNegocioException("Hora agendada é obrigatória.");
        }

        // Nova validação de datas e horas
        if (agendamento.getDataEntregue() != null && !agendamento.getDataEntregue().trim().isEmpty() &&
                agendamento.getHoraEntregue() != null && !agendamento.getHoraEntregue().trim().isEmpty()) {

            try {
                LocalDateTime dataHoraAgendada = LocalDateTime.of(
                        LocalDate.parse(agendamento.getDataAgendada()),
                        LocalTime.parse(agendamento.getHoraAgendada())
                );

                LocalDateTime dataHoraEntregue = LocalDateTime.of(
                        LocalDate.parse(agendamento.getDataEntregue()),
                        LocalTime.parse(agendamento.getHoraEntregue())
                );

                if (dataHoraEntregue.isBefore(dataHoraAgendada)) {
                    throw new RegraNegocioException("A data e hora de entrega não podem ser anteriores à data e hora agendada.");
                }
            } catch (DateTimeParseException e) {
                throw new RegraNegocioException("Formato de data ou hora inválido.");
            }
        }
    }
}