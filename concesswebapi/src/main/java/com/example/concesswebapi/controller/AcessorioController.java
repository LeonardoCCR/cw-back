package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.*;
import com.example.concesswebapi.api.dto.AcessorioDTO;
import com.example.concesswebapi.api.dto.VeiculoDTO;
import com.example.concesswebapi.service.AcessorioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/acessorios")
public class AcessorioController {

    private final AcessorioService acessorioService;
    private final ModelMapper modelMapper = new ModelMapper();

    public AcessorioController(AcessorioService acessorioService) {
        this.acessorioService = acessorioService;
    }

    @GetMapping
    public ResponseEntity<List<Acessorio>> listarTodos() {
        return ResponseEntity.ok(acessorioService.getAcessorios());
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Acessorio> acessorio = acessorioService.getAcessorioById(id);
        if (acessorio.isEmpty()) {
            return new ResponseEntity("Acessorio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(acessorio.map(AcessorioDTO::create));
    }

    private Acessorio converter(AcessorioDTO dto) {
        return modelMapper.map(dto, Acessorio.class);
    }
}
