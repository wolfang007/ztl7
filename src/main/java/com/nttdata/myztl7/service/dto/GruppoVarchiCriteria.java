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
 * Criteria class for the {@link com.nttdata.myztl7.domain.GruppoVarchi} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.GruppoVarchiResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /gruppo-varchis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GruppoVarchiCriteria implements Serializable, Criteria {

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

    private StringFilter nome;

    private StringFilter descrizione;

    private StringFilter descrioneIntervalli;

    private EntityStatusFilter stato;

    private LongFilter posizioneId;

    private LongFilter zonaId;

    public GruppoVarchiCriteria() {}

    public GruppoVarchiCriteria(GruppoVarchiCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descrizione = other.descrizione == null ? null : other.descrizione.copy();
        this.descrioneIntervalli = other.descrioneIntervalli == null ? null : other.descrioneIntervalli.copy();
        this.stato = other.stato == null ? null : other.stato.copy();
        this.posizioneId = other.posizioneId == null ? null : other.posizioneId.copy();
        this.zonaId = other.zonaId == null ? null : other.zonaId.copy();
    }

    @Override
    public GruppoVarchiCriteria copy() {
        return new GruppoVarchiCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(StringFilter descrizione) {
        this.descrizione = descrizione;
    }

    public StringFilter getDescrioneIntervalli() {
        return descrioneIntervalli;
    }

    public void setDescrioneIntervalli(StringFilter descrioneIntervalli) {
        this.descrioneIntervalli = descrioneIntervalli;
    }

    public EntityStatusFilter getStato() {
        return stato;
    }

    public void setStato(EntityStatusFilter stato) {
        this.stato = stato;
    }

    public LongFilter getPosizioneId() {
        return posizioneId;
    }

    public void setPosizioneId(LongFilter posizioneId) {
        this.posizioneId = posizioneId;
    }

    public LongFilter getZonaId() {
        return zonaId;
    }

    public void setZonaId(LongFilter zonaId) {
        this.zonaId = zonaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GruppoVarchiCriteria that = (GruppoVarchiCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descrizione, that.descrizione) &&
            Objects.equals(descrioneIntervalli, that.descrioneIntervalli) &&
            Objects.equals(stato, that.stato) &&
            Objects.equals(posizioneId, that.posizioneId) &&
            Objects.equals(zonaId, that.zonaId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descrizione, descrioneIntervalli, stato, posizioneId, zonaId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GruppoVarchiCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descrizione != null ? "descrizione=" + descrizione + ", " : "") +
                (descrioneIntervalli != null ? "descrioneIntervalli=" + descrioneIntervalli + ", " : "") +
                (stato != null ? "stato=" + stato + ", " : "") +
                (posizioneId != null ? "posizioneId=" + posizioneId + ", " : "") +
                (zonaId != null ? "zonaId=" + zonaId + ", " : "") +
            "}";
    }
}
