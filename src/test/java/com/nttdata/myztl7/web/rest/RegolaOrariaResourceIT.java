package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.ProfiloOrario;
import com.nttdata.myztl7.domain.RegolaOraria;
import com.nttdata.myztl7.domain.TipologiaVeicolo;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.domain.enumeration.MinutiEnum;
import com.nttdata.myztl7.domain.enumeration.MinutiEnum;
import com.nttdata.myztl7.domain.enumeration.OreEnum;
import com.nttdata.myztl7.domain.enumeration.OreEnum;
import com.nttdata.myztl7.repository.RegolaOrariaRepository;
import com.nttdata.myztl7.service.RegolaOrariaQueryService;
import com.nttdata.myztl7.service.RegolaOrariaService;
import com.nttdata.myztl7.service.dto.RegolaOrariaCriteria;
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

/**
 * Integration tests for the {@link RegolaOrariaResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RegolaOrariaResourceIT {
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final OreEnum DEFAULT_ORA_INIZIO = OreEnum.ZERO;
    private static final OreEnum UPDATED_ORA_INIZIO = OreEnum.UNA;

    private static final OreEnum DEFAULT_ORA_FINE = OreEnum.ZERO;
    private static final OreEnum UPDATED_ORA_FINE = OreEnum.UNA;

    private static final MinutiEnum DEFAULT_MINUTI_INIZIO = MinutiEnum.QUINDICI;
    private static final MinutiEnum UPDATED_MINUTI_INIZIO = MinutiEnum.TRENTA;

    private static final MinutiEnum DEFAULT_MINUTI_FINE = MinutiEnum.QUINDICI;
    private static final MinutiEnum UPDATED_MINUTI_FINE = MinutiEnum.TRENTA;

    private static final Boolean DEFAULT_LUNEDI = false;
    private static final Boolean UPDATED_LUNEDI = true;

    private static final Boolean DEFAULT_MARTEDI = false;
    private static final Boolean UPDATED_MARTEDI = true;

    private static final Boolean DEFAULT_MERCOLEDI = false;
    private static final Boolean UPDATED_MERCOLEDI = true;

    private static final Boolean DEFAULT_GIOVEDI = false;
    private static final Boolean UPDATED_GIOVEDI = true;

    private static final Boolean DEFAULT_VENERDI = false;
    private static final Boolean UPDATED_VENERDI = true;

    private static final Boolean DEFAULT_SABATO = false;
    private static final Boolean UPDATED_SABATO = true;

    private static final Boolean DEFAULT_DOMENICA = false;
    private static final Boolean UPDATED_DOMENICA = true;

    private static final Boolean DEFAULT_FESTIVI = false;
    private static final Boolean UPDATED_FESTIVI = true;

    private static final EntityStatus DEFAULT_STATO = EntityStatus.ATTIVO;
    private static final EntityStatus UPDATED_STATO = EntityStatus.DISATTIVO;

    @Autowired
    private RegolaOrariaRepository regolaOrariaRepository;

    @Mock
    private RegolaOrariaRepository regolaOrariaRepositoryMock;

    @Mock
    private RegolaOrariaService regolaOrariaServiceMock;

    @Autowired
    private RegolaOrariaService regolaOrariaService;

    @Autowired
    private RegolaOrariaQueryService regolaOrariaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegolaOrariaMockMvc;

    private RegolaOraria regolaOraria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegolaOraria createEntity(EntityManager em) {
        RegolaOraria regolaOraria = new RegolaOraria()
            .nome(DEFAULT_NOME)
            .oraInizio(DEFAULT_ORA_INIZIO)
            .oraFine(DEFAULT_ORA_FINE)
            .minutiInizio(DEFAULT_MINUTI_INIZIO)
            .minutiFine(DEFAULT_MINUTI_FINE)
            .lunedi(DEFAULT_LUNEDI)
            .martedi(DEFAULT_MARTEDI)
            .mercoledi(DEFAULT_MERCOLEDI)
            .giovedi(DEFAULT_GIOVEDI)
            .venerdi(DEFAULT_VENERDI)
            .sabato(DEFAULT_SABATO)
            .domenica(DEFAULT_DOMENICA)
            .festivi(DEFAULT_FESTIVI)
            .stato(DEFAULT_STATO);
        return regolaOraria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegolaOraria createUpdatedEntity(EntityManager em) {
        RegolaOraria regolaOraria = new RegolaOraria()
            .nome(UPDATED_NOME)
            .oraInizio(UPDATED_ORA_INIZIO)
            .oraFine(UPDATED_ORA_FINE)
            .minutiInizio(UPDATED_MINUTI_INIZIO)
            .minutiFine(UPDATED_MINUTI_FINE)
            .lunedi(UPDATED_LUNEDI)
            .martedi(UPDATED_MARTEDI)
            .mercoledi(UPDATED_MERCOLEDI)
            .giovedi(UPDATED_GIOVEDI)
            .venerdi(UPDATED_VENERDI)
            .sabato(UPDATED_SABATO)
            .domenica(UPDATED_DOMENICA)
            .festivi(UPDATED_FESTIVI)
            .stato(UPDATED_STATO);
        return regolaOraria;
    }

    @BeforeEach
    public void initTest() {
        regolaOraria = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegolaOraria() throws Exception {
        int databaseSizeBeforeCreate = regolaOrariaRepository.findAll().size();
        // Create the RegolaOraria
        restRegolaOrariaMockMvc
            .perform(
                post("/api/regola-orarias").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regolaOraria))
            )
            .andExpect(status().isCreated());

        // Validate the RegolaOraria in the database
        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeCreate + 1);
        RegolaOraria testRegolaOraria = regolaOrariaList.get(regolaOrariaList.size() - 1);
        assertThat(testRegolaOraria.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testRegolaOraria.getOraInizio()).isEqualTo(DEFAULT_ORA_INIZIO);
        assertThat(testRegolaOraria.getOraFine()).isEqualTo(DEFAULT_ORA_FINE);
        assertThat(testRegolaOraria.getMinutiInizio()).isEqualTo(DEFAULT_MINUTI_INIZIO);
        assertThat(testRegolaOraria.getMinutiFine()).isEqualTo(DEFAULT_MINUTI_FINE);
        assertThat(testRegolaOraria.isLunedi()).isEqualTo(DEFAULT_LUNEDI);
        assertThat(testRegolaOraria.isMartedi()).isEqualTo(DEFAULT_MARTEDI);
        assertThat(testRegolaOraria.isMercoledi()).isEqualTo(DEFAULT_MERCOLEDI);
        assertThat(testRegolaOraria.isGiovedi()).isEqualTo(DEFAULT_GIOVEDI);
        assertThat(testRegolaOraria.isVenerdi()).isEqualTo(DEFAULT_VENERDI);
        assertThat(testRegolaOraria.isSabato()).isEqualTo(DEFAULT_SABATO);
        assertThat(testRegolaOraria.isDomenica()).isEqualTo(DEFAULT_DOMENICA);
        assertThat(testRegolaOraria.isFestivi()).isEqualTo(DEFAULT_FESTIVI);
        assertThat(testRegolaOraria.getStato()).isEqualTo(DEFAULT_STATO);
    }

    @Test
    @Transactional
    public void createRegolaOrariaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regolaOrariaRepository.findAll().size();

        // Create the RegolaOraria with an existing ID
        regolaOraria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegolaOrariaMockMvc
            .perform(
                post("/api/regola-orarias").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regolaOraria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegolaOraria in the database
        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = regolaOrariaRepository.findAll().size();
        // set the field null
        regolaOraria.setNome(null);

        // Create the RegolaOraria, which fails.

        restRegolaOrariaMockMvc
            .perform(
                post("/api/regola-orarias").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regolaOraria))
            )
            .andExpect(status().isBadRequest());

        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOraInizioIsRequired() throws Exception {
        int databaseSizeBeforeTest = regolaOrariaRepository.findAll().size();
        // set the field null
        regolaOraria.setOraInizio(null);

        // Create the RegolaOraria, which fails.

        restRegolaOrariaMockMvc
            .perform(
                post("/api/regola-orarias").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regolaOraria))
            )
            .andExpect(status().isBadRequest());

        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOraFineIsRequired() throws Exception {
        int databaseSizeBeforeTest = regolaOrariaRepository.findAll().size();
        // set the field null
        regolaOraria.setOraFine(null);

        // Create the RegolaOraria, which fails.

        restRegolaOrariaMockMvc
            .perform(
                post("/api/regola-orarias").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regolaOraria))
            )
            .andExpect(status().isBadRequest());

        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinutiInizioIsRequired() throws Exception {
        int databaseSizeBeforeTest = regolaOrariaRepository.findAll().size();
        // set the field null
        regolaOraria.setMinutiInizio(null);

        // Create the RegolaOraria, which fails.

        restRegolaOrariaMockMvc
            .perform(
                post("/api/regola-orarias").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regolaOraria))
            )
            .andExpect(status().isBadRequest());

        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinutiFineIsRequired() throws Exception {
        int databaseSizeBeforeTest = regolaOrariaRepository.findAll().size();
        // set the field null
        regolaOraria.setMinutiFine(null);

        // Create the RegolaOraria, which fails.

        restRegolaOrariaMockMvc
            .perform(
                post("/api/regola-orarias").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regolaOraria))
            )
            .andExpect(status().isBadRequest());

        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = regolaOrariaRepository.findAll().size();
        // set the field null
        regolaOraria.setStato(null);

        // Create the RegolaOraria, which fails.

        restRegolaOrariaMockMvc
            .perform(
                post("/api/regola-orarias").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regolaOraria))
            )
            .andExpect(status().isBadRequest());

        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegolaOrarias() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList
        restRegolaOrariaMockMvc
            .perform(get("/api/regola-orarias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regolaOraria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].oraInizio").value(hasItem(DEFAULT_ORA_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].oraFine").value(hasItem(DEFAULT_ORA_FINE.toString())))
            .andExpect(jsonPath("$.[*].minutiInizio").value(hasItem(DEFAULT_MINUTI_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].minutiFine").value(hasItem(DEFAULT_MINUTI_FINE.toString())))
            .andExpect(jsonPath("$.[*].lunedi").value(hasItem(DEFAULT_LUNEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].martedi").value(hasItem(DEFAULT_MARTEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].mercoledi").value(hasItem(DEFAULT_MERCOLEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].giovedi").value(hasItem(DEFAULT_GIOVEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].venerdi").value(hasItem(DEFAULT_VENERDI.booleanValue())))
            .andExpect(jsonPath("$.[*].sabato").value(hasItem(DEFAULT_SABATO.booleanValue())))
            .andExpect(jsonPath("$.[*].domenica").value(hasItem(DEFAULT_DOMENICA.booleanValue())))
            .andExpect(jsonPath("$.[*].festivi").value(hasItem(DEFAULT_FESTIVI.booleanValue())))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllRegolaOrariasWithEagerRelationshipsIsEnabled() throws Exception {
        when(regolaOrariaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRegolaOrariaMockMvc.perform(get("/api/regola-orarias?eagerload=true")).andExpect(status().isOk());

        verify(regolaOrariaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllRegolaOrariasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(regolaOrariaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRegolaOrariaMockMvc.perform(get("/api/regola-orarias?eagerload=true")).andExpect(status().isOk());

        verify(regolaOrariaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRegolaOraria() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get the regolaOraria
        restRegolaOrariaMockMvc
            .perform(get("/api/regola-orarias/{id}", regolaOraria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regolaOraria.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.oraInizio").value(DEFAULT_ORA_INIZIO.toString()))
            .andExpect(jsonPath("$.oraFine").value(DEFAULT_ORA_FINE.toString()))
            .andExpect(jsonPath("$.minutiInizio").value(DEFAULT_MINUTI_INIZIO.toString()))
            .andExpect(jsonPath("$.minutiFine").value(DEFAULT_MINUTI_FINE.toString()))
            .andExpect(jsonPath("$.lunedi").value(DEFAULT_LUNEDI.booleanValue()))
            .andExpect(jsonPath("$.martedi").value(DEFAULT_MARTEDI.booleanValue()))
            .andExpect(jsonPath("$.mercoledi").value(DEFAULT_MERCOLEDI.booleanValue()))
            .andExpect(jsonPath("$.giovedi").value(DEFAULT_GIOVEDI.booleanValue()))
            .andExpect(jsonPath("$.venerdi").value(DEFAULT_VENERDI.booleanValue()))
            .andExpect(jsonPath("$.sabato").value(DEFAULT_SABATO.booleanValue()))
            .andExpect(jsonPath("$.domenica").value(DEFAULT_DOMENICA.booleanValue()))
            .andExpect(jsonPath("$.festivi").value(DEFAULT_FESTIVI.booleanValue()))
            .andExpect(jsonPath("$.stato").value(DEFAULT_STATO.toString()));
    }

    @Test
    @Transactional
    public void getRegolaOrariasByIdFiltering() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        Long id = regolaOraria.getId();

        defaultRegolaOrariaShouldBeFound("id.equals=" + id);
        defaultRegolaOrariaShouldNotBeFound("id.notEquals=" + id);

        defaultRegolaOrariaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegolaOrariaShouldNotBeFound("id.greaterThan=" + id);

        defaultRegolaOrariaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegolaOrariaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where nome equals to DEFAULT_NOME
        defaultRegolaOrariaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the regolaOrariaList where nome equals to UPDATED_NOME
        defaultRegolaOrariaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where nome not equals to DEFAULT_NOME
        defaultRegolaOrariaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the regolaOrariaList where nome not equals to UPDATED_NOME
        defaultRegolaOrariaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultRegolaOrariaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the regolaOrariaList where nome equals to UPDATED_NOME
        defaultRegolaOrariaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where nome is not null
        defaultRegolaOrariaShouldBeFound("nome.specified=true");

        // Get all the regolaOrariaList where nome is null
        defaultRegolaOrariaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByNomeContainsSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where nome contains DEFAULT_NOME
        defaultRegolaOrariaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the regolaOrariaList where nome contains UPDATED_NOME
        defaultRegolaOrariaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where nome does not contain DEFAULT_NOME
        defaultRegolaOrariaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the regolaOrariaList where nome does not contain UPDATED_NOME
        defaultRegolaOrariaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByOraInizioIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where oraInizio equals to DEFAULT_ORA_INIZIO
        defaultRegolaOrariaShouldBeFound("oraInizio.equals=" + DEFAULT_ORA_INIZIO);

        // Get all the regolaOrariaList where oraInizio equals to UPDATED_ORA_INIZIO
        defaultRegolaOrariaShouldNotBeFound("oraInizio.equals=" + UPDATED_ORA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByOraInizioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where oraInizio not equals to DEFAULT_ORA_INIZIO
        defaultRegolaOrariaShouldNotBeFound("oraInizio.notEquals=" + DEFAULT_ORA_INIZIO);

        // Get all the regolaOrariaList where oraInizio not equals to UPDATED_ORA_INIZIO
        defaultRegolaOrariaShouldBeFound("oraInizio.notEquals=" + UPDATED_ORA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByOraInizioIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where oraInizio in DEFAULT_ORA_INIZIO or UPDATED_ORA_INIZIO
        defaultRegolaOrariaShouldBeFound("oraInizio.in=" + DEFAULT_ORA_INIZIO + "," + UPDATED_ORA_INIZIO);

        // Get all the regolaOrariaList where oraInizio equals to UPDATED_ORA_INIZIO
        defaultRegolaOrariaShouldNotBeFound("oraInizio.in=" + UPDATED_ORA_INIZIO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByOraInizioIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where oraInizio is not null
        defaultRegolaOrariaShouldBeFound("oraInizio.specified=true");

        // Get all the regolaOrariaList where oraInizio is null
        defaultRegolaOrariaShouldNotBeFound("oraInizio.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByOraFineIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where oraFine equals to DEFAULT_ORA_FINE
        defaultRegolaOrariaShouldBeFound("oraFine.equals=" + DEFAULT_ORA_FINE);

        // Get all the regolaOrariaList where oraFine equals to UPDATED_ORA_FINE
        defaultRegolaOrariaShouldNotBeFound("oraFine.equals=" + UPDATED_ORA_FINE);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByOraFineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where oraFine not equals to DEFAULT_ORA_FINE
        defaultRegolaOrariaShouldNotBeFound("oraFine.notEquals=" + DEFAULT_ORA_FINE);

        // Get all the regolaOrariaList where oraFine not equals to UPDATED_ORA_FINE
        defaultRegolaOrariaShouldBeFound("oraFine.notEquals=" + UPDATED_ORA_FINE);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByOraFineIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where oraFine in DEFAULT_ORA_FINE or UPDATED_ORA_FINE
        defaultRegolaOrariaShouldBeFound("oraFine.in=" + DEFAULT_ORA_FINE + "," + UPDATED_ORA_FINE);

        // Get all the regolaOrariaList where oraFine equals to UPDATED_ORA_FINE
        defaultRegolaOrariaShouldNotBeFound("oraFine.in=" + UPDATED_ORA_FINE);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByOraFineIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where oraFine is not null
        defaultRegolaOrariaShouldBeFound("oraFine.specified=true");

        // Get all the regolaOrariaList where oraFine is null
        defaultRegolaOrariaShouldNotBeFound("oraFine.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMinutiInizioIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where minutiInizio equals to DEFAULT_MINUTI_INIZIO
        defaultRegolaOrariaShouldBeFound("minutiInizio.equals=" + DEFAULT_MINUTI_INIZIO);

        // Get all the regolaOrariaList where minutiInizio equals to UPDATED_MINUTI_INIZIO
        defaultRegolaOrariaShouldNotBeFound("minutiInizio.equals=" + UPDATED_MINUTI_INIZIO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMinutiInizioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where minutiInizio not equals to DEFAULT_MINUTI_INIZIO
        defaultRegolaOrariaShouldNotBeFound("minutiInizio.notEquals=" + DEFAULT_MINUTI_INIZIO);

        // Get all the regolaOrariaList where minutiInizio not equals to UPDATED_MINUTI_INIZIO
        defaultRegolaOrariaShouldBeFound("minutiInizio.notEquals=" + UPDATED_MINUTI_INIZIO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMinutiInizioIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where minutiInizio in DEFAULT_MINUTI_INIZIO or UPDATED_MINUTI_INIZIO
        defaultRegolaOrariaShouldBeFound("minutiInizio.in=" + DEFAULT_MINUTI_INIZIO + "," + UPDATED_MINUTI_INIZIO);

        // Get all the regolaOrariaList where minutiInizio equals to UPDATED_MINUTI_INIZIO
        defaultRegolaOrariaShouldNotBeFound("minutiInizio.in=" + UPDATED_MINUTI_INIZIO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMinutiInizioIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where minutiInizio is not null
        defaultRegolaOrariaShouldBeFound("minutiInizio.specified=true");

        // Get all the regolaOrariaList where minutiInizio is null
        defaultRegolaOrariaShouldNotBeFound("minutiInizio.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMinutiFineIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where minutiFine equals to DEFAULT_MINUTI_FINE
        defaultRegolaOrariaShouldBeFound("minutiFine.equals=" + DEFAULT_MINUTI_FINE);

        // Get all the regolaOrariaList where minutiFine equals to UPDATED_MINUTI_FINE
        defaultRegolaOrariaShouldNotBeFound("minutiFine.equals=" + UPDATED_MINUTI_FINE);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMinutiFineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where minutiFine not equals to DEFAULT_MINUTI_FINE
        defaultRegolaOrariaShouldNotBeFound("minutiFine.notEquals=" + DEFAULT_MINUTI_FINE);

        // Get all the regolaOrariaList where minutiFine not equals to UPDATED_MINUTI_FINE
        defaultRegolaOrariaShouldBeFound("minutiFine.notEquals=" + UPDATED_MINUTI_FINE);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMinutiFineIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where minutiFine in DEFAULT_MINUTI_FINE or UPDATED_MINUTI_FINE
        defaultRegolaOrariaShouldBeFound("minutiFine.in=" + DEFAULT_MINUTI_FINE + "," + UPDATED_MINUTI_FINE);

        // Get all the regolaOrariaList where minutiFine equals to UPDATED_MINUTI_FINE
        defaultRegolaOrariaShouldNotBeFound("minutiFine.in=" + UPDATED_MINUTI_FINE);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMinutiFineIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where minutiFine is not null
        defaultRegolaOrariaShouldBeFound("minutiFine.specified=true");

        // Get all the regolaOrariaList where minutiFine is null
        defaultRegolaOrariaShouldNotBeFound("minutiFine.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByLunediIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where lunedi equals to DEFAULT_LUNEDI
        defaultRegolaOrariaShouldBeFound("lunedi.equals=" + DEFAULT_LUNEDI);

        // Get all the regolaOrariaList where lunedi equals to UPDATED_LUNEDI
        defaultRegolaOrariaShouldNotBeFound("lunedi.equals=" + UPDATED_LUNEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByLunediIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where lunedi not equals to DEFAULT_LUNEDI
        defaultRegolaOrariaShouldNotBeFound("lunedi.notEquals=" + DEFAULT_LUNEDI);

        // Get all the regolaOrariaList where lunedi not equals to UPDATED_LUNEDI
        defaultRegolaOrariaShouldBeFound("lunedi.notEquals=" + UPDATED_LUNEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByLunediIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where lunedi in DEFAULT_LUNEDI or UPDATED_LUNEDI
        defaultRegolaOrariaShouldBeFound("lunedi.in=" + DEFAULT_LUNEDI + "," + UPDATED_LUNEDI);

        // Get all the regolaOrariaList where lunedi equals to UPDATED_LUNEDI
        defaultRegolaOrariaShouldNotBeFound("lunedi.in=" + UPDATED_LUNEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByLunediIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where lunedi is not null
        defaultRegolaOrariaShouldBeFound("lunedi.specified=true");

        // Get all the regolaOrariaList where lunedi is null
        defaultRegolaOrariaShouldNotBeFound("lunedi.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMartediIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where martedi equals to DEFAULT_MARTEDI
        defaultRegolaOrariaShouldBeFound("martedi.equals=" + DEFAULT_MARTEDI);

        // Get all the regolaOrariaList where martedi equals to UPDATED_MARTEDI
        defaultRegolaOrariaShouldNotBeFound("martedi.equals=" + UPDATED_MARTEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMartediIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where martedi not equals to DEFAULT_MARTEDI
        defaultRegolaOrariaShouldNotBeFound("martedi.notEquals=" + DEFAULT_MARTEDI);

        // Get all the regolaOrariaList where martedi not equals to UPDATED_MARTEDI
        defaultRegolaOrariaShouldBeFound("martedi.notEquals=" + UPDATED_MARTEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMartediIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where martedi in DEFAULT_MARTEDI or UPDATED_MARTEDI
        defaultRegolaOrariaShouldBeFound("martedi.in=" + DEFAULT_MARTEDI + "," + UPDATED_MARTEDI);

        // Get all the regolaOrariaList where martedi equals to UPDATED_MARTEDI
        defaultRegolaOrariaShouldNotBeFound("martedi.in=" + UPDATED_MARTEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMartediIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where martedi is not null
        defaultRegolaOrariaShouldBeFound("martedi.specified=true");

        // Get all the regolaOrariaList where martedi is null
        defaultRegolaOrariaShouldNotBeFound("martedi.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMercolediIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where mercoledi equals to DEFAULT_MERCOLEDI
        defaultRegolaOrariaShouldBeFound("mercoledi.equals=" + DEFAULT_MERCOLEDI);

        // Get all the regolaOrariaList where mercoledi equals to UPDATED_MERCOLEDI
        defaultRegolaOrariaShouldNotBeFound("mercoledi.equals=" + UPDATED_MERCOLEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMercolediIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where mercoledi not equals to DEFAULT_MERCOLEDI
        defaultRegolaOrariaShouldNotBeFound("mercoledi.notEquals=" + DEFAULT_MERCOLEDI);

        // Get all the regolaOrariaList where mercoledi not equals to UPDATED_MERCOLEDI
        defaultRegolaOrariaShouldBeFound("mercoledi.notEquals=" + UPDATED_MERCOLEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMercolediIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where mercoledi in DEFAULT_MERCOLEDI or UPDATED_MERCOLEDI
        defaultRegolaOrariaShouldBeFound("mercoledi.in=" + DEFAULT_MERCOLEDI + "," + UPDATED_MERCOLEDI);

        // Get all the regolaOrariaList where mercoledi equals to UPDATED_MERCOLEDI
        defaultRegolaOrariaShouldNotBeFound("mercoledi.in=" + UPDATED_MERCOLEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByMercolediIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where mercoledi is not null
        defaultRegolaOrariaShouldBeFound("mercoledi.specified=true");

        // Get all the regolaOrariaList where mercoledi is null
        defaultRegolaOrariaShouldNotBeFound("mercoledi.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByGiovediIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where giovedi equals to DEFAULT_GIOVEDI
        defaultRegolaOrariaShouldBeFound("giovedi.equals=" + DEFAULT_GIOVEDI);

        // Get all the regolaOrariaList where giovedi equals to UPDATED_GIOVEDI
        defaultRegolaOrariaShouldNotBeFound("giovedi.equals=" + UPDATED_GIOVEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByGiovediIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where giovedi not equals to DEFAULT_GIOVEDI
        defaultRegolaOrariaShouldNotBeFound("giovedi.notEquals=" + DEFAULT_GIOVEDI);

        // Get all the regolaOrariaList where giovedi not equals to UPDATED_GIOVEDI
        defaultRegolaOrariaShouldBeFound("giovedi.notEquals=" + UPDATED_GIOVEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByGiovediIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where giovedi in DEFAULT_GIOVEDI or UPDATED_GIOVEDI
        defaultRegolaOrariaShouldBeFound("giovedi.in=" + DEFAULT_GIOVEDI + "," + UPDATED_GIOVEDI);

        // Get all the regolaOrariaList where giovedi equals to UPDATED_GIOVEDI
        defaultRegolaOrariaShouldNotBeFound("giovedi.in=" + UPDATED_GIOVEDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByGiovediIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where giovedi is not null
        defaultRegolaOrariaShouldBeFound("giovedi.specified=true");

        // Get all the regolaOrariaList where giovedi is null
        defaultRegolaOrariaShouldNotBeFound("giovedi.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByVenerdiIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where venerdi equals to DEFAULT_VENERDI
        defaultRegolaOrariaShouldBeFound("venerdi.equals=" + DEFAULT_VENERDI);

        // Get all the regolaOrariaList where venerdi equals to UPDATED_VENERDI
        defaultRegolaOrariaShouldNotBeFound("venerdi.equals=" + UPDATED_VENERDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByVenerdiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where venerdi not equals to DEFAULT_VENERDI
        defaultRegolaOrariaShouldNotBeFound("venerdi.notEquals=" + DEFAULT_VENERDI);

        // Get all the regolaOrariaList where venerdi not equals to UPDATED_VENERDI
        defaultRegolaOrariaShouldBeFound("venerdi.notEquals=" + UPDATED_VENERDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByVenerdiIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where venerdi in DEFAULT_VENERDI or UPDATED_VENERDI
        defaultRegolaOrariaShouldBeFound("venerdi.in=" + DEFAULT_VENERDI + "," + UPDATED_VENERDI);

        // Get all the regolaOrariaList where venerdi equals to UPDATED_VENERDI
        defaultRegolaOrariaShouldNotBeFound("venerdi.in=" + UPDATED_VENERDI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByVenerdiIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where venerdi is not null
        defaultRegolaOrariaShouldBeFound("venerdi.specified=true");

        // Get all the regolaOrariaList where venerdi is null
        defaultRegolaOrariaShouldNotBeFound("venerdi.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasBySabatoIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where sabato equals to DEFAULT_SABATO
        defaultRegolaOrariaShouldBeFound("sabato.equals=" + DEFAULT_SABATO);

        // Get all the regolaOrariaList where sabato equals to UPDATED_SABATO
        defaultRegolaOrariaShouldNotBeFound("sabato.equals=" + UPDATED_SABATO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasBySabatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where sabato not equals to DEFAULT_SABATO
        defaultRegolaOrariaShouldNotBeFound("sabato.notEquals=" + DEFAULT_SABATO);

        // Get all the regolaOrariaList where sabato not equals to UPDATED_SABATO
        defaultRegolaOrariaShouldBeFound("sabato.notEquals=" + UPDATED_SABATO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasBySabatoIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where sabato in DEFAULT_SABATO or UPDATED_SABATO
        defaultRegolaOrariaShouldBeFound("sabato.in=" + DEFAULT_SABATO + "," + UPDATED_SABATO);

        // Get all the regolaOrariaList where sabato equals to UPDATED_SABATO
        defaultRegolaOrariaShouldNotBeFound("sabato.in=" + UPDATED_SABATO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasBySabatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where sabato is not null
        defaultRegolaOrariaShouldBeFound("sabato.specified=true");

        // Get all the regolaOrariaList where sabato is null
        defaultRegolaOrariaShouldNotBeFound("sabato.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByDomenicaIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where domenica equals to DEFAULT_DOMENICA
        defaultRegolaOrariaShouldBeFound("domenica.equals=" + DEFAULT_DOMENICA);

        // Get all the regolaOrariaList where domenica equals to UPDATED_DOMENICA
        defaultRegolaOrariaShouldNotBeFound("domenica.equals=" + UPDATED_DOMENICA);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByDomenicaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where domenica not equals to DEFAULT_DOMENICA
        defaultRegolaOrariaShouldNotBeFound("domenica.notEquals=" + DEFAULT_DOMENICA);

        // Get all the regolaOrariaList where domenica not equals to UPDATED_DOMENICA
        defaultRegolaOrariaShouldBeFound("domenica.notEquals=" + UPDATED_DOMENICA);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByDomenicaIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where domenica in DEFAULT_DOMENICA or UPDATED_DOMENICA
        defaultRegolaOrariaShouldBeFound("domenica.in=" + DEFAULT_DOMENICA + "," + UPDATED_DOMENICA);

        // Get all the regolaOrariaList where domenica equals to UPDATED_DOMENICA
        defaultRegolaOrariaShouldNotBeFound("domenica.in=" + UPDATED_DOMENICA);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByDomenicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where domenica is not null
        defaultRegolaOrariaShouldBeFound("domenica.specified=true");

        // Get all the regolaOrariaList where domenica is null
        defaultRegolaOrariaShouldNotBeFound("domenica.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByFestiviIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where festivi equals to DEFAULT_FESTIVI
        defaultRegolaOrariaShouldBeFound("festivi.equals=" + DEFAULT_FESTIVI);

        // Get all the regolaOrariaList where festivi equals to UPDATED_FESTIVI
        defaultRegolaOrariaShouldNotBeFound("festivi.equals=" + UPDATED_FESTIVI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByFestiviIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where festivi not equals to DEFAULT_FESTIVI
        defaultRegolaOrariaShouldNotBeFound("festivi.notEquals=" + DEFAULT_FESTIVI);

        // Get all the regolaOrariaList where festivi not equals to UPDATED_FESTIVI
        defaultRegolaOrariaShouldBeFound("festivi.notEquals=" + UPDATED_FESTIVI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByFestiviIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where festivi in DEFAULT_FESTIVI or UPDATED_FESTIVI
        defaultRegolaOrariaShouldBeFound("festivi.in=" + DEFAULT_FESTIVI + "," + UPDATED_FESTIVI);

        // Get all the regolaOrariaList where festivi equals to UPDATED_FESTIVI
        defaultRegolaOrariaShouldNotBeFound("festivi.in=" + UPDATED_FESTIVI);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByFestiviIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where festivi is not null
        defaultRegolaOrariaShouldBeFound("festivi.specified=true");

        // Get all the regolaOrariaList where festivi is null
        defaultRegolaOrariaShouldNotBeFound("festivi.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByStatoIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where stato equals to DEFAULT_STATO
        defaultRegolaOrariaShouldBeFound("stato.equals=" + DEFAULT_STATO);

        // Get all the regolaOrariaList where stato equals to UPDATED_STATO
        defaultRegolaOrariaShouldNotBeFound("stato.equals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByStatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where stato not equals to DEFAULT_STATO
        defaultRegolaOrariaShouldNotBeFound("stato.notEquals=" + DEFAULT_STATO);

        // Get all the regolaOrariaList where stato not equals to UPDATED_STATO
        defaultRegolaOrariaShouldBeFound("stato.notEquals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByStatoIsInShouldWork() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where stato in DEFAULT_STATO or UPDATED_STATO
        defaultRegolaOrariaShouldBeFound("stato.in=" + DEFAULT_STATO + "," + UPDATED_STATO);

        // Get all the regolaOrariaList where stato equals to UPDATED_STATO
        defaultRegolaOrariaShouldNotBeFound("stato.in=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByStatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);

        // Get all the regolaOrariaList where stato is not null
        defaultRegolaOrariaShouldBeFound("stato.specified=true");

        // Get all the regolaOrariaList where stato is null
        defaultRegolaOrariaShouldNotBeFound("stato.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByTipologiaVeicoloIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);
        TipologiaVeicolo tipologiaVeicolo = TipologiaVeicoloResourceIT.createEntity(em);
        em.persist(tipologiaVeicolo);
        em.flush();
        regolaOraria.addTipologiaVeicolo(tipologiaVeicolo);
        regolaOrariaRepository.saveAndFlush(regolaOraria);
        Long tipologiaVeicoloId = tipologiaVeicolo.getId();

        // Get all the regolaOrariaList where tipologiaVeicolo equals to tipologiaVeicoloId
        defaultRegolaOrariaShouldBeFound("tipologiaVeicoloId.equals=" + tipologiaVeicoloId);

        // Get all the regolaOrariaList where tipologiaVeicolo equals to tipologiaVeicoloId + 1
        defaultRegolaOrariaShouldNotBeFound("tipologiaVeicoloId.equals=" + (tipologiaVeicoloId + 1));
    }

    @Test
    @Transactional
    public void getAllRegolaOrariasByProfiloOrarioIsEqualToSomething() throws Exception {
        // Initialize the database
        regolaOrariaRepository.saveAndFlush(regolaOraria);
        ProfiloOrario profiloOrario = ProfiloOrarioResourceIT.createEntity(em);
        em.persist(profiloOrario);
        em.flush();
        regolaOraria.addProfiloOrario(profiloOrario);
        regolaOrariaRepository.saveAndFlush(regolaOraria);
        Long profiloOrarioId = profiloOrario.getId();

        // Get all the regolaOrariaList where profiloOrario equals to profiloOrarioId
        defaultRegolaOrariaShouldBeFound("profiloOrarioId.equals=" + profiloOrarioId);

        // Get all the regolaOrariaList where profiloOrario equals to profiloOrarioId + 1
        defaultRegolaOrariaShouldNotBeFound("profiloOrarioId.equals=" + (profiloOrarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegolaOrariaShouldBeFound(String filter) throws Exception {
        restRegolaOrariaMockMvc
            .perform(get("/api/regola-orarias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regolaOraria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].oraInizio").value(hasItem(DEFAULT_ORA_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].oraFine").value(hasItem(DEFAULT_ORA_FINE.toString())))
            .andExpect(jsonPath("$.[*].minutiInizio").value(hasItem(DEFAULT_MINUTI_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].minutiFine").value(hasItem(DEFAULT_MINUTI_FINE.toString())))
            .andExpect(jsonPath("$.[*].lunedi").value(hasItem(DEFAULT_LUNEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].martedi").value(hasItem(DEFAULT_MARTEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].mercoledi").value(hasItem(DEFAULT_MERCOLEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].giovedi").value(hasItem(DEFAULT_GIOVEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].venerdi").value(hasItem(DEFAULT_VENERDI.booleanValue())))
            .andExpect(jsonPath("$.[*].sabato").value(hasItem(DEFAULT_SABATO.booleanValue())))
            .andExpect(jsonPath("$.[*].domenica").value(hasItem(DEFAULT_DOMENICA.booleanValue())))
            .andExpect(jsonPath("$.[*].festivi").value(hasItem(DEFAULT_FESTIVI.booleanValue())))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));

        // Check, that the count call also returns 1
        restRegolaOrariaMockMvc
            .perform(get("/api/regola-orarias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegolaOrariaShouldNotBeFound(String filter) throws Exception {
        restRegolaOrariaMockMvc
            .perform(get("/api/regola-orarias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegolaOrariaMockMvc
            .perform(get("/api/regola-orarias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRegolaOraria() throws Exception {
        // Get the regolaOraria
        restRegolaOrariaMockMvc.perform(get("/api/regola-orarias/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegolaOraria() throws Exception {
        // Initialize the database
        regolaOrariaService.save(regolaOraria);

        int databaseSizeBeforeUpdate = regolaOrariaRepository.findAll().size();

        // Update the regolaOraria
        RegolaOraria updatedRegolaOraria = regolaOrariaRepository.findById(regolaOraria.getId()).get();
        // Disconnect from session so that the updates on updatedRegolaOraria are not directly saved in db
        em.detach(updatedRegolaOraria);
        updatedRegolaOraria
            .nome(UPDATED_NOME)
            .oraInizio(UPDATED_ORA_INIZIO)
            .oraFine(UPDATED_ORA_FINE)
            .minutiInizio(UPDATED_MINUTI_INIZIO)
            .minutiFine(UPDATED_MINUTI_FINE)
            .lunedi(UPDATED_LUNEDI)
            .martedi(UPDATED_MARTEDI)
            .mercoledi(UPDATED_MERCOLEDI)
            .giovedi(UPDATED_GIOVEDI)
            .venerdi(UPDATED_VENERDI)
            .sabato(UPDATED_SABATO)
            .domenica(UPDATED_DOMENICA)
            .festivi(UPDATED_FESTIVI)
            .stato(UPDATED_STATO);

        restRegolaOrariaMockMvc
            .perform(
                put("/api/regola-orarias")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRegolaOraria))
            )
            .andExpect(status().isOk());

        // Validate the RegolaOraria in the database
        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeUpdate);
        RegolaOraria testRegolaOraria = regolaOrariaList.get(regolaOrariaList.size() - 1);
        assertThat(testRegolaOraria.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testRegolaOraria.getOraInizio()).isEqualTo(UPDATED_ORA_INIZIO);
        assertThat(testRegolaOraria.getOraFine()).isEqualTo(UPDATED_ORA_FINE);
        assertThat(testRegolaOraria.getMinutiInizio()).isEqualTo(UPDATED_MINUTI_INIZIO);
        assertThat(testRegolaOraria.getMinutiFine()).isEqualTo(UPDATED_MINUTI_FINE);
        assertThat(testRegolaOraria.isLunedi()).isEqualTo(UPDATED_LUNEDI);
        assertThat(testRegolaOraria.isMartedi()).isEqualTo(UPDATED_MARTEDI);
        assertThat(testRegolaOraria.isMercoledi()).isEqualTo(UPDATED_MERCOLEDI);
        assertThat(testRegolaOraria.isGiovedi()).isEqualTo(UPDATED_GIOVEDI);
        assertThat(testRegolaOraria.isVenerdi()).isEqualTo(UPDATED_VENERDI);
        assertThat(testRegolaOraria.isSabato()).isEqualTo(UPDATED_SABATO);
        assertThat(testRegolaOraria.isDomenica()).isEqualTo(UPDATED_DOMENICA);
        assertThat(testRegolaOraria.isFestivi()).isEqualTo(UPDATED_FESTIVI);
        assertThat(testRegolaOraria.getStato()).isEqualTo(UPDATED_STATO);
    }

    @Test
    @Transactional
    public void updateNonExistingRegolaOraria() throws Exception {
        int databaseSizeBeforeUpdate = regolaOrariaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegolaOrariaMockMvc
            .perform(
                put("/api/regola-orarias").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regolaOraria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegolaOraria in the database
        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegolaOraria() throws Exception {
        // Initialize the database
        regolaOrariaService.save(regolaOraria);

        int databaseSizeBeforeDelete = regolaOrariaRepository.findAll().size();

        // Delete the regolaOraria
        restRegolaOrariaMockMvc
            .perform(delete("/api/regola-orarias/{id}", regolaOraria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegolaOraria> regolaOrariaList = regolaOrariaRepository.findAll();
        assertThat(regolaOrariaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
