package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.api.dto.ConcessionariaDTO;
import com.example.concesswebapi.service.ConcessionariaService;
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
@RequestMapping("/api/v1/concessionarias")
@RequiredArgsConstructor
public class ConcessionariaController {

    public final ConcessionariaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Concessionaria> concessionarias = service.getConcessionaria();
        return ResponseEntity.ok(concessionarias.stream().map(ConcessionariaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Concessionaria> concessionaria = service.getConcessionariaById(id);
        if (!concessionaria.isPresent()) {
            return new ResponseEntity("Concessionaria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(concessionaria.map(ConcessionariaDTO::create));
    }

    public Concessionaria converter(ConcessionariaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Concessionaria.class);
    }
}
