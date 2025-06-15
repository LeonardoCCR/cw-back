package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Carro;
import com.example.concesswebapi.Model.repository.CarroRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarroService {

    private CarroRepository repository;

    public CarroService(CarroRepository repository) {
        this.repository = repository;
    }

    public List<Carro> getCarros() {
        return repository.findAll();
    }

    public Optional<Carro> getCarroById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Carro salvar(Carro carro) {
        validar(carro);
        return repository.save(carro);
    }

    @Transactional
    public void excluir(Carro carro) {
        Objects.requireNonNull(carro.getId());
        repository.delete(carro);
    }

    public void validar(Carro carro)
    {
        if (verificaNullVazio(carro.getMotorizacao()) || verificaNumero(carro.getMotorizacao())) {
            throw new RegraNegocioException("Motorização inválida");
        }
        if (verificaNullVazio(carro.getTransmissao()) || verificaNumero(carro.getTransmissao())) {
            throw new RegraNegocioException("Transmissão inválida");
        }
        if (verificaValor(carro.getPotencia())) {
            throw new RegraNegocioException("Potência inválida");
        }
        if (verificaNullVazio(carro.getCategoria()) || verificaNumero(carro.getCategoria())) {
            throw new RegraNegocioException("Categoria inválida");
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public static boolean verificaValor(Double valor)
    {
        return valor == null || valor <= 0;
    }

    public boolean verificaNumero(String campo)
    {
        return campo.matches("-?\\d+(\\.\\d+)?") ;
    }
}

