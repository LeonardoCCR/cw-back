package com.example.concesswebapi.util;

import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.VeiculoTemAcessorio;
import com.example.concesswebapi.exception.RegraNegocioException;

import java.util.List;

public class ValidadorVeiculo {

    public static void validarCamposVeiculo(Veiculo veiculo)
    {
        if (verificaNullVazio(veiculo.getChassi())) {
            throw new RegraNegocioException("Chassi inválido");
        }
        if (verificaNullVazio(veiculo.getModeloVeiculo().getModelo().getNome())) {
            throw new RegraNegocioException("Modelo inválido");
        }
        if (verificaNullVazio(veiculo.getModeloVeiculo().getAnoFabricacao())) {
            throw new RegraNegocioException("Ano de fabricação inválido");
        }
        if (verificaValor((veiculo.getModeloVeiculo().getPrecoBase()))) {
            throw new RegraNegocioException("Preço base inválido");
        }
        if (verificaValor(veiculo.getPrecoAtual())) {
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
        if (verificaNullVazio(veiculo.getModeloVeiculo().getTipo())) {
            throw new RegraNegocioException("Tipo inválido");
        }
        if (verificaValor((veiculo.getModeloVeiculo().getQtdEstoque()))) {
            throw new RegraNegocioException("Quantidade de estoque inválida");
        }
        if (verificaValor((veiculo.getModeloVeiculo().getQtdEstoqueVenda()))) {
            throw new RegraNegocioException("Quantidade de estoque inválida");
        }
        if (verificaNullVazio(veiculo.getConcessionaria().getRazaoSocial())) {
            throw new RegraNegocioException("Concessionária inválida");
        }
        if (verificaNullVazio(veiculo.getModeloVeiculo().getFotoModelo())) {
            throw new RegraNegocioException("Foto inválida");
        }
        if (verificaNullVazio(veiculo.getModeloVeiculo().getTipoVeiculo().getCategoria())) {
            throw new RegraNegocioException("Categoria inválida");
        }
    }

    public static boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public static boolean verificaValor(float valor)
    {
        return valor <= 0;
    }
}
