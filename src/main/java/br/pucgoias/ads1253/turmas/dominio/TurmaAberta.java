package br.pucgoias.ads1253.turmas.dominio;

import java.time.Instant;
import java.util.UUID;

/** Evento de domínio: uma turma foi aberta. */
public record TurmaAberta(UUID turmaId, String nome, int capacidade, Instant ocorridoEm) {
}
