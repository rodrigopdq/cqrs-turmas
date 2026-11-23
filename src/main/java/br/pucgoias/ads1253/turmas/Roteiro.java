package br.pucgoias.ads1253.turmas;

import br.pucgoias.ads1253.turmas.apoio.CargaInicial;
import br.pucgoias.ads1253.turmas.apoio.MedidorSql;
import br.pucgoias.ads1253.turmas.escrita.InscreverEstudante;
import br.pucgoias.ads1253.turmas.escrita.TurmaComandos;
import br.pucgoias.ads1253.turmas.leitura.ReconstrutorPainel;
import br.pucgoias.ads1253.turmas.leitura.TurmaConsultas;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Roteiro executado na inicialização da aplicação.
 * É alterado ao longo do estudo dirigido, conforme as instruções de cada etapa.
 */
@Component
public class Roteiro implements CommandLineRunner {

    private final CargaInicial carga;
    private final TurmaComandos comandos;
    private final TurmaConsultas consultas;
    private final ReconstrutorPainel reconstrutor;

    public Roteiro(CargaInicial carga,
                   TurmaComandos comandos,
                   TurmaConsultas consultas,
                   ReconstrutorPainel reconstrutor) {
        this.carga = carga;
        this.comandos = comandos;
        this.consultas = consultas;
        this.reconstrutor = reconstrutor;
    }

    @Override
    public void run(String... args) {
        UUID primeiraTurma = carga.executar();
        System.out.println();
        System.out.println("Carga concluída: " + CargaInicial.TURMAS + " turmas.");

        medir("Painel calculado a cada consulta (projeção com junção)",
                () -> imprimir(consultas.painelCalculado()));
        medir("Resumo das turmas (projeção)", () -> imprimir(consultas.resumos()));
        medir("Painel lido da tabela de leitura", () -> imprimir(consultas.painel()));

        medir("Comando: nova inscrição na Turma 001",
                () -> comandos.inscrever(new InscreverEstudante(primeiraTurma, "novo@pucgoias.edu.br")));
        medir("Painel após o comando", () -> imprimir(consultas.painel()));

        medir("Reconstrução da tabela de leitura",
                () -> System.out.println("   Linhas recriadas: " + reconstrutor.reconstruir()));
        medir("Painel após a reconstrução", () -> imprimir(consultas.painel()));
    }

    /** Executa a ação informada e apresenta quantos comandos SQL foram enviados ao banco. */
    private void medir(String titulo, Runnable acao) {
        System.out.println();
        System.out.println("== " + titulo);
        MedidorSql.reiniciar();
        acao.run();
        System.out.println("   Comandos SQL executados: " + MedidorSql.total());
        System.out.println("   Tabelas lidas: " + MedidorSql.tabelasLidas());
        System.out.println("   Primeiro comando: " + MedidorSql.primeiroComando());
    }

    /** Apresenta as três primeiras linhas e a quantidade total de linhas. */
    private void imprimir(List<?> linhas) {
        linhas.stream().limit(3).forEach(linha -> System.out.println("   " + linha));
        System.out.println("   ... total de linhas: " + linhas.size());
    }
}