package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.AdmEmpresa;
import com.example.concesswebapi.Model.repository.AdmEmpresaRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.util.ValidadorPessoaFisica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.List;

@Service
public class AdmEmpresaService {

    @Autowired
    private ValidadorPessoaFisica validadorPessoaFisica;
    private AdmEmpresaRepository  repository;

    public AdmEmpresaService(AdmEmpresaRepository repository){
        this.repository = repository;
    }

    public List<AdmEmpresa> getAdmsEmpresas(){
        return repository.findAll();
    }

    public Optional<AdmEmpresa> getAdmEmpresaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public AdmEmpresa salvar(AdmEmpresa admEmpresa){
        validar(admEmpresa);
        return repository.save(admEmpresa);
    }

    @Transactional
    public void excluir(AdmEmpresa admEmpresa){
        Objects.requireNonNull(admEmpresa.getId());
        repository.delete(admEmpresa);
    }

    public void validar(AdmEmpresa admEmpresa){

        validadorPessoaFisica.validarCamposPessoaFisica(admEmpresa);
    //por conta da ser uma relação com atributo inverso não podemos cadastrar de forma fácil apenas uma empresa

       // if(admEmpresa.getEmpresa() == null || admEmpresa.getEmpresa().getId() == null || admEmpresa.getEmpresa().getId() == 0) {
       //     throw new RegraNegocioException("Campo empresa inválido");
       // }
    }
}
