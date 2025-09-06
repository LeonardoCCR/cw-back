package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.ItensVenda;
import com.example.concesswebapi.Model.Entity.ModeloVeiculo;
import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.Venda;
import com.example.concesswebapi.api.dto.ItensVendaDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.ItensVendaService;
import com.example.concesswebapi.service.ModeloVeiculoService;
import com.example.concesswebapi.service.VeiculoService;
import com.example.concesswebapi.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/itens-venda")
@RequiredArgsConstructor
public class ItensVendaController {

    private final ItensVendaService service;
    private final VendaService vendaService;
    private final ModeloVeiculoService modeloVeiculoService;
    private final VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<List<ItensVendaDTO>> get() {
        List<ItensVenda> itens = service.getItensVenda();
        List<ItensVendaDTO> dtoList = itens.stream().map(this::converter).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<ItensVenda> itemVenda = service.getItemVendaById(id);
        if (itemVenda.isEmpty()) {
            return new ResponseEntity<>("Item de Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(converter(itemVenda.get()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ItensVendaDTO dto) {
        try {
            ItensVenda itemVenda = converter(dto);
            service.salvar(itemVenda);
            return new ResponseEntity<>(converter(itemVenda), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody ItensVendaDTO dto) {
        return service.getItemVendaById(id).map(entity -> {
            try {
                ItensVenda itemVenda = converter(dto);
                itemVenda.setId(entity.getId());
                service.salvar(itemVenda);
                return ResponseEntity.ok(converter(itemVenda));
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity<>("Item de Venda não encontrado", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<ItensVenda> itemVenda = service.getItemVendaById(id);
        if (itemVenda.isEmpty()) {
            return new ResponseEntity<>("Item de Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        service.excluir(itemVenda.get());
        return ResponseEntity.noContent().build();
    }

    private ItensVenda converter(ItensVendaDTO dto) {
        Venda venda = vendaService.getVendaById(dto.getVendaId())
                .orElseThrow(() -> new RegraNegocioException("Venda não encontrada."));
        ModeloVeiculo modelo = modeloVeiculoService.getModeloVeiculoById(dto.getModeloVeiculoId())
                .orElseThrow(() -> new RegraNegocioException("Modelo de veículo não encontrado."));
        Veiculo veiculo = veiculoService.getVeiculoById(dto.getVeiculoId())
                .orElseThrow(() -> new RegraNegocioException("Veículo não encontrado."));

        ItensVenda itemVenda = new ItensVenda();
        itemVenda.setId(dto.getId());
        itemVenda.setVenda(venda);
        itemVenda.setModeloVeiculo(modelo);
        itemVenda.setVeiculo(veiculo);
        itemVenda.setDescontoParcial(dto.getDescontoParcial());

        return itemVenda;
    }

    private ItensVendaDTO converter(ItensVenda item) {
        return new ItensVendaDTO(
                item.getId(),
                item.getModeloVeiculo().getModelo().getId(),
                item.getVeiculo().getId(),
                item.getVenda().getId(),
                item.getDescontoParcial()
        );
    }
}