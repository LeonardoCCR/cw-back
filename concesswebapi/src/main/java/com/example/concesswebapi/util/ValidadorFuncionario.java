package com.example.concesswebapi.util;

import com.example.concesswebapi.Model.Entity.Empresa;
import com.example.concesswebapi.Model.Entity.Funcionario;
import com.example.concesswebapi.exception.RegraNegocioException;




public class ValidadorFuncionario {

    public static void validarCamposFuncionario( Funcionario funcionario){

        ValidadorPessoaFisica.validarCamposPessoaFisica(funcionario);

        if(ValidadorPessoa.verificaNullVazio(funcionario.getCargo())){
            throw new RegraNegocioException("Campo cargo inválido");
        }

        if(funcionario.getEmpresa() == null || funcionario.getEmpresa().getId() ==null ||funcionario.getEmpresa().getId()==0){
            throw new RegraNegocioException("Campo empresa inválido");
        }
    }
}




