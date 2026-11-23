package br.pucgoias.ads1253.turmas.leitura;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Reconstrução da tabela de leitura: apaga todas as linhas e as recalcula
 * a partir das tabelas de escrita.
 */
@Service
public class ReconstrutorPainel {

    private final TurmaLeituraRepository turmas;
    private final PainelTurmaRepository paineis;

    public ReconstrutorPainel(TurmaLeituraRepository turmas, PainelTurmaRepository paineis) {
        this.turmas = turmas;
        this.paineis = paineis;
    }

    @Transactional
    public int reconstruir() {
        paineis.apagarTudo();
        int linhas = 0;
        for (OcupacaoTurma o : turmas.ocupacaoCalculada()) {
            paineis.save(new PainelTurma(o.turmaId(), o.nome(), o.capacidade(), (int) o.inscritos()));
            linhas++;
        }
        return linhas;
    }
}