package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.AgendamentoTestDrive;
import com.example.concesswebapi.Model.Entity.Cliente;
import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import com.example.concesswebapi.Model.repository.AgendamentoTestDriveRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoTestDriveServiceTest {

    @InjectMocks
    private AgendamentoTestDriveService service;

    @Mock
    private AgendamentoTestDriveRepository repository;

    private AgendamentoTestDrive agendamento;

    @BeforeEach
    void setUp() {
        agendamento = new AgendamentoTestDrive();
        agendamento.setCliente(new Cliente());

        VeiculoUsado veiculoConcreto = new VeiculoUsado();
        agendamento.setVeiculo(veiculoConcreto);

        agendamento.setDataAgendada("2023-12-25");
        agendamento.setHoraAgendada("10:00");
    }

    @Test
    @DisplayName("Deve validar com sucesso quando apenas dados de agendamento estão presentes")
    void deveValidarAgendamentoSimples() {
        assertDoesNotThrow(() -> service.validar(agendamento));
    }

    @Test
    @DisplayName("Deve validar com sucesso quando entrega ocorre após agendamento")
    void deveValidarAgendamentoComEntregaValida() {
        agendamento.setDataEntregue("2023-12-25");
        agendamento.setHoraEntregue("12:00");

        assertDoesNotThrow(() -> service.validar(agendamento));
    }

    @Test
    @DisplayName("Deve lançar erro quando Cliente é nulo")
    void deveFalharClienteNull() {
        agendamento.setCliente(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(agendamento));
        assertEquals("Cliente não informado ou não encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando Veículo é nulo")
    void deveFalharVeiculoNull() {
        agendamento.setVeiculo(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(agendamento));
        assertEquals("Veículo não informado ou não encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando Data Agendada é vazia")
    void deveFalharDataVazia() {
        agendamento.setDataAgendada("");
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(agendamento));
        assertEquals("Data agendada é obrigatória.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando Data de Entrega é ANTERIOR à Data Agendada")
    void deveFalharEntregaAnteriorAoAgendamento() {
        agendamento.setDataAgendada("2023-12-25");
        agendamento.setHoraAgendada("14:00");

        agendamento.setDataEntregue("2023-12-25");
        agendamento.setHoraEntregue("09:00");

        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(agendamento));
        assertEquals("A data e hora de entrega não podem ser anteriores à data e hora agendada.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando formato da data é inválido")
    void deveFalharFormatoDataInvalido() {
        agendamento.setDataEntregue("DATA_DOIDA");
        agendamento.setHoraEntregue("10:00");

        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(agendamento));
        assertEquals("Formato de data ou hora inválido.", ex.getMessage());
    }
}