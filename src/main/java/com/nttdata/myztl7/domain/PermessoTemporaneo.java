package com.nttdata.myztl7.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nttdata.myztl7.domain.enumeration.TipoPersona;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PermessoTemporaneo.
 */
@Entity
@Table(name = "permesso_temporaneo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PermessoTemporaneo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "targa", length = 10, nullable = false)
    private String targa;

    @NotNull
    @Size(max = 50)
    @Column(name = "domicilio_digitale", length = 50, nullable = false)
    private String domicilioDigitale;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_persona")
    private TipoPersona tipoPersona;

    @Column(name = "nome_richiedente")
    private String nomeRichiedente;

    @Column(name = "cognome_richiedente")
    private String cognomeRichiedente;

    @Column(name = "ragione_sociale")
    private String ragioneSociale;

    @Column(name = "codice_fiscale_richiedente")
    private String codiceFiscaleRichiedente;

    @Column(name = "partita_iva")
    private String partitaIva;

    @Column(name = "data_inizio")
    private LocalDate dataInizio;

    @Column(name = "imposta_di_bollo")
    private Boolean impostaDiBollo;

    @Column(name = "costo")
    private Double costo;

    @Lob
    @Column(name = "copia_firmata")
    private byte[] copiaFirmata;

    @Column(name = "copia_firmata_content_type")
    private String copiaFirmataContentType;

    @Lob
    @Column(name = "documento_riconoscimento")
    private byte[] documentoRiconoscimento;

    @Column(name = "documento_riconoscimento_content_type")
    private String documentoRiconoscimentoContentType;

    @Column(name = "pagato")
    private Boolean pagato;

    @Column(name = "protocollo")
    private String protocollo;

    @OneToOne
    @JoinColumn(unique = true)
    private Calendarizzazione calendario;

    @ManyToOne
    @JsonIgnoreProperties(value = "permessoTemporaneos", allowSetters = true)
    private TipologiaPermesso tipoPermesso;

    @ManyToOne
    @JsonIgnoreProperties(value = "permessoTemporaneos", allowSetters = true)
    private DurataCosto durata;

    @ManyToOne
    @JsonIgnoreProperties(value = "permessoTemporaneos", allowSetters = true)
    private Zona nome;

    @ManyToOne
    @JsonIgnoreProperties(value = "permessoTemporaneos", allowSetters = true)
    private Motivazione motivazione;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "permesso_temporaneo_autorizzazioni",
        joinColumns = @JoinColumn(name = "permesso_temporaneo_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "autorizzazioni_id", referencedColumnName = "id")
    )
    private Set<Autorizzazione> autorizzazionis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarga() {
        return targa;
    }

    public PermessoTemporaneo targa(String targa) {
        this.targa = targa;
        return this;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getDomicilioDigitale() {
        return domicilioDigitale;
    }

    public PermessoTemporaneo domicilioDigitale(String domicilioDigitale) {
        this.domicilioDigitale = domicilioDigitale;
        return this;
    }

    public void setDomicilioDigitale(String domicilioDigitale) {
        this.domicilioDigitale = domicilioDigitale;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public PermessoTemporaneo tipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
        return this;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getNomeRichiedente() {
        return nomeRichiedente;
    }

    public PermessoTemporaneo nomeRichiedente(String nomeRichiedente) {
        this.nomeRichiedente = nomeRichiedente;
        return this;
    }

    public void setNomeRichiedente(String nomeRichiedente) {
        this.nomeRichiedente = nomeRichiedente;
    }

    public String getCognomeRichiedente() {
        return cognomeRichiedente;
    }

    public PermessoTemporaneo cognomeRichiedente(String cognomeRichiedente) {
        this.cognomeRichiedente = cognomeRichiedente;
        return this;
    }

    public void setCognomeRichiedente(String cognomeRichiedente) {
        this.cognomeRichiedente = cognomeRichiedente;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public PermessoTemporaneo ragioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
        return this;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getCodiceFiscaleRichiedente() {
        return codiceFiscaleRichiedente;
    }

    public PermessoTemporaneo codiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
        this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
        return this;
    }

    public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
        this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public PermessoTemporaneo partitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
        return this;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public PermessoTemporaneo dataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
        return this;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Boolean isImpostaDiBollo() {
        return impostaDiBollo;
    }

    public PermessoTemporaneo impostaDiBollo(Boolean impostaDiBollo) {
        this.impostaDiBollo = impostaDiBollo;
        return this;
    }

    public void setImpostaDiBollo(Boolean impostaDiBollo) {
        this.impostaDiBollo = impostaDiBollo;
    }

    public Double getCosto() {
        return costo;
    }

    public PermessoTemporaneo costo(Double costo) {
        this.costo = costo;
        return this;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public byte[] getCopiaFirmata() {
        return copiaFirmata;
    }

    public PermessoTemporaneo copiaFirmata(byte[] copiaFirmata) {
        this.copiaFirmata = copiaFirmata;
        return this;
    }

    public void setCopiaFirmata(byte[] copiaFirmata) {
        this.copiaFirmata = copiaFirmata;
    }

    public String getCopiaFirmataContentType() {
        return copiaFirmataContentType;
    }

    public PermessoTemporaneo copiaFirmataContentType(String copiaFirmataContentType) {
        this.copiaFirmataContentType = copiaFirmataContentType;
        return this;
    }

    public void setCopiaFirmataContentType(String copiaFirmataContentType) {
        this.copiaFirmataContentType = copiaFirmataContentType;
    }

    public byte[] getDocumentoRiconoscimento() {
        return documentoRiconoscimento;
    }

    public PermessoTemporaneo documentoRiconoscimento(byte[] documentoRiconoscimento) {
        this.documentoRiconoscimento = documentoRiconoscimento;
        return this;
    }

    public void setDocumentoRiconoscimento(byte[] documentoRiconoscimento) {
        this.documentoRiconoscimento = documentoRiconoscimento;
    }

    public String getDocumentoRiconoscimentoContentType() {
        return documentoRiconoscimentoContentType;
    }

    public PermessoTemporaneo documentoRiconoscimentoContentType(String documentoRiconoscimentoContentType) {
        this.documentoRiconoscimentoContentType = documentoRiconoscimentoContentType;
        return this;
    }

    public void setDocumentoRiconoscimentoContentType(String documentoRiconoscimentoContentType) {
        this.documentoRiconoscimentoContentType = documentoRiconoscimentoContentType;
    }

    public Boolean isPagato() {
        return pagato;
    }

    public PermessoTemporaneo pagato(Boolean pagato) {
        this.pagato = pagato;
        return this;
    }

    public void setPagato(Boolean pagato) {
        this.pagato = pagato;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public PermessoTemporaneo protocollo(String protocollo) {
        this.protocollo = protocollo;
        return this;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public Calendarizzazione getCalendario() {
        return calendario;
    }

    public PermessoTemporaneo calendario(Calendarizzazione calendarizzazione) {
        this.calendario = calendarizzazione;
        return this;
    }

    public void setCalendario(Calendarizzazione calendarizzazione) {
        this.calendario = calendarizzazione;
    }

    public TipologiaPermesso getTipoPermesso() {
        return tipoPermesso;
    }

    public PermessoTemporaneo tipoPermesso(TipologiaPermesso tipologiaPermesso) {
        this.tipoPermesso = tipologiaPermesso;
        return this;
    }

    public void setTipoPermesso(TipologiaPermesso tipologiaPermesso) {
        this.tipoPermesso = tipologiaPermesso;
    }

    public DurataCosto getDurata() {
        return durata;
    }

    public PermessoTemporaneo durata(DurataCosto durataCosto) {
        this.durata = durataCosto;
        return this;
    }

    public void setDurata(DurataCosto durataCosto) {
        this.durata = durataCosto;
    }

    public Zona getNome() {
        return nome;
    }

    public PermessoTemporaneo nome(Zona zona) {
        this.nome = zona;
        return this;
    }

    public void setNome(Zona zona) {
        this.nome = zona;
    }

    public Motivazione getMotivazione() {
        return motivazione;
    }

    public PermessoTemporaneo motivazione(Motivazione motivazione) {
        this.motivazione = motivazione;
        return this;
    }

    public void setMotivazione(Motivazione motivazione) {
        this.motivazione = motivazione;
    }

    public Set<Autorizzazione> getAutorizzazionis() {
        return autorizzazionis;
    }

    public PermessoTemporaneo autorizzazionis(Set<Autorizzazione> autorizzaziones) {
        this.autorizzazionis = autorizzaziones;
        return this;
    }

    public PermessoTemporaneo addAutorizzazioni(Autorizzazione autorizzazione) {
        this.autorizzazionis.add(autorizzazione);
        autorizzazione.getPermessoTemporaneos().add(this);
        return this;
    }

    public PermessoTemporaneo removeAutorizzazioni(Autorizzazione autorizzazione) {
        this.autorizzazionis.remove(autorizzazione);
        autorizzazione.getPermessoTemporaneos().remove(this);
        return this;
    }

    public void setAutorizzazionis(Set<Autorizzazione> autorizzaziones) {
        this.autorizzazionis = autorizzaziones;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PermessoTemporaneo)) {
            return false;
        }
        return id != null && id.equals(((PermessoTemporaneo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PermessoTemporaneo{" +
            "id=" + getId() +
            ", targa='" + getTarga() + "'" +
            ", domicilioDigitale='" + getDomicilioDigitale() + "'" +
            ", tipoPersona='" + getTipoPersona() + "'" +
            ", nomeRichiedente='" + getNomeRichiedente() + "'" +
            ", cognomeRichiedente='" + getCognomeRichiedente() + "'" +
            ", ragioneSociale='" + getRagioneSociale() + "'" +
            ", codiceFiscaleRichiedente='" + getCodiceFiscaleRichiedente() + "'" +
            ", partitaIva='" + getPartitaIva() + "'" +
            ", dataInizio='" + getDataInizio() + "'" +
            ", impostaDiBollo='" + isImpostaDiBollo() + "'" +
            ", costo=" + getCosto() +
            ", copiaFirmata='" + getCopiaFirmata() + "'" +
            ", copiaFirmataContentType='" + getCopiaFirmataContentType() + "'" +
            ", documentoRiconoscimento='" + getDocumentoRiconoscimento() + "'" +
            ", documentoRiconoscimentoContentType='" + getDocumentoRiconoscimentoContentType() + "'" +
            ", pagato='" + isPagato() + "'" +
            ", protocollo='" + getProtocollo() + "'" +
            "}";
    }
}
