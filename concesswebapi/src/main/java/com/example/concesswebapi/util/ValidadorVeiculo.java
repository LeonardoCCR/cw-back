package com.example.concesswebapi.util;

import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoTemAcessorio;
import com.example.concesswebapi.Model.repository.ModeloVeiculoRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidadorVeiculo {

    public void validarCamposVeiculo(Veiculo veiculo)
    {
        if (verificaNullVazio(veiculo.getChassi())) {
            throw new RegraNegocioException("Chassi inválido");
        }
        if (verificaNullVazio(veiculo.getModeloVeiculo().getModelo().getNome())) {
            throw new RegraNegocioException("Modelo inválido");
        }
        if (verificaPreco(veiculo.getPrecoAtual())) {
            throw new RegraNegocioException("Preço atual inválido");
        }
        if (verificaNullVazio(veiculo.getCondicao())) {
            throw new RegraNegocioException("Condição inválida");
        }
        if (verificaNullVazio(veiculo.getCor()) || verificaNumero(veiculo.getCor())) {
            throw new RegraNegocioException("Cor inválida");
        }
        if (verificaNullVazio(veiculo.getGarantia()) || verificaNumero(veiculo.getGarantia())) {
            throw new RegraNegocioException("Garantia inválida");
        }
        if (veiculo.getModeloVeiculo() == null) {
            throw new RegraNegocioException("Modelo de veículo inválido");
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public boolean verificaPreco(BigDecimal preco)
    {
        return preco == null || preco.compareTo(BigDecimal.ZERO) <= 0;
    }

    public boolean verificaNumero(String campo) {
        return campo.matches("-?\\d+(\\.\\d+)?");
    }
}
