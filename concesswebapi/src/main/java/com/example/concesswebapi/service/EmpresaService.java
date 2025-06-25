package com.example.concesswebapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.concesswebapi.util.ValidadorPessoaJuridica;
import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.Model.repository.EmpresaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    ValidadorPessoaJuridica validadorPessoaJuridica;

    private EmpresaRepository repository;

    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    public List<Empresa> getEmpresa() {
        return repository.findAll();
    }

    public Optional<Empresa> getEmpresaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Empresa salvar(Empresa empresa) {
        validar(empresa);
        return repository.save(empresa);
    }

    @Transactional
    public void excluir(Empresa empresa) {
        Objects.requireNonNull(empresa.getId());
        repository.delete(empresa);
    }

    public void validar(Empresa empresa) {
        validadorPessoaJuridica.validarCamposPessoaJuridica(empresa);
    }
}
