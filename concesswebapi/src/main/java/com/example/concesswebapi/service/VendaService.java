package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Venda;
import com.example.concesswebapi.Model.repository.VendaRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VendaService {

    private final VendaRepository repository;

    public VendaService(VendaRepository repository) {
        this.repository = repository;
    }

    public List<Venda> getVendas() {
        return repository.findAll();
    }

    public Optional<Venda> getVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Venda salvar(Venda venda) {
        validar(venda);
        return repository.save(venda);
    }

    @Transactional
    public void excluir(Venda venda) {
        Objects.requireNonNull(venda.getId(), "ID da venda não pode ser nulo.");
        repository.delete(venda);
    }

    public void validar(Venda venda) {
        if (venda.getData() == null || venda.getData().trim().isEmpty()) {
            throw new RegraNegocioException("Data inválida.");
        }
        if (venda.getFormaPag() == null || venda.getFormaPag().trim().isEmpty()) {
            throw new RegraNegocioException("Forma de pagamento inválida.");
        }
        if (venda.getDescontoTotal() == null || venda.getDescontoTotal() < 0) {
            throw new RegraNegocioException("Desconto inválido.");
        }
        if (venda.getCliente() == null || venda.getCliente().getId() == null) {
            throw new RegraNegocioException("Cliente inválido.");
        }
        if (venda.getVendedor() == null || venda.getVendedor().getId() == null) {
            throw new RegraNegocioException("Vendedor inválido.");
        }
    }
}
