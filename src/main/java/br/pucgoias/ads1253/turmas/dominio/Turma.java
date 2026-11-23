package br.pucgoias.ads1253.turmas.dominio;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Raiz do agregado Turma. Aplica as regras de negócio e registra os eventos de domínio.
 * A coleção de inscritos é carregada sob demanda (carregamento tardio).
 */
@Entity
@Table(name = "turma")
public class Turma extends AbstractAggregateRoot<Turma> {

    @Id
    private UUID id;

    @Version
    private Long versao;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int capacidade;

    @ElementCollection
    @CollectionTable(name = "turma_inscrito", joinColumns = @JoinColumn(name = "turma_id"))
    @Column(name = "email", nullable = false)
    private Set<String> inscritos = new LinkedHashSet<>();

    protected Turma() {
        // exigido pela JPA
    }

    public static Turma abrir(String nome, int capacidade) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("O nome da turma é obrigatório.");
        }
        if (capacidade <= 0) {
            throw new RegraDeNegocioException("A capacidade da turma deve ser positiva.");
        }
        Turma turma = new Turma();
        turma.id = UUID.randomUUID();
        turma.nome = nome.trim();
        turma.capacidade = capacidade;
        turma.registerEvent(new TurmaAberta(turma.id, turma.nome, capacidade, Instant.now()));
        return turma;
    }

    public void inscrever(String email) {
        if (email == null || email.isBlank()) {
            throw new RegraDeNegocioException("O e-mail do estudante é obrigatório.");
        }
        if (inscritos.size() >= capacidade) {
            throw new RegraDeNegocioException("A turma " + nome + " não possui vagas.");
        }
        if (!inscritos.add(email.trim().toLowerCase())) {
            throw new RegraDeNegocioException("O e-mail " + email + " já está inscrito na turma.");
        }
        registerEvent(new InscricaoConfirmada(id, email.trim().toLowerCase(), Instant.now()));
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public Set<String> getInscritos() {
        return Set.copyOf(inscritos);
    }
}
