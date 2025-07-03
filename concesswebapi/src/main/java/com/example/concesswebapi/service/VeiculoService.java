package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.*;
import com.example.concesswebapi.Model.repository.VeiculoTemAcessorioRepository;
import com.example.concesswebapi.api.dto.VeiculoDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoNovoService veiculoNovoService;
    private final VeiculoUsadoService veiculoUsadoService;
    private VeiculoTemAcessorioRepository veiculoTemAcessorioRepository;

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

    public void addAcessorios(VeiculoDTO veiculoDTO)
    {
        List<VeiculoTemAcessorio> veiculosEAcessorios = veiculoTemAcessorioRepository.findByVeiculoId(veiculoDTO.getId());

        List<Long> acessoriosIds = veiculosEAcessorios.stream()
                .map(item -> item.getAcessorio().getId())
                .toList();

        veiculoDTO.setAcessoriosIds(acessoriosIds);
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
