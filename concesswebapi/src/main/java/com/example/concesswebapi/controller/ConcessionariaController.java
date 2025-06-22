package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.api.dto.ConcessionariaRequestDTO;
import com.example.concesswebapi.api.dto.ConcessionariaResponseDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.ConcessionariaService;
import com.example.concesswebapi.service.EmpresaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/concessionarias")
public class ConcessionariaController {

    private final ConcessionariaService concessionariaService;
    private final EmpresaService empresaService;
    private final ModelMapper modelMapper = new ModelMapper();

    public ConcessionariaController(ConcessionariaService concessionariaService, EmpresaService empresaService) {
        this.concessionariaService = concessionariaService;
        this.empresaService = empresaService;
    }

    @GetMapping
    public ResponseEntity<List<ConcessionariaResponseDTO>> get() {
        List<Concessionaria> lista = concessionariaService.getConcessionaria();
        List<ConcessionariaResponseDTO> dtoList = lista.stream()
                .map(ConcessionariaResponseDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Concessionaria> optional = concessionariaService.getConcessionariaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Concessionaria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ConcessionariaResponseDTO.create(optional.get()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ConcessionariaRequestDTO dto) {
        try {
            Concessionaria concessionaria = converter(dto);
            concessionaria.setId(null);
            concessionariaService.salvar(concessionaria);
            return new ResponseEntity<>(ConcessionariaResponseDTO.create(concessionaria), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ConcessionariaRequestDTO dto) {
        Optional<Concessionaria> optional = concessionariaService.getConcessionariaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Concessionaria não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Concessionaria concessionariaExistente = optional.get();
            Concessionaria atualizada = converter(dto);
            atualizada.setId(concessionariaExistente.getId());
            concessionariaService.salvar(atualizada);
            return ResponseEntity.ok(ConcessionariaResponseDTO.create(atualizada));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Concessionaria> optional = concessionariaService.getConcessionariaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Concessionaria não encontrada", HttpStatus.NOT_FOUND);
        }
        concessionariaService.excluir(optional.get());
        return ResponseEntity.noContent().build();
    }

    private Concessionaria converter(ConcessionariaRequestDTO dto) {
        return modelMapper.map(dto, Concessionaria.class);
    }

}
