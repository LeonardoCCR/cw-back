package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.api.dto.EmpresaDTO;
import com.example.concesswebapi.api.dto.EmpresaListagemDTO;
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
    public ResponseEntity<List<EmpresaListagemDTO>> get() {
        List<Empresa> empresas = service.getEmpresa();
        List<EmpresaListagemDTO> dtoList = empresas.stream()
                .map(EmpresaListagemDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Empresa> empresa = service.getEmpresaById(id);
        if (empresa.isEmpty()) {
            return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(EmpresaListagemDTO.create(empresa.get()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody EmpresaDTO dto) {
        try {
            Empresa empresa = converter(dto);
            empresa = service.salvar(empresa);
            return new ResponseEntity<>(EmpresaListagemDTO.create(empresa), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EmpresaDTO dto) {
        Optional<Empresa> optional = service.getEmpresaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Empresa empresaExistente = optional.get();
            Empresa empresaAtualizada = converter(dto);
            empresaAtualizada.setId(empresaExistente.getId());
            empresaAtualizada = service.salvar(empresaAtualizada);
            return ResponseEntity.ok(EmpresaListagemDTO.create(empresaAtualizada));
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

    private Empresa converter(EmpresaDTO dto) {
        return modelMapper.map(dto, Empresa.class);
    }
}