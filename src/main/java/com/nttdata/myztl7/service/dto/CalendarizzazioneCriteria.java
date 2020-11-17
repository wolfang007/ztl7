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
 * Criteria class for the {@link com.nttdata.myztl7.domain.Calendarizzazione} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.CalendarizzazioneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /calendarizzaziones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CalendarizzazioneCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter lunedi;

    private BooleanFilter martedi;

    private BooleanFilter mercoledi;

    private BooleanFilter giovedi;

    private BooleanFilter venerdi;

    private BooleanFilter sabato;

    private BooleanFilter domenica;

    private BooleanFilter siRipete;

    private BooleanFilter festivi;

    public CalendarizzazioneCriteria() {}

    public CalendarizzazioneCriteria(CalendarizzazioneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.lunedi = other.lunedi == null ? null : other.lunedi.copy();
        this.martedi = other.martedi == null ? null : other.martedi.copy();
        this.mercoledi = other.mercoledi == null ? null : other.mercoledi.copy();
        this.giovedi = other.giovedi == null ? null : other.giovedi.copy();
        this.venerdi = other.venerdi == null ? null : other.venerdi.copy();
        this.sabato = other.sabato == null ? null : other.sabato.copy();
        this.domenica = other.domenica == null ? null : other.domenica.copy();
        this.siRipete = other.siRipete == null ? null : other.siRipete.copy();
        this.festivi = other.festivi == null ? null : other.festivi.copy();
    }

    @Override
    public CalendarizzazioneCriteria copy() {
        return new CalendarizzazioneCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getLunedi() {
        return lunedi;
    }

    public void setLunedi(BooleanFilter lunedi) {
        this.lunedi = lunedi;
    }

    public BooleanFilter getMartedi() {
        return martedi;
    }

    public void setMartedi(BooleanFilter martedi) {
        this.martedi = martedi;
    }

    public BooleanFilter getMercoledi() {
        return mercoledi;
    }

    public void setMercoledi(BooleanFilter mercoledi) {
        this.mercoledi = mercoledi;
    }

    public BooleanFilter getGiovedi() {
        return giovedi;
    }

    public void setGiovedi(BooleanFilter giovedi) {
        this.giovedi = giovedi;
    }

    public BooleanFilter getVenerdi() {
        return venerdi;
    }

    public void setVenerdi(BooleanFilter venerdi) {
        this.venerdi = venerdi;
    }

    public BooleanFilter getSabato() {
        return sabato;
    }

    public void setSabato(BooleanFilter sabato) {
        this.sabato = sabato;
    }

    public BooleanFilter getDomenica() {
        return domenica;
    }

    public void setDomenica(BooleanFilter domenica) {
        this.domenica = domenica;
    }

    public BooleanFilter getSiRipete() {
        return siRipete;
    }

    public void setSiRipete(BooleanFilter siRipete) {
        this.siRipete = siRipete;
    }

    public BooleanFilter getFestivi() {
        return festivi;
    }

    public void setFestivi(BooleanFilter festivi) {
        this.festivi = festivi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CalendarizzazioneCriteria that = (CalendarizzazioneCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(lunedi, that.lunedi) &&
            Objects.equals(martedi, that.martedi) &&
            Objects.equals(mercoledi, that.mercoledi) &&
            Objects.equals(giovedi, that.giovedi) &&
            Objects.equals(venerdi, that.venerdi) &&
            Objects.equals(sabato, that.sabato) &&
            Objects.equals(domenica, that.domenica) &&
            Objects.equals(siRipete, that.siRipete) &&
            Objects.equals(festivi, that.festivi)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lunedi, martedi, mercoledi, giovedi, venerdi, sabato, domenica, siRipete, festivi);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalendarizzazioneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lunedi != null ? "lunedi=" + lunedi + ", " : "") +
                (martedi != null ? "martedi=" + martedi + ", " : "") +
                (mercoledi != null ? "mercoledi=" + mercoledi + ", " : "") +
                (giovedi != null ? "giovedi=" + giovedi + ", " : "") +
                (venerdi != null ? "venerdi=" + venerdi + ", " : "") +
                (sabato != null ? "sabato=" + sabato + ", " : "") +
                (domenica != null ? "domenica=" + domenica + ", " : "") +
                (siRipete != null ? "siRipete=" + siRipete + ", " : "") +
                (festivi != null ? "festivi=" + festivi + ", " : "") +
            "}";
    }
}
