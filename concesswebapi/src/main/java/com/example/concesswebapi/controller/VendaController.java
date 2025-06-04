package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Venda;
import com.example.concesswebapi.api.dto.VendaDTO;
import com.example.concesswebapi.service.ClienteService;
import com.example.concesswebapi.service.VendaService;
import com.example.concesswebapi.service.VendedorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;
    private final VendedorService vendedorService;
    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<VendaDTO>> getAll() {
        List<Venda> vendas = vendaService.getAll();
        List<VendaDTO> dtos = vendas.stream()
                .map(VendaDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Optional<Venda> venda = vendaService.getById(id);
        if (!venda.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada");
        }
        return ResponseEntity.ok(VendaDTO.create(venda.get()));
    }

    public Venda converter(VendaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Venda.class);
    }
}
