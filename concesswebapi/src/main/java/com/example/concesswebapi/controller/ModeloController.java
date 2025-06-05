package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Modelo;
import com.example.concesswebapi.api.dto.AcessorioDTO;
import com.example.concesswebapi.api.dto.ModeloDTO;
import com.example.concesswebapi.service.ModeloService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/modelos")
public class ModeloController {

    private final ModeloService modeloService;
    private final ModelMapper modelMapper = new ModelMapper();

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> listarTodos() {
        return ResponseEntity.ok(modeloService.getModelos());
    }

    private Modelo converter(ModeloDTO dto) {
        return modelMapper.map(dto, Modelo.class);
    }
}
