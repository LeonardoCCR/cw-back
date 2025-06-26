package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Acessorio;
import com.example.concesswebapi.Model.Entity.Fabricante;
import com.example.concesswebapi.api.dto.FabricanteDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.FabricanteService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fabricantes")
public class FabricanteController {

    private final FabricanteService fabricanteService;
    private final ModelMapper modelMapper = new ModelMapper();

    public FabricanteController(FabricanteService fabricanteService) {
        this.fabricanteService = fabricanteService;
    }

    @GetMapping
    public ResponseEntity listarTodos() {
        List<Fabricante> fabricantes = fabricanteService.getFabricantes();
        return ResponseEntity.ok(fabricantes.stream().map(FabricanteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fabricante> fabricante = fabricanteService.getFabricanteById(id);
        if (fabricante.isEmpty()) {
            return new ResponseEntity("Fabricante não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fabricante.map(FabricanteDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody FabricanteDTO dto) {
        try {
            Fabricante fabricante = converter(dto);
            fabricanteService.salvar(fabricante);
            return new ResponseEntity(fabricante, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FabricanteDTO dto) {
        if (fabricanteService.getFabricanteById(id).isEmpty()) {
            return new ResponseEntity("Fabricante não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Fabricante fabricante = converter(dto);
            fabricante.setId(id);
            fabricanteService.salvar(fabricante);
            return ResponseEntity.ok(fabricante);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Fabricante converter(FabricanteDTO dto) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Fabricante.class);
    }
}
