package com.nttdata.myztl7.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A TestX.
 */
@Entity
@Table(name = "test_x")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TestX implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "x_string")
    private String xString;

    @Column(name = "x_integer")
    private Integer xInteger;

    @Column(name = "x_long")
    private Long xLong;

    @Column(name = "x_big_decimal", precision = 21, scale = 2)
    private BigDecimal xBigDecimal;

    @Column(name = "x_float")
    private Float xFloat;

    @Column(name = "x_double")
    private Double xDouble;

    @Column(name = "x_boolean")
    private Boolean xBoolean;

    @Column(name = "x_local_date")
    private LocalDate xLocalDate;

    @Column(name = "x_zoned_date_time")
    private ZonedDateTime xZonedDateTime;

    @Column(name = "x_instant")
    private Instant xInstant;

    @Column(name = "x_uuid")
    private UUID xUUID;

    @Lob
    @Column(name = "x_blob")
    private byte[] xBlob;

    @Column(name = "x_blob_content_type")
    private String xBlobContentType;

    @Lob
    @Column(name = "x_any_blob")
    private byte[] xAnyBlob;

    @Column(name = "x_any_blob_content_type")
    private String xAnyBlobContentType;

    @Lob
    @Column(name = "x_image_blob")
    private byte[] xImageBlob;

    @Column(name = "x_image_blob_content_type")
    private String xImageBlobContentType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "x_text_blob")
    private String xTextBlob;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getxString() {
        return xString;
    }

    public TestX xString(String xString) {
        this.xString = xString;
        return this;
    }

    public void setxString(String xString) {
        this.xString = xString;
    }

    public Integer getxInteger() {
        return xInteger;
    }

    public TestX xInteger(Integer xInteger) {
        this.xInteger = xInteger;
        return this;
    }

    public void setxInteger(Integer xInteger) {
        this.xInteger = xInteger;
    }

    public Long getxLong() {
        return xLong;
    }

    public TestX xLong(Long xLong) {
        this.xLong = xLong;
        return this;
    }

    public void setxLong(Long xLong) {
        this.xLong = xLong;
    }

    public BigDecimal getxBigDecimal() {
        return xBigDecimal;
    }

    public TestX xBigDecimal(BigDecimal xBigDecimal) {
        this.xBigDecimal = xBigDecimal;
        return this;
    }

    public void setxBigDecimal(BigDecimal xBigDecimal) {
        this.xBigDecimal = xBigDecimal;
    }

    public Float getxFloat() {
        return xFloat;
    }

    public TestX xFloat(Float xFloat) {
        this.xFloat = xFloat;
        return this;
    }

    public void setxFloat(Float xFloat) {
        this.xFloat = xFloat;
    }

    public Double getxDouble() {
        return xDouble;
    }

    public TestX xDouble(Double xDouble) {
        this.xDouble = xDouble;
        return this;
    }

    public void setxDouble(Double xDouble) {
        this.xDouble = xDouble;
    }

    public Boolean isxBoolean() {
        return xBoolean;
    }

    public TestX xBoolean(Boolean xBoolean) {
        this.xBoolean = xBoolean;
        return this;
    }

    public void setxBoolean(Boolean xBoolean) {
        this.xBoolean = xBoolean;
    }

    public LocalDate getxLocalDate() {
        return xLocalDate;
    }

    public TestX xLocalDate(LocalDate xLocalDate) {
        this.xLocalDate = xLocalDate;
        return this;
    }

    public void setxLocalDate(LocalDate xLocalDate) {
        this.xLocalDate = xLocalDate;
    }

    public ZonedDateTime getxZonedDateTime() {
        return xZonedDateTime;
    }

    public TestX xZonedDateTime(ZonedDateTime xZonedDateTime) {
        this.xZonedDateTime = xZonedDateTime;
        return this;
    }

    public void setxZonedDateTime(ZonedDateTime xZonedDateTime) {
        this.xZonedDateTime = xZonedDateTime;
    }

    public Instant getxInstant() {
        return xInstant;
    }

    public TestX xInstant(Instant xInstant) {
        this.xInstant = xInstant;
        return this;
    }

    public void setxInstant(Instant xInstant) {
        this.xInstant = xInstant;
    }

    public UUID getxUUID() {
        return xUUID;
    }

    public TestX xUUID(UUID xUUID) {
        this.xUUID = xUUID;
        return this;
    }

    public void setxUUID(UUID xUUID) {
        this.xUUID = xUUID;
    }

    public byte[] getxBlob() {
        return xBlob;
    }

    public TestX xBlob(byte[] xBlob) {
        this.xBlob = xBlob;
        return this;
    }

    public void setxBlob(byte[] xBlob) {
        this.xBlob = xBlob;
    }

    public String getxBlobContentType() {
        return xBlobContentType;
    }

    public TestX xBlobContentType(String xBlobContentType) {
        this.xBlobContentType = xBlobContentType;
        return this;
    }

    public void setxBlobContentType(String xBlobContentType) {
        this.xBlobContentType = xBlobContentType;
    }

    public byte[] getxAnyBlob() {
        return xAnyBlob;
    }

    public TestX xAnyBlob(byte[] xAnyBlob) {
        this.xAnyBlob = xAnyBlob;
        return this;
    }

    public void setxAnyBlob(byte[] xAnyBlob) {
        this.xAnyBlob = xAnyBlob;
    }

    public String getxAnyBlobContentType() {
        return xAnyBlobContentType;
    }

    public TestX xAnyBlobContentType(String xAnyBlobContentType) {
        this.xAnyBlobContentType = xAnyBlobContentType;
        return this;
    }

    public void setxAnyBlobContentType(String xAnyBlobContentType) {
        this.xAnyBlobContentType = xAnyBlobContentType;
    }

    public byte[] getxImageBlob() {
        return xImageBlob;
    }

    public TestX xImageBlob(byte[] xImageBlob) {
        this.xImageBlob = xImageBlob;
        return this;
    }

    public void setxImageBlob(byte[] xImageBlob) {
        this.xImageBlob = xImageBlob;
    }

    public String getxImageBlobContentType() {
        return xImageBlobContentType;
    }

    public TestX xImageBlobContentType(String xImageBlobContentType) {
        this.xImageBlobContentType = xImageBlobContentType;
        return this;
    }

    public void setxImageBlobContentType(String xImageBlobContentType) {
        this.xImageBlobContentType = xImageBlobContentType;
    }

    public String getxTextBlob() {
        return xTextBlob;
    }

    public TestX xTextBlob(String xTextBlob) {
        this.xTextBlob = xTextBlob;
        return this;
    }

    public void setxTextBlob(String xTextBlob) {
        this.xTextBlob = xTextBlob;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestX)) {
            return false;
        }
        return id != null && id.equals(((TestX) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestX{" +
            "id=" + getId() +
            ", xString='" + getxString() + "'" +
            ", xInteger=" + getxInteger() +
            ", xLong=" + getxLong() +
            ", xBigDecimal=" + getxBigDecimal() +
            ", xFloat=" + getxFloat() +
            ", xDouble=" + getxDouble() +
            ", xBoolean='" + isxBoolean() + "'" +
            ", xLocalDate='" + getxLocalDate() + "'" +
            ", xZonedDateTime='" + getxZonedDateTime() + "'" +
            ", xInstant='" + getxInstant() + "'" +
            ", xUUID='" + getxUUID() + "'" +
            ", xBlob='" + getxBlob() + "'" +
            ", xBlobContentType='" + getxBlobContentType() + "'" +
            ", xAnyBlob='" + getxAnyBlob() + "'" +
            ", xAnyBlobContentType='" + getxAnyBlobContentType() + "'" +
            ", xImageBlob='" + getxImageBlob() + "'" +
            ", xImageBlobContentType='" + getxImageBlobContentType() + "'" +
            ", xTextBlob='" + getxTextBlob() + "'" +
            "}";
    }
}
