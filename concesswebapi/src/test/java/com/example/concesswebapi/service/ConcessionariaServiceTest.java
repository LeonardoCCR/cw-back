package com.example.concesswebapi.service;

import com.example.concesswebapi.Model.Entity.AdmEmpresa;
import com.example.concesswebapi.Model.Entity.AdmEmpresaGerenciaConcessionaria;
import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.Model.repository.ConcessionariaRepository;
import com.example.concesswebapi.api.dto.AdmEmpresaDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.util.ValidadorPessoaJuridica;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcessionariaServiceTest {

    @InjectMocks
    private ConcessionariaService service;

    @Mock
    private ConcessionariaRepository repository;

    @Mock
    private AdmEmpresaGerenciaConcessionariaService admService;

    @Mock
    private ValidadorPessoaJuridica validadorPessoaJuridica;

    @Test
    @DisplayName("Sincronizar: Deve adicionar novas relações quando DTO > Banco")
    void deveSincronizarAdicionando() {
        AdmEmpresa adm = new AdmEmpresa();
        adm.setId(1L);

        AdmEmpresaGerenciaConcessionaria relacaoExistente = new AdmEmpresaGerenciaConcessionaria();
        when(admService.getAdmEmpresaGerenciaConcessionariaById(1L))
                .thenReturn(Collections.singletonList(relacaoExistente));

        AdmEmpresaDTO dto = new AdmEmpresaDTO();
        dto.setConcessionariasIds(Arrays.asList(10L, 20L));

        Concessionaria c1 = new Concessionaria(); c1.setId(10L);
        Concessionaria c2 = new Concessionaria(); c2.setId(20L);
        when(repository.findAllById(dto.getConcessionariasIds())).thenReturn(Arrays.asList(c1, c2));

        service.sincronizarAdmEmpresaGerenciaConcessionaria(dto, adm);

        verify(admService, times(1)).salvar(relacaoExistente);
        verify(admService, times(1)).salvar(argThat(relacao ->
                relacao.getConcessionaria().getId().equals(20L)
        ));
    }

    @Test
    @DisplayName("Sincronizar: Deve remover relações excedentes quando DTO < Banco")
    void deveSincronizarRemovendo() {
        AdmEmpresa adm = new AdmEmpresa();
        adm.setId(1L);

        AdmEmpresaGerenciaConcessionaria r1 = new AdmEmpresaGerenciaConcessionaria();
        AdmEmpresaGerenciaConcessionaria r2 = new AdmEmpresaGerenciaConcessionaria();
        when(admService.getAdmEmpresaGerenciaConcessionariaById(1L))
                .thenReturn(Arrays.asList(r1, r2));

        AdmEmpresaDTO dto = new AdmEmpresaDTO();
        dto.setConcessionariasIds(Collections.singletonList(10L));

        Concessionaria c1 = new Concessionaria(); c1.setId(10L);
        when(repository.findAllById(dto.getConcessionariasIds())).thenReturn(Collections.singletonList(c1));

        // AÇÃO
        service.sincronizarAdmEmpresaGerenciaConcessionaria(dto, adm);

        verify(admService, times(1)).salvar(r1);
        verify(admService, times(1)).excluir(r2);
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar concessionárias com lista vazia")
    void deveFalharBuscaListaVazia() {
        List<Long> listaVazia = new ArrayList<>();

        RegraNegocioException ex = assertThrows(RegraNegocioException.class, () ->
                service.getConcessionariasByIds(listaVazia)
        );

        assertEquals("Pelo menos uma concessionária é obrigatória", ex.getMessage());
    }

    @Test
    @DisplayName("Deve chamar o validador corretamente no método salvar")
    void deveValidarAoSalvar() {
        Concessionaria c = new Concessionaria();

        service.salvar(c);

        verify(validadorPessoaJuridica, times(1)).validarCamposPessoaJuridica(c);
        verify(repository, times(1)).save(c);
    }
}