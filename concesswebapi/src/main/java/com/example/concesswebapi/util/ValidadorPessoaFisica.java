package com.example.concesswebapi.util;
import com.example.concesswebapi.Model.Entity.PessoaFisica;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.concesswebapi.util.ValidadorPessoa.*;

@Component
public class ValidadorPessoaFisica {

    @Autowired
    public ValidadorPessoa validadorPessoa;

    public void validarCamposPessoaFisica(PessoaFisica pessoa){

        validadorPessoa.validarCamposPessoa(pessoa);

        if( verificaNullVazio(pessoa.getNome())){
            throw new RegraNegocioException("Campo Nome inválido");
        }

        if( verificaNullVazio(pessoa.getCpf())){
            throw new RegraNegocioException("Campo CPF inválido");
        }

        if( verificaNullVazio(pessoa.getLogin())){
            throw new RegraNegocioException("Campo Login inválido");
        }

        if( verificaNullVazio(pessoa.getSenha())){
            throw new RegraNegocioException("Campo Senha inválido");
        }
    }
}
