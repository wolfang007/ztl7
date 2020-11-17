package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.GruppoVarchi;
import com.nttdata.myztl7.domain.Varco;
import com.nttdata.myztl7.domain.Zona;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.repository.GruppoVarchiRepository;
import com.nttdata.myztl7.service.GruppoVarchiQueryService;
import com.nttdata.myztl7.service.GruppoVarchiService;
import com.nttdata.myztl7.service.dto.GruppoVarchiCriteria;
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
 * Integration tests for the {@link GruppoVarchiResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GruppoVarchiResourceIT {
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIONE_INTERVALLI = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIONE_INTERVALLI = "BBBBBBBBBB";

    private static final EntityStatus DEFAULT_STATO = EntityStatus.ATTIVO;
    private static final EntityStatus UPDATED_STATO = EntityStatus.DISATTIVO;

    @Autowired
    private GruppoVarchiRepository gruppoVarchiRepository;

    @Mock
    private GruppoVarchiRepository gruppoVarchiRepositoryMock;

    @Mock
    private GruppoVarchiService gruppoVarchiServiceMock;

    @Autowired
    private GruppoVarchiService gruppoVarchiService;

    @Autowired
    private GruppoVarchiQueryService gruppoVarchiQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGruppoVarchiMockMvc;

    private GruppoVarchi gruppoVarchi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GruppoVarchi createEntity(EntityManager em) {
        GruppoVarchi gruppoVarchi = new GruppoVarchi()
            .nome(DEFAULT_NOME)
            .descrizione(DEFAULT_DESCRIZIONE)
            .descrioneIntervalli(DEFAULT_DESCRIONE_INTERVALLI)
            .stato(DEFAULT_STATO);
        return gruppoVarchi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GruppoVarchi createUpdatedEntity(EntityManager em) {
        GruppoVarchi gruppoVarchi = new GruppoVarchi()
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE)
            .descrioneIntervalli(UPDATED_DESCRIONE_INTERVALLI)
            .stato(UPDATED_STATO);
        return gruppoVarchi;
    }

    @BeforeEach
    public void initTest() {
        gruppoVarchi = createEntity(em);
    }

    @Test
    @Transactional
    public void createGruppoVarchi() throws Exception {
        int databaseSizeBeforeCreate = gruppoVarchiRepository.findAll().size();
        // Create the GruppoVarchi
        restGruppoVarchiMockMvc
            .perform(
                post("/api/gruppo-varchis").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppoVarchi))
            )
            .andExpect(status().isCreated());

        // Validate the GruppoVarchi in the database
        List<GruppoVarchi> gruppoVarchiList = gruppoVarchiRepository.findAll();
        assertThat(gruppoVarchiList).hasSize(databaseSizeBeforeCreate + 1);
        GruppoVarchi testGruppoVarchi = gruppoVarchiList.get(gruppoVarchiList.size() - 1);
        assertThat(testGruppoVarchi.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testGruppoVarchi.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testGruppoVarchi.getDescrioneIntervalli()).isEqualTo(DEFAULT_DESCRIONE_INTERVALLI);
        assertThat(testGruppoVarchi.getStato()).isEqualTo(DEFAULT_STATO);
    }

    @Test
    @Transactional
    public void createGruppoVarchiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gruppoVarchiRepository.findAll().size();

        // Create the GruppoVarchi with an existing ID
        gruppoVarchi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGruppoVarchiMockMvc
            .perform(
                post("/api/gruppo-varchis").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppoVarchi))
            )
            .andExpect(status().isBadRequest());

        // Validate the GruppoVarchi in the database
        List<GruppoVarchi> gruppoVarchiList = gruppoVarchiRepository.findAll();
        assertThat(gruppoVarchiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gruppoVarchiRepository.findAll().size();
        // set the field null
        gruppoVarchi.setNome(null);

        // Create the GruppoVarchi, which fails.

        restGruppoVarchiMockMvc
            .perform(
                post("/api/gruppo-varchis").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppoVarchi))
            )
            .andExpect(status().isBadRequest());

        List<GruppoVarchi> gruppoVarchiList = gruppoVarchiRepository.findAll();
        assertThat(gruppoVarchiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gruppoVarchiRepository.findAll().size();
        // set the field null
        gruppoVarchi.setStato(null);

        // Create the GruppoVarchi, which fails.

        restGruppoVarchiMockMvc
            .perform(
                post("/api/gruppo-varchis").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppoVarchi))
            )
            .andExpect(status().isBadRequest());

        List<GruppoVarchi> gruppoVarchiList = gruppoVarchiRepository.findAll();
        assertThat(gruppoVarchiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchis() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList
        restGruppoVarchiMockMvc
            .perform(get("/api/gruppo-varchis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gruppoVarchi.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].descrioneIntervalli").value(hasItem(DEFAULT_DESCRIONE_INTERVALLI)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllGruppoVarchisWithEagerRelationshipsIsEnabled() throws Exception {
        when(gruppoVarchiServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGruppoVarchiMockMvc.perform(get("/api/gruppo-varchis?eagerload=true")).andExpect(status().isOk());

        verify(gruppoVarchiServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllGruppoVarchisWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(gruppoVarchiServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGruppoVarchiMockMvc.perform(get("/api/gruppo-varchis?eagerload=true")).andExpect(status().isOk());

        verify(gruppoVarchiServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGruppoVarchi() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get the gruppoVarchi
        restGruppoVarchiMockMvc
            .perform(get("/api/gruppo-varchis/{id}", gruppoVarchi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gruppoVarchi.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE))
            .andExpect(jsonPath("$.descrioneIntervalli").value(DEFAULT_DESCRIONE_INTERVALLI))
            .andExpect(jsonPath("$.stato").value(DEFAULT_STATO.toString()));
    }

    @Test
    @Transactional
    public void getGruppoVarchisByIdFiltering() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        Long id = gruppoVarchi.getId();

        defaultGruppoVarchiShouldBeFound("id.equals=" + id);
        defaultGruppoVarchiShouldNotBeFound("id.notEquals=" + id);

        defaultGruppoVarchiShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGruppoVarchiShouldNotBeFound("id.greaterThan=" + id);

        defaultGruppoVarchiShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGruppoVarchiShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where nome equals to DEFAULT_NOME
        defaultGruppoVarchiShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the gruppoVarchiList where nome equals to UPDATED_NOME
        defaultGruppoVarchiShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where nome not equals to DEFAULT_NOME
        defaultGruppoVarchiShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the gruppoVarchiList where nome not equals to UPDATED_NOME
        defaultGruppoVarchiShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultGruppoVarchiShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the gruppoVarchiList where nome equals to UPDATED_NOME
        defaultGruppoVarchiShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where nome is not null
        defaultGruppoVarchiShouldBeFound("nome.specified=true");

        // Get all the gruppoVarchiList where nome is null
        defaultGruppoVarchiShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByNomeContainsSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where nome contains DEFAULT_NOME
        defaultGruppoVarchiShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the gruppoVarchiList where nome contains UPDATED_NOME
        defaultGruppoVarchiShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where nome does not contain DEFAULT_NOME
        defaultGruppoVarchiShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the gruppoVarchiList where nome does not contain UPDATED_NOME
        defaultGruppoVarchiShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrizioneIsEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrizione equals to DEFAULT_DESCRIZIONE
        defaultGruppoVarchiShouldBeFound("descrizione.equals=" + DEFAULT_DESCRIZIONE);

        // Get all the gruppoVarchiList where descrizione equals to UPDATED_DESCRIZIONE
        defaultGruppoVarchiShouldNotBeFound("descrizione.equals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrizioneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrizione not equals to DEFAULT_DESCRIZIONE
        defaultGruppoVarchiShouldNotBeFound("descrizione.notEquals=" + DEFAULT_DESCRIZIONE);

        // Get all the gruppoVarchiList where descrizione not equals to UPDATED_DESCRIZIONE
        defaultGruppoVarchiShouldBeFound("descrizione.notEquals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrizioneIsInShouldWork() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrizione in DEFAULT_DESCRIZIONE or UPDATED_DESCRIZIONE
        defaultGruppoVarchiShouldBeFound("descrizione.in=" + DEFAULT_DESCRIZIONE + "," + UPDATED_DESCRIZIONE);

        // Get all the gruppoVarchiList where descrizione equals to UPDATED_DESCRIZIONE
        defaultGruppoVarchiShouldNotBeFound("descrizione.in=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrizioneIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrizione is not null
        defaultGruppoVarchiShouldBeFound("descrizione.specified=true");

        // Get all the gruppoVarchiList where descrizione is null
        defaultGruppoVarchiShouldNotBeFound("descrizione.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrizioneContainsSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrizione contains DEFAULT_DESCRIZIONE
        defaultGruppoVarchiShouldBeFound("descrizione.contains=" + DEFAULT_DESCRIZIONE);

        // Get all the gruppoVarchiList where descrizione contains UPDATED_DESCRIZIONE
        defaultGruppoVarchiShouldNotBeFound("descrizione.contains=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrizioneNotContainsSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrizione does not contain DEFAULT_DESCRIZIONE
        defaultGruppoVarchiShouldNotBeFound("descrizione.doesNotContain=" + DEFAULT_DESCRIZIONE);

        // Get all the gruppoVarchiList where descrizione does not contain UPDATED_DESCRIZIONE
        defaultGruppoVarchiShouldBeFound("descrizione.doesNotContain=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrioneIntervalliIsEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrioneIntervalli equals to DEFAULT_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldBeFound("descrioneIntervalli.equals=" + DEFAULT_DESCRIONE_INTERVALLI);

        // Get all the gruppoVarchiList where descrioneIntervalli equals to UPDATED_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldNotBeFound("descrioneIntervalli.equals=" + UPDATED_DESCRIONE_INTERVALLI);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrioneIntervalliIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrioneIntervalli not equals to DEFAULT_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldNotBeFound("descrioneIntervalli.notEquals=" + DEFAULT_DESCRIONE_INTERVALLI);

        // Get all the gruppoVarchiList where descrioneIntervalli not equals to UPDATED_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldBeFound("descrioneIntervalli.notEquals=" + UPDATED_DESCRIONE_INTERVALLI);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrioneIntervalliIsInShouldWork() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrioneIntervalli in DEFAULT_DESCRIONE_INTERVALLI or UPDATED_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldBeFound("descrioneIntervalli.in=" + DEFAULT_DESCRIONE_INTERVALLI + "," + UPDATED_DESCRIONE_INTERVALLI);

        // Get all the gruppoVarchiList where descrioneIntervalli equals to UPDATED_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldNotBeFound("descrioneIntervalli.in=" + UPDATED_DESCRIONE_INTERVALLI);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrioneIntervalliIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrioneIntervalli is not null
        defaultGruppoVarchiShouldBeFound("descrioneIntervalli.specified=true");

        // Get all the gruppoVarchiList where descrioneIntervalli is null
        defaultGruppoVarchiShouldNotBeFound("descrioneIntervalli.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrioneIntervalliContainsSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrioneIntervalli contains DEFAULT_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldBeFound("descrioneIntervalli.contains=" + DEFAULT_DESCRIONE_INTERVALLI);

        // Get all the gruppoVarchiList where descrioneIntervalli contains UPDATED_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldNotBeFound("descrioneIntervalli.contains=" + UPDATED_DESCRIONE_INTERVALLI);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByDescrioneIntervalliNotContainsSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where descrioneIntervalli does not contain DEFAULT_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldNotBeFound("descrioneIntervalli.doesNotContain=" + DEFAULT_DESCRIONE_INTERVALLI);

        // Get all the gruppoVarchiList where descrioneIntervalli does not contain UPDATED_DESCRIONE_INTERVALLI
        defaultGruppoVarchiShouldBeFound("descrioneIntervalli.doesNotContain=" + UPDATED_DESCRIONE_INTERVALLI);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByStatoIsEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where stato equals to DEFAULT_STATO
        defaultGruppoVarchiShouldBeFound("stato.equals=" + DEFAULT_STATO);

        // Get all the gruppoVarchiList where stato equals to UPDATED_STATO
        defaultGruppoVarchiShouldNotBeFound("stato.equals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByStatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where stato not equals to DEFAULT_STATO
        defaultGruppoVarchiShouldNotBeFound("stato.notEquals=" + DEFAULT_STATO);

        // Get all the gruppoVarchiList where stato not equals to UPDATED_STATO
        defaultGruppoVarchiShouldBeFound("stato.notEquals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByStatoIsInShouldWork() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where stato in DEFAULT_STATO or UPDATED_STATO
        defaultGruppoVarchiShouldBeFound("stato.in=" + DEFAULT_STATO + "," + UPDATED_STATO);

        // Get all the gruppoVarchiList where stato equals to UPDATED_STATO
        defaultGruppoVarchiShouldNotBeFound("stato.in=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByStatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);

        // Get all the gruppoVarchiList where stato is not null
        defaultGruppoVarchiShouldBeFound("stato.specified=true");

        // Get all the gruppoVarchiList where stato is null
        defaultGruppoVarchiShouldNotBeFound("stato.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByPosizioneIsEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);
        Varco posizione = VarcoResourceIT.createEntity(em);
        em.persist(posizione);
        em.flush();
        gruppoVarchi.addPosizione(posizione);
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);
        Long posizioneId = posizione.getId();

        // Get all the gruppoVarchiList where posizione equals to posizioneId
        defaultGruppoVarchiShouldBeFound("posizioneId.equals=" + posizioneId);

        // Get all the gruppoVarchiList where posizione equals to posizioneId + 1
        defaultGruppoVarchiShouldNotBeFound("posizioneId.equals=" + (posizioneId + 1));
    }

    @Test
    @Transactional
    public void getAllGruppoVarchisByZonaIsEqualToSomething() throws Exception {
        // Initialize the database
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);
        Zona zona = ZonaResourceIT.createEntity(em);
        em.persist(zona);
        em.flush();
        gruppoVarchi.addZona(zona);
        gruppoVarchiRepository.saveAndFlush(gruppoVarchi);
        Long zonaId = zona.getId();

        // Get all the gruppoVarchiList where zona equals to zonaId
        defaultGruppoVarchiShouldBeFound("zonaId.equals=" + zonaId);

        // Get all the gruppoVarchiList where zona equals to zonaId + 1
        defaultGruppoVarchiShouldNotBeFound("zonaId.equals=" + (zonaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGruppoVarchiShouldBeFound(String filter) throws Exception {
        restGruppoVarchiMockMvc
            .perform(get("/api/gruppo-varchis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gruppoVarchi.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].descrioneIntervalli").value(hasItem(DEFAULT_DESCRIONE_INTERVALLI)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));

        // Check, that the count call also returns 1
        restGruppoVarchiMockMvc
            .perform(get("/api/gruppo-varchis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGruppoVarchiShouldNotBeFound(String filter) throws Exception {
        restGruppoVarchiMockMvc
            .perform(get("/api/gruppo-varchis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGruppoVarchiMockMvc
            .perform(get("/api/gruppo-varchis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGruppoVarchi() throws Exception {
        // Get the gruppoVarchi
        restGruppoVarchiMockMvc.perform(get("/api/gruppo-varchis/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGruppoVarchi() throws Exception {
        // Initialize the database
        gruppoVarchiService.save(gruppoVarchi);

        int databaseSizeBeforeUpdate = gruppoVarchiRepository.findAll().size();

        // Update the gruppoVarchi
        GruppoVarchi updatedGruppoVarchi = gruppoVarchiRepository.findById(gruppoVarchi.getId()).get();
        // Disconnect from session so that the updates on updatedGruppoVarchi are not directly saved in db
        em.detach(updatedGruppoVarchi);
        updatedGruppoVarchi
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE)
            .descrioneIntervalli(UPDATED_DESCRIONE_INTERVALLI)
            .stato(UPDATED_STATO);

        restGruppoVarchiMockMvc
            .perform(
                put("/api/gruppo-varchis")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGruppoVarchi))
            )
            .andExpect(status().isOk());

        // Validate the GruppoVarchi in the database
        List<GruppoVarchi> gruppoVarchiList = gruppoVarchiRepository.findAll();
        assertThat(gruppoVarchiList).hasSize(databaseSizeBeforeUpdate);
        GruppoVarchi testGruppoVarchi = gruppoVarchiList.get(gruppoVarchiList.size() - 1);
        assertThat(testGruppoVarchi.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testGruppoVarchi.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testGruppoVarchi.getDescrioneIntervalli()).isEqualTo(UPDATED_DESCRIONE_INTERVALLI);
        assertThat(testGruppoVarchi.getStato()).isEqualTo(UPDATED_STATO);
    }

    @Test
    @Transactional
    public void updateNonExistingGruppoVarchi() throws Exception {
        int databaseSizeBeforeUpdate = gruppoVarchiRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGruppoVarchiMockMvc
            .perform(
                put("/api/gruppo-varchis").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppoVarchi))
            )
            .andExpect(status().isBadRequest());

        // Validate the GruppoVarchi in the database
        List<GruppoVarchi> gruppoVarchiList = gruppoVarchiRepository.findAll();
        assertThat(gruppoVarchiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGruppoVarchi() throws Exception {
        // Initialize the database
        gruppoVarchiService.save(gruppoVarchi);

        int databaseSizeBeforeDelete = gruppoVarchiRepository.findAll().size();

        // Delete the gruppoVarchi
        restGruppoVarchiMockMvc
            .perform(delete("/api/gruppo-varchis/{id}", gruppoVarchi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GruppoVarchi> gruppoVarchiList = gruppoVarchiRepository.findAll();
        assertThat(gruppoVarchiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
