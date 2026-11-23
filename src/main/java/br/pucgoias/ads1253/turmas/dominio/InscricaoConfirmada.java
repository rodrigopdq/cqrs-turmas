package br.pucgoias.ads1253.turmas.dominio;

import java.time.Instant;
import java.util.UUID;

/** Evento de domínio: um estudante foi inscrito em uma turma. */
public record InscricaoConfirmada(UUID turmaId, String email, Instant ocorridoEm) {
}
