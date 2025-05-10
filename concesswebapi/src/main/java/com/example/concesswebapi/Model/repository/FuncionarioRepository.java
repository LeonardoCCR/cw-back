package com.example.concesswebapi.Model.repository;

import com.example.concesswebapi.Model.Entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {}
