package com.example.concesswebapi.Model.repository;

import com.example.concesswebapi.Model.Entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {}
