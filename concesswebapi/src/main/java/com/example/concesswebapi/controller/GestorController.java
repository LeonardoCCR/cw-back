package com.example.concesswebapi.controller;



import com.example.concesswebapi.Model.Entity.Gestor;
import com.example.concesswebapi.api.dto.GestorDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.GestorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    @GetMapping
    public ResponseEntity get() {
        List<Gestor> lista = service.getGestores();
        return ResponseEntity.ok(lista.stream().map(GestorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Gestor> entidade = service.getGestorById(id);
        if (!entidade.isPresent()) {
            return new ResponseEntity("Gestor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(entidade.map(GestorDTO::create));
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, GestorDTO dto) {
        if (!service.getGestorById(id).isPresent()) {
            return new ResponseEntity("Gestor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Gestor gestor = converter(dto);
            gestor.setId(id);
            service.salvar(gestor);
            return ResponseEntity.ok(gestor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Gestor converter(GestorDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Gestor.class);
    }
}
