package br.pucgoias.ads1253.turmas.escrita;

import java.util.UUID;

/** Comando: intenção de inscrever um estudante em uma turma. */
public record InscreverEstudante(UUID turmaId, String email) {
}
