package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.AdmSuporte;
import com.example.concesswebapi.Model.repository.AdmSuporteRepository;
import com.example.concesswebapi.util.ValidadorPessoaFisica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdmSuporteService {

    @Autowired
    private ValidadorPessoaFisica validadorPessoaFisica;
    private AdmSuporteRepository repository;


    public AdmSuporteService( AdmSuporteRepository repository){
        this.repository = repository;
    }

    public List<AdmSuporte> getAdmsSuporte(){
        return repository.findAll();
    }

    public Optional<AdmSuporte> getAdmSuporteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public AdmSuporte salvar(AdmSuporte admSuporte){
        validar(admSuporte);
        return repository.save(admSuporte);
    }

    @Transactional
    public void excluir(AdmSuporte admSuporte){
        Objects.requireNonNull(admSuporte.getId());
        repository.delete(admSuporte);
    }

    public void validar(AdmSuporte admSuporte){

        validadorPessoaFisica.validarCamposPessoaFisica(admSuporte);
    }
}
