package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.api.dto.EmpresaRequestDTO;
import com.example.concesswebapi.api.dto.EmpresaResponseDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.EmpresaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    private final EmpresaService service;
    private final ModelMapper modelMapper = new ModelMapper();

    public EmpresaController(EmpresaService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> get() {
        List<Empresa> empresas = service.getEmpresa();
        List<EmpresaResponseDTO> dtoList = empresas.stream()
                .map(EmpresaResponseDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Empresa> empresa = service.getEmpresaById(id);
        if (empresa.isEmpty()) {
            return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(EmpresaResponseDTO.create(empresa.get()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody EmpresaRequestDTO dto) {
        try {
            Empresa empresa = converter(dto);
            empresa = service.salvar(empresa);
            return new ResponseEntity<>(EmpresaResponseDTO.create(empresa), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EmpresaRequestDTO dto) {
        Optional<Empresa> optional = service.getEmpresaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Empresa empresaExistente = optional.get();
            Empresa empresaAtualizada = converter(dto);
            empresaAtualizada.setId(empresaExistente.getId());
            empresaAtualizada = service.salvar(empresaAtualizada);
            return ResponseEntity.ok(EmpresaResponseDTO.create(empresaAtualizada));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Empresa> optional = service.getEmpresaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
        }
        service.excluir(optional.get());
        return ResponseEntity.noContent().build();
    }

    private Empresa converter(EmpresaRequestDTO dto) {
        return modelMapper.map(dto, Empresa.class);
    }
}
