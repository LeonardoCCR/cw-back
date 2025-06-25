package com.example.concesswebapi.util;

import com.example.concesswebapi.Model.Entity.PessoaJuridica;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.example.concesswebapi.util.ValidadorPessoa.verificaNullVazio;

@Component
public class ValidadorPessoaJuridica {

    @Autowired
    private ValidadorPessoa validadorPessoa;

    public void validarCamposPessoaJuridica(PessoaJuridica pessoa){

        validadorPessoa.validarCamposPessoa(pessoa);

        if( verificaNullVazio(pessoa.getCnpj())){
            throw new RegraNegocioException("CNPJ inválido");
        }

        if( verificaNullVazio(pessoa.getRazaoSocial())){
            throw new RegraNegocioException("Razão Social inválida");
        }

    }
}
