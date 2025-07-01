package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Carro;
import com.example.concesswebapi.Model.Entity.Moto;
import com.example.concesswebapi.Model.Entity.TipoVeiculo;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TipoVeiculoService {

    private final CarroService carroService;
    private final MotoService motoService;

    public TipoVeiculoService(CarroService carroService, MotoService motoService) {
        this.carroService = carroService;
        this.motoService = motoService;
    }

    public TipoVeiculo salvar(TipoVeiculo tipoVeiculo) {
        if (tipoVeiculo instanceof Carro) {
            return carroService.salvar((Carro) tipoVeiculo);
        } else if (tipoVeiculo instanceof Moto) {
            return motoService.salvar((Moto) tipoVeiculo);
        }

        throw new RegraNegocioException("Tipo de veículo inválido");
    }

    public void excluir(TipoVeiculo tipoVeiculo) {
        System.out.println(tipoVeiculo);
        if (tipoVeiculo instanceof Carro) {
            carroService.excluir((Carro) tipoVeiculo);
        } else if (tipoVeiculo instanceof Moto) {
            motoService.excluir((Moto) tipoVeiculo);
        } else {
            throw new RegraNegocioException("Tipo de veículo inválido");
        }
    }

    public void validar(TipoVeiculo tipoVeiculo) {
        if (tipoVeiculo.getTipo() == null) {
            throw new RegraNegocioException("O campo tipo é obrigatório");
        }
    }
}
