package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Modelo;
import com.example.concesswebapi.api.dto.ModeloResponseDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.ModeloService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/modelos")
public class ModeloController {

    private final ModeloService modeloService;
    private final ModelMapper modelMapper = new ModelMapper();

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @GetMapping
    public ResponseEntity listarTodos() {

        List<Modelo> modelos = modeloService.getModelos();
        return ResponseEntity.ok(modelos.stream().map(ModeloResponseDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Modelo> modelo = modeloService.getModeloById(id);
        if (modelo.isEmpty()) {
            return new ResponseEntity("Modelo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelo.map(ModeloResponseDTO::create));
    }

    private Modelo converter(ModeloResponseDTO dto) {
        return modelMapper.map(dto, Modelo.class);
    }
}
