package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.Model.Entity.Empresa; // Importar Empresa
import com.example.concesswebapi.Model.Entity.Vendedor;
import com.example.concesswebapi.api.dto.VendedorDTO;
import com.example.concesswebapi.api.dto.VendedorListagemDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.ConcessionariaService;
import com.example.concesswebapi.service.EmpresaService; // Importar EmpresaService
import com.example.concesswebapi.service.VendedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vendedores")
@RequiredArgsConstructor
public class VendedorController {

    public final VendedorService service;
    public final ConcessionariaService concessionariaService;
    public final EmpresaService empresaService; // Injetar EmpresaService

    @GetMapping
    public ResponseEntity get() {
        List<Vendedor> lista = service.getVendedores();
        return ResponseEntity.ok(lista.stream().map(VendedorListagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Vendedor> entidade = service.getVendedorById(id);
        if (!entidade.isPresent()) {
            return new ResponseEntity("Vendedor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(entidade.map(VendedorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody VendedorDTO dto) {
        try {
            Vendedor vendedor = converter(dto);
            service.salvar(vendedor);
            return new ResponseEntity(vendedor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody VendedorDTO dto) {
        if (!service.getVendedorById(id).isPresent()) {
            return new ResponseEntity("Vendedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Vendedor vendedor = converter(dto);
            vendedor.setId(id);
            service.salvar(vendedor);
            return ResponseEntity.ok(vendedor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Vendedor> optional = service.getVendedorById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Vendedor não encontrada", HttpStatus.NOT_FOUND);
        }
        service.excluir(optional.get());
        return ResponseEntity.noContent().build();
    }

    public Vendedor converter(VendedorDTO dto){
        Vendedor vendedor = new Vendedor();

        vendedor.setEmail1(dto.getEmail1());
        vendedor.setEmail2(dto.getEmail2());
        vendedor.setTelefone1(dto.getTelefone1());
        vendedor.setTelefone2(dto.getTelefone2());
        vendedor.setLogradouro(dto.getLogradouro());
        vendedor.setNumero(dto.getNumero());
        vendedor.setComplemento(dto.getComplemento());
        vendedor.setBairro(dto.getBairro());
        vendedor.setCep(dto.getCep());
        vendedor.setUf(dto.getUf());

        vendedor.setNome(dto.getNome());
        vendedor.setCpf(dto.getCpf());
        vendedor.setLogin(dto.getLogin());
        vendedor.setSenha(dto.getSenha());
        vendedor.setCargo(dto.getCargo());

        if(dto.getIdConcessionaria() != null){
            Optional<Concessionaria> concessionaria = concessionariaService.getConcessionariaById(dto.getIdConcessionaria());
            concessionaria.ifPresent(vendedor::setConcessionaria);
        } else {
            vendedor.setConcessionaria(null);
        }

        if(dto.getEmpresaId() != null){
            Optional<Empresa> empresa = empresaService.getEmpresaById(dto.getEmpresaId());
            empresa.ifPresent(vendedor::setEmpresa);
        } else {
            vendedor.setEmpresa(null);
        }

        return vendedor;
    }
}