package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Venda;
import com.example.concesswebapi.Model.repository.VendaRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

import java.util.List;

@Service
public class VendaService {

    private VendaRepository repository;

    public VendaService(VendaRepository repository) { this.repository = repository; }

    public List<Venda> getVendas(){
        return repository.findAll();
    }

    public Optional<Venda> getVendaById(Long id) { return repository.findById(id); }

    @Transactional
    public Venda salvar(Venda venda){
        validar(venda);
        return repository.save(venda);
    }

    @Transactional
    public void excluir(Venda venda){
        Objects.requireNonNull(venda.getId());
        repository.delete(venda);
    }

    public void validar(Venda venda)
    {
        if (verificaNullVazio(venda.getData())) {
            throw new RegraNegocioException("Data inválida");
        }
        if (verificaNullVazio(venda.getFormaPag())) {
            throw new RegraNegocioException("Forma de Pagamento inválida");
        }
        if (verificaValor(venda.getDescontoTotal())) {
            throw new RegraNegocioException("Desconto Total inválido");
        }
        if (verificaNullVazio(venda.getAprovada())) {
            throw new RegraNegocioException("Status de Aprovado inválido");
        }
        if(venda.getVendedor() == null || venda.getVendedor().getId() == null || venda.getVendedor().getId() == 0){
            throw new RegraNegocioException("Campo Vendedor inválido");
        }
        if(venda.getCliente() == null || venda.getCliente().getId() == null || venda.getCliente().getId() == 0){
            throw new RegraNegocioException("Campo Cliente inválido");
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public boolean verificaValor(Double valor) {
        return valor == null || valor < 0;
    }
}
