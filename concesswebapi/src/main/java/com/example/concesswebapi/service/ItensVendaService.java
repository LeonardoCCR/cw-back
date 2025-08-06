package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.ItensVenda;
import com.example.concesswebapi.Model.repository.ItensVendaRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItensVendaService {

    private final ItensVendaRepository repository;

    public ItensVendaService(ItensVendaRepository repository) {
        this.repository = repository;
    }

    public List<ItensVenda> getItensVenda() {
        return repository.findAll();
    }

    public List<ItensVenda> getItensByVendaId(Long vendaId) {
        return repository.findByVendaId(vendaId);
    }

    public Optional<ItensVenda> getItemVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ItensVenda salvar(ItensVenda itemVenda) {
        validar(itemVenda);
        return repository.save(itemVenda);
    }

    @Transactional
    public void excluir(ItensVenda itemVenda) {
        Objects.requireNonNull(itemVenda.getId(), "ID do item de venda não pode ser nulo.");
        repository.delete(itemVenda);
    }

    public void validar(ItensVenda itemVenda) {
        if (itemVenda.getDescontoParcial() != null && itemVenda.getDescontoParcial().doubleValue() < 0) {
            throw new RegraNegocioException("Desconto parcial inválido.");
        }
        if (itemVenda.getVenda() == null || itemVenda.getVenda().getId() == null) {
            throw new RegraNegocioException("Venda inválida.");
        }
        if (itemVenda.getModeloVeiculo() == null || itemVenda.getModeloVeiculo().getId() == null) {
            throw new RegraNegocioException("Modelo do veículo inválido.");
        }
        if (itemVenda.getVeiculo() == null || itemVenda.getVeiculo().getId() == null) {
            throw new RegraNegocioException("Veículo inválido.");
        }
    }
}