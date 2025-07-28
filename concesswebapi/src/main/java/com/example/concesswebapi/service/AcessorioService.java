package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Acessorio;
import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoTemAcessorio;
import com.example.concesswebapi.Model.repository.AcessorioRepository;
import com.example.concesswebapi.api.dto.VeiculoDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AcessorioService {

    private AcessorioRepository repository;

    private final VeiculoTemAcessorioService veiculoTemAcessorioService;

    public AcessorioService(AcessorioRepository repository, VeiculoTemAcessorioService veiculoTemAcessorioService) {
        this.repository = repository;
        this.veiculoTemAcessorioService = veiculoTemAcessorioService;
    }

    public List<Acessorio> getAcessorios() {
        return repository.findAll();
    }

    public Optional<Acessorio> getAcessorioById(Long id) {
        return repository.findById(id);
    }

    public List<Acessorio> getAcessoriosByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new RegraNegocioException("Pelo menos um acessório é obrigatório");
        }
        return repository.findAllById(ids);
    }

    @Transactional
    public Acessorio salvar(Acessorio acessorio) {
        validar(acessorio);
        return repository.save(acessorio);
    }

    @Transactional
    public void excluir(Acessorio acessorio) {
        Objects.requireNonNull(acessorio.getId());
        repository.delete(acessorio);
    }

    public void validar(Acessorio acessorio) {
        if (verificaNullVazio(acessorio.getDescricao())) {
            throw new RegraNegocioException("Descrição inválida");
        }

        if (verificaNumero(acessorio.getDescricao())) {
            throw new RegraNegocioException("O valor não pode ser um número");
        }
    }

    public void sincronizarVeiculoTemAcessorio(VeiculoDTO dto, Veiculo veiculo) {
        List<Acessorio> acessorios = this.getAcessoriosByIds(dto.getAcessoriosIds());
        List<VeiculoTemAcessorio> veiculosTemAcessorio = veiculoTemAcessorioService.getVeiculoTemAcessorioById(veiculo.getId());

        int i = 0;
        if (!veiculosTemAcessorio.isEmpty()) {

            for (VeiculoTemAcessorio item : veiculosTemAcessorio) {
                if (i >= acessorios.size()) {
                    //DELETE
                    veiculoTemAcessorioService.excluir(item);
                } else {
                    item.setAcessorio(acessorios.get(i));
                    //UPDATE
                    veiculoTemAcessorioService.salvar(item);
                }
                i++;
            }
        }

        if (i < acessorios.size()) {
            int j = 0;
            for (j = i; j < acessorios.size(); j++) {
                VeiculoTemAcessorio novoVeiculoTemAcessorio = new VeiculoTemAcessorio();
                novoVeiculoTemAcessorio.setVeiculo(veiculo);
                novoVeiculoTemAcessorio.setAcessorio(acessorios.get(j));
                //CREATE
                veiculoTemAcessorioService.salvar(novoVeiculoTemAcessorio);
            }
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public boolean verificaNumero(String campo) {
        return campo.matches("-?\\d+(\\.\\d+)?");
    }
}

