package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.Autorizzazione;
import com.nttdata.myztl7.domain.Calendarizzazione;
import com.nttdata.myztl7.domain.DurataCosto;
import com.nttdata.myztl7.domain.Motivazione;
import com.nttdata.myztl7.domain.PermessoTemporaneo;
import com.nttdata.myztl7.domain.TipologiaPermesso;
import com.nttdata.myztl7.domain.Zona;
import com.nttdata.myztl7.domain.enumeration.TipoPersona;
import com.nttdata.myztl7.repository.PermessoTemporaneoRepository;
import com.nttdata.myztl7.service.PermessoTemporaneoQueryService;
import com.nttdata.myztl7.service.PermessoTemporaneoService;
import com.nttdata.myztl7.service.dto.PermessoTemporaneoCriteria;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PermessoTemporaneoResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PermessoTemporaneoResourceIT {
    private static final String DEFAULT_TARGA = "AAAAAAAAAA";
    private static final String UPDATED_TARGA = "BBBBBBBBBB";

    private static final String DEFAULT_DOMICILIO_DIGITALE = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO_DIGITALE = "BBBBBBBBBB";

    private static final TipoPersona DEFAULT_TIPO_PERSONA = TipoPersona.FISICA;
    private static final TipoPersona UPDATED_TIPO_PERSONA = TipoPersona.GIURIDICA;

    private static final String DEFAULT_NOME_RICHIEDENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_RICHIEDENTE = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME_RICHIEDENTE = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME_RICHIEDENTE = "BBBBBBBBBB";

    private static final String DEFAULT_RAGIONE_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAGIONE_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_CODICE_FISCALE_RICHIEDENTE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE_FISCALE_RICHIEDENTE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTITA_IVA = "AAAAAAAAAA";
    private static final String UPDATED_PARTITA_IVA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INIZIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INIZIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_INIZIO = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_IMPOSTA_DI_BOLLO = false;
    private static final Boolean UPDATED_IMPOSTA_DI_BOLLO = true;

    private static final Double DEFAULT_COSTO = 1D;
    private static final Double UPDATED_COSTO = 2D;
    private static final Double SMALLER_COSTO = 1D - 1D;

    private static final byte[] DEFAULT_COPIA_FIRMATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COPIA_FIRMATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COPIA_FIRMATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COPIA_FIRMATA_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOCUMENTO_RICONOSCIMENTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO_RICONOSCIMENTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_PAGATO = false;
    private static final Boolean UPDATED_PAGATO = true;

    private static final String DEFAULT_PROTOCOLLO = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOLLO = "BBBBBBBBBB";

    @Autowired
    private PermessoTemporaneoRepository permessoTemporaneoRepository;

    @Mock
    private PermessoTemporaneoRepository permessoTemporaneoRepositoryMock;

    @Mock
    private PermessoTemporaneoService permessoTemporaneoServiceMock;

    @Autowired
    private PermessoTemporaneoService permessoTemporaneoService;

    @Autowired
    private PermessoTemporaneoQueryService permessoTemporaneoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPermessoTemporaneoMockMvc;

    private PermessoTemporaneo permessoTemporaneo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PermessoTemporaneo createEntity(EntityManager em) {
        PermessoTemporaneo permessoTemporaneo = new PermessoTemporaneo()
            .targa(DEFAULT_TARGA)
            .domicilioDigitale(DEFAULT_DOMICILIO_DIGITALE)
            .tipoPersona(DEFAULT_TIPO_PERSONA)
            .nomeRichiedente(DEFAULT_NOME_RICHIEDENTE)
            .cognomeRichiedente(DEFAULT_COGNOME_RICHIEDENTE)
            .ragioneSociale(DEFAULT_RAGIONE_SOCIALE)
            .codiceFiscaleRichiedente(DEFAULT_CODICE_FISCALE_RICHIEDENTE)
            .partitaIva(DEFAULT_PARTITA_IVA)
            .dataInizio(DEFAULT_DATA_INIZIO)
            .impostaDiBollo(DEFAULT_IMPOSTA_DI_BOLLO)
            .costo(DEFAULT_COSTO)
            .copiaFirmata(DEFAULT_COPIA_FIRMATA)
            .copiaFirmataContentType(DEFAULT_COPIA_FIRMATA_CONTENT_TYPE)
            .documentoRiconoscimento(DEFAULT_DOCUMENTO_RICONOSCIMENTO)
            .documentoRiconoscimentoContentType(DEFAULT_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE)
            .pagato(DEFAULT_PAGATO)
            .protocollo(DEFAULT_PROTOCOLLO);
        return permessoTemporaneo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PermessoTemporaneo createUpdatedEntity(EntityManager em) {
        PermessoTemporaneo permessoTemporaneo = new PermessoTemporaneo()
            .targa(UPDATED_TARGA)
            .domicilioDigitale(UPDATED_DOMICILIO_DIGITALE)
            .tipoPersona(UPDATED_TIPO_PERSONA)
            .nomeRichiedente(UPDATED_NOME_RICHIEDENTE)
            .cognomeRichiedente(UPDATED_COGNOME_RICHIEDENTE)
            .ragioneSociale(UPDATED_RAGIONE_SOCIALE)
            .codiceFiscaleRichiedente(UPDATED_CODICE_FISCALE_RICHIEDENTE)
            .partitaIva(UPDATED_PARTITA_IVA)
            .dataInizio(UPDATED_DATA_INIZIO)
            .impostaDiBollo(UPDATED_IMPOSTA_DI_BOLLO)
            .costo(UPDATED_COSTO)
            .copiaFirmata(UPDATED_COPIA_FIRMATA)
            .copiaFirmataContentType(UPDATED_COPIA_FIRMATA_CONTENT_TYPE)
            .documentoRiconoscimento(UPDATED_DOCUMENTO_RICONOSCIMENTO)
            .documentoRiconoscimentoContentType(UPDATED_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE)
            .pagato(UPDATED_PAGATO)
            .protocollo(UPDATED_PROTOCOLLO);
        return permessoTemporaneo;
    }

    @BeforeEach
    public void initTest() {
        permessoTemporaneo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPermessoTemporaneo() throws Exception {
        int databaseSizeBeforeCreate = permessoTemporaneoRepository.findAll().size();
        // Create the PermessoTemporaneo
        restPermessoTemporaneoMockMvc
            .perform(
                post("/api/permesso-temporaneos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permessoTemporaneo))
            )
            .andExpect(status().isCreated());

        // Validate the PermessoTemporaneo in the database
        List<PermessoTemporaneo> permessoTemporaneoList = permessoTemporaneoRepository.findAll();
        assertThat(permessoTemporaneoList).hasSize(databaseSizeBeforeCreate + 1);
        PermessoTemporaneo testPermessoTemporaneo = permessoTemporaneoList.get(permessoTemporaneoList.size() - 1);
        assertThat(testPermessoTemporaneo.getTarga()).isEqualTo(DEFAULT_TARGA);
        assertThat(testPermessoTemporaneo.getDomicilioDigitale()).isEqualTo(DEFAULT_DOMICILIO_DIGITALE);
        assertThat(testPermessoTemporaneo.getTipoPersona()).isEqualTo(DEFAULT_TIPO_PERSONA);
        assertThat(testPermessoTemporaneo.getNomeRichiedente()).isEqualTo(DEFAULT_NOME_RICHIEDENTE);
        assertThat(testPermessoTemporaneo.getCognomeRichiedente()).isEqualTo(DEFAULT_COGNOME_RICHIEDENTE);
        assertThat(testPermessoTemporaneo.getRagioneSociale()).isEqualTo(DEFAULT_RAGIONE_SOCIALE);
        assertThat(testPermessoTemporaneo.getCodiceFiscaleRichiedente()).isEqualTo(DEFAULT_CODICE_FISCALE_RICHIEDENTE);
        assertThat(testPermessoTemporaneo.getPartitaIva()).isEqualTo(DEFAULT_PARTITA_IVA);
        assertThat(testPermessoTemporaneo.getDataInizio()).isEqualTo(DEFAULT_DATA_INIZIO);
        assertThat(testPermessoTemporaneo.isImpostaDiBollo()).isEqualTo(DEFAULT_IMPOSTA_DI_BOLLO);
        assertThat(testPermessoTemporaneo.getCosto()).isEqualTo(DEFAULT_COSTO);
        assertThat(testPermessoTemporaneo.getCopiaFirmata()).isEqualTo(DEFAULT_COPIA_FIRMATA);
        assertThat(testPermessoTemporaneo.getCopiaFirmataContentType()).isEqualTo(DEFAULT_COPIA_FIRMATA_CONTENT_TYPE);
        assertThat(testPermessoTemporaneo.getDocumentoRiconoscimento()).isEqualTo(DEFAULT_DOCUMENTO_RICONOSCIMENTO);
        assertThat(testPermessoTemporaneo.getDocumentoRiconoscimentoContentType()).isEqualTo(DEFAULT_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE);
        assertThat(testPermessoTemporaneo.isPagato()).isEqualTo(DEFAULT_PAGATO);
        assertThat(testPermessoTemporaneo.getProtocollo()).isEqualTo(DEFAULT_PROTOCOLLO);
    }

    @Test
    @Transactional
    public void createPermessoTemporaneoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permessoTemporaneoRepository.findAll().size();

        // Create the PermessoTemporaneo with an existing ID
        permessoTemporaneo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermessoTemporaneoMockMvc
            .perform(
                post("/api/permesso-temporaneos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permessoTemporaneo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PermessoTemporaneo in the database
        List<PermessoTemporaneo> permessoTemporaneoList = permessoTemporaneoRepository.findAll();
        assertThat(permessoTemporaneoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTargaIsRequired() throws Exception {
        int databaseSizeBeforeTest = permessoTemporaneoRepository.findAll().size();
        // set the field null
        permessoTemporaneo.setTarga(null);

        // Create the PermessoTemporaneo, which fails.

        restPermessoTemporaneoMockMvc
            .perform(
                post("/api/permesso-temporaneos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permessoTemporaneo))
            )
            .andExpect(status().isBadRequest());

        List<PermessoTemporaneo> permessoTemporaneoList = permessoTemporaneoRepository.findAll();
        assertThat(permessoTemporaneoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomicilioDigitaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = permessoTemporaneoRepository.findAll().size();
        // set the field null
        permessoTemporaneo.setDomicilioDigitale(null);

        // Create the PermessoTemporaneo, which fails.

        restPermessoTemporaneoMockMvc
            .perform(
                post("/api/permesso-temporaneos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permessoTemporaneo))
            )
            .andExpect(status().isBadRequest());

        List<PermessoTemporaneo> permessoTemporaneoList = permessoTemporaneoRepository.findAll();
        assertThat(permessoTemporaneoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneos() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList
        restPermessoTemporaneoMockMvc
            .perform(get("/api/permesso-temporaneos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permessoTemporaneo.getId().intValue())))
            .andExpect(jsonPath("$.[*].targa").value(hasItem(DEFAULT_TARGA)))
            .andExpect(jsonPath("$.[*].domicilioDigitale").value(hasItem(DEFAULT_DOMICILIO_DIGITALE)))
            .andExpect(jsonPath("$.[*].tipoPersona").value(hasItem(DEFAULT_TIPO_PERSONA.toString())))
            .andExpect(jsonPath("$.[*].nomeRichiedente").value(hasItem(DEFAULT_NOME_RICHIEDENTE)))
            .andExpect(jsonPath("$.[*].cognomeRichiedente").value(hasItem(DEFAULT_COGNOME_RICHIEDENTE)))
            .andExpect(jsonPath("$.[*].ragioneSociale").value(hasItem(DEFAULT_RAGIONE_SOCIALE)))
            .andExpect(jsonPath("$.[*].codiceFiscaleRichiedente").value(hasItem(DEFAULT_CODICE_FISCALE_RICHIEDENTE)))
            .andExpect(jsonPath("$.[*].partitaIva").value(hasItem(DEFAULT_PARTITA_IVA)))
            .andExpect(jsonPath("$.[*].dataInizio").value(hasItem(DEFAULT_DATA_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].impostaDiBollo").value(hasItem(DEFAULT_IMPOSTA_DI_BOLLO.booleanValue())))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].copiaFirmataContentType").value(hasItem(DEFAULT_COPIA_FIRMATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].copiaFirmata").value(hasItem(Base64Utils.encodeToString(DEFAULT_COPIA_FIRMATA))))
            .andExpect(jsonPath("$.[*].documentoRiconoscimentoContentType").value(hasItem(DEFAULT_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE)))
            .andExpect(
                jsonPath("$.[*].documentoRiconoscimento").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO_RICONOSCIMENTO)))
            )
            .andExpect(jsonPath("$.[*].pagato").value(hasItem(DEFAULT_PAGATO.booleanValue())))
            .andExpect(jsonPath("$.[*].protocollo").value(hasItem(DEFAULT_PROTOCOLLO)));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllPermessoTemporaneosWithEagerRelationshipsIsEnabled() throws Exception {
        when(permessoTemporaneoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPermessoTemporaneoMockMvc.perform(get("/api/permesso-temporaneos?eagerload=true")).andExpect(status().isOk());

        verify(permessoTemporaneoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllPermessoTemporaneosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(permessoTemporaneoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPermessoTemporaneoMockMvc.perform(get("/api/permesso-temporaneos?eagerload=true")).andExpect(status().isOk());

        verify(permessoTemporaneoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPermessoTemporaneo() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get the permessoTemporaneo
        restPermessoTemporaneoMockMvc
            .perform(get("/api/permesso-temporaneos/{id}", permessoTemporaneo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(permessoTemporaneo.getId().intValue()))
            .andExpect(jsonPath("$.targa").value(DEFAULT_TARGA))
            .andExpect(jsonPath("$.domicilioDigitale").value(DEFAULT_DOMICILIO_DIGITALE))
            .andExpect(jsonPath("$.tipoPersona").value(DEFAULT_TIPO_PERSONA.toString()))
            .andExpect(jsonPath("$.nomeRichiedente").value(DEFAULT_NOME_RICHIEDENTE))
            .andExpect(jsonPath("$.cognomeRichiedente").value(DEFAULT_COGNOME_RICHIEDENTE))
            .andExpect(jsonPath("$.ragioneSociale").value(DEFAULT_RAGIONE_SOCIALE))
            .andExpect(jsonPath("$.codiceFiscaleRichiedente").value(DEFAULT_CODICE_FISCALE_RICHIEDENTE))
            .andExpect(jsonPath("$.partitaIva").value(DEFAULT_PARTITA_IVA))
            .andExpect(jsonPath("$.dataInizio").value(DEFAULT_DATA_INIZIO.toString()))
            .andExpect(jsonPath("$.impostaDiBollo").value(DEFAULT_IMPOSTA_DI_BOLLO.booleanValue()))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO.doubleValue()))
            .andExpect(jsonPath("$.copiaFirmataContentType").value(DEFAULT_COPIA_FIRMATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.copiaFirmata").value(Base64Utils.encodeToString(DEFAULT_COPIA_FIRMATA)))
            .andExpect(jsonPath("$.documentoRiconoscimentoContentType").value(DEFAULT_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentoRiconoscimento").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTO_RICONOSCIMENTO)))
            .andExpect(jsonPath("$.pagato").value(DEFAULT_PAGATO.booleanValue()))
            .andExpect(jsonPath("$.protocollo").value(DEFAULT_PROTOCOLLO));
    }

    @Test
    @Transactional
    public void getPermessoTemporaneosByIdFiltering() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        Long id = permessoTemporaneo.getId();

        defaultPermessoTemporaneoShouldBeFound("id.equals=" + id);
        defaultPermessoTemporaneoShouldNotBeFound("id.notEquals=" + id);

        defaultPermessoTemporaneoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPermessoTemporaneoShouldNotBeFound("id.greaterThan=" + id);

        defaultPermessoTemporaneoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPermessoTemporaneoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTargaIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where targa equals to DEFAULT_TARGA
        defaultPermessoTemporaneoShouldBeFound("targa.equals=" + DEFAULT_TARGA);

        // Get all the permessoTemporaneoList where targa equals to UPDATED_TARGA
        defaultPermessoTemporaneoShouldNotBeFound("targa.equals=" + UPDATED_TARGA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTargaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where targa not equals to DEFAULT_TARGA
        defaultPermessoTemporaneoShouldNotBeFound("targa.notEquals=" + DEFAULT_TARGA);

        // Get all the permessoTemporaneoList where targa not equals to UPDATED_TARGA
        defaultPermessoTemporaneoShouldBeFound("targa.notEquals=" + UPDATED_TARGA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTargaIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where targa in DEFAULT_TARGA or UPDATED_TARGA
        defaultPermessoTemporaneoShouldBeFound("targa.in=" + DEFAULT_TARGA + "," + UPDATED_TARGA);

        // Get all the permessoTemporaneoList where targa equals to UPDATED_TARGA
        defaultPermessoTemporaneoShouldNotBeFound("targa.in=" + UPDATED_TARGA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTargaIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where targa is not null
        defaultPermessoTemporaneoShouldBeFound("targa.specified=true");

        // Get all the permessoTemporaneoList where targa is null
        defaultPermessoTemporaneoShouldNotBeFound("targa.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTargaContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where targa contains DEFAULT_TARGA
        defaultPermessoTemporaneoShouldBeFound("targa.contains=" + DEFAULT_TARGA);

        // Get all the permessoTemporaneoList where targa contains UPDATED_TARGA
        defaultPermessoTemporaneoShouldNotBeFound("targa.contains=" + UPDATED_TARGA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTargaNotContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where targa does not contain DEFAULT_TARGA
        defaultPermessoTemporaneoShouldNotBeFound("targa.doesNotContain=" + DEFAULT_TARGA);

        // Get all the permessoTemporaneoList where targa does not contain UPDATED_TARGA
        defaultPermessoTemporaneoShouldBeFound("targa.doesNotContain=" + UPDATED_TARGA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDomicilioDigitaleIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where domicilioDigitale equals to DEFAULT_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldBeFound("domicilioDigitale.equals=" + DEFAULT_DOMICILIO_DIGITALE);

        // Get all the permessoTemporaneoList where domicilioDigitale equals to UPDATED_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldNotBeFound("domicilioDigitale.equals=" + UPDATED_DOMICILIO_DIGITALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDomicilioDigitaleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where domicilioDigitale not equals to DEFAULT_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldNotBeFound("domicilioDigitale.notEquals=" + DEFAULT_DOMICILIO_DIGITALE);

        // Get all the permessoTemporaneoList where domicilioDigitale not equals to UPDATED_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldBeFound("domicilioDigitale.notEquals=" + UPDATED_DOMICILIO_DIGITALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDomicilioDigitaleIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where domicilioDigitale in DEFAULT_DOMICILIO_DIGITALE or UPDATED_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldBeFound("domicilioDigitale.in=" + DEFAULT_DOMICILIO_DIGITALE + "," + UPDATED_DOMICILIO_DIGITALE);

        // Get all the permessoTemporaneoList where domicilioDigitale equals to UPDATED_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldNotBeFound("domicilioDigitale.in=" + UPDATED_DOMICILIO_DIGITALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDomicilioDigitaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where domicilioDigitale is not null
        defaultPermessoTemporaneoShouldBeFound("domicilioDigitale.specified=true");

        // Get all the permessoTemporaneoList where domicilioDigitale is null
        defaultPermessoTemporaneoShouldNotBeFound("domicilioDigitale.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDomicilioDigitaleContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where domicilioDigitale contains DEFAULT_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldBeFound("domicilioDigitale.contains=" + DEFAULT_DOMICILIO_DIGITALE);

        // Get all the permessoTemporaneoList where domicilioDigitale contains UPDATED_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldNotBeFound("domicilioDigitale.contains=" + UPDATED_DOMICILIO_DIGITALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDomicilioDigitaleNotContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where domicilioDigitale does not contain DEFAULT_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldNotBeFound("domicilioDigitale.doesNotContain=" + DEFAULT_DOMICILIO_DIGITALE);

        // Get all the permessoTemporaneoList where domicilioDigitale does not contain UPDATED_DOMICILIO_DIGITALE
        defaultPermessoTemporaneoShouldBeFound("domicilioDigitale.doesNotContain=" + UPDATED_DOMICILIO_DIGITALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTipoPersonaIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where tipoPersona equals to DEFAULT_TIPO_PERSONA
        defaultPermessoTemporaneoShouldBeFound("tipoPersona.equals=" + DEFAULT_TIPO_PERSONA);

        // Get all the permessoTemporaneoList where tipoPersona equals to UPDATED_TIPO_PERSONA
        defaultPermessoTemporaneoShouldNotBeFound("tipoPersona.equals=" + UPDATED_TIPO_PERSONA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTipoPersonaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where tipoPersona not equals to DEFAULT_TIPO_PERSONA
        defaultPermessoTemporaneoShouldNotBeFound("tipoPersona.notEquals=" + DEFAULT_TIPO_PERSONA);

        // Get all the permessoTemporaneoList where tipoPersona not equals to UPDATED_TIPO_PERSONA
        defaultPermessoTemporaneoShouldBeFound("tipoPersona.notEquals=" + UPDATED_TIPO_PERSONA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTipoPersonaIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where tipoPersona in DEFAULT_TIPO_PERSONA or UPDATED_TIPO_PERSONA
        defaultPermessoTemporaneoShouldBeFound("tipoPersona.in=" + DEFAULT_TIPO_PERSONA + "," + UPDATED_TIPO_PERSONA);

        // Get all the permessoTemporaneoList where tipoPersona equals to UPDATED_TIPO_PERSONA
        defaultPermessoTemporaneoShouldNotBeFound("tipoPersona.in=" + UPDATED_TIPO_PERSONA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTipoPersonaIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where tipoPersona is not null
        defaultPermessoTemporaneoShouldBeFound("tipoPersona.specified=true");

        // Get all the permessoTemporaneoList where tipoPersona is null
        defaultPermessoTemporaneoShouldNotBeFound("tipoPersona.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByNomeRichiedenteIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where nomeRichiedente equals to DEFAULT_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("nomeRichiedente.equals=" + DEFAULT_NOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where nomeRichiedente equals to UPDATED_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("nomeRichiedente.equals=" + UPDATED_NOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByNomeRichiedenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where nomeRichiedente not equals to DEFAULT_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("nomeRichiedente.notEquals=" + DEFAULT_NOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where nomeRichiedente not equals to UPDATED_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("nomeRichiedente.notEquals=" + UPDATED_NOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByNomeRichiedenteIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where nomeRichiedente in DEFAULT_NOME_RICHIEDENTE or UPDATED_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("nomeRichiedente.in=" + DEFAULT_NOME_RICHIEDENTE + "," + UPDATED_NOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where nomeRichiedente equals to UPDATED_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("nomeRichiedente.in=" + UPDATED_NOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByNomeRichiedenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where nomeRichiedente is not null
        defaultPermessoTemporaneoShouldBeFound("nomeRichiedente.specified=true");

        // Get all the permessoTemporaneoList where nomeRichiedente is null
        defaultPermessoTemporaneoShouldNotBeFound("nomeRichiedente.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByNomeRichiedenteContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where nomeRichiedente contains DEFAULT_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("nomeRichiedente.contains=" + DEFAULT_NOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where nomeRichiedente contains UPDATED_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("nomeRichiedente.contains=" + UPDATED_NOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByNomeRichiedenteNotContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where nomeRichiedente does not contain DEFAULT_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("nomeRichiedente.doesNotContain=" + DEFAULT_NOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where nomeRichiedente does not contain UPDATED_NOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("nomeRichiedente.doesNotContain=" + UPDATED_NOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCognomeRichiedenteIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where cognomeRichiedente equals to DEFAULT_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("cognomeRichiedente.equals=" + DEFAULT_COGNOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where cognomeRichiedente equals to UPDATED_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("cognomeRichiedente.equals=" + UPDATED_COGNOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCognomeRichiedenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where cognomeRichiedente not equals to DEFAULT_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("cognomeRichiedente.notEquals=" + DEFAULT_COGNOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where cognomeRichiedente not equals to UPDATED_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("cognomeRichiedente.notEquals=" + UPDATED_COGNOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCognomeRichiedenteIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where cognomeRichiedente in DEFAULT_COGNOME_RICHIEDENTE or UPDATED_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("cognomeRichiedente.in=" + DEFAULT_COGNOME_RICHIEDENTE + "," + UPDATED_COGNOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where cognomeRichiedente equals to UPDATED_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("cognomeRichiedente.in=" + UPDATED_COGNOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCognomeRichiedenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where cognomeRichiedente is not null
        defaultPermessoTemporaneoShouldBeFound("cognomeRichiedente.specified=true");

        // Get all the permessoTemporaneoList where cognomeRichiedente is null
        defaultPermessoTemporaneoShouldNotBeFound("cognomeRichiedente.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCognomeRichiedenteContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where cognomeRichiedente contains DEFAULT_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("cognomeRichiedente.contains=" + DEFAULT_COGNOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where cognomeRichiedente contains UPDATED_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("cognomeRichiedente.contains=" + UPDATED_COGNOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCognomeRichiedenteNotContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where cognomeRichiedente does not contain DEFAULT_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("cognomeRichiedente.doesNotContain=" + DEFAULT_COGNOME_RICHIEDENTE);

        // Get all the permessoTemporaneoList where cognomeRichiedente does not contain UPDATED_COGNOME_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("cognomeRichiedente.doesNotContain=" + UPDATED_COGNOME_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByRagioneSocialeIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where ragioneSociale equals to DEFAULT_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldBeFound("ragioneSociale.equals=" + DEFAULT_RAGIONE_SOCIALE);

        // Get all the permessoTemporaneoList where ragioneSociale equals to UPDATED_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldNotBeFound("ragioneSociale.equals=" + UPDATED_RAGIONE_SOCIALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByRagioneSocialeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where ragioneSociale not equals to DEFAULT_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldNotBeFound("ragioneSociale.notEquals=" + DEFAULT_RAGIONE_SOCIALE);

        // Get all the permessoTemporaneoList where ragioneSociale not equals to UPDATED_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldBeFound("ragioneSociale.notEquals=" + UPDATED_RAGIONE_SOCIALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByRagioneSocialeIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where ragioneSociale in DEFAULT_RAGIONE_SOCIALE or UPDATED_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldBeFound("ragioneSociale.in=" + DEFAULT_RAGIONE_SOCIALE + "," + UPDATED_RAGIONE_SOCIALE);

        // Get all the permessoTemporaneoList where ragioneSociale equals to UPDATED_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldNotBeFound("ragioneSociale.in=" + UPDATED_RAGIONE_SOCIALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByRagioneSocialeIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where ragioneSociale is not null
        defaultPermessoTemporaneoShouldBeFound("ragioneSociale.specified=true");

        // Get all the permessoTemporaneoList where ragioneSociale is null
        defaultPermessoTemporaneoShouldNotBeFound("ragioneSociale.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByRagioneSocialeContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where ragioneSociale contains DEFAULT_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldBeFound("ragioneSociale.contains=" + DEFAULT_RAGIONE_SOCIALE);

        // Get all the permessoTemporaneoList where ragioneSociale contains UPDATED_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldNotBeFound("ragioneSociale.contains=" + UPDATED_RAGIONE_SOCIALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByRagioneSocialeNotContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where ragioneSociale does not contain DEFAULT_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldNotBeFound("ragioneSociale.doesNotContain=" + DEFAULT_RAGIONE_SOCIALE);

        // Get all the permessoTemporaneoList where ragioneSociale does not contain UPDATED_RAGIONE_SOCIALE
        defaultPermessoTemporaneoShouldBeFound("ragioneSociale.doesNotContain=" + UPDATED_RAGIONE_SOCIALE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCodiceFiscaleRichiedenteIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente equals to DEFAULT_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("codiceFiscaleRichiedente.equals=" + DEFAULT_CODICE_FISCALE_RICHIEDENTE);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente equals to UPDATED_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("codiceFiscaleRichiedente.equals=" + UPDATED_CODICE_FISCALE_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCodiceFiscaleRichiedenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente not equals to DEFAULT_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("codiceFiscaleRichiedente.notEquals=" + DEFAULT_CODICE_FISCALE_RICHIEDENTE);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente not equals to UPDATED_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("codiceFiscaleRichiedente.notEquals=" + UPDATED_CODICE_FISCALE_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCodiceFiscaleRichiedenteIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente in DEFAULT_CODICE_FISCALE_RICHIEDENTE or UPDATED_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound(
            "codiceFiscaleRichiedente.in=" + DEFAULT_CODICE_FISCALE_RICHIEDENTE + "," + UPDATED_CODICE_FISCALE_RICHIEDENTE
        );

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente equals to UPDATED_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("codiceFiscaleRichiedente.in=" + UPDATED_CODICE_FISCALE_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCodiceFiscaleRichiedenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente is not null
        defaultPermessoTemporaneoShouldBeFound("codiceFiscaleRichiedente.specified=true");

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente is null
        defaultPermessoTemporaneoShouldNotBeFound("codiceFiscaleRichiedente.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCodiceFiscaleRichiedenteContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente contains DEFAULT_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("codiceFiscaleRichiedente.contains=" + DEFAULT_CODICE_FISCALE_RICHIEDENTE);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente contains UPDATED_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("codiceFiscaleRichiedente.contains=" + UPDATED_CODICE_FISCALE_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCodiceFiscaleRichiedenteNotContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente does not contain DEFAULT_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldNotBeFound("codiceFiscaleRichiedente.doesNotContain=" + DEFAULT_CODICE_FISCALE_RICHIEDENTE);

        // Get all the permessoTemporaneoList where codiceFiscaleRichiedente does not contain UPDATED_CODICE_FISCALE_RICHIEDENTE
        defaultPermessoTemporaneoShouldBeFound("codiceFiscaleRichiedente.doesNotContain=" + UPDATED_CODICE_FISCALE_RICHIEDENTE);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPartitaIvaIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where partitaIva equals to DEFAULT_PARTITA_IVA
        defaultPermessoTemporaneoShouldBeFound("partitaIva.equals=" + DEFAULT_PARTITA_IVA);

        // Get all the permessoTemporaneoList where partitaIva equals to UPDATED_PARTITA_IVA
        defaultPermessoTemporaneoShouldNotBeFound("partitaIva.equals=" + UPDATED_PARTITA_IVA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPartitaIvaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where partitaIva not equals to DEFAULT_PARTITA_IVA
        defaultPermessoTemporaneoShouldNotBeFound("partitaIva.notEquals=" + DEFAULT_PARTITA_IVA);

        // Get all the permessoTemporaneoList where partitaIva not equals to UPDATED_PARTITA_IVA
        defaultPermessoTemporaneoShouldBeFound("partitaIva.notEquals=" + UPDATED_PARTITA_IVA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPartitaIvaIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where partitaIva in DEFAULT_PARTITA_IVA or UPDATED_PARTITA_IVA
        defaultPermessoTemporaneoShouldBeFound("partitaIva.in=" + DEFAULT_PARTITA_IVA + "," + UPDATED_PARTITA_IVA);

        // Get all the permessoTemporaneoList where partitaIva equals to UPDATED_PARTITA_IVA
        defaultPermessoTemporaneoShouldNotBeFound("partitaIva.in=" + UPDATED_PARTITA_IVA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPartitaIvaIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where partitaIva is not null
        defaultPermessoTemporaneoShouldBeFound("partitaIva.specified=true");

        // Get all the permessoTemporaneoList where partitaIva is null
        defaultPermessoTemporaneoShouldNotBeFound("partitaIva.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPartitaIvaContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where partitaIva contains DEFAULT_PARTITA_IVA
        defaultPermessoTemporaneoShouldBeFound("partitaIva.contains=" + DEFAULT_PARTITA_IVA);

        // Get all the permessoTemporaneoList where partitaIva contains UPDATED_PARTITA_IVA
        defaultPermessoTemporaneoShouldNotBeFound("partitaIva.contains=" + UPDATED_PARTITA_IVA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPartitaIvaNotContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where partitaIva does not contain DEFAULT_PARTITA_IVA
        defaultPermessoTemporaneoShouldNotBeFound("partitaIva.doesNotContain=" + DEFAULT_PARTITA_IVA);

        // Get all the permessoTemporaneoList where partitaIva does not contain UPDATED_PARTITA_IVA
        defaultPermessoTemporaneoShouldBeFound("partitaIva.doesNotContain=" + UPDATED_PARTITA_IVA);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDataInizioIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where dataInizio equals to DEFAULT_DATA_INIZIO
        defaultPermessoTemporaneoShouldBeFound("dataInizio.equals=" + DEFAULT_DATA_INIZIO);

        // Get all the permessoTemporaneoList where dataInizio equals to UPDATED_DATA_INIZIO
        defaultPermessoTemporaneoShouldNotBeFound("dataInizio.equals=" + UPDATED_DATA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDataInizioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where dataInizio not equals to DEFAULT_DATA_INIZIO
        defaultPermessoTemporaneoShouldNotBeFound("dataInizio.notEquals=" + DEFAULT_DATA_INIZIO);

        // Get all the permessoTemporaneoList where dataInizio not equals to UPDATED_DATA_INIZIO
        defaultPermessoTemporaneoShouldBeFound("dataInizio.notEquals=" + UPDATED_DATA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDataInizioIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where dataInizio in DEFAULT_DATA_INIZIO or UPDATED_DATA_INIZIO
        defaultPermessoTemporaneoShouldBeFound("dataInizio.in=" + DEFAULT_DATA_INIZIO + "," + UPDATED_DATA_INIZIO);

        // Get all the permessoTemporaneoList where dataInizio equals to UPDATED_DATA_INIZIO
        defaultPermessoTemporaneoShouldNotBeFound("dataInizio.in=" + UPDATED_DATA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDataInizioIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where dataInizio is not null
        defaultPermessoTemporaneoShouldBeFound("dataInizio.specified=true");

        // Get all the permessoTemporaneoList where dataInizio is null
        defaultPermessoTemporaneoShouldNotBeFound("dataInizio.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDataInizioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where dataInizio is greater than or equal to DEFAULT_DATA_INIZIO
        defaultPermessoTemporaneoShouldBeFound("dataInizio.greaterThanOrEqual=" + DEFAULT_DATA_INIZIO);

        // Get all the permessoTemporaneoList where dataInizio is greater than or equal to UPDATED_DATA_INIZIO
        defaultPermessoTemporaneoShouldNotBeFound("dataInizio.greaterThanOrEqual=" + UPDATED_DATA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDataInizioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where dataInizio is less than or equal to DEFAULT_DATA_INIZIO
        defaultPermessoTemporaneoShouldBeFound("dataInizio.lessThanOrEqual=" + DEFAULT_DATA_INIZIO);

        // Get all the permessoTemporaneoList where dataInizio is less than or equal to SMALLER_DATA_INIZIO
        defaultPermessoTemporaneoShouldNotBeFound("dataInizio.lessThanOrEqual=" + SMALLER_DATA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDataInizioIsLessThanSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where dataInizio is less than DEFAULT_DATA_INIZIO
        defaultPermessoTemporaneoShouldNotBeFound("dataInizio.lessThan=" + DEFAULT_DATA_INIZIO);

        // Get all the permessoTemporaneoList where dataInizio is less than UPDATED_DATA_INIZIO
        defaultPermessoTemporaneoShouldBeFound("dataInizio.lessThan=" + UPDATED_DATA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDataInizioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where dataInizio is greater than DEFAULT_DATA_INIZIO
        defaultPermessoTemporaneoShouldNotBeFound("dataInizio.greaterThan=" + DEFAULT_DATA_INIZIO);

        // Get all the permessoTemporaneoList where dataInizio is greater than SMALLER_DATA_INIZIO
        defaultPermessoTemporaneoShouldBeFound("dataInizio.greaterThan=" + SMALLER_DATA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByImpostaDiBolloIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where impostaDiBollo equals to DEFAULT_IMPOSTA_DI_BOLLO
        defaultPermessoTemporaneoShouldBeFound("impostaDiBollo.equals=" + DEFAULT_IMPOSTA_DI_BOLLO);

        // Get all the permessoTemporaneoList where impostaDiBollo equals to UPDATED_IMPOSTA_DI_BOLLO
        defaultPermessoTemporaneoShouldNotBeFound("impostaDiBollo.equals=" + UPDATED_IMPOSTA_DI_BOLLO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByImpostaDiBolloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where impostaDiBollo not equals to DEFAULT_IMPOSTA_DI_BOLLO
        defaultPermessoTemporaneoShouldNotBeFound("impostaDiBollo.notEquals=" + DEFAULT_IMPOSTA_DI_BOLLO);

        // Get all the permessoTemporaneoList where impostaDiBollo not equals to UPDATED_IMPOSTA_DI_BOLLO
        defaultPermessoTemporaneoShouldBeFound("impostaDiBollo.notEquals=" + UPDATED_IMPOSTA_DI_BOLLO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByImpostaDiBolloIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where impostaDiBollo in DEFAULT_IMPOSTA_DI_BOLLO or UPDATED_IMPOSTA_DI_BOLLO
        defaultPermessoTemporaneoShouldBeFound("impostaDiBollo.in=" + DEFAULT_IMPOSTA_DI_BOLLO + "," + UPDATED_IMPOSTA_DI_BOLLO);

        // Get all the permessoTemporaneoList where impostaDiBollo equals to UPDATED_IMPOSTA_DI_BOLLO
        defaultPermessoTemporaneoShouldNotBeFound("impostaDiBollo.in=" + UPDATED_IMPOSTA_DI_BOLLO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByImpostaDiBolloIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where impostaDiBollo is not null
        defaultPermessoTemporaneoShouldBeFound("impostaDiBollo.specified=true");

        // Get all the permessoTemporaneoList where impostaDiBollo is null
        defaultPermessoTemporaneoShouldNotBeFound("impostaDiBollo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCostoIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where costo equals to DEFAULT_COSTO
        defaultPermessoTemporaneoShouldBeFound("costo.equals=" + DEFAULT_COSTO);

        // Get all the permessoTemporaneoList where costo equals to UPDATED_COSTO
        defaultPermessoTemporaneoShouldNotBeFound("costo.equals=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCostoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where costo not equals to DEFAULT_COSTO
        defaultPermessoTemporaneoShouldNotBeFound("costo.notEquals=" + DEFAULT_COSTO);

        // Get all the permessoTemporaneoList where costo not equals to UPDATED_COSTO
        defaultPermessoTemporaneoShouldBeFound("costo.notEquals=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCostoIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where costo in DEFAULT_COSTO or UPDATED_COSTO
        defaultPermessoTemporaneoShouldBeFound("costo.in=" + DEFAULT_COSTO + "," + UPDATED_COSTO);

        // Get all the permessoTemporaneoList where costo equals to UPDATED_COSTO
        defaultPermessoTemporaneoShouldNotBeFound("costo.in=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCostoIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where costo is not null
        defaultPermessoTemporaneoShouldBeFound("costo.specified=true");

        // Get all the permessoTemporaneoList where costo is null
        defaultPermessoTemporaneoShouldNotBeFound("costo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCostoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where costo is greater than or equal to DEFAULT_COSTO
        defaultPermessoTemporaneoShouldBeFound("costo.greaterThanOrEqual=" + DEFAULT_COSTO);

        // Get all the permessoTemporaneoList where costo is greater than or equal to UPDATED_COSTO
        defaultPermessoTemporaneoShouldNotBeFound("costo.greaterThanOrEqual=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCostoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where costo is less than or equal to DEFAULT_COSTO
        defaultPermessoTemporaneoShouldBeFound("costo.lessThanOrEqual=" + DEFAULT_COSTO);

        // Get all the permessoTemporaneoList where costo is less than or equal to SMALLER_COSTO
        defaultPermessoTemporaneoShouldNotBeFound("costo.lessThanOrEqual=" + SMALLER_COSTO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCostoIsLessThanSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where costo is less than DEFAULT_COSTO
        defaultPermessoTemporaneoShouldNotBeFound("costo.lessThan=" + DEFAULT_COSTO);

        // Get all the permessoTemporaneoList where costo is less than UPDATED_COSTO
        defaultPermessoTemporaneoShouldBeFound("costo.lessThan=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCostoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where costo is greater than DEFAULT_COSTO
        defaultPermessoTemporaneoShouldNotBeFound("costo.greaterThan=" + DEFAULT_COSTO);

        // Get all the permessoTemporaneoList where costo is greater than SMALLER_COSTO
        defaultPermessoTemporaneoShouldBeFound("costo.greaterThan=" + SMALLER_COSTO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPagatoIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where pagato equals to DEFAULT_PAGATO
        defaultPermessoTemporaneoShouldBeFound("pagato.equals=" + DEFAULT_PAGATO);

        // Get all the permessoTemporaneoList where pagato equals to UPDATED_PAGATO
        defaultPermessoTemporaneoShouldNotBeFound("pagato.equals=" + UPDATED_PAGATO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPagatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where pagato not equals to DEFAULT_PAGATO
        defaultPermessoTemporaneoShouldNotBeFound("pagato.notEquals=" + DEFAULT_PAGATO);

        // Get all the permessoTemporaneoList where pagato not equals to UPDATED_PAGATO
        defaultPermessoTemporaneoShouldBeFound("pagato.notEquals=" + UPDATED_PAGATO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPagatoIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where pagato in DEFAULT_PAGATO or UPDATED_PAGATO
        defaultPermessoTemporaneoShouldBeFound("pagato.in=" + DEFAULT_PAGATO + "," + UPDATED_PAGATO);

        // Get all the permessoTemporaneoList where pagato equals to UPDATED_PAGATO
        defaultPermessoTemporaneoShouldNotBeFound("pagato.in=" + UPDATED_PAGATO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByPagatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where pagato is not null
        defaultPermessoTemporaneoShouldBeFound("pagato.specified=true");

        // Get all the permessoTemporaneoList where pagato is null
        defaultPermessoTemporaneoShouldNotBeFound("pagato.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByProtocolloIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where protocollo equals to DEFAULT_PROTOCOLLO
        defaultPermessoTemporaneoShouldBeFound("protocollo.equals=" + DEFAULT_PROTOCOLLO);

        // Get all the permessoTemporaneoList where protocollo equals to UPDATED_PROTOCOLLO
        defaultPermessoTemporaneoShouldNotBeFound("protocollo.equals=" + UPDATED_PROTOCOLLO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByProtocolloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where protocollo not equals to DEFAULT_PROTOCOLLO
        defaultPermessoTemporaneoShouldNotBeFound("protocollo.notEquals=" + DEFAULT_PROTOCOLLO);

        // Get all the permessoTemporaneoList where protocollo not equals to UPDATED_PROTOCOLLO
        defaultPermessoTemporaneoShouldBeFound("protocollo.notEquals=" + UPDATED_PROTOCOLLO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByProtocolloIsInShouldWork() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where protocollo in DEFAULT_PROTOCOLLO or UPDATED_PROTOCOLLO
        defaultPermessoTemporaneoShouldBeFound("protocollo.in=" + DEFAULT_PROTOCOLLO + "," + UPDATED_PROTOCOLLO);

        // Get all the permessoTemporaneoList where protocollo equals to UPDATED_PROTOCOLLO
        defaultPermessoTemporaneoShouldNotBeFound("protocollo.in=" + UPDATED_PROTOCOLLO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByProtocolloIsNullOrNotNull() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where protocollo is not null
        defaultPermessoTemporaneoShouldBeFound("protocollo.specified=true");

        // Get all the permessoTemporaneoList where protocollo is null
        defaultPermessoTemporaneoShouldNotBeFound("protocollo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByProtocolloContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where protocollo contains DEFAULT_PROTOCOLLO
        defaultPermessoTemporaneoShouldBeFound("protocollo.contains=" + DEFAULT_PROTOCOLLO);

        // Get all the permessoTemporaneoList where protocollo contains UPDATED_PROTOCOLLO
        defaultPermessoTemporaneoShouldNotBeFound("protocollo.contains=" + UPDATED_PROTOCOLLO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByProtocolloNotContainsSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);

        // Get all the permessoTemporaneoList where protocollo does not contain DEFAULT_PROTOCOLLO
        defaultPermessoTemporaneoShouldNotBeFound("protocollo.doesNotContain=" + DEFAULT_PROTOCOLLO);

        // Get all the permessoTemporaneoList where protocollo does not contain UPDATED_PROTOCOLLO
        defaultPermessoTemporaneoShouldBeFound("protocollo.doesNotContain=" + UPDATED_PROTOCOLLO);
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByCalendarioIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Calendarizzazione calendario = CalendarizzazioneResourceIT.createEntity(em);
        em.persist(calendario);
        em.flush();
        permessoTemporaneo.setCalendario(calendario);
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Long calendarioId = calendario.getId();

        // Get all the permessoTemporaneoList where calendario equals to calendarioId
        defaultPermessoTemporaneoShouldBeFound("calendarioId.equals=" + calendarioId);

        // Get all the permessoTemporaneoList where calendario equals to calendarioId + 1
        defaultPermessoTemporaneoShouldNotBeFound("calendarioId.equals=" + (calendarioId + 1));
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByTipoPermessoIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        TipologiaPermesso tipoPermesso = TipologiaPermessoResourceIT.createEntity(em);
        em.persist(tipoPermesso);
        em.flush();
        permessoTemporaneo.setTipoPermesso(tipoPermesso);
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Long tipoPermessoId = tipoPermesso.getId();

        // Get all the permessoTemporaneoList where tipoPermesso equals to tipoPermessoId
        defaultPermessoTemporaneoShouldBeFound("tipoPermessoId.equals=" + tipoPermessoId);

        // Get all the permessoTemporaneoList where tipoPermesso equals to tipoPermessoId + 1
        defaultPermessoTemporaneoShouldNotBeFound("tipoPermessoId.equals=" + (tipoPermessoId + 1));
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByDurataIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        DurataCosto durata = DurataCostoResourceIT.createEntity(em);
        em.persist(durata);
        em.flush();
        permessoTemporaneo.setDurata(durata);
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Long durataId = durata.getId();

        // Get all the permessoTemporaneoList where durata equals to durataId
        defaultPermessoTemporaneoShouldBeFound("durataId.equals=" + durataId);

        // Get all the permessoTemporaneoList where durata equals to durataId + 1
        defaultPermessoTemporaneoShouldNotBeFound("durataId.equals=" + (durataId + 1));
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Zona nome = ZonaResourceIT.createEntity(em);
        em.persist(nome);
        em.flush();
        permessoTemporaneo.setNome(nome);
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Long nomeId = nome.getId();

        // Get all the permessoTemporaneoList where nome equals to nomeId
        defaultPermessoTemporaneoShouldBeFound("nomeId.equals=" + nomeId);

        // Get all the permessoTemporaneoList where nome equals to nomeId + 1
        defaultPermessoTemporaneoShouldNotBeFound("nomeId.equals=" + (nomeId + 1));
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByMotivazioneIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Motivazione motivazione = MotivazioneResourceIT.createEntity(em);
        em.persist(motivazione);
        em.flush();
        permessoTemporaneo.setMotivazione(motivazione);
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Long motivazioneId = motivazione.getId();

        // Get all the permessoTemporaneoList where motivazione equals to motivazioneId
        defaultPermessoTemporaneoShouldBeFound("motivazioneId.equals=" + motivazioneId);

        // Get all the permessoTemporaneoList where motivazione equals to motivazioneId + 1
        defaultPermessoTemporaneoShouldNotBeFound("motivazioneId.equals=" + (motivazioneId + 1));
    }

    @Test
    @Transactional
    public void getAllPermessoTemporaneosByAutorizzazioniIsEqualToSomething() throws Exception {
        // Initialize the database
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Autorizzazione autorizzazioni = AutorizzazioneResourceIT.createEntity(em);
        em.persist(autorizzazioni);
        em.flush();
        permessoTemporaneo.addAutorizzazioni(autorizzazioni);
        permessoTemporaneoRepository.saveAndFlush(permessoTemporaneo);
        Long autorizzazioniId = autorizzazioni.getId();

        // Get all the permessoTemporaneoList where autorizzazioni equals to autorizzazioniId
        defaultPermessoTemporaneoShouldBeFound("autorizzazioniId.equals=" + autorizzazioniId);

        // Get all the permessoTemporaneoList where autorizzazioni equals to autorizzazioniId + 1
        defaultPermessoTemporaneoShouldNotBeFound("autorizzazioniId.equals=" + (autorizzazioniId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPermessoTemporaneoShouldBeFound(String filter) throws Exception {
        restPermessoTemporaneoMockMvc
            .perform(get("/api/permesso-temporaneos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permessoTemporaneo.getId().intValue())))
            .andExpect(jsonPath("$.[*].targa").value(hasItem(DEFAULT_TARGA)))
            .andExpect(jsonPath("$.[*].domicilioDigitale").value(hasItem(DEFAULT_DOMICILIO_DIGITALE)))
            .andExpect(jsonPath("$.[*].tipoPersona").value(hasItem(DEFAULT_TIPO_PERSONA.toString())))
            .andExpect(jsonPath("$.[*].nomeRichiedente").value(hasItem(DEFAULT_NOME_RICHIEDENTE)))
            .andExpect(jsonPath("$.[*].cognomeRichiedente").value(hasItem(DEFAULT_COGNOME_RICHIEDENTE)))
            .andExpect(jsonPath("$.[*].ragioneSociale").value(hasItem(DEFAULT_RAGIONE_SOCIALE)))
            .andExpect(jsonPath("$.[*].codiceFiscaleRichiedente").value(hasItem(DEFAULT_CODICE_FISCALE_RICHIEDENTE)))
            .andExpect(jsonPath("$.[*].partitaIva").value(hasItem(DEFAULT_PARTITA_IVA)))
            .andExpect(jsonPath("$.[*].dataInizio").value(hasItem(DEFAULT_DATA_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].impostaDiBollo").value(hasItem(DEFAULT_IMPOSTA_DI_BOLLO.booleanValue())))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].copiaFirmataContentType").value(hasItem(DEFAULT_COPIA_FIRMATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].copiaFirmata").value(hasItem(Base64Utils.encodeToString(DEFAULT_COPIA_FIRMATA))))
            .andExpect(jsonPath("$.[*].documentoRiconoscimentoContentType").value(hasItem(DEFAULT_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE)))
            .andExpect(
                jsonPath("$.[*].documentoRiconoscimento").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO_RICONOSCIMENTO)))
            )
            .andExpect(jsonPath("$.[*].pagato").value(hasItem(DEFAULT_PAGATO.booleanValue())))
            .andExpect(jsonPath("$.[*].protocollo").value(hasItem(DEFAULT_PROTOCOLLO)));

        // Check, that the count call also returns 1
        restPermessoTemporaneoMockMvc
            .perform(get("/api/permesso-temporaneos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPermessoTemporaneoShouldNotBeFound(String filter) throws Exception {
        restPermessoTemporaneoMockMvc
            .perform(get("/api/permesso-temporaneos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPermessoTemporaneoMockMvc
            .perform(get("/api/permesso-temporaneos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPermessoTemporaneo() throws Exception {
        // Get the permessoTemporaneo
        restPermessoTemporaneoMockMvc.perform(get("/api/permesso-temporaneos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePermessoTemporaneo() throws Exception {
        // Initialize the database
        permessoTemporaneoService.save(permessoTemporaneo);

        int databaseSizeBeforeUpdate = permessoTemporaneoRepository.findAll().size();

        // Update the permessoTemporaneo
        PermessoTemporaneo updatedPermessoTemporaneo = permessoTemporaneoRepository.findById(permessoTemporaneo.getId()).get();
        // Disconnect from session so that the updates on updatedPermessoTemporaneo are not directly saved in db
        em.detach(updatedPermessoTemporaneo);
        updatedPermessoTemporaneo
            .targa(UPDATED_TARGA)
            .domicilioDigitale(UPDATED_DOMICILIO_DIGITALE)
            .tipoPersona(UPDATED_TIPO_PERSONA)
            .nomeRichiedente(UPDATED_NOME_RICHIEDENTE)
            .cognomeRichiedente(UPDATED_COGNOME_RICHIEDENTE)
            .ragioneSociale(UPDATED_RAGIONE_SOCIALE)
            .codiceFiscaleRichiedente(UPDATED_CODICE_FISCALE_RICHIEDENTE)
            .partitaIva(UPDATED_PARTITA_IVA)
            .dataInizio(UPDATED_DATA_INIZIO)
            .impostaDiBollo(UPDATED_IMPOSTA_DI_BOLLO)
            .costo(UPDATED_COSTO)
            .copiaFirmata(UPDATED_COPIA_FIRMATA)
            .copiaFirmataContentType(UPDATED_COPIA_FIRMATA_CONTENT_TYPE)
            .documentoRiconoscimento(UPDATED_DOCUMENTO_RICONOSCIMENTO)
            .documentoRiconoscimentoContentType(UPDATED_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE)
            .pagato(UPDATED_PAGATO)
            .protocollo(UPDATED_PROTOCOLLO);

        restPermessoTemporaneoMockMvc
            .perform(
                put("/api/permesso-temporaneos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPermessoTemporaneo))
            )
            .andExpect(status().isOk());

        // Validate the PermessoTemporaneo in the database
        List<PermessoTemporaneo> permessoTemporaneoList = permessoTemporaneoRepository.findAll();
        assertThat(permessoTemporaneoList).hasSize(databaseSizeBeforeUpdate);
        PermessoTemporaneo testPermessoTemporaneo = permessoTemporaneoList.get(permessoTemporaneoList.size() - 1);
        assertThat(testPermessoTemporaneo.getTarga()).isEqualTo(UPDATED_TARGA);
        assertThat(testPermessoTemporaneo.getDomicilioDigitale()).isEqualTo(UPDATED_DOMICILIO_DIGITALE);
        assertThat(testPermessoTemporaneo.getTipoPersona()).isEqualTo(UPDATED_TIPO_PERSONA);
        assertThat(testPermessoTemporaneo.getNomeRichiedente()).isEqualTo(UPDATED_NOME_RICHIEDENTE);
        assertThat(testPermessoTemporaneo.getCognomeRichiedente()).isEqualTo(UPDATED_COGNOME_RICHIEDENTE);
        assertThat(testPermessoTemporaneo.getRagioneSociale()).isEqualTo(UPDATED_RAGIONE_SOCIALE);
        assertThat(testPermessoTemporaneo.getCodiceFiscaleRichiedente()).isEqualTo(UPDATED_CODICE_FISCALE_RICHIEDENTE);
        assertThat(testPermessoTemporaneo.getPartitaIva()).isEqualTo(UPDATED_PARTITA_IVA);
        assertThat(testPermessoTemporaneo.getDataInizio()).isEqualTo(UPDATED_DATA_INIZIO);
        assertThat(testPermessoTemporaneo.isImpostaDiBollo()).isEqualTo(UPDATED_IMPOSTA_DI_BOLLO);
        assertThat(testPermessoTemporaneo.getCosto()).isEqualTo(UPDATED_COSTO);
        assertThat(testPermessoTemporaneo.getCopiaFirmata()).isEqualTo(UPDATED_COPIA_FIRMATA);
        assertThat(testPermessoTemporaneo.getCopiaFirmataContentType()).isEqualTo(UPDATED_COPIA_FIRMATA_CONTENT_TYPE);
        assertThat(testPermessoTemporaneo.getDocumentoRiconoscimento()).isEqualTo(UPDATED_DOCUMENTO_RICONOSCIMENTO);
        assertThat(testPermessoTemporaneo.getDocumentoRiconoscimentoContentType()).isEqualTo(UPDATED_DOCUMENTO_RICONOSCIMENTO_CONTENT_TYPE);
        assertThat(testPermessoTemporaneo.isPagato()).isEqualTo(UPDATED_PAGATO);
        assertThat(testPermessoTemporaneo.getProtocollo()).isEqualTo(UPDATED_PROTOCOLLO);
    }

    @Test
    @Transactional
    public void updateNonExistingPermessoTemporaneo() throws Exception {
        int databaseSizeBeforeUpdate = permessoTemporaneoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermessoTemporaneoMockMvc
            .perform(
                put("/api/permesso-temporaneos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(permessoTemporaneo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PermessoTemporaneo in the database
        List<PermessoTemporaneo> permessoTemporaneoList = permessoTemporaneoRepository.findAll();
        assertThat(permessoTemporaneoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePermessoTemporaneo() throws Exception {
        // Initialize the database
        permessoTemporaneoService.save(permessoTemporaneo);

        int databaseSizeBeforeDelete = permessoTemporaneoRepository.findAll().size();

        // Delete the permessoTemporaneo
        restPermessoTemporaneoMockMvc
            .perform(delete("/api/permesso-temporaneos/{id}", permessoTemporaneo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PermessoTemporaneo> permessoTemporaneoList = permessoTemporaneoRepository.findAll();
        assertThat(permessoTemporaneoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
