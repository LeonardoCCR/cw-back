package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Fabricante;
import com.example.concesswebapi.Model.Entity.Modelo;
import com.example.concesswebapi.Model.repository.FabricanteRepository;
import com.example.concesswebapi.Model.repository.ModeloRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ModeloService {

    private ModeloRepository repository;
    private FabricanteRepository fabricanteRepository;

    public ModeloService(ModeloRepository repository) {
        this.repository = repository;
    }

    public List<Modelo> getModelos() {
        return repository.findAll();
    }

    public Optional<Modelo> getModeloById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Modelo salvar(Modelo modelo) {
        validar(modelo);
        return repository.save(modelo);
    }

    @Transactional
    public void excluir(Modelo modelo) {
        Objects.requireNonNull(modelo.getId());
        repository.delete(modelo);
    }

    public void validar(Modelo modelo)
    {
        if (verificaNullVazio(modelo.getNome())) {
            throw new RegraNegocioException("Nome de modelo inválido");
        }
        if (modelo.getFabricante() == null || modelo.getFabricante().getId() == null) {
            throw new RegraNegocioException("Fabricante inválida");
        }

        boolean existe = fabricanteRepository.existsById(modelo.getFabricante().getId());

        if (!existe) {
            throw new RegraNegocioException("Fabricante não encontrada");
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public static boolean verificaValor(Fabricante valor)
    {
        return valor == null;
    }
}
