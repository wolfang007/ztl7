package com.nttdata.myztl7.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DurataCosto.
 */
@Entity
@Table(name = "durata_costo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DurataCosto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "durata", length = 50, nullable = false, unique = true)
    private String durata;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "costo")
    private Double costo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDurata() {
        return durata;
    }

    public DurataCosto durata(String durata) {
        this.durata = durata;
        return this;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public DurataCosto descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Double getCosto() {
        return costo;
    }

    public DurataCosto costo(Double costo) {
        this.costo = costo;
        return this;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DurataCosto)) {
            return false;
        }
        return id != null && id.equals(((DurataCosto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DurataCosto{" +
            "id=" + getId() +
            ", durata='" + getDurata() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", costo=" + getCosto() +
            "}";
    }
}
