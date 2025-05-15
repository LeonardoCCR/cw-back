package com.example.concesswebapi.util;
import com.example.concesswebapi.Model.Entity.PessoaFisica;
import com.example.concesswebapi.exception.RegraNegocioException;
import static com.example.concesswebapi.util.ValidadorPessoa.*;

public class ValidadorPessoaFisica {

    public static void validarCamposPessoaFisica(PessoaFisica pessoa){

        validarCamposPessoa(pessoa);

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
