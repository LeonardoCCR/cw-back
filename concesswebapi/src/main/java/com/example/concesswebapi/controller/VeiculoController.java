package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoNovo;
import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import com.example.concesswebapi.api.dto.VeiculoDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.VeiculoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
