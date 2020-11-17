package com.nttdata.myztl7.domain;

import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProfiloOrario.
 */
@Entity
@Table(name = "profilo_orario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProfiloOrario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private EntityStatus stato;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "profilo_orario_regola_oraria",
        joinColumns = @JoinColumn(name = "profilo_orario_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "regola_oraria_id", referencedColumnName = "id")
    )
    private Set<RegolaOraria> regolaOrarias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public ProfiloOrario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EntityStatus getStato() {
        return stato;
    }

    public ProfiloOrario stato(EntityStatus stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(EntityStatus stato) {
        this.stato = stato;
    }

    public Set<RegolaOraria> getRegolaOrarias() {
        return regolaOrarias;
    }

    public ProfiloOrario regolaOrarias(Set<RegolaOraria> regolaOrarias) {
        this.regolaOrarias = regolaOrarias;
        return this;
    }

    public ProfiloOrario addRegolaOraria(RegolaOraria regolaOraria) {
        this.regolaOrarias.add(regolaOraria);
        regolaOraria.getProfiloOrarios().add(this);
        return this;
    }

    public ProfiloOrario removeRegolaOraria(RegolaOraria regolaOraria) {
        this.regolaOrarias.remove(regolaOraria);
        regolaOraria.getProfiloOrarios().remove(this);
        return this;
    }

    public void setRegolaOrarias(Set<RegolaOraria> regolaOrarias) {
        this.regolaOrarias = regolaOrarias;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfiloOrario)) {
            return false;
        }
        return id != null && id.equals(((ProfiloOrario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfiloOrario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", stato='" + getStato() + "'" +
            "}";
    }
}
