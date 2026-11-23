package br.pucgoias.ads1253.turmas.leitura;

import br.pucgoias.ads1253.turmas.dominio.Turma;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

/** Repositório do lado da leitura: consultas que devolvem projeções, nunca entidades. */
public interface TurmaLeituraRepository extends Repository<Turma, UUID> {

    @Query("""
            select new br.pucgoias.ads1253.turmas.leitura.ResumoTurma(t.id, t.nome, t.capacidade)
            from Turma t
            order by t.nome
            """)
    List<ResumoTurma> resumos();

    @Query("""
            select new br.pucgoias.ads1253.turmas.leitura.OcupacaoTurma(
                t.id, t.nome, t.capacidade, count(i))
            from Turma t left join t.inscritos i
            group by t.id, t.nome, t.capacidade
            order by t.nome
            """)
    List<OcupacaoTurma> ocupacaoCalculada();
}