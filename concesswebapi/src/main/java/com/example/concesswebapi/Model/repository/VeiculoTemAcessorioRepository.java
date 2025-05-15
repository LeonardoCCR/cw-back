package com.example.concesswebapi.Model.repository;

import com.example.concesswebapi.Model.Entity.VeiculoTemAcessorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoTemAcessorioRepository extends JpaRepository<VeiculoTemAcessorio, Long>
{
    List<VeiculoTemAcessorio> findByVeiculoId(Long veiculoId);
}
