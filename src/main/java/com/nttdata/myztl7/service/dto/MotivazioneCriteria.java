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
 * Criteria class for the {@link com.nttdata.myztl7.domain.Motivazione} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.MotivazioneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /motivaziones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MotivazioneCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descrizione;

    public MotivazioneCriteria() {}

    public MotivazioneCriteria(MotivazioneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descrizione = other.descrizione == null ? null : other.descrizione.copy();
    }

    @Override
    public MotivazioneCriteria copy() {
        return new MotivazioneCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(StringFilter descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MotivazioneCriteria that = (MotivazioneCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(descrizione, that.descrizione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descrizione);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MotivazioneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descrizione != null ? "descrizione=" + descrizione + ", " : "") +
            "}";
    }
}
