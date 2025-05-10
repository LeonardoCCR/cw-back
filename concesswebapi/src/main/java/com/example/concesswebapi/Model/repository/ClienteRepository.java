package com.example.concesswebapi.Model.repository;


import com.example.concesswebapi.Model.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
