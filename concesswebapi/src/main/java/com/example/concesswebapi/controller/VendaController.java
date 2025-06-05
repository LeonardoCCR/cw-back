package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Venda;
import com.example.concesswebapi.service.VendaService;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listar() {
        List<Venda> vendas = vendaService.getVendas();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscar(@PathVariable Long id) {
        return vendaService.getVendaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Venda venda) {
        try {
            Venda vendaSalva = vendaService.salvar(venda);
            return new ResponseEntity<>(vendaSalva, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return vendaService.getVendaById(id).map(venda -> {
            vendaService.excluir(venda);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND));
    }
}
