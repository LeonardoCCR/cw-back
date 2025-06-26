package com.example.concesswebapi.util;


import com.example.concesswebapi.Model.Entity.Pessoa;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPessoa {

    public void validarCamposPessoa(Pessoa pessoa) {

        if( verificaNullVazio(pessoa.getEmail1())){
            throw new RegraNegocioException("Campo email1 inválido");
        }

        if( verificaNullVazio(pessoa.getEmail2())){
            // throw new RegraNegocioException("Campo email2 inválido");
        }

        if( verificaNullVazio(pessoa.getTelefone1())){
            throw new RegraNegocioException("Campo telefone1 inválido");
        }

        if( verificaNullVazio(pessoa.getTelefone2())){
            // throw new RegraNegocioException("Campo telefone2 inválido");
        }

        if( verificaNullVazio(pessoa.getLogradouro())){
            throw new RegraNegocioException("Campo logradouro inválido");
        }

        if( verificaNullVazio(pessoa.getNumero())){
            throw new RegraNegocioException("Campo logradouro inválido");
        }

        if( verificaNullVazio(pessoa.getBairro())){
            throw new RegraNegocioException("Campo logradouro inválido");
        }

        if( verificaNullVazio(pessoa.getCep())){
            throw new RegraNegocioException("Campo logradouro inválido");
        }

        if( verificaNullVazio(pessoa.getUf())){
            throw new RegraNegocioException("Campo logradouro inválido");
        }

    }

    public static boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }
}
