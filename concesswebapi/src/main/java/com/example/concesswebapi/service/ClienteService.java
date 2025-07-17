package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Cliente;
import com.example.concesswebapi.Model.repository.ClienteRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.util.ValidadorPessoaFisica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ValidadorPessoaFisica validadorPessoaFisica;

    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

    public List<Cliente> getClientes(){
        return repository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id){
        return repository.findById(id);
    }

    public Optional<Cliente> getClienteByCpf(String cpf) {
        return getClientes().stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst();
    }

    @Transactional
    public Cliente salvar(Cliente cliente){
        validar(cliente);
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente){
        Objects.requireNonNull(cliente.getId());
        repository.delete(cliente);
    }

    public void validar(Cliente cliente){
        validadorPessoaFisica.validarCamposPessoaFisica(cliente);

        if(cliente.getDataNascimento() == null || cliente.getDataNascimento().trim().equals("")){
            throw new RegraNegocioException("Campo Data de Nascimento inválida");
        }
    }
}