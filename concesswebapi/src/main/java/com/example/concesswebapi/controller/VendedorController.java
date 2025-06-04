package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.Model.Entity.Vendedor;
import com.example.concesswebapi.api.dto.VendedorDTO;
import com.example.concesswebapi.service.ConcessionariaService;
import com.example.concesswebapi.service.VendedorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
