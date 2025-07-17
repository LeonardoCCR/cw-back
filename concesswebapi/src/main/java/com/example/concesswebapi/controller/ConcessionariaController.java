package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.api.dto.ConcessionariaDTO;
import com.example.concesswebapi.api.dto.ConcessionariaListagemDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.ConcessionariaService;
import com.example.concesswebapi.service.EmpresaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/concessionarias")
public class ConcessionariaController {

    private final ConcessionariaService concessionariaService;
    private final EmpresaService empresaService;
    private final ModelMapper modelMapper;

    public ConcessionariaController(ConcessionariaService concessionariaService, EmpresaService empresaService) {
        this.concessionariaService = concessionariaService;
        this.empresaService = empresaService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping
    public ResponseEntity<List<ConcessionariaListagemDTO>> get() {
        List<Concessionaria> lista = concessionariaService.getConcessionaria();
        List<ConcessionariaListagemDTO> dtoList = lista.stream()
                .map(ConcessionariaListagemDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Concessionaria> optional = concessionariaService.getConcessionariaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Concessionária não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ConcessionariaListagemDTO.create(optional.get()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ConcessionariaDTO dto) {
        try {
            Concessionaria concessionaria = converter(dto);
            concessionaria.setId(null);
            concessionariaService.salvar(concessionaria);
            return new ResponseEntity<>(ConcessionariaListagemDTO.create(concessionaria), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ConcessionariaDTO dto) {
        if (concessionariaService.getConcessionariaById(id).isEmpty()) {
            return new ResponseEntity<>("Concessionária não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Concessionaria concessionariaAtualizada = converter(dto);
            concessionariaAtualizada.setId(id);
            concessionariaService.salvar(concessionariaAtualizada);
            return ResponseEntity.ok(ConcessionariaListagemDTO.create(concessionariaAtualizada));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Concessionaria> optional = concessionariaService.getConcessionariaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Concessionária não encontrada", HttpStatus.NOT_FOUND);
        }
        concessionariaService.excluir(optional.get());
        return ResponseEntity.noContent().build();
    }

    private Concessionaria converter(ConcessionariaDTO dto) {
        Concessionaria concessionaria = modelMapper.map(dto, Concessionaria.class);

        if (dto.getEmpresaId() == null) {
            throw new RegraNegocioException("A empresa matriz é obrigatória.");
        }

        Optional<Empresa> empresaOptional = empresaService.getEmpresaById(dto.getEmpresaId());
        if (empresaOptional.isEmpty()) {
            throw new RegraNegocioException("Empresa não encontrada para o ID informado.");
        }

        concessionaria.setEmpresa(empresaOptional.get());

        return concessionaria;
    }
}