package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoNovo;
import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import com.example.concesswebapi.Model.repository.VeiculoTemAcessorioRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoNovoService veiculoNovoService;
    private final VeiculoUsadoService veiculoUsadoService;
    private final VeiculoTemAcessorioRepository veiculoTemAcessorioRepository;

    public VeiculoService(VeiculoNovoService novoService, VeiculoUsadoService usadoService, VeiculoTemAcessorioRepository vtRepository) {
        this.veiculoNovoService = novoService;
        this.veiculoUsadoService = usadoService;
        this.veiculoTemAcessorioRepository = vtRepository;
    }

    public List<Veiculo> listarTodos() {
        List<Veiculo> veiculos = new ArrayList<>();
        veiculos.addAll(veiculoNovoService.getVeiculosNovos());
        veiculos.addAll(veiculoUsadoService.getVeiculosUsados());
        return veiculos;
    }

    public Optional<Veiculo> getVeiculoById(Long id) {
        Optional<VeiculoNovo> veiculoNovo = veiculoNovoService.getVeiculoNovoById(id);
        if (veiculoNovo.isPresent()) {
            return Optional.of(veiculoNovo.get());
        }

        Optional<VeiculoUsado> veiculoUsado = veiculoUsadoService.getVeiculoUsadoById(id);
        if (veiculoUsado.isPresent()) {
            return Optional.of(veiculoUsado.get());
        }

        return Optional.empty();
    }

    public Optional<Veiculo> getVeiculoDisponivelPorModelo(String nomeModelo) {
        Optional<VeiculoNovo> veiculoNovo = veiculoNovoService.getVeiculoDisponivelPorModelo(nomeModelo);
        if (veiculoNovo.isPresent()) {
            return Optional.of(veiculoNovo.get());
        }

        Optional<VeiculoUsado> veiculoUsado = veiculoUsadoService.getVeiculoDisponivelPorModelo(nomeModelo);
        if (veiculoUsado.isPresent()) {
            return Optional.of(veiculoUsado.get());
        }

        return Optional.empty();
    }

    public Veiculo salvar(Veiculo veiculo) {
        if (veiculo instanceof VeiculoNovo) {
            return veiculoNovoService.salvar((VeiculoNovo) veiculo);
        } else if (veiculo instanceof VeiculoUsado) {
            return veiculoUsadoService.salvar((VeiculoUsado) veiculo);
        }
        throw new RegraNegocioException("Tipo de veículo inválido");
    }

    public void excluir(Veiculo veiculo) {
        if (veiculo instanceof VeiculoNovo) {
            veiculoNovoService.excluir((VeiculoNovo) veiculo);
        } else if (veiculo instanceof VeiculoUsado) {
            veiculoUsadoService.excluir((VeiculoUsado) veiculo);
        }
    }
}