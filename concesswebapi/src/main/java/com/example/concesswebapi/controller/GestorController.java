package com.example.concesswebapi.controller;


import com.example.concesswebapi.Model.Entity.Gestor;
import com.example.concesswebapi.api.dto.GestorDTO;
import com.example.concesswebapi.service.GestorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/gestores")
@RequiredArgsConstructor
public class GestorController {

    public final GestorService service;


    public Gestor converter(GestorDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Gestor.class);
    }

}
