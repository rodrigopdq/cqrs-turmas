package br.pucgoias.ads1253.turmas.escrita;

import br.pucgoias.ads1253.turmas.dominio.Turma;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface TurmaRepository extends Repository<Turma, UUID> {

    Turma save(Turma turma);

    Optional<Turma> findById(UUID id);
}