package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Venda;
import com.example.concesswebapi.api.dto.VendaRequestDTO;
import com.example.concesswebapi.api.dto.VendaResponseDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.ClienteService;
import com.example.concesswebapi.service.VendaService;
import com.example.concesswebapi.service.VendedorService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final ClienteService clienteService;
    private final VendedorService vendedorService;
    private final ModelMapper modelMapper = new ModelMapper();

    public VendaController(VendaService vendaService, ClienteService clienteService, VendedorService vendedorService) {
        this.vendaService = vendaService;
        this.clienteService = clienteService;
        this.vendedorService = vendedorService;
    }

    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> get() {
        List<Venda> vendas = vendaService.getVendas();
        List<VendaResponseDTO> dtoList = vendas.stream()
                .map(VendaResponseDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Venda> venda = vendaService.getVendaById(id);
        if (venda.isEmpty()) {
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(VendaResponseDTO.create(venda.get()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody VendaRequestDTO dto) {
        try {
            Venda novaVenda = converter(dto);
            vendaService.salvar(novaVenda);
            return new ResponseEntity<>(VendaResponseDTO.create(novaVenda), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody VendaRequestDTO dto) {
        Optional<Venda> optional = vendaService.getVendaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Venda vendaExistente = optional.get();
            Venda atualizada = converter(dto);
            atualizada.setId(vendaExistente.getId());
            vendaService.salvar(atualizada);
            return ResponseEntity.ok(VendaResponseDTO.create(atualizada));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Venda> optional = vendaService.getVendaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        vendaService.excluir(optional.get());
        return ResponseEntity.noContent().build();
    }

    private Venda converter(VendaRequestDTO dto) { return modelMapper.map(dto, Venda.class); }
}
