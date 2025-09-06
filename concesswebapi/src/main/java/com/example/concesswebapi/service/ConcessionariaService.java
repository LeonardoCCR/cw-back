
package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.*;
import com.example.concesswebapi.Model.repository.ConcessionariaRepository;
import com.example.concesswebapi.api.dto.AdmEmpresaDTO;
import com.example.concesswebapi.api.dto.ConcessionariaDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.util.ValidadorPessoaJuridica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConcessionariaService {

    @Autowired
    ValidadorPessoaJuridica validadorPessoaJuridica;
    private final ConcessionariaRepository repository;
    private final AdmEmpresaGerenciaConcessionariaService admEmpresaGerenciaConcessionariaService;

    public ConcessionariaService(ConcessionariaRepository repository, AdmEmpresaGerenciaConcessionariaService admEmpresaGerenciaConcessionariaService) {
        this.repository = repository;
        this.admEmpresaGerenciaConcessionariaService = admEmpresaGerenciaConcessionariaService;
    }

    public List<Concessionaria> getConcessionaria() {
        return repository.findAll();
    }

    public Optional<Concessionaria> getConcessionariaById(Long id) {
        return repository.findById(id);
    }

    public List<Concessionaria> getConcessionariasByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new RegraNegocioException("Pelo menos uma concessionária é obrigatória");
        }
        return repository.findAllById(ids);
    }

    @Transactional
    public Concessionaria salvar(Concessionaria concessionaria) {
        validar(concessionaria);
        return repository.save(concessionaria);
    }

    @Transactional
    public void excluir(Concessionaria concessionaria) {
        Objects.requireNonNull(concessionaria.getId(), "Id da concessionaria não pode ser nulo");
        repository.delete(concessionaria);
    }

    public void sincronizarAdmEmpresaGerenciaConcessionaria(AdmEmpresaDTO dto, AdmEmpresa admEmpresa) {
        List<Concessionaria> concessionarias = this.getConcessionariasByIds(dto.getConcessionariasIds());
        List<AdmEmpresaGerenciaConcessionaria> admEmpresaGerenciaConcessionaria = admEmpresaGerenciaConcessionariaService.getAdmEmpresaGerenciaConcessionariaById(admEmpresa.getId());

        int i = 0;
        if (!admEmpresaGerenciaConcessionaria.isEmpty()) {

            for (AdmEmpresaGerenciaConcessionaria item : admEmpresaGerenciaConcessionaria) {

                if (i >= concessionarias.size()) {
                    admEmpresaGerenciaConcessionariaService.excluir(item);
                }else{
                    item.setConcessionaria(concessionarias.get(i));
                    admEmpresaGerenciaConcessionariaService.salvar(item);
                }

                i++;
            }
        }

        if (i<concessionarias.size()){
            int j = 0;
            for(j = i ; j < concessionarias.size(); j++){
                AdmEmpresaGerenciaConcessionaria novoAdmEmpresaGerenciaConcessionaria = new AdmEmpresaGerenciaConcessionaria();
                novoAdmEmpresaGerenciaConcessionaria.setAdmEmpresa(admEmpresa);
                novoAdmEmpresaGerenciaConcessionaria.setConcessionaria(concessionarias.get(j));
                admEmpresaGerenciaConcessionariaService.salvar(novoAdmEmpresaGerenciaConcessionaria);
            }
        }

    }


    public void validar(Concessionaria concessionaria) {
        validadorPessoaJuridica.validarCamposPessoaJuridica(concessionaria);
    }
}
