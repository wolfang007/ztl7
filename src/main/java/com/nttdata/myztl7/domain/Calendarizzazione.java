package com.nttdata.myztl7.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Calendarizzazione.
 */
@Entity
@Table(name = "calendarizzazione")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Calendarizzazione implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @Column(name = "si_ripete")
    private Boolean siRipete;

    @Column(name = "festivi")
    private Boolean festivi;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isLunedi() {
        return lunedi;
    }

    public Calendarizzazione lunedi(Boolean lunedi) {
        this.lunedi = lunedi;
        return this;
    }

    public void setLunedi(Boolean lunedi) {
        this.lunedi = lunedi;
    }

    public Boolean isMartedi() {
        return martedi;
    }

    public Calendarizzazione martedi(Boolean martedi) {
        this.martedi = martedi;
        return this;
    }

    public void setMartedi(Boolean martedi) {
        this.martedi = martedi;
    }

    public Boolean isMercoledi() {
        return mercoledi;
    }

    public Calendarizzazione mercoledi(Boolean mercoledi) {
        this.mercoledi = mercoledi;
        return this;
    }

    public void setMercoledi(Boolean mercoledi) {
        this.mercoledi = mercoledi;
    }

    public Boolean isGiovedi() {
        return giovedi;
    }

    public Calendarizzazione giovedi(Boolean giovedi) {
        this.giovedi = giovedi;
        return this;
    }

    public void setGiovedi(Boolean giovedi) {
        this.giovedi = giovedi;
    }

    public Boolean isVenerdi() {
        return venerdi;
    }

    public Calendarizzazione venerdi(Boolean venerdi) {
        this.venerdi = venerdi;
        return this;
    }

    public void setVenerdi(Boolean venerdi) {
        this.venerdi = venerdi;
    }

    public Boolean isSabato() {
        return sabato;
    }

    public Calendarizzazione sabato(Boolean sabato) {
        this.sabato = sabato;
        return this;
    }

    public void setSabato(Boolean sabato) {
        this.sabato = sabato;
    }

    public Boolean isDomenica() {
        return domenica;
    }

    public Calendarizzazione domenica(Boolean domenica) {
        this.domenica = domenica;
        return this;
    }

    public void setDomenica(Boolean domenica) {
        this.domenica = domenica;
    }

    public Boolean isSiRipete() {
        return siRipete;
    }

    public Calendarizzazione siRipete(Boolean siRipete) {
        this.siRipete = siRipete;
        return this;
    }

    public void setSiRipete(Boolean siRipete) {
        this.siRipete = siRipete;
    }

    public Boolean isFestivi() {
        return festivi;
    }

    public Calendarizzazione festivi(Boolean festivi) {
        this.festivi = festivi;
        return this;
    }

    public void setFestivi(Boolean festivi) {
        this.festivi = festivi;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Calendarizzazione)) {
            return false;
        }
        return id != null && id.equals(((Calendarizzazione) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Calendarizzazione{" +
            "id=" + getId() +
            ", lunedi='" + isLunedi() + "'" +
            ", martedi='" + isMartedi() + "'" +
            ", mercoledi='" + isMercoledi() + "'" +
            ", giovedi='" + isGiovedi() + "'" +
            ", venerdi='" + isVenerdi() + "'" +
            ", sabato='" + isSabato() + "'" +
            ", domenica='" + isDomenica() + "'" +
            ", siRipete='" + isSiRipete() + "'" +
            ", festivi='" + isFestivi() + "'" +
            "}";
    }
}
