package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.VeiculoTemAcessorio;
import com.example.concesswebapi.Model.repository.AcessorioRepository;
import com.example.concesswebapi.Model.repository.VeiculoRepository;
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
    private VeiculoRepository veiculoRepository;
    private AcessorioRepository acessorioRepository;

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
        if (veiculoTemAcessorio.getAcessorio() == null || veiculoTemAcessorio.getAcessorio().getId() == null) {
            throw new RegraNegocioException("Acessório inválido");
        }
        if (veiculoTemAcessorio.getVeiculo() ==  null || veiculoTemAcessorio.getVeiculo().getId() == null) {
            throw new RegraNegocioException("Veículo inválido");
        }

        boolean existeVeiculo = veiculoRepository.existsById(veiculoTemAcessorio.getVeiculo().getId());
        boolean existeAcessorio = acessorioRepository.existsById(veiculoTemAcessorio.getAcessorio().getId());

        if (!existeVeiculo || !existeAcessorio) {
            throw new RegraNegocioException("Veículo ou acessório não encontrado");
        }
    }
}
