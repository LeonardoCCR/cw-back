package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.*;
import com.example.concesswebapi.api.dto.AcessorioDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.AcessorioService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/acessorios")
@Api("API de acessórios")

public class AcessorioController {

    private final AcessorioService acessorioService;
    private final ModelMapper modelMapper = new ModelMapper();

    public AcessorioController(AcessorioService acessorioService) {
        this.acessorioService = acessorioService;
    }

    @GetMapping
    public ResponseEntity listarTodos() {

        List<Acessorio> acessorios = acessorioService.getAcessorios();
        return ResponseEntity.ok(acessorios.stream().map(AcessorioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Acessorio> acessorio = acessorioService.getAcessorioById(id);
        if (acessorio.isEmpty()) {
            return new ResponseEntity("Acessorio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(acessorio.map(AcessorioDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AcessorioDTO dto) {
        try {
            Acessorio acessorio = converter(dto);
            acessorioService.salvar(acessorio);
            return new ResponseEntity(acessorio, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AcessorioDTO dto) {
        if (acessorioService.getAcessorioById(id).isEmpty()) {
            return new ResponseEntity("Acessório não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Acessorio acessorio = converter(dto);
            acessorio.setId(id);
            acessorioService.salvar(acessorio);
            return ResponseEntity.ok(acessorio);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Acessorio> acessorio = acessorioService.getAcessorioById(id);
        if (acessorio.isEmpty()) {
            return new ResponseEntity("Acessório não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            acessorioService.excluir(acessorio.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Acessorio converter(AcessorioDTO dto) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Acessorio.class);
    }
}
