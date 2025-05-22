package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.AdmEmpresa;
import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.api.dto.AdmEmpresaDTO;
import com.example.concesswebapi.service.AdmEmpresaService;
import com.example.concesswebapi.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admsempresa")
@RequiredArgsConstructor
public class AdmEmpresaController {

    public final AdmEmpresaService service;
    public final EmpresaService empresaService;

    public AdmEmpresa converter(AdmEmpresaDTO dto){

        ModelMapper modelMapper = new ModelMapper();
        AdmEmpresa admEmpresa = modelMapper.map(dto, AdmEmpresa.class);

        if(dto.getIdEmpresa() != null){
            Optional<Empresa> empresa = empresaService.getEmpresaById(dto.getIdEmpresa());

            if(!empresa.isPresent()){
                admEmpresa.setEmpresa(null);
            }else{
                admEmpresa.setEmpresa(empresa.get());
            }
        }

        return modelMapper.map(dto, AdmEmpresa.class);
    }


}
