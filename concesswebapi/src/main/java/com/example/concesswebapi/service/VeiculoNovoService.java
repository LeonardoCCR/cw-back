package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.VeiculoNovo;
import com.example.concesswebapi.Model.repository.VeiculoNovoRepository;
import com.example.concesswebapi.util.ValidadorVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VeiculoNovoService {

    @Autowired
    private ValidadorVeiculo validadorVeiculo;

    private VeiculoNovoRepository repository;

    public VeiculoNovoService(VeiculoNovoRepository repository) {
        this.repository = repository;
    }

    public List<VeiculoNovo> getVeiculosNovos() {
        return repository.findAll();
    }

    public Optional<VeiculoNovo> getVeiculoNovoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public VeiculoNovo salvar(VeiculoNovo veiculoNovo) {
        validadorVeiculo.validarCamposVeiculo(veiculoNovo);
        return repository.save(veiculoNovo);
    }

    @Transactional
    public void excluir(VeiculoNovo veiculoNovo) {
        Objects.requireNonNull(veiculoNovo.getId());
        repository.delete(veiculoNovo);
    }
}
