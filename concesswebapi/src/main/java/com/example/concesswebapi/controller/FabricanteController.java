package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Fabricante;
import com.example.concesswebapi.Model.Entity.Fabricante;
import com.example.concesswebapi.api.dto.AcessorioDTO;
import com.example.concesswebapi.api.dto.FabricanteDTO;
import com.example.concesswebapi.api.dto.VeiculoDTO;
import com.example.concesswebapi.service.FabricanteService;
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
@RequestMapping("/api/v1/fabricantes")
public class FabricanteController {

    private final FabricanteService fabricanteService;
    private final ModelMapper modelMapper = new ModelMapper();

    public FabricanteController(FabricanteService fabricanteService) {
        this.fabricanteService = fabricanteService;
    }

    @GetMapping
    public ResponseEntity<List<Fabricante>> listarTodos() {
        return ResponseEntity.ok(fabricanteService.getFabricantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fabricante> fabricante = fabricanteService.getFabricanteById(id);
        if (fabricante.isEmpty()) {
            return new ResponseEntity("Fabricante não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fabricante.map(FabricanteDTO::create));
    }

    private Fabricante converter(FabricanteDTO dto) {
        return modelMapper.map(dto, Fabricante.class);
    }
}
