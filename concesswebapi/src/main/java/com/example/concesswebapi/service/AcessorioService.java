package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Acessorio;
import com.example.concesswebapi.Model.repository.AcessorioRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AcessorioService {

    private AcessorioRepository repository;

    public AcessorioService(AcessorioRepository repository) {
        this.repository = repository;
    }

    public List<Acessorio> getAcessorios() {
        return repository.findAll();
    }

    public Optional<Acessorio> getAcessorioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Acessorio salvar(Acessorio acessorio) {
        validar(acessorio);
        return repository.save(acessorio);
    }

    @Transactional
    public void excluir(Acessorio acessorio) {
        Objects.requireNonNull(acessorio.getId());
        repository.delete(acessorio);
    }

    public void validar(Acessorio acessorio)
    {
        if (verificaNullVazio(acessorio.getDescricao())) {
            throw new RegraNegocioException("Descrição inválida");
        }

        if(verificaNumero(acessorio.getDescricao()))
        {
            throw new RegraNegocioException("O valor não pode ser um número");
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public boolean verificaNumero(String campo)
    {
       return campo.matches("-?\\d+(\\.\\d+)?") ;
    }
}

