package com.example.aep2b.repository;

import com.example.aep2b.model.HistoricoSolicitacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<HistoricoSolicitacaoModel, Long> {
}
