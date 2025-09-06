package com.example.concesswebapi.service;



import com.example.concesswebapi.Model.Entity.AdmEmpresaGerenciaConcessionaria;
import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.repository.*;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdmEmpresaGerenciaConcessionariaService {

    private AdmEmpresaGerenciaConcessionariaRepository repository;
    private AdmEmpresaRepository admEmpresaRepository;
    private ConcessionariaRepository concessionariaRepository;

    public AdmEmpresaGerenciaConcessionariaService(AdmEmpresaGerenciaConcessionariaRepository repository, AdmEmpresaRepository admEmpresaRepository, ConcessionariaRepository concessionariaRepository) {
        this.repository = repository;
        this.admEmpresaRepository = admEmpresaRepository;
        this.concessionariaRepository = concessionariaRepository;
    }

    public List<AdmEmpresaGerenciaConcessionaria> getAdmsEmpresaGerenciamConcessionarias(){
        return repository.findAll();
    }


    public List<AdmEmpresaGerenciaConcessionaria> getAdmEmpresaGerenciaConcessionariaById(Long id){
        return repository.findByAdmEmpresaId(id);
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

        boolean existeAdmEmpresa = admEmpresaRepository.existsById(admEmpresaGerenciaConcessionaria.getAdmEmpresa().getId());
        boolean existeConcessionaria = concessionariaRepository.existsById(admEmpresaGerenciaConcessionaria.getConcessionaria().getId());

        if (!existeAdmEmpresa || !existeConcessionaria) {
            throw new RegraNegocioException("Adm empresa ou concessionária não encontrados");
        }

    }

}
