package br.pucgoias.ads1253.turmas.leitura;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/** Tabela de leitura dedicada ao painel: reflete o estado das turmas, atualizada por projeção. */
@Entity
@Table(name = "painel_turma")
public class PainelTurma {

    @Id
    private UUID turmaId;

    private String nome;

    private int capacidade;

    private int inscritos;

    protected PainelTurma() {
    }

    public PainelTurma(UUID turmaId, String nome, int capacidade, int inscritos) {
        this.turmaId = turmaId;
        this.nome = nome;
        this.capacidade = capacidade;
        this.inscritos = inscritos;
    }

    public UUID getTurmaId() {
        return turmaId;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getInscritos() {
        return inscritos;
    }

    public int getVagas() {
        return capacidade - inscritos;
    }

    public void registrarInscricao() {
        this.inscritos++;
    }

    @Override
    public String toString() {
        return String.format("PainelTurma[turma=%s, cap=%d, insc=%d, vagas=%d]",
                nome, capacidade, inscritos, getVagas());
    }
}