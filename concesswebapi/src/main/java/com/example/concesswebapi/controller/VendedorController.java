package com.example.concesswebapi.controller;


import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.Model.Entity.Vendedor;
import com.example.concesswebapi.api.dto.VendedorDTO;
import com.example.concesswebapi.service.ConcessionariaService;
import com.example.concesswebapi.service.VendedorService;
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
@RequestMapping("/api/v1/vendedores")
@RequiredArgsConstructor
public class VendedorController {

    public final VendedorService service;
    public final ConcessionariaService concessionariaService;

    @GetMapping
    public ResponseEntity get() {
        List<Vendedor> lista = service.getVendedores();
        return ResponseEntity.ok(lista.stream().map(VendedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Vendedor> entidade = service.getVendedorById(id);
        if (!entidade.isPresent()) {
            return new ResponseEntity("Vendedor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(entidade.map(VendedorDTO::create));
    }

    public Vendedor converter(VendedorDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        Vendedor vendedor = modelMapper.map(dto, Vendedor.class);


        if(dto.getIdConcessionaria() != null){
            Optional<Concessionaria> concessionaria = concessionariaService.getConcessionariaById(dto.getIdConcessionaria());

            if(!concessionaria.isPresent()){ //método do Optional
                vendedor.setConcessionaria(null); //mas isso pode ? Opcional ?
            }else{
                vendedor.setConcessionaria(concessionaria.get());//metodo do Optional
            }
        }
        return vendedor;
    }

}
