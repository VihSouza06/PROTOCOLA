package com.example.aep2b.Repositories;

import com.example.aep2b.Models.HistoricoSolicitacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<HistoricoSolicitacaoModel, Long> {
}
