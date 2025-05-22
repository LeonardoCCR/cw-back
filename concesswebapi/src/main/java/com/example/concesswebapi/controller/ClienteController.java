package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Cliente;
import com.example.concesswebapi.api.dto.ClienteDTO;
import com.example.concesswebapi.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    public final ClienteService service;


    public Cliente converter(ClienteDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cliente.class);
    }
}
