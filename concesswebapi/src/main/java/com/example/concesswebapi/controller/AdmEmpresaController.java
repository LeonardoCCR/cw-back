package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.AdmEmpresa;
import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.api.dto.AdmEmpresaDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.AdmEmpresaService;
import com.example.concesswebapi.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admsempresa")
@RequiredArgsConstructor
public class AdmEmpresaController {

    public final AdmEmpresaService service;
    public final EmpresaService empresaService;

    @GetMapping()
    public ResponseEntity get(){
        List<AdmEmpresa> admsEmpresa = service.getAdmsEmpresas();
        return ResponseEntity.ok(admsEmpresa.stream().map(AdmEmpresaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AdmEmpresa> admEmpresa = service.getAdmEmpresaById(id);
        if (!admEmpresa.isPresent()) {
            return new ResponseEntity("Administrador de Empresa não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(admEmpresa.map(AdmEmpresaDTO::create));
    }


    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, AdmEmpresaDTO dto) {
        if (!service.getAdmEmpresaById(id).isPresent()) {
            return new ResponseEntity("Administrador de Empresa não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            AdmEmpresa admEmpresa = converter(dto);
            admEmpresa.setId(id);
            Empresa empresa = empresaService.salvar(admEmpresa.getEmpresa());
            admEmpresa.setEmpresa(empresa);
            service.salvar(admEmpresa);
            return ResponseEntity.ok(admEmpresa);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
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
