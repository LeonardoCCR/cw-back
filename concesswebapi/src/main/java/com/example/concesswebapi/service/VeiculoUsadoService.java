package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import com.example.concesswebapi.Model.repository.ModeloRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.Model.repository.VeiculoUsadoRepository;
import com.example.concesswebapi.util.ValidadorVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VeiculoUsadoService {

    @Autowired
    private ValidadorVeiculo validadorVeiculo;

    private VeiculoUsadoRepository repository;
    private ModeloRepository modeloRepository;

    public VeiculoUsadoService(VeiculoUsadoRepository repository, ModeloRepository modeloRepository) {

        this.repository = repository;
        this.modeloRepository = modeloRepository; // Inicialização do modeloRepository, se necessário
    }

    public List<VeiculoUsado> getVeiculosUsados() {
        return repository.findAll();
    }

    public Optional<VeiculoUsado> getVeiculoUsadoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public VeiculoUsado salvar(VeiculoUsado veiculoUsado) {
        validadorVeiculo.validarCamposVeiculo(veiculoUsado);
        validar(veiculoUsado);
        return repository.save(veiculoUsado);
    }

    @Transactional
    public void excluir(VeiculoUsado veiculoUsado) {
        Objects.requireNonNull(veiculoUsado.getId());
        repository.delete(veiculoUsado);
    }

    public void validar(VeiculoUsado veiculo)
    {
        if (verificaValor(veiculo.getQuilometragem())) {
            throw new RegraNegocioException("Quilometragem inválida");
        }
        if (verificaNullVazio(veiculo.getDataUltimaRevisao()) || verificaNumero(veiculo.getDataUltimaRevisao())) {
            throw new RegraNegocioException("Data da última revisão inválida");
        }
        if (verificaNullVazio(veiculo.getLaudoVistoria()) || verificaNumero(veiculo.getLaudoVistoria())) {
            throw new RegraNegocioException("Laudo da vistoria inválido");
        }
        if (verificaNullVazio(veiculo.getDocumentacao()) || verificaNumero(veiculo.getDocumentacao())) {
            throw new RegraNegocioException("Documentação inválida");
        }
        if (verificaNullVazio(veiculo.getManutencao()) || verificaNumero(veiculo.getManutencao())) {
            throw new RegraNegocioException("Manutenção inválida");
        }
        if (verificaNullVazio(veiculo.getManutencao()) || verificaNumero(veiculo.getManutencao())) {
            throw new RegraNegocioException("Sinistro de acidente inválido");
        }
        if(verificaNullVazio(veiculo.getSinistroAcidente()) || verificaNumero(veiculo.getSinistroAcidente())) {
            throw new RegraNegocioException("Sinistro de acidente inválido");
        }
    }

    public boolean verificaNullVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public boolean verificaValor(Double valor)
    {
        return valor == null;
    }

    public boolean verificaNumero(String campo) {
        return campo.matches("-?\\d+(\\.\\d+)?");
    }
}
