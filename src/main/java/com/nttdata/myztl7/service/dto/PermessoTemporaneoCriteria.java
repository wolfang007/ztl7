package com.nttdata.myztl7.service.dto;

import com.nttdata.myztl7.domain.enumeration.TipoPersona;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.nttdata.myztl7.domain.PermessoTemporaneo} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.PermessoTemporaneoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /permesso-temporaneos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PermessoTemporaneoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoPersona
     */
    public static class TipoPersonaFilter extends Filter<TipoPersona> {

        public TipoPersonaFilter() {}

        public TipoPersonaFilter(TipoPersonaFilter filter) {
            super(filter);
        }

        @Override
        public TipoPersonaFilter copy() {
            return new TipoPersonaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter targa;

    private StringFilter domicilioDigitale;

    private TipoPersonaFilter tipoPersona;

    private StringFilter nomeRichiedente;

    private StringFilter cognomeRichiedente;

    private StringFilter ragioneSociale;

    private StringFilter codiceFiscaleRichiedente;

    private StringFilter partitaIva;

    private LocalDateFilter dataInizio;

    private BooleanFilter impostaDiBollo;

    private DoubleFilter costo;

    private BooleanFilter pagato;

    private StringFilter protocollo;

    private LongFilter calendarioId;

    private LongFilter tipoPermessoId;

    private LongFilter durataId;

    private LongFilter nomeId;

    private LongFilter motivazioneId;

    private LongFilter autorizzazioniId;

    public PermessoTemporaneoCriteria() {}

    public PermessoTemporaneoCriteria(PermessoTemporaneoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.targa = other.targa == null ? null : other.targa.copy();
        this.domicilioDigitale = other.domicilioDigitale == null ? null : other.domicilioDigitale.copy();
        this.tipoPersona = other.tipoPersona == null ? null : other.tipoPersona.copy();
        this.nomeRichiedente = other.nomeRichiedente == null ? null : other.nomeRichiedente.copy();
        this.cognomeRichiedente = other.cognomeRichiedente == null ? null : other.cognomeRichiedente.copy();
        this.ragioneSociale = other.ragioneSociale == null ? null : other.ragioneSociale.copy();
        this.codiceFiscaleRichiedente = other.codiceFiscaleRichiedente == null ? null : other.codiceFiscaleRichiedente.copy();
        this.partitaIva = other.partitaIva == null ? null : other.partitaIva.copy();
        this.dataInizio = other.dataInizio == null ? null : other.dataInizio.copy();
        this.impostaDiBollo = other.impostaDiBollo == null ? null : other.impostaDiBollo.copy();
        this.costo = other.costo == null ? null : other.costo.copy();
        this.pagato = other.pagato == null ? null : other.pagato.copy();
        this.protocollo = other.protocollo == null ? null : other.protocollo.copy();
        this.calendarioId = other.calendarioId == null ? null : other.calendarioId.copy();
        this.tipoPermessoId = other.tipoPermessoId == null ? null : other.tipoPermessoId.copy();
        this.durataId = other.durataId == null ? null : other.durataId.copy();
        this.nomeId = other.nomeId == null ? null : other.nomeId.copy();
        this.motivazioneId = other.motivazioneId == null ? null : other.motivazioneId.copy();
        this.autorizzazioniId = other.autorizzazioniId == null ? null : other.autorizzazioniId.copy();
    }

    @Override
    public PermessoTemporaneoCriteria copy() {
        return new PermessoTemporaneoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTarga() {
        return targa;
    }

    public void setTarga(StringFilter targa) {
        this.targa = targa;
    }

    public StringFilter getDomicilioDigitale() {
        return domicilioDigitale;
    }

    public void setDomicilioDigitale(StringFilter domicilioDigitale) {
        this.domicilioDigitale = domicilioDigitale;
    }

    public TipoPersonaFilter getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersonaFilter tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public StringFilter getNomeRichiedente() {
        return nomeRichiedente;
    }

    public void setNomeRichiedente(StringFilter nomeRichiedente) {
        this.nomeRichiedente = nomeRichiedente;
    }

    public StringFilter getCognomeRichiedente() {
        return cognomeRichiedente;
    }

    public void setCognomeRichiedente(StringFilter cognomeRichiedente) {
        this.cognomeRichiedente = cognomeRichiedente;
    }

    public StringFilter getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(StringFilter ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public StringFilter getCodiceFiscaleRichiedente() {
        return codiceFiscaleRichiedente;
    }

    public void setCodiceFiscaleRichiedente(StringFilter codiceFiscaleRichiedente) {
        this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
    }

    public StringFilter getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(StringFilter partitaIva) {
        this.partitaIva = partitaIva;
    }

    public LocalDateFilter getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDateFilter dataInizio) {
        this.dataInizio = dataInizio;
    }

    public BooleanFilter getImpostaDiBollo() {
        return impostaDiBollo;
    }

    public void setImpostaDiBollo(BooleanFilter impostaDiBollo) {
        this.impostaDiBollo = impostaDiBollo;
    }

    public DoubleFilter getCosto() {
        return costo;
    }

    public void setCosto(DoubleFilter costo) {
        this.costo = costo;
    }

    public BooleanFilter getPagato() {
        return pagato;
    }

    public void setPagato(BooleanFilter pagato) {
        this.pagato = pagato;
    }

    public StringFilter getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(StringFilter protocollo) {
        this.protocollo = protocollo;
    }

    public LongFilter getCalendarioId() {
        return calendarioId;
    }

    public void setCalendarioId(LongFilter calendarioId) {
        this.calendarioId = calendarioId;
    }

    public LongFilter getTipoPermessoId() {
        return tipoPermessoId;
    }

    public void setTipoPermessoId(LongFilter tipoPermessoId) {
        this.tipoPermessoId = tipoPermessoId;
    }

    public LongFilter getDurataId() {
        return durataId;
    }

    public void setDurataId(LongFilter durataId) {
        this.durataId = durataId;
    }

    public LongFilter getNomeId() {
        return nomeId;
    }

    public void setNomeId(LongFilter nomeId) {
        this.nomeId = nomeId;
    }

    public LongFilter getMotivazioneId() {
        return motivazioneId;
    }

    public void setMotivazioneId(LongFilter motivazioneId) {
        this.motivazioneId = motivazioneId;
    }

    public LongFilter getAutorizzazioniId() {
        return autorizzazioniId;
    }

    public void setAutorizzazioniId(LongFilter autorizzazioniId) {
        this.autorizzazioniId = autorizzazioniId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PermessoTemporaneoCriteria that = (PermessoTemporaneoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(targa, that.targa) &&
            Objects.equals(domicilioDigitale, that.domicilioDigitale) &&
            Objects.equals(tipoPersona, that.tipoPersona) &&
            Objects.equals(nomeRichiedente, that.nomeRichiedente) &&
            Objects.equals(cognomeRichiedente, that.cognomeRichiedente) &&
            Objects.equals(ragioneSociale, that.ragioneSociale) &&
            Objects.equals(codiceFiscaleRichiedente, that.codiceFiscaleRichiedente) &&
            Objects.equals(partitaIva, that.partitaIva) &&
            Objects.equals(dataInizio, that.dataInizio) &&
            Objects.equals(impostaDiBollo, that.impostaDiBollo) &&
            Objects.equals(costo, that.costo) &&
            Objects.equals(pagato, that.pagato) &&
            Objects.equals(protocollo, that.protocollo) &&
            Objects.equals(calendarioId, that.calendarioId) &&
            Objects.equals(tipoPermessoId, that.tipoPermessoId) &&
            Objects.equals(durataId, that.durataId) &&
            Objects.equals(nomeId, that.nomeId) &&
            Objects.equals(motivazioneId, that.motivazioneId) &&
            Objects.equals(autorizzazioniId, that.autorizzazioniId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            targa,
            domicilioDigitale,
            tipoPersona,
            nomeRichiedente,
            cognomeRichiedente,
            ragioneSociale,
            codiceFiscaleRichiedente,
            partitaIva,
            dataInizio,
            impostaDiBollo,
            costo,
            pagato,
            protocollo,
            calendarioId,
            tipoPermessoId,
            durataId,
            nomeId,
            motivazioneId,
            autorizzazioniId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PermessoTemporaneoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (targa != null ? "targa=" + targa + ", " : "") +
                (domicilioDigitale != null ? "domicilioDigitale=" + domicilioDigitale + ", " : "") +
                (tipoPersona != null ? "tipoPersona=" + tipoPersona + ", " : "") +
                (nomeRichiedente != null ? "nomeRichiedente=" + nomeRichiedente + ", " : "") +
                (cognomeRichiedente != null ? "cognomeRichiedente=" + cognomeRichiedente + ", " : "") +
                (ragioneSociale != null ? "ragioneSociale=" + ragioneSociale + ", " : "") +
                (codiceFiscaleRichiedente != null ? "codiceFiscaleRichiedente=" + codiceFiscaleRichiedente + ", " : "") +
                (partitaIva != null ? "partitaIva=" + partitaIva + ", " : "") +
                (dataInizio != null ? "dataInizio=" + dataInizio + ", " : "") +
                (impostaDiBollo != null ? "impostaDiBollo=" + impostaDiBollo + ", " : "") +
                (costo != null ? "costo=" + costo + ", " : "") +
                (pagato != null ? "pagato=" + pagato + ", " : "") +
                (protocollo != null ? "protocollo=" + protocollo + ", " : "") +
                (calendarioId != null ? "calendarioId=" + calendarioId + ", " : "") +
                (tipoPermessoId != null ? "tipoPermessoId=" + tipoPermessoId + ", " : "") +
                (durataId != null ? "durataId=" + durataId + ", " : "") +
                (nomeId != null ? "nomeId=" + nomeId + ", " : "") +
                (motivazioneId != null ? "motivazioneId=" + motivazioneId + ", " : "") +
                (autorizzazioniId != null ? "autorizzazioniId=" + autorizzazioniId + ", " : "") +
            "}";
    }
}
