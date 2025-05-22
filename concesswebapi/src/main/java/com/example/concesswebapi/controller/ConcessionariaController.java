package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.api.dto.ConcessionariaDTO;
import com.example.concesswebapi.service.ConcessionariaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/concessionarias")
@RequiredArgsConstructor
public class ConcessionariaController {

    public final ConcessionariaService service;

    public Concessionaria converter(ConcessionariaDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Concessionaria.class);
    }
}
