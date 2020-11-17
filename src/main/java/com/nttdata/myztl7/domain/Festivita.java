package com.nttdata.myztl7.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Festivita.
 */
@Entity
@Table(name = "festivita")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Festivita implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private LocalDate nome;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getNome() {
        return nome;
    }

    public Festivita nome(LocalDate nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(LocalDate nome) {
        this.nome = nome;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Festivita)) {
            return false;
        }
        return id != null && id.equals(((Festivita) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Festivita{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
