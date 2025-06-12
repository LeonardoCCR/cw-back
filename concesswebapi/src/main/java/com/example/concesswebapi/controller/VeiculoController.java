package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.*;
import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.api.dto.VeiculoDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.VeiculoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final ModelMapper modelMapper = new ModelMapper();

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Veiculo> veiculo = veiculoService.getVeiculoById(id);
        if (veiculo.isEmpty()) {
            return new ResponseEntity("Veiculo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(veiculo.map(VeiculoDTO::create));
    }

    private Veiculo converter(VeiculoDTO dto) {
        if ("novo".equalsIgnoreCase(dto.getCondicao())) {
            return modelMapper.map(dto, VeiculoNovo.class);
        } else if ("usado".equalsIgnoreCase(dto.getCondicao())) {
            return modelMapper.map(dto, VeiculoUsado.class);
        } else {
            throw new RegraNegocioException("Condição de veículo inválida: " + dto.getCondicao());
        }
    }
}
