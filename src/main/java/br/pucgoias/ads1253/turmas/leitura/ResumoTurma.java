package br.pucgoias.ads1253.turmas.leitura;

import java.util.UUID;

/** Projeção: dados básicos de uma turma, sem os inscritos. */
public record ResumoTurma(UUID id, String nome, int capacidade) {
}