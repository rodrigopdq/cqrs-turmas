package br.pucgoias.ads1253.turmas.leitura;

import br.pucgoias.ads1253.turmas.dominio.InscricaoConfirmada;
import br.pucgoias.ads1253.turmas.dominio.TurmaAberta;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/** Projetor: mantém a tabela de leitura a partir dos eventos de domínio. */
@Component
public class PainelTurmaProjetor {

    private final PainelTurmaRepository paineis;

    public PainelTurmaProjetor(PainelTurmaRepository paineis) {
        this.paineis = paineis;
    }

    @EventListener
    public void aoAbrir(TurmaAberta evento) {
        paineis.save(new PainelTurma(evento.turmaId(), evento.nome(), evento.capacidade(), 0));
    }

    @EventListener
    public void aoInscrever(InscricaoConfirmada evento) {
        PainelTurma painel = paineis.findById(evento.turmaId()).orElseThrow();
        painel.registrarInscricao();
        paineis.save(painel);
    }
}