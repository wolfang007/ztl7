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
 * A TipologiaVeicolo.
 */
@Entity
@Table(name = "tipologia_veicolo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipologiaVeicolo implements Serializable {
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

    @ManyToMany(mappedBy = "tipologiaVeicolos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
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

    public TipologiaVeicolo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EntityStatus getStato() {
        return stato;
    }

    public TipologiaVeicolo stato(EntityStatus stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(EntityStatus stato) {
        this.stato = stato;
    }

    public Set<RegolaOraria> getRegolaOrarias() {
        return regolaOrarias;
    }

    public TipologiaVeicolo regolaOrarias(Set<RegolaOraria> regolaOrarias) {
        this.regolaOrarias = regolaOrarias;
        return this;
    }

    public TipologiaVeicolo addRegolaOraria(RegolaOraria regolaOraria) {
        this.regolaOrarias.add(regolaOraria);
        regolaOraria.getTipologiaVeicolos().add(this);
        return this;
    }

    public TipologiaVeicolo removeRegolaOraria(RegolaOraria regolaOraria) {
        this.regolaOrarias.remove(regolaOraria);
        regolaOraria.getTipologiaVeicolos().remove(this);
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
        if (!(o instanceof TipologiaVeicolo)) {
            return false;
        }
        return id != null && id.equals(((TipologiaVeicolo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipologiaVeicolo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", stato='" + getStato() + "'" +
            "}";
    }
}
