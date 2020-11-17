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
 * Criteria class for the {@link com.nttdata.myztl7.domain.Zona} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.ZonaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /zonas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZonaCriteria implements Serializable, Criteria {

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

    private EntityStatusFilter stato;

    private LongFilter profiloOrarioId;

    private LongFilter tipologiaZonaId;

    private LongFilter gruppoVarchiId;

    public ZonaCriteria() {}

    public ZonaCriteria(ZonaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.stato = other.stato == null ? null : other.stato.copy();
        this.profiloOrarioId = other.profiloOrarioId == null ? null : other.profiloOrarioId.copy();
        this.tipologiaZonaId = other.tipologiaZonaId == null ? null : other.tipologiaZonaId.copy();
        this.gruppoVarchiId = other.gruppoVarchiId == null ? null : other.gruppoVarchiId.copy();
    }

    @Override
    public ZonaCriteria copy() {
        return new ZonaCriteria(this);
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

    public EntityStatusFilter getStato() {
        return stato;
    }

    public void setStato(EntityStatusFilter stato) {
        this.stato = stato;
    }

    public LongFilter getProfiloOrarioId() {
        return profiloOrarioId;
    }

    public void setProfiloOrarioId(LongFilter profiloOrarioId) {
        this.profiloOrarioId = profiloOrarioId;
    }

    public LongFilter getTipologiaZonaId() {
        return tipologiaZonaId;
    }

    public void setTipologiaZonaId(LongFilter tipologiaZonaId) {
        this.tipologiaZonaId = tipologiaZonaId;
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
        final ZonaCriteria that = (ZonaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(stato, that.stato) &&
            Objects.equals(profiloOrarioId, that.profiloOrarioId) &&
            Objects.equals(tipologiaZonaId, that.tipologiaZonaId) &&
            Objects.equals(gruppoVarchiId, that.gruppoVarchiId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, stato, profiloOrarioId, tipologiaZonaId, gruppoVarchiId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZonaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (stato != null ? "stato=" + stato + ", " : "") +
                (profiloOrarioId != null ? "profiloOrarioId=" + profiloOrarioId + ", " : "") +
                (tipologiaZonaId != null ? "tipologiaZonaId=" + tipologiaZonaId + ", " : "") +
                (gruppoVarchiId != null ? "gruppoVarchiId=" + gruppoVarchiId + ", " : "") +
            "}";
    }
}
