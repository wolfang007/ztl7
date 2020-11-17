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
 * Criteria class for the {@link com.nttdata.myztl7.domain.Autorizzazione} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.AutorizzazioneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /autorizzaziones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AutorizzazioneCriteria implements Serializable, Criteria {

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

    private EntityStatusFilter stato;

    private LongFilter permessoTemporaneoId;

    public AutorizzazioneCriteria() {}

    public AutorizzazioneCriteria(AutorizzazioneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descrizione = other.descrizione == null ? null : other.descrizione.copy();
        this.stato = other.stato == null ? null : other.stato.copy();
        this.permessoTemporaneoId = other.permessoTemporaneoId == null ? null : other.permessoTemporaneoId.copy();
    }

    @Override
    public AutorizzazioneCriteria copy() {
        return new AutorizzazioneCriteria(this);
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

    public EntityStatusFilter getStato() {
        return stato;
    }

    public void setStato(EntityStatusFilter stato) {
        this.stato = stato;
    }

    public LongFilter getPermessoTemporaneoId() {
        return permessoTemporaneoId;
    }

    public void setPermessoTemporaneoId(LongFilter permessoTemporaneoId) {
        this.permessoTemporaneoId = permessoTemporaneoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AutorizzazioneCriteria that = (AutorizzazioneCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descrizione, that.descrizione) &&
            Objects.equals(stato, that.stato) &&
            Objects.equals(permessoTemporaneoId, that.permessoTemporaneoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descrizione, stato, permessoTemporaneoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutorizzazioneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descrizione != null ? "descrizione=" + descrizione + ", " : "") +
                (stato != null ? "stato=" + stato + ", " : "") +
                (permessoTemporaneoId != null ? "permessoTemporaneoId=" + permessoTemporaneoId + ", " : "") +
            "}";
    }
}
