package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Modelo;
import com.example.concesswebapi.Model.Entity.ModeloVeiculo;
import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import com.example.concesswebapi.Model.repository.VeiculoUsadoRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.util.ValidadorVeiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoUsadoServiceTest {

    @InjectMocks
    private VeiculoUsadoService service;

    @Mock
    private VeiculoUsadoRepository repository;

    @Mock
    private ValidadorVeiculo validadorVeiculo;

    private VeiculoUsado veiculoValido;

    @BeforeEach
    void setUp() {
        veiculoValido = new VeiculoUsado();
        veiculoValido.setId(1L);
        veiculoValido.setQuilometragem(50000.0);
        veiculoValido.setDataUltimaRevisao("10/01/2023");
        veiculoValido.setLaudoVistoria("Aprovado");
        veiculoValido.setDocumentacao("OK");
        veiculoValido.setManutencao("Em dia");
        veiculoValido.setSinistroAcidente("Nenhum");
    }

    @Test
    @DisplayName("Deve validar com sucesso um veículo usado completo")
    void deveValidarSucesso() {
        assertDoesNotThrow(() -> service.validar(veiculoValido));
    }

    @Test
    @DisplayName("Deve falhar se Quilometragem for inválida (Nula)")
    void deveFalharQuilometragem() {
        veiculoValido.setQuilometragem(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(veiculoValido));
        assertEquals("Quilometragem inválida", ex.getMessage());
    }

    @Test
    @DisplayName("Deve falhar se Data da Revisão for Nula ou Numérica")
    void deveFalharDataRevisao() {
        veiculoValido.setDataUltimaRevisao(null);
        assertThrows(RegraNegocioException.class, () -> service.validar(veiculoValido));

        veiculoValido.setDataUltimaRevisao("123456");
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(veiculoValido));
        assertEquals("Data da última revisão inválida", ex.getMessage());
    }

    @Test
    @DisplayName("Deve falhar se Laudo for Vazio")
    void deveFalharLaudo() {
        veiculoValido.setLaudoVistoria("");
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(veiculoValido));
        assertEquals("Laudo da vistoria inválido", ex.getMessage());
    }

    @Test
    @DisplayName("Deve falhar se Documentação for Numérica")
    void deveFalharDocumentacao() {
        veiculoValido.setDocumentacao("9999");
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(veiculoValido));
        assertEquals("Documentação inválida", ex.getMessage());
    }

    @Test
    @DisplayName("Deve falhar se Sinistro for Nulo")
    void deveFalharSinistro() {
        veiculoValido.setSinistroAcidente(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(veiculoValido));
        assertEquals("Sinistro de acidente inválido", ex.getMessage());
    }


    @Test
    @DisplayName("Deve encontrar veículo disponível pelo nome do modelo")
    void deveEncontrarVeiculoPorModelo() {

        VeiculoUsado v1 = new VeiculoUsado();
        v1.setVendido(false);

        Modelo modelo = new Modelo();
        modelo.setNome("Civic");

        ModeloVeiculo mv = new ModeloVeiculo();
        mv.setModelo(modelo);
        v1.setModeloVeiculo(mv);

        when(repository.findAll()).thenReturn(Collections.singletonList(v1));

        Optional<VeiculoUsado> resultado = service.getVeiculoDisponivelPorModelo("Civic");

        assertTrue(resultado.isPresent());
        assertEquals(v1, resultado.get());
    }

    @Test
    @DisplayName("Não deve encontrar veículo se estiver vendido")
    void naoDeveEncontrarSeVendido() {
        VeiculoUsado v1 = new VeiculoUsado();
        v1.setVendido(true);

        Modelo modelo = new Modelo();
        modelo.setNome("Civic");

        ModeloVeiculo mv = new ModeloVeiculo();
        mv.setModelo(modelo);
        v1.setModeloVeiculo(mv);

        when(repository.findAll()).thenReturn(Collections.singletonList(v1));

        Optional<VeiculoUsado> resultado = service.getVeiculoDisponivelPorModelo("Civic");

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Não deve encontrar veículo se o modelo for diferente")
    void naoDeveEncontrarModeloDiferente() {
        VeiculoUsado v1 = new VeiculoUsado();
        v1.setVendido(false);

        Modelo modelo = new Modelo();
        modelo.setNome("Corolla");

        ModeloVeiculo mv = new ModeloVeiculo();
        mv.setModelo(modelo);
        v1.setModeloVeiculo(mv);

        when(repository.findAll()).thenReturn(Collections.singletonList(v1));

        Optional<VeiculoUsado> resultado = service.getVeiculoDisponivelPorModelo("Civic");

        assertFalse(resultado.isPresent());
    }
}