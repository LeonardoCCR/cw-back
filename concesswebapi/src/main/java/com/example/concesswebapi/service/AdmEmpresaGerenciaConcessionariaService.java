package com.example.concesswebapi.service;



import com.example.concesswebapi.Model.Entity.AdmEmpresaGerenciaConcessionaria;
import com.example.concesswebapi.Model.repository.AdmEmpresaGerenciaConcessionariaRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdmEmpresaGerenciaConcessionariaService {

    private AdmEmpresaGerenciaConcessionariaRepository repository;

    public AdmEmpresaGerenciaConcessionariaService ( AdmEmpresaGerenciaConcessionariaRepository repository){
        this.repository = repository;
    }

    public List<AdmEmpresaGerenciaConcessionaria> getTodosAdmsEmpresaGerenciamConcessionarias(){
        return repository.findAll();
    }

    public Optional<AdmEmpresaGerenciaConcessionaria> getAdmEmpresaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public AdmEmpresaGerenciaConcessionaria salvar( AdmEmpresaGerenciaConcessionaria admEmpresaGerenciaConcessionaria){
        validar(admEmpresaGerenciaConcessionaria);
        return repository.save(admEmpresaGerenciaConcessionaria);
    }

    @Transactional
    public void excluir(AdmEmpresaGerenciaConcessionaria admEmpresaGerenciaConcessionaria){
        Objects.requireNonNull(admEmpresaGerenciaConcessionaria.getId());
        repository.delete(admEmpresaGerenciaConcessionaria);
    }

    private void validar(AdmEmpresaGerenciaConcessionaria admEmpresaGerenciaConcessionaria){

        if(admEmpresaGerenciaConcessionaria.getAdmEmpresa() == null || admEmpresaGerenciaConcessionaria.getAdmEmpresa().getId() == null || admEmpresaGerenciaConcessionaria.getAdmEmpresa().getId() == 0 ){
            throw new RegraNegocioException("Campo adm empresa inválido");
        }

        if( admEmpresaGerenciaConcessionaria.getConcessionaria() == null || admEmpresaGerenciaConcessionaria.getConcessionaria().getId() == null || admEmpresaGerenciaConcessionaria.getConcessionaria().getId() == 0){
            throw new RegraNegocioException("Campo concessionaria inválido");
        }

    }

}
