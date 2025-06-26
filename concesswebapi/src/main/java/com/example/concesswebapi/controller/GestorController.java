package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Gestor;
import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.api.dto.GestorDTO;
import com.example.concesswebapi.api.dto.GestorListagemDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.GestorService;
import com.example.concesswebapi.service.EmpresaService; // Adicionado para resolver Empresa, se necessário
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/gestores")
@RequiredArgsConstructor
public class GestorController {

    public final GestorService service;
    public final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity get() {
        List<Gestor> lista = service.getGestores();
        return ResponseEntity.ok(lista.stream().map(GestorListagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Gestor> entidade = service.getGestorById(id);
        if (!entidade.isPresent()) {
            return new ResponseEntity("Gestor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(entidade.map(GestorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody GestorDTO dto) {

        try {
            Gestor gestor = converter(dto);
            service.salvar(gestor);
            return new ResponseEntity(gestor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody GestorDTO dto) {
        if (!service.getGestorById(id).isPresent()) {
            return new ResponseEntity("Gestor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Gestor gestor = converter(dto);
            gestor.setId(id);
            if (gestor.getEmpresa() != null && gestor.getEmpresa().getId() == null) {
                Empresa empresaSalva = empresaService.salvar(gestor.getEmpresa());
                gestor.setEmpresa(empresaSalva);
            }
            service.salvar(gestor);
            return ResponseEntity.ok(gestor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Gestor> optional = service.getGestorById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Gestor não encontrado", HttpStatus.NOT_FOUND);
        }
        service.excluir(optional.get());
        return ResponseEntity.noContent().build();
    }

    public Gestor converter(GestorDTO dto){
        Gestor gestor = new Gestor();

        gestor.setEmail1(dto.getEmail1());
        gestor.setEmail2(dto.getEmail2());
        gestor.setTelefone1(dto.getTelefone1());
        gestor.setTelefone2(dto.getTelefone2());
        gestor.setLogradouro(dto.getLogradouro());
        gestor.setNumero(dto.getNumero());
        gestor.setComplemento(dto.getComplemento());
        gestor.setBairro(dto.getBairro());
        gestor.setCep(dto.getCep());
        gestor.setUf(dto.getUf());

        gestor.setNome(dto.getNome());
        gestor.setCpf(dto.getCpf());
        gestor.setLogin(dto.getLogin());
        gestor.setSenha(dto.getSenha());

        gestor.setCargo(dto.getCargo());

        if (dto.getIdEmpresa() != null) {
            Optional<Empresa> empresa = empresaService.getEmpresaById(dto.getIdEmpresa());
            empresa.ifPresent(gestor::setEmpresa);
        } else {
            gestor.setEmpresa(null);
        }

        return gestor;
    }
}