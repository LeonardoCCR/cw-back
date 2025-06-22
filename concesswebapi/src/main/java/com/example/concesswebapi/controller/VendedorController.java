package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.Model.Entity.Gestor;
import com.example.concesswebapi.Model.Entity.Vendedor;
import com.example.concesswebapi.api.dto.GestorDTO;
import com.example.concesswebapi.api.dto.VendedorDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.ConcessionariaService;
import com.example.concesswebapi.service.VendedorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, VendedorDTO dto) {
        if (!service.getVendedorById(id).isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Vendedor vendedor = converter(dto);
            vendedor.setId(id);
            service.salvar(vendedor);
            return ResponseEntity.ok(vendedor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
