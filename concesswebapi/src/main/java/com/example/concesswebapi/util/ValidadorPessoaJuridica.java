package com.example.concesswebapi.util;

import com.example.concesswebapi.Model.Entity.PessoaJuridica;
import com.example.concesswebapi.exception.RegraNegocioException;

import static com.example.concesswebapi.util.ValidadorPessoa.validarCamposPessoa;
import static com.example.concesswebapi.util.ValidadorPessoa.verificaNullVazio;

public class ValidadorPessoaJuridica {

    public static void validarCamposPessoaJuridica(PessoaJuridica pessoa){

        validarCamposPessoa(pessoa);

        if( verificaNullVazio(pessoa.getCnpj())){
            throw new RegraNegocioException("CNPJ inválido");
        }

        if( verificaNullVazio(pessoa.getRazaoSocial())){
            throw new RegraNegocioException("Razão Social inválida");
        }

    }
}
