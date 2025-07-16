package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.ItensVenda;
import com.example.concesswebapi.Model.Entity.Venda;
import com.example.concesswebapi.api.dto.ItensVendaDTO;
import com.example.concesswebapi.api.dto.VendaDTO;
import com.example.concesswebapi.api.dto.VendaListagemDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final ClienteService clienteService;
    private final VendedorService vendedorService;
    private final ModeloVeiculoService modeloVeiculoService;
    private final VeiculoService veiculoService;

    public VendaController(VendaService vendaService, ClienteService clienteService,
                           VendedorService vendedorService, ModeloVeiculoService modeloVeiculoService,
                           VeiculoService veiculoService) {
        this.vendaService = vendaService;
        this.clienteService = clienteService;
        this.vendedorService = vendedorService;
        this.modeloVeiculoService = modeloVeiculoService;
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<VendaListagemDTO>> get() {
        List<Venda> vendas = vendaService.getVendas();
        List<VendaListagemDTO> dtoList = vendas.stream()
                .map(VendaListagemDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Venda> optionalVenda = vendaService.getVendaById(id);
        if (optionalVenda.isEmpty()) {
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(converterParaDTO(optionalVenda.get()));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> post(@RequestBody VendaDTO dto) {
        try {
            Venda venda = converterDeDTO(dto);
            venda = vendaService.salvar(venda);
            return new ResponseEntity<>(converterParaDTO(venda), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody VendaDTO dto) {
        return vendaService.getVendaById(id).map(vendaExistente -> {
            try {
                atualizarVendaDeDTO(vendaExistente, dto);
                Venda vendaSalva = vendaService.salvar(vendaExistente);
                return ResponseEntity.ok(converterParaDTO(vendaSalva));
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Venda> venda = vendaService.getVendaById(id);
        if (venda.isEmpty()) {
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        vendaService.excluir(venda.get());
        return ResponseEntity.noContent().build();
    }

    private VendaDTO converterParaDTO(Venda venda) {
        VendaDTO dto = new VendaDTO();
        dto.setId(venda.getId());
        dto.setData(venda.getData());
        dto.setFormaPag(venda.getFormaPag());
        dto.setClienteId(venda.getCliente().getId());
        dto.setVendedorId(venda.getVendedor().getId());

        List<ItensVendaDTO> itensDTO = venda.getItens().stream().map(item -> {
            ItensVendaDTO itemDTO = new ItensVendaDTO();
            itemDTO.setId(item.getId());
            itemDTO.setModeloVeiculoId(item.getModeloVeiculo().getId());
            itemDTO.setVeiculoId(item.getVeiculo().getId());
            itemDTO.setDescontoParcial(item.getDescontoParcial());
            return itemDTO;
        }).collect(Collectors.toList());

        dto.setItens(itensDTO);
        return dto;
    }

    private Venda converterDeDTO(VendaDTO dto) {
        Venda venda = new Venda();
        venda.setItens(new ArrayList<>());
        venda.setAprovada("não");
        atualizarVendaDeDTO(venda, dto);
        return venda;
    }

    private void atualizarVendaDeDTO(Venda venda, VendaDTO dto) {
        venda.setData(dto.getData());
        venda.setFormaPag(dto.getFormaPag());
        venda.setCliente(clienteService.getClienteById(dto.getClienteId())
                .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado.")));
        venda.setVendedor(vendedorService.getVendedorById(dto.getVendedorId())
                .orElseThrow(() -> new RegraNegocioException("Vendedor não encontrado.")));

        Map<Long, ItensVenda> itensExistentesMap = venda.getItens().stream()
                .collect(Collectors.toMap(ItensVenda::getId, item -> item));

        List<ItensVenda> itensAtualizados = new ArrayList<>();

        if (dto.getItens() != null) {
            for (ItensVendaDTO itemDto : dto.getItens()) {
                ItensVenda item;
                if (itemDto.getId() != null && itensExistentesMap.containsKey(itemDto.getId())) {
                    item = itensExistentesMap.get(itemDto.getId());
                } else {
                    item = new ItensVenda();
                    item.setVenda(venda);
                }

                item.setDescontoParcial(itemDto.getDescontoParcial() != null ? itemDto.getDescontoParcial() : BigDecimal.ZERO);
                item.setModeloVeiculo(modeloVeiculoService.getModeloVeiculoById(itemDto.getModeloVeiculoId())
                        .orElseThrow(() -> new RegraNegocioException("Modelo de veículo não encontrado: " + itemDto.getModeloVeiculoId())));
                item.setVeiculo(veiculoService.getVeiculoById(itemDto.getVeiculoId())
                        .orElseThrow(() -> new RegraNegocioException("Veículo (chassi) não encontrado: " + itemDto.getVeiculoId())));

                itensAtualizados.add(item);
            }
        }

        venda.getItens().clear();
        venda.getItens().addAll(itensAtualizados);
    }
}