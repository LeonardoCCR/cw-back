package com.example.concesswebapi.Model.repository;

import com.example.concesswebapi.Model.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    List<Veiculo> findByVendidoFalse();

}