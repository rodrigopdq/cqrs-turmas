package br.pucgoias.ads1253.turmas.apoio;

import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Instrumento de medição do estudo dirigido.
 * O Hibernate invoca o método inspect antes de enviar cada comando SQL ao banco;
 * a classe apenas conta os comandos e anota as tabelas consultadas.
 */
public class MedidorSql implements StatementInspector {

    private static final Pattern TABELA = Pattern.compile("\\b(?:from|join)\\s+([a-z_]+)", Pattern.CASE_INSENSITIVE);
    private static final List<String> COMANDOS = new ArrayList<>();

    @Override
    public String inspect(String sql) {
        synchronized (COMANDOS) {
            COMANDOS.add(sql);
        }
        return sql;
    }

    public static void reiniciar() {
        synchronized (COMANDOS) {
            COMANDOS.clear();
        }
    }

    public static int total() {
        synchronized (COMANDOS) {
            return COMANDOS.size();
        }
    }

    /** Nomes das tabelas que aparecem em cláusulas from ou join dos comandos registrados. */
    public static Set<String> tabelasLidas() {
        Set<String> tabelas = new TreeSet<>();
        synchronized (COMANDOS) {
            for (String sql : COMANDOS) {
                Matcher m = TABELA.matcher(sql);
                while (m.find()) {
                    tabelas.add(m.group(1).toLowerCase());
                }
            }
        }
        return tabelas;
    }

    /** Primeiro comando SQL registrado desde a última reinicialização. */
    public static String primeiroComando() {
        synchronized (COMANDOS) {
            return COMANDOS.isEmpty() ? "(nenhum)" : COMANDOS.get(0);
        }
    }
}
