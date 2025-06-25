package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Vendedor;
import com.example.concesswebapi.Model.repository.VendedorRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.util.ValidadorFuncionario;
import com.example.concesswebapi.util.ValidadorPessoaFisica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

import java.util.List;
//repository << vendedor repository <<  jpa repository << métodos consulta/alteração e conexão bd

@Service
public class VendedorService {

    @Autowired
    private ValidadorFuncionario validadorFuncionario;
    private VendedorRepository repository;

    public VendedorService(VendedorRepository repository){
        this.repository = repository; //acesso ao banco de dados
    }

    public List<Vendedor> getVendedores(){
        return repository.findAll();
    }

    public Optional<Vendedor> getVendedorById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Vendedor salvar(Vendedor vendedor){
        validar(vendedor);
        return repository.save(vendedor);
    }

    @Transactional
    public void excluir(Vendedor vendedor){
        Objects.requireNonNull(vendedor.getId()); //tem que ser existente no BD
        repository.delete(vendedor);
    }

    public void validar(Vendedor vendedor){
        validadorFuncionario.validarCamposFuncionario(vendedor);

        if( vendedor.getConcessionaria() == null || vendedor.getConcessionaria().getId() == null || vendedor.getConcessionaria().getId() == 0){
             throw new RegraNegocioException("Campo concessionária inválido");
        }
    }

}
