package com.nttdata.myztl7.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.domain.enumeration.MinutiEnum;
import com.nttdata.myztl7.domain.enumeration.OreEnum;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RegolaOraria.
 */
@Entity
@Table(name = "regola_oraria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RegolaOraria implements Serializable {
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
    @Column(name = "ora_inizio", nullable = false)
    private OreEnum oraInizio;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ora_fine", nullable = false)
    private OreEnum oraFine;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "minuti_inizio", nullable = false)
    private MinutiEnum minutiInizio;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "minuti_fine", nullable = false)
    private MinutiEnum minutiFine;

    @Column(name = "lunedi")
    private Boolean lunedi;

    @Column(name = "martedi")
    private Boolean martedi;

    @Column(name = "mercoledi")
    private Boolean mercoledi;

    @Column(name = "giovedi")
    private Boolean giovedi;

    @Column(name = "venerdi")
    private Boolean venerdi;

    @Column(name = "sabato")
    private Boolean sabato;

    @Column(name = "domenica")
    private Boolean domenica;

    @Column(name = "festivi")
    private Boolean festivi;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private EntityStatus stato;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "regola_oraria_tipologia_veicolo",
        joinColumns = @JoinColumn(name = "regola_oraria_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "tipologia_veicolo_id", referencedColumnName = "id")
    )
    private Set<TipologiaVeicolo> tipologiaVeicolos = new HashSet<>();

    @ManyToMany(mappedBy = "regolaOrarias")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ProfiloOrario> profiloOrarios = new HashSet<>();

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

    public RegolaOraria nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public OreEnum getOraInizio() {
        return oraInizio;
    }

    public RegolaOraria oraInizio(OreEnum oraInizio) {
        this.oraInizio = oraInizio;
        return this;
    }

    public void setOraInizio(OreEnum oraInizio) {
        this.oraInizio = oraInizio;
    }

    public OreEnum getOraFine() {
        return oraFine;
    }

    public RegolaOraria oraFine(OreEnum oraFine) {
        this.oraFine = oraFine;
        return this;
    }

    public void setOraFine(OreEnum oraFine) {
        this.oraFine = oraFine;
    }

    public MinutiEnum getMinutiInizio() {
        return minutiInizio;
    }

    public RegolaOraria minutiInizio(MinutiEnum minutiInizio) {
        this.minutiInizio = minutiInizio;
        return this;
    }

    public void setMinutiInizio(MinutiEnum minutiInizio) {
        this.minutiInizio = minutiInizio;
    }

    public MinutiEnum getMinutiFine() {
        return minutiFine;
    }

    public RegolaOraria minutiFine(MinutiEnum minutiFine) {
        this.minutiFine = minutiFine;
        return this;
    }

    public void setMinutiFine(MinutiEnum minutiFine) {
        this.minutiFine = minutiFine;
    }

    public Boolean isLunedi() {
        return lunedi;
    }

    public RegolaOraria lunedi(Boolean lunedi) {
        this.lunedi = lunedi;
        return this;
    }

    public void setLunedi(Boolean lunedi) {
        this.lunedi = lunedi;
    }

    public Boolean isMartedi() {
        return martedi;
    }

    public RegolaOraria martedi(Boolean martedi) {
        this.martedi = martedi;
        return this;
    }

    public void setMartedi(Boolean martedi) {
        this.martedi = martedi;
    }

    public Boolean isMercoledi() {
        return mercoledi;
    }

    public RegolaOraria mercoledi(Boolean mercoledi) {
        this.mercoledi = mercoledi;
        return this;
    }

    public void setMercoledi(Boolean mercoledi) {
        this.mercoledi = mercoledi;
    }

    public Boolean isGiovedi() {
        return giovedi;
    }

    public RegolaOraria giovedi(Boolean giovedi) {
        this.giovedi = giovedi;
        return this;
    }

    public void setGiovedi(Boolean giovedi) {
        this.giovedi = giovedi;
    }

    public Boolean isVenerdi() {
        return venerdi;
    }

    public RegolaOraria venerdi(Boolean venerdi) {
        this.venerdi = venerdi;
        return this;
    }

    public void setVenerdi(Boolean venerdi) {
        this.venerdi = venerdi;
    }

    public Boolean isSabato() {
        return sabato;
    }

    public RegolaOraria sabato(Boolean sabato) {
        this.sabato = sabato;
        return this;
    }

    public void setSabato(Boolean sabato) {
        this.sabato = sabato;
    }

    public Boolean isDomenica() {
        return domenica;
    }

    public RegolaOraria domenica(Boolean domenica) {
        this.domenica = domenica;
        return this;
    }

    public void setDomenica(Boolean domenica) {
        this.domenica = domenica;
    }

    public Boolean isFestivi() {
        return festivi;
    }

    public RegolaOraria festivi(Boolean festivi) {
        this.festivi = festivi;
        return this;
    }

    public void setFestivi(Boolean festivi) {
        this.festivi = festivi;
    }

    public EntityStatus getStato() {
        return stato;
    }

    public RegolaOraria stato(EntityStatus stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(EntityStatus stato) {
        this.stato = stato;
    }

    public Set<TipologiaVeicolo> getTipologiaVeicolos() {
        return tipologiaVeicolos;
    }

    public RegolaOraria tipologiaVeicolos(Set<TipologiaVeicolo> tipologiaVeicolos) {
        this.tipologiaVeicolos = tipologiaVeicolos;
        return this;
    }

    public RegolaOraria addTipologiaVeicolo(TipologiaVeicolo tipologiaVeicolo) {
        this.tipologiaVeicolos.add(tipologiaVeicolo);
        tipologiaVeicolo.getRegolaOrarias().add(this);
        return this;
    }

    public RegolaOraria removeTipologiaVeicolo(TipologiaVeicolo tipologiaVeicolo) {
        this.tipologiaVeicolos.remove(tipologiaVeicolo);
        tipologiaVeicolo.getRegolaOrarias().remove(this);
        return this;
    }

    public void setTipologiaVeicolos(Set<TipologiaVeicolo> tipologiaVeicolos) {
        this.tipologiaVeicolos = tipologiaVeicolos;
    }

    public Set<ProfiloOrario> getProfiloOrarios() {
        return profiloOrarios;
    }

    public RegolaOraria profiloOrarios(Set<ProfiloOrario> profiloOrarios) {
        this.profiloOrarios = profiloOrarios;
        return this;
    }

    public RegolaOraria addProfiloOrario(ProfiloOrario profiloOrario) {
        this.profiloOrarios.add(profiloOrario);
        profiloOrario.getRegolaOrarias().add(this);
        return this;
    }

    public RegolaOraria removeProfiloOrario(ProfiloOrario profiloOrario) {
        this.profiloOrarios.remove(profiloOrario);
        profiloOrario.getRegolaOrarias().remove(this);
        return this;
    }

    public void setProfiloOrarios(Set<ProfiloOrario> profiloOrarios) {
        this.profiloOrarios = profiloOrarios;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegolaOraria)) {
            return false;
        }
        return id != null && id.equals(((RegolaOraria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegolaOraria{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", oraInizio='" + getOraInizio() + "'" +
            ", oraFine='" + getOraFine() + "'" +
            ", minutiInizio='" + getMinutiInizio() + "'" +
            ", minutiFine='" + getMinutiFine() + "'" +
            ", lunedi='" + isLunedi() + "'" +
            ", martedi='" + isMartedi() + "'" +
            ", mercoledi='" + isMercoledi() + "'" +
            ", giovedi='" + isGiovedi() + "'" +
            ", venerdi='" + isVenerdi() + "'" +
            ", sabato='" + isSabato() + "'" +
            ", domenica='" + isDomenica() + "'" +
            ", festivi='" + isFestivi() + "'" +
            ", stato='" + getStato() + "'" +
            "}";
    }
}
