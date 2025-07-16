package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.AgendamentoTestDrive;
import com.example.concesswebapi.Model.Entity.Cliente;
import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.api.dto.AgendamentoTestDriveDTO;
import com.example.concesswebapi.api.dto.AgendamentoTestDriveListagemDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.AgendamentoTestDriveService;
import com.example.concesswebapi.service.ClienteService;
import com.example.concesswebapi.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/testdrives")
@RequiredArgsConstructor
public class AgendamentoTestDriveController {

    private final AgendamentoTestDriveService service;
    private final ClienteService clienteService;
    private final VeiculoService veiculoService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<AgendamentoTestDriveListagemDTO>> get() {
        List<AgendamentoTestDrive> agendamentos = service.getAgendamentos();
        List<AgendamentoTestDriveListagemDTO> dtoList = agendamentos.stream()
                .map(AgendamentoTestDriveListagemDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<AgendamentoTestDrive> agendamento = service.getAgendamentoById(id);
        if (agendamento.isEmpty()) {
            return new ResponseEntity<>("Agendamento de Test Drive não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(AgendamentoTestDriveDTO.create(agendamento.get()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody AgendamentoTestDriveDTO dto) {
        try {
            AgendamentoTestDrive agendamento = converter(dto);
            agendamento = service.salvar(agendamento);
            return new ResponseEntity<>(AgendamentoTestDriveListagemDTO.create(agendamento), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AgendamentoTestDriveDTO dto) {
        return service.getAgendamentoById(id).map(agendamentoExistente -> {
            try {
                AgendamentoTestDrive agendamentoAtualizado = converter(dto);
                agendamentoAtualizado.setId(agendamentoExistente.getId());
                service.salvar(agendamentoAtualizado);
                return ResponseEntity.ok(AgendamentoTestDriveDTO.create(agendamentoAtualizado));
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity<>("Agendamento de Test Drive não encontrado", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<AgendamentoTestDrive> agendamento = service.getAgendamentoById(id);
        if (agendamento.isEmpty()) {
            return new ResponseEntity<>("Agendamento de Test Drive não encontrado", HttpStatus.NOT_FOUND);
        }
        service.excluir(agendamento.get());
        return ResponseEntity.noContent().build();
    }

    private AgendamentoTestDrive converter(AgendamentoTestDriveDTO dto) {
        AgendamentoTestDrive agendamento = modelMapper.map(dto, AgendamentoTestDrive.class);

        if (dto.getCpfCliente() == null || dto.getCpfCliente().isEmpty()) {
            throw new RegraNegocioException("CPF do cliente é obrigatório.");
        }
        Cliente cliente = clienteService.getClienteByCpf(dto.getCpfCliente())
                .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado para o CPF informado."));
        agendamento.setCliente(cliente);

        if (dto.getModeloVeiculo() == null || dto.getModeloVeiculo().isEmpty()) {
            throw new RegraNegocioException("Modelo do veículo é obrigatório.");
        }
        Veiculo veiculo = veiculoService.getVeiculoDisponivelPorModelo(dto.getModeloVeiculo())
                .orElseThrow(() -> new RegraNegocioException("Nenhum veículo disponível encontrado para o modelo informado."));
        agendamento.setVeiculo(veiculo);

        return agendamento;
    }
}