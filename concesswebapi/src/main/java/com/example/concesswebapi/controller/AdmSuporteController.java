package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.AdmSuporte;
import com.example.concesswebapi.api.dto.AdmSuporteDTO;
import com.example.concesswebapi.service.AdmSuporteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/admssuporte")
@RequiredArgsConstructor
public class AdmSuporteController {

    public final AdmSuporteService service;

    public AdmSuporte converter(AdmSuporteDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, AdmSuporte.class);
    }

}
