package com.nttdata.myztl7.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.nttdata.myztl7.domain.DurataCosto} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.DurataCostoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /durata-costos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DurataCostoCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter durata;

    private StringFilter descrizione;

    private DoubleFilter costo;

    public DurataCostoCriteria() {}

    public DurataCostoCriteria(DurataCostoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.durata = other.durata == null ? null : other.durata.copy();
        this.descrizione = other.descrizione == null ? null : other.descrizione.copy();
        this.costo = other.costo == null ? null : other.costo.copy();
    }

    @Override
    public DurataCostoCriteria copy() {
        return new DurataCostoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDurata() {
        return durata;
    }

    public void setDurata(StringFilter durata) {
        this.durata = durata;
    }

    public StringFilter getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(StringFilter descrizione) {
        this.descrizione = descrizione;
    }

    public DoubleFilter getCosto() {
        return costo;
    }

    public void setCosto(DoubleFilter costo) {
        this.costo = costo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DurataCostoCriteria that = (DurataCostoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(durata, that.durata) &&
            Objects.equals(descrizione, that.descrizione) &&
            Objects.equals(costo, that.costo)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, durata, descrizione, costo);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DurataCostoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (durata != null ? "durata=" + durata + ", " : "") +
                (descrizione != null ? "descrizione=" + descrizione + ", " : "") +
                (costo != null ? "costo=" + costo + ", " : "") +
            "}";
    }
}
