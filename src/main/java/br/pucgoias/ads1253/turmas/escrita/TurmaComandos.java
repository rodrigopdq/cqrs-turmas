package br.pucgoias.ads1253.turmas.escrita;

import br.pucgoias.ads1253.turmas.dominio.RegraDeNegocioException;
import br.pucgoias.ads1253.turmas.dominio.Turma;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/** Lado da escrita: um método para cada comando. */
@Service
public class TurmaComandos {

    private final TurmaRepository turmas;

    public TurmaComandos(TurmaRepository turmas) {
        this.turmas = turmas;
    }

    @Transactional
    public UUID abrir(AbrirTurma comando) {
        Turma turma = Turma.abrir(comando.nome(), comando.capacidade());
        turmas.save(turma);
        return turma.getId();
    }

    @Transactional
    public void inscrever(InscreverEstudante comando) {
        Turma turma = turmas.findById(comando.turmaId())
                .orElseThrow(() -> new RegraDeNegocioException("Turma não encontrada: " + comando.turmaId()));
        turma.inscrever(comando.email());
        turmas.save(turma);
    }
}