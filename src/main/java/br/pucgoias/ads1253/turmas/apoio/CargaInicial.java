package br.pucgoias.ads1253.turmas.apoio;

import br.pucgoias.ads1253.turmas.escrita.AbrirTurma;
import br.pucgoias.ads1253.turmas.escrita.InscreverEstudante;
import br.pucgoias.ads1253.turmas.escrita.TurmaComandos;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Cria os dados do estudo: 100 turmas com capacidade para 30 estudantes
 * e quantidade variável de inscritos (de 0 a 20), totalizando 976 inscrições.
 */
@Component
public class CargaInicial {

    public static final int TURMAS = 100;

    private final TurmaComandos comandos;

    public CargaInicial(TurmaComandos comandos) {
        this.comandos = comandos;
    }

    /** @return identificador da primeira turma criada (Turma 001) */
    public UUID executar() {
        UUID primeira = null;
        for (int i = 1; i <= TURMAS; i++) {
            UUID turma = comandos.abrir(new AbrirTurma(String.format("Turma %03d", i), 30));
            for (int j = 1; j <= i % 21; j++) {
                comandos.inscrever(new InscreverEstudante(turma, "estudante" + j + "@pucgoias.edu.br"));
            }
            if (primeira == null) {
                primeira = turma;
            }
        }
        return primeira;
    }
}