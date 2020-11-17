package com.nttdata.myztl7.domain;

import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A TipologiaZona.
 */
@Entity
@Table(name = "tipologia_zona")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipologiaZona implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "regole_circolazione", nullable = false)
    private String regoleCircolazione;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private EntityStatus stato;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public TipologiaZona nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegoleCircolazione() {
        return regoleCircolazione;
    }

    public TipologiaZona regoleCircolazione(String regoleCircolazione) {
        this.regoleCircolazione = regoleCircolazione;
        return this;
    }

    public void setRegoleCircolazione(String regoleCircolazione) {
        this.regoleCircolazione = regoleCircolazione;
    }

    public EntityStatus getStato() {
        return stato;
    }

    public TipologiaZona stato(EntityStatus stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(EntityStatus stato) {
        this.stato = stato;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipologiaZona)) {
            return false;
        }
        return id != null && id.equals(((TipologiaZona) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipologiaZona{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", regoleCircolazione='" + getRegoleCircolazione() + "'" +
            ", stato='" + getStato() + "'" +
            "}";
    }
}
