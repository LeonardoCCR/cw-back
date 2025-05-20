package com.example.concesswebapi.controller;


import com.example.concesswebapi.Model.Entity.Gestor;
import com.example.concesswebapi.api.dto.GestorDTO;
import org.modelmapper.ModelMapper;

public class GestorController {


    //pega um dto do front-end e converte em uma classe de domínio
    public Gestor converter(GestorDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Gestor.class);
    }
    //método map() de  modelMapper transforma o dto na classe desejada
}
