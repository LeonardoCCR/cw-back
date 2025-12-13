package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.Model.Entity.Gestor;
import com.example.concesswebapi.Model.Entity.GestorConcessionaria;
import com.example.concesswebapi.Model.repository.GestorConcessionariaRepository;
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
class GestorConcessionariaServiceTest {

    @InjectMocks
    private GestorConcessionariaService service;

    @Mock
    private GestorConcessionariaRepository repository;

    private GestorConcessionaria gestorConcessionaria;

    @BeforeEach
    void setUp() {
        gestorConcessionaria = new GestorConcessionaria();

        Gestor gestor = new Gestor();
        gestor.setId(1L);
        gestorConcessionaria.setGestor(gestor);

        Concessionaria concessionaria = new Concessionaria();
        concessionaria.setId(10L);
        gestorConcessionaria.setConcessionaria(concessionaria);
    }

    @Test
    @DisplayName("Deve salvar com sucesso quando Gestor e Concessionária são válidos")
    void deveSalvarSucesso() {
        assertDoesNotThrow(() -> service.salvar(gestorConcessionaria));
    }

    @Test
    @DisplayName("Deve lançar erro quando Gestor for nulo")
    void deveFalharGestorNulo() {
        gestorConcessionaria.setGestor(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.salvar(gestorConcessionaria));
        assertEquals("Campo gestor inválido ", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando ID do Gestor for nulo")
    void deveFalharIdGestorNulo() {
        gestorConcessionaria.getGestor().setId(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.salvar(gestorConcessionaria));
        assertEquals("Campo gestor inválido ", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando ID do Gestor for Zero")
    void deveFalharIdGestorZero() {
        gestorConcessionaria.getGestor().setId(0L);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.salvar(gestorConcessionaria));
        assertEquals("Campo gestor inválido ", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando Concessionária for nula")
    void deveFalharConcessionariaNula() {
        gestorConcessionaria.setConcessionaria(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.salvar(gestorConcessionaria));
        assertEquals("Campo concessionaria inválido", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando ID da Concessionária for nulo")
    void deveFalharIdConcessionariaNulo() {
        gestorConcessionaria.getConcessionaria().setId(null);
        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () -> service.salvar(gestorConcessionaria));
        assertEquals("Campo concessionaria inválido", ex.getMessage());
    }
}