package com.nttdata.myztl7.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Zona.
 */
@Entity
@Table(name = "zona")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Zona implements Serializable {
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

    @ManyToOne
    @JsonIgnoreProperties(value = "zonas", allowSetters = true)
    private ProfiloOrario profiloOrario;

    @ManyToOne
    @JsonIgnoreProperties(value = "zonas", allowSetters = true)
    private TipologiaZona tipologiaZona;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "zona_gruppo_varchi",
        joinColumns = @JoinColumn(name = "zona_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "gruppo_varchi_id", referencedColumnName = "id")
    )
    private Set<GruppoVarchi> gruppoVarchis = new HashSet<>();

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

    public Zona nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EntityStatus getStato() {
        return stato;
    }

    public Zona stato(EntityStatus stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(EntityStatus stato) {
        this.stato = stato;
    }

    public ProfiloOrario getProfiloOrario() {
        return profiloOrario;
    }

    public Zona profiloOrario(ProfiloOrario profiloOrario) {
        this.profiloOrario = profiloOrario;
        return this;
    }

    public void setProfiloOrario(ProfiloOrario profiloOrario) {
        this.profiloOrario = profiloOrario;
    }

    public TipologiaZona getTipologiaZona() {
        return tipologiaZona;
    }

    public Zona tipologiaZona(TipologiaZona tipologiaZona) {
        this.tipologiaZona = tipologiaZona;
        return this;
    }

    public void setTipologiaZona(TipologiaZona tipologiaZona) {
        this.tipologiaZona = tipologiaZona;
    }

    public Set<GruppoVarchi> getGruppoVarchis() {
        return gruppoVarchis;
    }

    public Zona gruppoVarchis(Set<GruppoVarchi> gruppoVarchis) {
        this.gruppoVarchis = gruppoVarchis;
        return this;
    }

    public Zona addGruppoVarchi(GruppoVarchi gruppoVarchi) {
        this.gruppoVarchis.add(gruppoVarchi);
        gruppoVarchi.getZonas().add(this);
        return this;
    }

    public Zona removeGruppoVarchi(GruppoVarchi gruppoVarchi) {
        this.gruppoVarchis.remove(gruppoVarchi);
        gruppoVarchi.getZonas().remove(this);
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
        if (!(o instanceof Zona)) {
            return false;
        }
        return id != null && id.equals(((Zona) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zona{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", stato='" + getStato() + "'" +
            "}";
    }
}
