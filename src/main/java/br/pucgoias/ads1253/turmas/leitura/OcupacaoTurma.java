package br.pucgoias.ads1253.turmas.leitura;

import java.util.UUID;

/** Projeção: linha do painel de ocupação. */
public record OcupacaoTurma(UUID turmaId, String nome, int capacidade, long inscritos) {

    public long vagas() {
        return capacidade - inscritos;
    }
}
