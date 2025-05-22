package com.example.concesswebapi.controller;


import com.example.concesswebapi.Model.Entity.Gestor;
import com.example.concesswebapi.api.dto.GestorDTO;
import com.example.concesswebapi.service.GestorService;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorController {

    public final GestorService service;

    //pega um dto do front-end e converte em uma classe de domínio
    public Gestor converter(GestorDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Gestor.class);
    }
    //método map() de  modelMapper transforma o dto na classe desejada
}
