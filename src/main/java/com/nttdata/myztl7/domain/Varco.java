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
 * A Varco.
 */
@Entity
@Table(name = "varco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Varco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "codice", length = 50, nullable = false, unique = true)
    private String codice;

    @NotNull
    @Size(max = 250)
    @Column(name = "descrizione_posizione", length = 250, nullable = false, unique = true)
    private String descrizionePosizione;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private EntityStatus stato;

    @ManyToMany(mappedBy = "posiziones")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<GruppoVarchi> gruppoVarchis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public Varco codice(String codice) {
        this.codice = codice;
        return this;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizionePosizione() {
        return descrizionePosizione;
    }

    public Varco descrizionePosizione(String descrizionePosizione) {
        this.descrizionePosizione = descrizionePosizione;
        return this;
    }

    public void setDescrizionePosizione(String descrizionePosizione) {
        this.descrizionePosizione = descrizionePosizione;
    }

    public EntityStatus getStato() {
        return stato;
    }

    public Varco stato(EntityStatus stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(EntityStatus stato) {
        this.stato = stato;
    }

    public Set<GruppoVarchi> getGruppoVarchis() {
        return gruppoVarchis;
    }

    public Varco gruppoVarchis(Set<GruppoVarchi> gruppoVarchis) {
        this.gruppoVarchis = gruppoVarchis;
        return this;
    }

    public Varco addGruppoVarchi(GruppoVarchi gruppoVarchi) {
        this.gruppoVarchis.add(gruppoVarchi);
        gruppoVarchi.getPosiziones().add(this);
        return this;
    }

    public Varco removeGruppoVarchi(GruppoVarchi gruppoVarchi) {
        this.gruppoVarchis.remove(gruppoVarchi);
        gruppoVarchi.getPosiziones().remove(this);
        return this;
    }

    public void setGruppoVarchis(Set<GruppoVarchi> gruppoVarchis) {
        this.gruppoVarchis = gruppoVarchis;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Varco)) {
            return false;
        }
        return id != null && id.equals(((Varco) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Varco{" +
            "id=" + getId() +
            ", codice='" + getCodice() + "'" +
            ", descrizionePosizione='" + getDescrizionePosizione() + "'" +
            ", stato='" + getStato() + "'" +
            "}";
    }
}
