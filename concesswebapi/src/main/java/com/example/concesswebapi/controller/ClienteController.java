package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Cliente;
import com.example.concesswebapi.api.dto.ClienteDTO;
import com.example.concesswebapi.api.dto.ClienteListagemDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.ClienteService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Api("API de clientes")
public class ClienteController {

    public final ClienteService service;

    @GetMapping
    public ResponseEntity get() {
        List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteListagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
        if (!service.getClienteById(id).isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cliente cliente = converter(dto);
            cliente.setId(id);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ClienteDTO dto) {
        try {
            Cliente cliente = converter(dto);
            service.salvar(cliente);
            return new ResponseEntity(cliente, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Cliente> optional = service.getClienteById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        service.excluir(optional.get());
        return ResponseEntity.noContent().build();
    }

    public Cliente converter(ClienteDTO dto) {
        Cliente cliente = new Cliente();

        cliente.setEmail1(dto.getEmail1());
        cliente.setEmail2(dto.getEmail2());
        cliente.setTelefone1(dto.getTelefone1());
        cliente.setTelefone2(dto.getTelefone2());
        cliente.setLogradouro(dto.getLogradouro());
        cliente.setNumero(dto.getNumero());
        cliente.setComplemento(dto.getComplemento());
        cliente.setBairro(dto.getBairro());
        cliente.setCep(dto.getCep());
        cliente.setUf(dto.getUf());

        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setLogin(dto.getLogin());
        cliente.setSenha(dto.getSenha());

        cliente.setDataNascimento(dto.getDataNascimento());

        return cliente;
    }
}