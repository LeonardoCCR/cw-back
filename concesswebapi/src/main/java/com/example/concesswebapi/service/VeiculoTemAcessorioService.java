package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.VeiculoTemAcessorio;
import com.example.concesswebapi.Model.repository.VeiculoTemAcessorioRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VeiculoTemAcessorioService {

    private VeiculoTemAcessorioRepository repository;

    public VeiculoTemAcessorioService(VeiculoTemAcessorioRepository repository) {
        this.repository = repository;
    }

    public List<VeiculoTemAcessorio> getVeiculosTemAcessorios() {
        return repository.findAll();
    }

    public Optional<VeiculoTemAcessorio> getVeiculoTemAcessorioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public VeiculoTemAcessorio salvar(VeiculoTemAcessorio veiculoTemAcessorio) {
        validar(veiculoTemAcessorio);
        return repository.save(veiculoTemAcessorio);
    }

    @Transactional
    public void excluir(VeiculoTemAcessorio veiculoTemAcessorio) {
        Objects.requireNonNull(veiculoTemAcessorio.getId());
        repository.delete(veiculoTemAcessorio);
    }

    public void validar(VeiculoTemAcessorio veiculoTemAcessorio)
    {
        if (verificaValor(veiculoTemAcessorio.getAcessorio().getId())) {
            throw new RegraNegocioException("Acessório inválido");
        }
        if (verificaValor(veiculoTemAcessorio.getVeiculo().getId())) {
            throw new RegraNegocioException("Veículo inválido");
        }
    }

    public boolean verificaValor(Long campo) {
        return campo == null;
    }
}
