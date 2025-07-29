package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.AdmSuporte;
import com.example.concesswebapi.api.dto.AdmSuporteDTO;
import com.example.concesswebapi.api.dto.AdmSuporteListagemDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.AdmSuporteService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/admssuporte")
@RequiredArgsConstructor
@Api("API de administradores de suporte")
public class AdmSuporteController {

    public final AdmSuporteService service;

    @GetMapping()
    public ResponseEntity get(){
        List<AdmSuporte> admsSuporte = service.getAdmsSuporte();
        return ResponseEntity.ok(admsSuporte.stream().map(AdmSuporteListagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AdmSuporte> admSuporte = service.getAdmSuporteById(id);
        if (!admSuporte.isPresent()) {
            return new ResponseEntity("Administrador de Suporte não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(admSuporte.map(AdmSuporteDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AdmSuporteDTO dto) {

        try {
            AdmSuporte admSuporte = converter(dto);
            service.salvar(admSuporte);
            return new ResponseEntity(admSuporte, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AdmSuporteDTO dto) {
        if (!service.getAdmSuporteById(id).isPresent()) {
            return new ResponseEntity("Administrador de Suporte não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            AdmSuporte admSuporte = converter(dto);
            admSuporte.setId(id);
            service.salvar(admSuporte);
            return ResponseEntity.ok(admSuporte);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<AdmSuporte> optional = service.getAdmSuporteById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Administrador de Suporte não encontrado", HttpStatus.NOT_FOUND);
        }
        service.excluir(optional.get());
        return ResponseEntity.noContent().build();
    }

    public AdmSuporte converter(AdmSuporteDTO dto){
        AdmSuporte admSuporte = new AdmSuporte();

        admSuporte.setEmail1(dto.getEmail1());
        admSuporte.setEmail2(dto.getEmail2());
        admSuporte.setTelefone1(dto.getTelefone1());
        admSuporte.setTelefone2(dto.getTelefone2());
        admSuporte.setLogradouro(dto.getLogradouro());
        admSuporte.setNumero(dto.getNumero());
        admSuporte.setComplemento(dto.getComplemento());
        admSuporte.setBairro(dto.getBairro());
        admSuporte.setCep(dto.getCep());
        admSuporte.setUf(dto.getUf());

        admSuporte.setNome(dto.getNome());
        admSuporte.setCpf(dto.getCpf());
        //admSuporte.setLogin(dto.getLogin());
        //admSuporte.setSenha(dto.getSenha());

        return admSuporte;
    }
}