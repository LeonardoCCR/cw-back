package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.ModeloVeiculo;
import com.example.concesswebapi.Model.repository.ModeloVeiculoRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ModeloVeiculoService {

    private ModeloVeiculoRepository repository;

    public ModeloVeiculoService(ModeloVeiculoRepository repository) {
        this.repository = repository;
    }

    public List<ModeloVeiculo> getModelosVeiculo() {
        return repository.findAll();
    }

    public Optional<ModeloVeiculo> getModeloVeiculoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ModeloVeiculo salvar(ModeloVeiculo modeloVeiculo) {
        validar(modeloVeiculo);
        return repository.save(modeloVeiculo);
    }

    @Transactional
    public void excluir(ModeloVeiculo modeloVeiculo) {
        Objects.requireNonNull(modeloVeiculo.getId());
        repository.delete(modeloVeiculo);
    }

    public void validar(ModeloVeiculo modeloVeiculo)
    {
        if (verificaNullVazio(modeloVeiculo.getAnoFabricacao())) {
            throw new RegraNegocioException("Ano de fabricação inválido");
        }
        if (verificaPreco((modeloVeiculo.getPrecoBase()))) {
            throw new RegraNegocioException("Preço base inválido");
        }
        if (verificaValor((modeloVeiculo.getQtdEstoque()))) {
            throw new RegraNegocioException("Quantidade de estoque inválida");
        }
        if (verificaValor((modeloVeiculo.getQtdEstoqueVenda()))) {
            throw new RegraNegocioException("Quantidade de estoque inválida");
        }
        if (verificaNullVazio(modeloVeiculo.getFotoModelo())) {
            throw new RegraNegocioException("Foto inválida");
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public static boolean verificaValor(Integer valor)
    {
        return valor == null;
    }

    public static boolean verificaPreco(BigDecimal preco)
    {
        return preco == null || preco.compareTo(BigDecimal.ZERO) <= 0;
    }
}
