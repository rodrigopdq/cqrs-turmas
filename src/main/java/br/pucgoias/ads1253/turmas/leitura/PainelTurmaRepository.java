package br.pucgoias.ads1253.turmas.leitura;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PainelTurmaRepository extends Repository<PainelTurma, UUID> {

    PainelTurma save(PainelTurma painel);

    Optional<PainelTurma> findById(UUID turmaId);

    List<PainelTurma> findAllByOrderByNomeAsc();

    /** Remove todas as linhas com um único comando SQL. */
    @Modifying
    @Query("delete from PainelTurma")
    void apagarTudo();
}