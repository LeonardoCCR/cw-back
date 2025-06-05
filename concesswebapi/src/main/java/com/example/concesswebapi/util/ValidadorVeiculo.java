package com.example.concesswebapi.util;

import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoTemAcessorio;
import com.example.concesswebapi.Model.repository.ModeloVeiculoRepository;
import com.example.concesswebapi.exception.RegraNegocioException;

import java.math.BigDecimal;

public class ValidadorVeiculo {

    private static ModeloVeiculoRepository modeloVeiculoRepository;

    public static void validarCamposVeiculo(Veiculo veiculo)
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
        if (verificaNullVazio(veiculo.getCor())) {
            throw new RegraNegocioException("Cor inválida");
        }
        if (verificaNullVazio(veiculo.getGarantia())) {
            throw new RegraNegocioException("Garantia inválida");
        }
        if (verificaNullVazio(veiculo.getConcessionaria().getRazaoSocial())) {
            throw new RegraNegocioException("Concessionária inválida");
        }
        if (veiculo.getModeloVeiculo() == null || veiculo.getModeloVeiculo().getId() == null) {
            throw new RegraNegocioException("Modelo de veículo inválido");
        }

        boolean existe = modeloVeiculoRepository.existsById(veiculo.getModeloVeiculo().getId());

        if (!existe) {
            throw new RegraNegocioException("Modelo de veículo não encontrado");
        }
    }

    public static boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public static boolean verificaPreco(BigDecimal preco)
    {
        return preco == null || preco.compareTo(BigDecimal.ZERO) <= 0;
    }
}
