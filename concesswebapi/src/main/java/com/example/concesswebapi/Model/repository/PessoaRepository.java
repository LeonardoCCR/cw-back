package com.example.concesswebapi.Model.repository;

import com.example.concesswebapi.Model.Entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {}
