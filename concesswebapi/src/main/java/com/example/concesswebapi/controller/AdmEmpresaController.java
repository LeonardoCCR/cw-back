package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.AdmEmpresa;
import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.api.dto.AdmEmpresaDTO;
import com.example.concesswebapi.api.dto.AdmEmpresaListagemDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.AdmEmpresaService;
import com.example.concesswebapi.service.EmpresaService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admsempresa")
@RequiredArgsConstructor
@Api("API de administradores de empresa")
public class AdmEmpresaController {

    public final AdmEmpresaService service;
    public final EmpresaService empresaService;

    @GetMapping()
    public ResponseEntity get(){
        List<AdmEmpresa> admsEmpresa = service.getAdmsEmpresas();
        return ResponseEntity.ok(admsEmpresa.stream().map(AdmEmpresaListagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AdmEmpresa> admEmpresa = service.getAdmEmpresaById(id);
        if (!admEmpresa.isPresent()) {
            return new ResponseEntity("Administrador de Empresa não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(admEmpresa.map(AdmEmpresaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AdmEmpresaDTO dto) {
        try {
            AdmEmpresa admEmpresa = converter(dto);
            service.salvar(admEmpresa);
            return new ResponseEntity(admEmpresa, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AdmEmpresaDTO dto) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<AdmEmpresa> optional = service.getAdmEmpresaById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Administrador de empresa não encontrado", HttpStatus.NOT_FOUND);
        }
        service.excluir(optional.get());
        return ResponseEntity.noContent().build();
    }

    public AdmEmpresa converter(AdmEmpresaDTO dto){
        AdmEmpresa admEmpresa = new AdmEmpresa();

        admEmpresa.setId(dto.getId());
        admEmpresa.setEmail1(dto.getEmail1());
        admEmpresa.setEmail2(dto.getEmail2());
        admEmpresa.setTelefone1(dto.getTelefone1());
        admEmpresa.setTelefone2(dto.getTelefone2());
        admEmpresa.setLogradouro(dto.getLogradouro());
        admEmpresa.setNumero(dto.getNumero());
        admEmpresa.setComplemento(dto.getComplemento());
        admEmpresa.setBairro(dto.getBairro());
        admEmpresa.setCep(dto.getCep());
        admEmpresa.setUf(dto.getUf());

        admEmpresa.setNome(dto.getNome());
        admEmpresa.setCpf(dto.getCpf());
        admEmpresa.setLogin(dto.getLogin());
        admEmpresa.setSenha(dto.getSenha());

        if(dto.getIdEmpresa() != null){
            Optional<Empresa> empresa = empresaService.getEmpresaById(dto.getIdEmpresa());

            if(empresa.isPresent()){
                admEmpresa.setEmpresa(empresa.get());
            } else {
                admEmpresa.setEmpresa(null);
            }
        } else {
            admEmpresa.setEmpresa(null);
        }

        return admEmpresa;
    }
}