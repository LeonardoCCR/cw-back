package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Fabricante;
import com.example.concesswebapi.Model.repository.FabricanteRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FabricanteService {

    private FabricanteRepository repository;

    public FabricanteService(FabricanteRepository repository) {
        this.repository = repository;
    }

    public List<Fabricante> getFabricantes() {
        return repository.findAll();
    }

    public Optional<Fabricante> getFabricanteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Fabricante salvar(Fabricante fabricante) {
        validar(fabricante);
        return repository.save(fabricante);
    }

    @Transactional
    public void excluir(Fabricante fabricante) {
        Objects.requireNonNull(fabricante.getId());
        repository.delete(fabricante);
    }

    public void validar(Fabricante fabricante)
    {
        if (verificaNullVazio(fabricante.getNome())) {
            throw new RegraNegocioException("Fabricante inválida");
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }
}
