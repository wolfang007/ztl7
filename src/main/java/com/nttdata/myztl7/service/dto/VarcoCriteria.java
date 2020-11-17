package com.nttdata.myztl7.service.dto;

import com.nttdata.myztl7.domain.enumeration.EntityStatus;
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
 * Criteria class for the {@link com.nttdata.myztl7.domain.Varco} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.VarcoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /varcos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VarcoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EntityStatus
     */
    public static class EntityStatusFilter extends Filter<EntityStatus> {

        public EntityStatusFilter() {}

        public EntityStatusFilter(EntityStatusFilter filter) {
            super(filter);
        }

        @Override
        public EntityStatusFilter copy() {
            return new EntityStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codice;

    private StringFilter descrizionePosizione;

    private EntityStatusFilter stato;

    private LongFilter gruppoVarchiId;

    public VarcoCriteria() {}

    public VarcoCriteria(VarcoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codice = other.codice == null ? null : other.codice.copy();
        this.descrizionePosizione = other.descrizionePosizione == null ? null : other.descrizionePosizione.copy();
        this.stato = other.stato == null ? null : other.stato.copy();
        this.gruppoVarchiId = other.gruppoVarchiId == null ? null : other.gruppoVarchiId.copy();
    }

    @Override
    public VarcoCriteria copy() {
        return new VarcoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodice() {
        return codice;
    }

    public void setCodice(StringFilter codice) {
        this.codice = codice;
    }

    public StringFilter getDescrizionePosizione() {
        return descrizionePosizione;
    }

    public void setDescrizionePosizione(StringFilter descrizionePosizione) {
        this.descrizionePosizione = descrizionePosizione;
    }

    public EntityStatusFilter getStato() {
        return stato;
    }

    public void setStato(EntityStatusFilter stato) {
        this.stato = stato;
    }

    public LongFilter getGruppoVarchiId() {
        return gruppoVarchiId;
    }

    public void setGruppoVarchiId(LongFilter gruppoVarchiId) {
        this.gruppoVarchiId = gruppoVarchiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VarcoCriteria that = (VarcoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codice, that.codice) &&
            Objects.equals(descrizionePosizione, that.descrizionePosizione) &&
            Objects.equals(stato, that.stato) &&
            Objects.equals(gruppoVarchiId, that.gruppoVarchiId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codice, descrizionePosizione, stato, gruppoVarchiId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VarcoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codice != null ? "codice=" + codice + ", " : "") +
                (descrizionePosizione != null ? "descrizionePosizione=" + descrizionePosizione + ", " : "") +
                (stato != null ? "stato=" + stato + ", " : "") +
                (gruppoVarchiId != null ? "gruppoVarchiId=" + gruppoVarchiId + ", " : "") +
            "}";
    }
}
