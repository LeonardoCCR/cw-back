package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.ItensVenda;
import com.example.concesswebapi.Model.Entity.ModeloVeiculo;
import com.example.concesswebapi.Model.Entity.VeiculoUsado;
import com.example.concesswebapi.Model.Entity.Venda;
import com.example.concesswebapi.Model.repository.ItensVendaRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItensVendaServiceTest {

    @InjectMocks
    private ItensVendaService service;

    @Mock
    private ItensVendaRepository repository;

    private ItensVenda itemValido;

    @BeforeEach
    void setUp() {
        itemValido = new ItensVenda();
        itemValido.setDescontoParcial(new BigDecimal("10.0"));

        Venda venda = new Venda();
        venda.setId(1L);
        itemValido.setVenda(venda);

        ModeloVeiculo modelo = new ModeloVeiculo();
        modelo.setId(1L);
        itemValido.setModeloVeiculo(modelo);

        VeiculoUsado veiculo = new VeiculoUsado();
        veiculo.setId(1L);
        itemValido.setVeiculo(veiculo);
    }

    @Test
    @DisplayName("Deve validar com sucesso um item correto")
    void deveValidarSucesso() {
        assertDoesNotThrow(() -> service.validar(itemValido));
    }

    @Test
    @DisplayName("Deve lançar erro se Desconto Parcial for negativo")
    void deveFalharDescontoNegativo() {
        itemValido.setDescontoParcial(new BigDecimal("-5.00"));
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(itemValido));
        assertEquals("Desconto parcial inválido.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro se Venda for nula")
    void deveFalharVendaNula() {
        itemValido.setVenda(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(itemValido));
        assertEquals("Venda inválida.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro se ModeloVeiculo for nulo")
    void deveFalharModeloNulo() {
        itemValido.setModeloVeiculo(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(itemValido));
        assertEquals("Modelo do veículo inválido.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro se Veículo for nulo")
    void deveFalharVeiculoNulo() {
        itemValido.setVeiculo(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(itemValido));
        assertEquals("Veículo inválido.", ex.getMessage());
    }
}