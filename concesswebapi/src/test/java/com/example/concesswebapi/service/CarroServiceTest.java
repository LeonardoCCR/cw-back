package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Carro;
import com.example.concesswebapi.Model.repository.CarroRepository;
import com.example.concesswebapi.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    private CarroService service;

    @Mock
    private CarroRepository repository;

    private Carro carroValido;

    @BeforeEach
    void setUp() {
        carroValido = new Carro();
        carroValido.setMotorizacao("2.0 Turbo");
        carroValido.setTransmissao("Automática");
        carroValido.setPotencia(150.0);
        carroValido.setCategoria("SUV");
    }

    @Test
    @DisplayName("Deve validar com sucesso um carro com dados corretos")
    void deveValidarSucesso() {
        assertDoesNotThrow(() -> service.validar(carroValido));
    }

    @Test
    @DisplayName("Deve lançar erro se Motorização for inválida (Nula ou Apenas Números)")
    void deveFalharMotorizacaoInvalida() {
        carroValido.setMotorizacao(null);
        RegraNegocioException ex1 = assertThrows(RegraNegocioException.class, () -> service.validar(carroValido));
        assertEquals("Motorização inválida", ex1.getMessage());

        carroValido.setMotorizacao("2000");
        RegraNegocioException ex2 = assertThrows(RegraNegocioException.class, () -> service.validar(carroValido));
        assertEquals("Motorização inválida", ex2.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro se Transmissão for inválida (Vazia ou Numérica)")
    void deveFalharTransmissaoInvalida() {
        carroValido.setTransmissao("");
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(carroValido));
        assertEquals("Transmissão inválida", ex.getMessage());

        carroValido.setTransmissao("12345");
        assertThrows(RegraNegocioException.class, () -> service.validar(carroValido));
    }

    @Test
    @DisplayName("Deve lançar erro se Potência for Zero ou Negativa")
    void deveFalharPotenciaInvalida() {
        carroValido.setPotencia(0.0);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(carroValido));
        assertEquals("Potência inválida", ex.getMessage());

        carroValido.setPotencia(-100.0);
        assertThrows(RegraNegocioException.class, () -> service.validar(carroValido));
    }

    @Test
    @DisplayName("Deve lançar erro se Categoria for inválida")
    void deveFalharCategoriaInvalida() {
        carroValido.setCategoria(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.validar(carroValido));
        assertEquals("Categoria inválida", ex.getMessage());
    }
}