package com.nttdata.myztl7.service.dto;

import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.domain.enumeration.MinutiEnum;
import com.nttdata.myztl7.domain.enumeration.MinutiEnum;
import com.nttdata.myztl7.domain.enumeration.OreEnum;
import com.nttdata.myztl7.domain.enumeration.OreEnum;
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
 * Criteria class for the {@link com.nttdata.myztl7.domain.RegolaOraria} entity. This class is used
 * in {@link com.nttdata.myztl7.web.rest.RegolaOrariaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /regola-orarias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegolaOrariaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering OreEnum
     */
    public static class OreEnumFilter extends Filter<OreEnum> {

        public OreEnumFilter() {}

        public OreEnumFilter(OreEnumFilter filter) {
            super(filter);
        }

        @Override
        public OreEnumFilter copy() {
            return new OreEnumFilter(this);
        }
    }

    /**
     * Class for filtering MinutiEnum
     */
    public static class MinutiEnumFilter extends Filter<MinutiEnum> {

        public MinutiEnumFilter() {}

        public MinutiEnumFilter(MinutiEnumFilter filter) {
            super(filter);
        }

        @Override
        public MinutiEnumFilter copy() {
            return new MinutiEnumFilter(this);
        }
    }

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

    private OreEnumFilter oraInizio;

    private OreEnumFilter oraFine;

    private MinutiEnumFilter minutiInizio;

    private MinutiEnumFilter minutiFine;

    private BooleanFilter lunedi;

    private BooleanFilter martedi;

    private BooleanFilter mercoledi;

    private BooleanFilter giovedi;

    private BooleanFilter venerdi;

    private BooleanFilter sabato;

    private BooleanFilter domenica;

    private BooleanFilter festivi;

    private EntityStatusFilter stato;

    private LongFilter tipologiaVeicoloId;

    private LongFilter profiloOrarioId;

    public RegolaOrariaCriteria() {}

    public RegolaOrariaCriteria(RegolaOrariaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.oraInizio = other.oraInizio == null ? null : other.oraInizio.copy();
        this.oraFine = other.oraFine == null ? null : other.oraFine.copy();
        this.minutiInizio = other.minutiInizio == null ? null : other.minutiInizio.copy();
        this.minutiFine = other.minutiFine == null ? null : other.minutiFine.copy();
        this.lunedi = other.lunedi == null ? null : other.lunedi.copy();
        this.martedi = other.martedi == null ? null : other.martedi.copy();
        this.mercoledi = other.mercoledi == null ? null : other.mercoledi.copy();
        this.giovedi = other.giovedi == null ? null : other.giovedi.copy();
        this.venerdi = other.venerdi == null ? null : other.venerdi.copy();
        this.sabato = other.sabato == null ? null : other.sabato.copy();
        this.domenica = other.domenica == null ? null : other.domenica.copy();
        this.festivi = other.festivi == null ? null : other.festivi.copy();
        this.stato = other.stato == null ? null : other.stato.copy();
        this.tipologiaVeicoloId = other.tipologiaVeicoloId == null ? null : other.tipologiaVeicoloId.copy();
        this.profiloOrarioId = other.profiloOrarioId == null ? null : other.profiloOrarioId.copy();
    }

    @Override
    public RegolaOrariaCriteria copy() {
        return new RegolaOrariaCriteria(this);
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

    public OreEnumFilter getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(OreEnumFilter oraInizio) {
        this.oraInizio = oraInizio;
    }

    public OreEnumFilter getOraFine() {
        return oraFine;
    }

    public void setOraFine(OreEnumFilter oraFine) {
        this.oraFine = oraFine;
    }

    public MinutiEnumFilter getMinutiInizio() {
        return minutiInizio;
    }

    public void setMinutiInizio(MinutiEnumFilter minutiInizio) {
        this.minutiInizio = minutiInizio;
    }

    public MinutiEnumFilter getMinutiFine() {
        return minutiFine;
    }

    public void setMinutiFine(MinutiEnumFilter minutiFine) {
        this.minutiFine = minutiFine;
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

    public BooleanFilter getFestivi() {
        return festivi;
    }

    public void setFestivi(BooleanFilter festivi) {
        this.festivi = festivi;
    }

    public EntityStatusFilter getStato() {
        return stato;
    }

    public void setStato(EntityStatusFilter stato) {
        this.stato = stato;
    }

    public LongFilter getTipologiaVeicoloId() {
        return tipologiaVeicoloId;
    }

    public void setTipologiaVeicoloId(LongFilter tipologiaVeicoloId) {
        this.tipologiaVeicoloId = tipologiaVeicoloId;
    }

    public LongFilter getProfiloOrarioId() {
        return profiloOrarioId;
    }

    public void setProfiloOrarioId(LongFilter profiloOrarioId) {
        this.profiloOrarioId = profiloOrarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegolaOrariaCriteria that = (RegolaOrariaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(oraInizio, that.oraInizio) &&
            Objects.equals(oraFine, that.oraFine) &&
            Objects.equals(minutiInizio, that.minutiInizio) &&
            Objects.equals(minutiFine, that.minutiFine) &&
            Objects.equals(lunedi, that.lunedi) &&
            Objects.equals(martedi, that.martedi) &&
            Objects.equals(mercoledi, that.mercoledi) &&
            Objects.equals(giovedi, that.giovedi) &&
            Objects.equals(venerdi, that.venerdi) &&
            Objects.equals(sabato, that.sabato) &&
            Objects.equals(domenica, that.domenica) &&
            Objects.equals(festivi, that.festivi) &&
            Objects.equals(stato, that.stato) &&
            Objects.equals(tipologiaVeicoloId, that.tipologiaVeicoloId) &&
            Objects.equals(profiloOrarioId, that.profiloOrarioId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            oraInizio,
            oraFine,
            minutiInizio,
            minutiFine,
            lunedi,
            martedi,
            mercoledi,
            giovedi,
            venerdi,
            sabato,
            domenica,
            festivi,
            stato,
            tipologiaVeicoloId,
            profiloOrarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegolaOrariaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (oraInizio != null ? "oraInizio=" + oraInizio + ", " : "") +
                (oraFine != null ? "oraFine=" + oraFine + ", " : "") +
                (minutiInizio != null ? "minutiInizio=" + minutiInizio + ", " : "") +
                (minutiFine != null ? "minutiFine=" + minutiFine + ", " : "") +
                (lunedi != null ? "lunedi=" + lunedi + ", " : "") +
                (martedi != null ? "martedi=" + martedi + ", " : "") +
                (mercoledi != null ? "mercoledi=" + mercoledi + ", " : "") +
                (giovedi != null ? "giovedi=" + giovedi + ", " : "") +
                (venerdi != null ? "venerdi=" + venerdi + ", " : "") +
                (sabato != null ? "sabato=" + sabato + ", " : "") +
                (domenica != null ? "domenica=" + domenica + ", " : "") +
                (festivi != null ? "festivi=" + festivi + ", " : "") +
                (stato != null ? "stato=" + stato + ", " : "") +
                (tipologiaVeicoloId != null ? "tipologiaVeicoloId=" + tipologiaVeicoloId + ", " : "") +
                (profiloOrarioId != null ? "profiloOrarioId=" + profiloOrarioId + ", " : "") +
            "}";
    }
}
