package com.nttdata.myztl7.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Autorizzazione.
 */
@Entity
@Table(name = "autorizzazione")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Autorizzazione implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;

    @Column(name = "descrizione")
    private String descrizione;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private EntityStatus stato;

    @ManyToMany(mappedBy = "autorizzazionis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<PermessoTemporaneo> permessoTemporaneos = new HashSet<>();

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

    public Autorizzazione nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Autorizzazione descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public EntityStatus getStato() {
        return stato;
    }

    public Autorizzazione stato(EntityStatus stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(EntityStatus stato) {
        this.stato = stato;
    }

    public Set<PermessoTemporaneo> getPermessoTemporaneos() {
        return permessoTemporaneos;
    }

    public Autorizzazione permessoTemporaneos(Set<PermessoTemporaneo> permessoTemporaneos) {
        this.permessoTemporaneos = permessoTemporaneos;
        return this;
    }

    public Autorizzazione addPermessoTemporaneo(PermessoTemporaneo permessoTemporaneo) {
        this.permessoTemporaneos.add(permessoTemporaneo);
        permessoTemporaneo.getAutorizzazionis().add(this);
        return this;
    }

    public Autorizzazione removePermessoTemporaneo(PermessoTemporaneo permessoTemporaneo) {
        this.permessoTemporaneos.remove(permessoTemporaneo);
        permessoTemporaneo.getAutorizzazionis().remove(this);
        return this;
    }

    public void setPermessoTemporaneos(Set<PermessoTemporaneo> permessoTemporaneos) {
        this.permessoTemporaneos = permessoTemporaneos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autorizzazione)) {
            return false;
        }
        return id != null && id.equals(((Autorizzazione) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Autorizzazione{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", stato='" + getStato() + "'" +
            "}";
    }
}
