package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.api.dto.EmpresaDTO;
import com.example.concesswebapi.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    public final EmpresaService service;

    public Empresa converter(EmpresaDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Empresa.class);
    }

}
