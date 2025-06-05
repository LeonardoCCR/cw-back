package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoNovo;
import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoNovoService veiculoNovoService;
    private final VeiculoUsadoService veiculoUsadoService;

    public VeiculoService(VeiculoNovoService novoService, VeiculoUsadoService usadoService) {
        this.veiculoNovoService = novoService;
        this.veiculoUsadoService = usadoService;
    }

    public List<Veiculo> listarTodos() {
        List<Veiculo> veiculos = new ArrayList<>();
        veiculos.addAll(veiculoNovoService.getVeiculosNovos());
        veiculos.addAll(veiculoUsadoService.getVeiculosUsados());
        return veiculos;
    }

    public Veiculo salvar(Veiculo veiculo) {
        if (veiculo instanceof VeiculoNovo) {
            return veiculoNovoService.salvar((VeiculoNovo) veiculo);
        } else if (veiculo instanceof VeiculoUsado) {
            return veiculoUsadoService.salvar((VeiculoUsado) veiculo);
        }
        throw new RegraNegocioException("Tipo de veículo inválido");
    }

}
