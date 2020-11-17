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
 * Criteria class for the {@link com.nttdata.myztl7.domain.TipologiaPermesso} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.TipologiaPermessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipologia-permessos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TipologiaPermessoCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter codice;

    public TipologiaPermessoCriteria() {}

    public TipologiaPermessoCriteria(TipologiaPermessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.codice = other.codice == null ? null : other.codice.copy();
    }

    @Override
    public TipologiaPermessoCriteria copy() {
        return new TipologiaPermessoCriteria(this);
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

    public StringFilter getCodice() {
        return codice;
    }

    public void setCodice(StringFilter codice) {
        this.codice = codice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TipologiaPermessoCriteria that = (TipologiaPermessoCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(codice, that.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, codice);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipologiaPermessoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (codice != null ? "codice=" + codice + ", " : "") +
            "}";
    }
}
