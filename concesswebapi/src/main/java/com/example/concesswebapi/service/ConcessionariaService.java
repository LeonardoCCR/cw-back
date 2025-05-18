package com.example.concesswebapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.concesswebapi.util.ValidadorPessoaJuridica;
import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.Model.repository.ConcessionariaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConcessionariaService {

    private ConcessionariaRepository repository;

    public ConcessionariaService(ConcessionariaRepository repository) {
        this.repository = repository;
    }

    public List<Concessionaria> getConcessionaria() {
        return repository.findAll();
    }

    public Optional<Concessionaria> getConcessionariaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Concessionaria salvar(Concessionaria concessionaria) {
        validar(concessionaria);
        return repository.save(concessionaria);
    }

    @Transactional
    public void excluir(Concessionaria concessionaria) {
        Objects.requireNonNull(concessionaria.getId());
        repository.delete(concessionaria);
    }

    public void validar(Concessionaria concessionaria) {
        ValidadorPessoaJuridica.validarCamposPessoaJuridica(concessionaria);
    }
}
