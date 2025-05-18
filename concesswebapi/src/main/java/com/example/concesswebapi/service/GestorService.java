package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Gestor;
import com.example.concesswebapi.Model.repository.GestorRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.util.ValidadorFuncionario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GestorService {

    private GestorRepository repository;

    public GestorService(GestorRepository repository){
        this.repository = repository; //acesso ao banco de dados
    }

    public List<Gestor> getGestores(){
        return repository.findAll();
    }

    public Optional<Gestor> getGestorById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Gestor salvar(Gestor gestor){
        validar(gestor);
        return repository.save(gestor);
    }

    @Transactional
    public void excluir(Gestor gestor){
        Objects.requireNonNull(gestor.getId());
        repository.delete(gestor);
    }

    public void validar(Gestor gestor){
        ValidadorFuncionario.validarCamposFuncionario(gestor);
    }
}
