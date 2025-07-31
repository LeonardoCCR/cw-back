package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.GestorConcessionaria;
import com.example.concesswebapi.Model.repository.GestorConcessionariaRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GestorConcessionariaService {

    private final GestorConcessionariaRepository repository;

    public GestorConcessionariaService(GestorConcessionariaRepository repository) {
        this.repository = repository;
    }

    public List<GestorConcessionaria> getTodosGestoresConcessionarias() {
        return repository.findAll();
    }

    public Optional<GestorConcessionaria> getGestorConcessionariaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public GestorConcessionaria salvar(GestorConcessionaria gestorConcessionaria) {
        validar(gestorConcessionaria);
        return repository.save(gestorConcessionaria);
    }

    @Transactional
    public void excluir(GestorConcessionaria gestorConcessionaria) {
        Objects.requireNonNull(gestorConcessionaria.getId());
        repository.delete(gestorConcessionaria);
    }


    private void validar(GestorConcessionaria gestorConcessionaria){

        if( gestorConcessionaria.getGestor() == null || gestorConcessionaria.getGestor().getId() == null || gestorConcessionaria.getGestor().getId() == 0){
            throw new RegraNegocioException("Campo gestor inválido ");
        }

        if( gestorConcessionaria.getConcessionaria() == null || gestorConcessionaria.getConcessionaria().getId() == null || gestorConcessionaria.getConcessionaria().getId() == 0){
            throw new RegraNegocioException("Campo concessionaria inválido");
        }
    }

}
