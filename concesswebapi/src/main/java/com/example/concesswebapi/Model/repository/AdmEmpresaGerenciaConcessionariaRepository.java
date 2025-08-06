package com.example.concesswebapi.Model.repository;

import com.example.concesswebapi.Model.Entity.AdmEmpresa;
import com.example.concesswebapi.Model.Entity.AdmEmpresaGerenciaConcessionaria;
import com.example.concesswebapi.Model.Entity.Concessionaria;
import com.example.concesswebapi.Model.Entity.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdmEmpresaGerenciaConcessionariaRepository extends JpaRepository<AdmEmpresaGerenciaConcessionaria, Long> {

    List<AdmEmpresaGerenciaConcessionaria> findByAdmEmpresaId(Long admEmpresaId);
}
