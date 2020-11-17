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
 * A GruppoVarchi.
 */
@Entity
@Table(name = "gruppo_varchi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GruppoVarchi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;

    @Size(max = 250)
    @Column(name = "descrizione", length = 250)
    private String descrizione;

    @Column(name = "descrione_intervalli")
    private String descrioneIntervalli;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private EntityStatus stato;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "gruppo_varchi_posizione",
        joinColumns = @JoinColumn(name = "gruppo_varchi_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "posizione_id", referencedColumnName = "id")
    )
    private Set<Varco> posiziones = new HashSet<>();

    @ManyToMany(mappedBy = "gruppoVarchis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Zona> zonas = new HashSet<>();

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

    public GruppoVarchi nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public GruppoVarchi descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrioneIntervalli() {
        return descrioneIntervalli;
    }

    public GruppoVarchi descrioneIntervalli(String descrioneIntervalli) {
        this.descrioneIntervalli = descrioneIntervalli;
        return this;
    }

    public void setDescrioneIntervalli(String descrioneIntervalli) {
        this.descrioneIntervalli = descrioneIntervalli;
    }

    public EntityStatus getStato() {
        return stato;
    }

    public GruppoVarchi stato(EntityStatus stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(EntityStatus stato) {
        this.stato = stato;
    }

    public Set<Varco> getPosiziones() {
        return posiziones;
    }

    public GruppoVarchi posiziones(Set<Varco> varcos) {
        this.posiziones = varcos;
        return this;
    }

    public GruppoVarchi addPosizione(Varco varco) {
        this.posiziones.add(varco);
        varco.getGruppoVarchis().add(this);
        return this;
    }

    public GruppoVarchi removePosizione(Varco varco) {
        this.posiziones.remove(varco);
        varco.getGruppoVarchis().remove(this);
        return this;
    }

    public void setPosiziones(Set<Varco> varcos) {
        this.posiziones = varcos;
    }

    public Set<Zona> getZonas() {
        return zonas;
    }

    public GruppoVarchi zonas(Set<Zona> zonas) {
        this.zonas = zonas;
        return this;
    }

    public GruppoVarchi addZona(Zona zona) {
        this.zonas.add(zona);
        zona.getGruppoVarchis().add(this);
        return this;
    }

    public GruppoVarchi removeZona(Zona zona) {
        this.zonas.remove(zona);
        zona.getGruppoVarchis().remove(this);
        return this;
    }

    public void setZonas(Set<Zona> zonas) {
        this.zonas = zonas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GruppoVarchi)) {
            return false;
        }
        return id != null && id.equals(((GruppoVarchi) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GruppoVarchi{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", descrioneIntervalli='" + getDescrioneIntervalli() + "'" +
            ", stato='" + getStato() + "'" +
            "}";
    }
}
