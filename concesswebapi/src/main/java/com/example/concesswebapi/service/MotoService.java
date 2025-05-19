package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Moto;
import com.example.concesswebapi.Model.repository.MotoRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MotoService {

    private MotoRepository repository;

    public MotoService(MotoRepository repository) {
        this.repository = repository;
    }

    public List<Moto> getMotos() {
        return repository.findAll();
    }

    public Optional<Moto> getMotoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Moto salvar(Moto moto) {
        validar(moto);
        return repository.save(moto);
    }

    @Transactional
    public void excluir(Moto moto) {
        Objects.requireNonNull(moto.getId());
        repository.delete(moto);
    }

    public void validar(Moto moto)
    {
        if (verificaValor(moto.getQtdMarcha())) {
            throw new RegraNegocioException("Quantidade de marcha inválida");
        }
        if (verificaNullVazio(moto.getTipoPartida())) {
            throw new RegraNegocioException("Tipo de partida inválida");
        }
        if (verificaNullVazio(moto.getTipoMotor())) {
            throw new RegraNegocioException("Tipo de motor inválida");
        }
        if (verificaValor(moto.getCilindrada())) {
            throw new RegraNegocioException("Cilindrada inválida");
        }
        if (verificaNullVazio(moto.getCategoria())) {
            throw new RegraNegocioException("Categoria inválida");
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public static boolean verificaValor(Integer valor)
    {
        return valor == null || valor <= 0;
    }
}

