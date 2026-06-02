package com.example.aep2b.repository;

import com.example.aep2b.enums.Categoria;
import com.example.aep2b.enums.Prioridade;
import com.example.aep2b.model.SolicitacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository extends JpaRepository<SolicitacaoModel, Long> {
    Optional<SolicitacaoModel> findByProtocolo(String protocolo);

    List<SolicitacaoModel> findByPrioridade(Prioridade prioridade);

    List<SolicitacaoModel> findByCategoria(Categoria categoria);

    List<SolicitacaoModel> findByEnderecoContainingIgnoreCase(String endereco);

    @Query("SELECT s FROM SolicitacaoModel s WHERE " +
            "(:prioridade IS NULL OR s.prioridade = :prioridade) AND " +
            "(:categoria IS NULL OR s.categoria = :categoria) AND " +
            "(:endereco IS NULL OR LOWER(s.endereco) LIKE LOWER(CONCAT('%', :endereco, '%')))")
    List<SolicitacaoModel> findComFiltros(
            @Param("prioridade") Prioridade prioridade,
            @Param("categoria") Categoria categoria,
            @Param("endereco") String endereco
    );
}
