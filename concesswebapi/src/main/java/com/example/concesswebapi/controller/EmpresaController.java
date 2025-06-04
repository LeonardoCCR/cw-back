package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.api.dto.EmpresaDTO;
import com.example.concesswebapi.service.EmpresaService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    public final EmpresaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Empresa> empresas = service.getEmpresa();
        return ResponseEntity.ok(empresas.stream().map(EmpresaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Empresa> empresa = service.getEmpresaById(id);
        if (!empresa.isPresent()) {
            return new ResponseEntity("Aluno não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(empresa.map(EmpresaDTO::create));
    }

    public Empresa converter(EmpresaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Empresa.class);
    }

}
