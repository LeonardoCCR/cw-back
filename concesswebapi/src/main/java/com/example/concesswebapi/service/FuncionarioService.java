package com.example.concesswebapi.service;


import com.example.concesswebapi.Model.Entity.Funcionario;
import com.example.concesswebapi.Model.repository.FuncionarioRepository;
import com.example.concesswebapi.util.ValidadorFuncionario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository  repository){
        this.repository = repository; //acesso ao banco de dados
    }

    public List<Funcionario> getFuncionarios(){
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar( Funcionario funcionario){
        validar(funcionario);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario){
        Objects.requireNonNull(funcionario.getId()); //tem que ser existente no BD
        repository.delete(funcionario);
    }

    public void validar(Funcionario funcionario) {
        ValidadorFuncionario.validarCamposFuncionario(funcionario);
    }
}
