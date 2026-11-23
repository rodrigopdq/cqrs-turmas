package br.pucgoias.ads1253.turmas.leitura;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Lado da leitura: não altera dados. */
@Service
@Transactional(readOnly = true)
public class TurmaConsultas {

    private final TurmaLeituraRepository leitura;
    private final PainelTurmaRepository paineis;

    public TurmaConsultas(TurmaLeituraRepository leitura, PainelTurmaRepository paineis) {
        this.leitura = leitura;
        this.paineis = paineis;
    }

    public List<ResumoTurma> resumos() {
        return leitura.resumos();
    }

    public List<OcupacaoTurma> painelCalculado() {
        return leitura.ocupacaoCalculada();
    }

    public List<OcupacaoTurma> painel() {
        return paineis.findAllByOrderByNomeAsc().stream()
                .map(p -> new OcupacaoTurma(p.getTurmaId(), p.getNome(), p.getCapacidade(), p.getInscritos()))
                .toList();
    }
}