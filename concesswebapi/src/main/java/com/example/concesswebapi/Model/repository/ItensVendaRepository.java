package com.example.concesswebapi.Model.repository;

import com.example.concesswebapi.Model.Entity.ItensVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItensVendaRepository extends JpaRepository<ItensVenda, Long> {

    List<ItensVenda> findByVendaId(Long vendaId);

}