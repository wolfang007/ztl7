package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.Autorizzazione;
import com.nttdata.myztl7.domain.PermessoTemporaneo;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.repository.AutorizzazioneRepository;
import com.nttdata.myztl7.service.AutorizzazioneQueryService;
import com.nttdata.myztl7.service.AutorizzazioneService;
import com.nttdata.myztl7.service.dto.AutorizzazioneCriteria;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AutorizzazioneResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class AutorizzazioneResourceIT {
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final EntityStatus DEFAULT_STATO = EntityStatus.ATTIVO;
    private static final EntityStatus UPDATED_STATO = EntityStatus.DISATTIVO;

    @Autowired
    private AutorizzazioneRepository autorizzazioneRepository;

    @Autowired
    private AutorizzazioneService autorizzazioneService;

    @Autowired
    private AutorizzazioneQueryService autorizzazioneQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutorizzazioneMockMvc;

    private Autorizzazione autorizzazione;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autorizzazione createEntity(EntityManager em) {
        Autorizzazione autorizzazione = new Autorizzazione().nome(DEFAULT_NOME).descrizione(DEFAULT_DESCRIZIONE).stato(DEFAULT_STATO);
        return autorizzazione;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autorizzazione createUpdatedEntity(EntityManager em) {
        Autorizzazione autorizzazione = new Autorizzazione().nome(UPDATED_NOME).descrizione(UPDATED_DESCRIZIONE).stato(UPDATED_STATO);
        return autorizzazione;
    }

    @BeforeEach
    public void initTest() {
        autorizzazione = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutorizzazione() throws Exception {
        int databaseSizeBeforeCreate = autorizzazioneRepository.findAll().size();
        // Create the Autorizzazione
        restAutorizzazioneMockMvc
            .perform(
                post("/api/autorizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autorizzazione))
            )
            .andExpect(status().isCreated());

        // Validate the Autorizzazione in the database
        List<Autorizzazione> autorizzazioneList = autorizzazioneRepository.findAll();
        assertThat(autorizzazioneList).hasSize(databaseSizeBeforeCreate + 1);
        Autorizzazione testAutorizzazione = autorizzazioneList.get(autorizzazioneList.size() - 1);
        assertThat(testAutorizzazione.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAutorizzazione.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testAutorizzazione.getStato()).isEqualTo(DEFAULT_STATO);
    }

    @Test
    @Transactional
    public void createAutorizzazioneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autorizzazioneRepository.findAll().size();

        // Create the Autorizzazione with an existing ID
        autorizzazione.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutorizzazioneMockMvc
            .perform(
                post("/api/autorizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autorizzazione))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autorizzazione in the database
        List<Autorizzazione> autorizzazioneList = autorizzazioneRepository.findAll();
        assertThat(autorizzazioneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizzazioneRepository.findAll().size();
        // set the field null
        autorizzazione.setNome(null);

        // Create the Autorizzazione, which fails.

        restAutorizzazioneMockMvc
            .perform(
                post("/api/autorizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autorizzazione))
            )
            .andExpect(status().isBadRequest());

        List<Autorizzazione> autorizzazioneList = autorizzazioneRepository.findAll();
        assertThat(autorizzazioneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizzazioneRepository.findAll().size();
        // set the field null
        autorizzazione.setStato(null);

        // Create the Autorizzazione, which fails.

        restAutorizzazioneMockMvc
            .perform(
                post("/api/autorizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autorizzazione))
            )
            .andExpect(status().isBadRequest());

        List<Autorizzazione> autorizzazioneList = autorizzazioneRepository.findAll();
        assertThat(autorizzazioneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutorizzaziones() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList
        restAutorizzazioneMockMvc
            .perform(get("/api/autorizzaziones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autorizzazione.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));
    }

    @Test
    @Transactional
    public void getAutorizzazione() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get the autorizzazione
        restAutorizzazioneMockMvc
            .perform(get("/api/autorizzaziones/{id}", autorizzazione.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autorizzazione.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE))
            .andExpect(jsonPath("$.stato").value(DEFAULT_STATO.toString()));
    }

    @Test
    @Transactional
    public void getAutorizzazionesByIdFiltering() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        Long id = autorizzazione.getId();

        defaultAutorizzazioneShouldBeFound("id.equals=" + id);
        defaultAutorizzazioneShouldNotBeFound("id.notEquals=" + id);

        defaultAutorizzazioneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAutorizzazioneShouldNotBeFound("id.greaterThan=" + id);

        defaultAutorizzazioneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAutorizzazioneShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where nome equals to DEFAULT_NOME
        defaultAutorizzazioneShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the autorizzazioneList where nome equals to UPDATED_NOME
        defaultAutorizzazioneShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where nome not equals to DEFAULT_NOME
        defaultAutorizzazioneShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the autorizzazioneList where nome not equals to UPDATED_NOME
        defaultAutorizzazioneShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAutorizzazioneShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the autorizzazioneList where nome equals to UPDATED_NOME
        defaultAutorizzazioneShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where nome is not null
        defaultAutorizzazioneShouldBeFound("nome.specified=true");

        // Get all the autorizzazioneList where nome is null
        defaultAutorizzazioneShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByNomeContainsSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where nome contains DEFAULT_NOME
        defaultAutorizzazioneShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the autorizzazioneList where nome contains UPDATED_NOME
        defaultAutorizzazioneShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where nome does not contain DEFAULT_NOME
        defaultAutorizzazioneShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the autorizzazioneList where nome does not contain UPDATED_NOME
        defaultAutorizzazioneShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByDescrizioneIsEqualToSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where descrizione equals to DEFAULT_DESCRIZIONE
        defaultAutorizzazioneShouldBeFound("descrizione.equals=" + DEFAULT_DESCRIZIONE);

        // Get all the autorizzazioneList where descrizione equals to UPDATED_DESCRIZIONE
        defaultAutorizzazioneShouldNotBeFound("descrizione.equals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByDescrizioneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where descrizione not equals to DEFAULT_DESCRIZIONE
        defaultAutorizzazioneShouldNotBeFound("descrizione.notEquals=" + DEFAULT_DESCRIZIONE);

        // Get all the autorizzazioneList where descrizione not equals to UPDATED_DESCRIZIONE
        defaultAutorizzazioneShouldBeFound("descrizione.notEquals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByDescrizioneIsInShouldWork() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where descrizione in DEFAULT_DESCRIZIONE or UPDATED_DESCRIZIONE
        defaultAutorizzazioneShouldBeFound("descrizione.in=" + DEFAULT_DESCRIZIONE + "," + UPDATED_DESCRIZIONE);

        // Get all the autorizzazioneList where descrizione equals to UPDATED_DESCRIZIONE
        defaultAutorizzazioneShouldNotBeFound("descrizione.in=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByDescrizioneIsNullOrNotNull() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where descrizione is not null
        defaultAutorizzazioneShouldBeFound("descrizione.specified=true");

        // Get all the autorizzazioneList where descrizione is null
        defaultAutorizzazioneShouldNotBeFound("descrizione.specified=false");
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByDescrizioneContainsSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where descrizione contains DEFAULT_DESCRIZIONE
        defaultAutorizzazioneShouldBeFound("descrizione.contains=" + DEFAULT_DESCRIZIONE);

        // Get all the autorizzazioneList where descrizione contains UPDATED_DESCRIZIONE
        defaultAutorizzazioneShouldNotBeFound("descrizione.contains=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByDescrizioneNotContainsSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where descrizione does not contain DEFAULT_DESCRIZIONE
        defaultAutorizzazioneShouldNotBeFound("descrizione.doesNotContain=" + DEFAULT_DESCRIZIONE);

        // Get all the autorizzazioneList where descrizione does not contain UPDATED_DESCRIZIONE
        defaultAutorizzazioneShouldBeFound("descrizione.doesNotContain=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByStatoIsEqualToSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where stato equals to DEFAULT_STATO
        defaultAutorizzazioneShouldBeFound("stato.equals=" + DEFAULT_STATO);

        // Get all the autorizzazioneList where stato equals to UPDATED_STATO
        defaultAutorizzazioneShouldNotBeFound("stato.equals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByStatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where stato not equals to DEFAULT_STATO
        defaultAutorizzazioneShouldNotBeFound("stato.notEquals=" + DEFAULT_STATO);

        // Get all the autorizzazioneList where stato not equals to UPDATED_STATO
        defaultAutorizzazioneShouldBeFound("stato.notEquals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByStatoIsInShouldWork() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where stato in DEFAULT_STATO or UPDATED_STATO
        defaultAutorizzazioneShouldBeFound("stato.in=" + DEFAULT_STATO + "," + UPDATED_STATO);

        // Get all the autorizzazioneList where stato equals to UPDATED_STATO
        defaultAutorizzazioneShouldNotBeFound("stato.in=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByStatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);

        // Get all the autorizzazioneList where stato is not null
        defaultAutorizzazioneShouldBeFound("stato.specified=true");

        // Get all the autorizzazioneList where stato is null
        defaultAutorizzazioneShouldNotBeFound("stato.specified=false");
    }

    @Test
    @Transactional
    public void getAllAutorizzazionesByPermessoTemporaneoIsEqualToSomething() throws Exception {
        // Initialize the database
        autorizzazioneRepository.saveAndFlush(autorizzazione);
        PermessoTemporaneo permessoTemporaneo = PermessoTemporaneoResourceIT.createEntity(em);
        em.persist(permessoTemporaneo);
        em.flush();
        autorizzazione.addPermessoTemporaneo(permessoTemporaneo);
        autorizzazioneRepository.saveAndFlush(autorizzazione);
        Long permessoTemporaneoId = permessoTemporaneo.getId();

        // Get all the autorizzazioneList where permessoTemporaneo equals to permessoTemporaneoId
        defaultAutorizzazioneShouldBeFound("permessoTemporaneoId.equals=" + permessoTemporaneoId);

        // Get all the autorizzazioneList where permessoTemporaneo equals to permessoTemporaneoId + 1
        defaultAutorizzazioneShouldNotBeFound("permessoTemporaneoId.equals=" + (permessoTemporaneoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutorizzazioneShouldBeFound(String filter) throws Exception {
        restAutorizzazioneMockMvc
            .perform(get("/api/autorizzaziones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autorizzazione.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));

        // Check, that the count call also returns 1
        restAutorizzazioneMockMvc
            .perform(get("/api/autorizzaziones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutorizzazioneShouldNotBeFound(String filter) throws Exception {
        restAutorizzazioneMockMvc
            .perform(get("/api/autorizzaziones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutorizzazioneMockMvc
            .perform(get("/api/autorizzaziones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAutorizzazione() throws Exception {
        // Get the autorizzazione
        restAutorizzazioneMockMvc.perform(get("/api/autorizzaziones/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutorizzazione() throws Exception {
        // Initialize the database
        autorizzazioneService.save(autorizzazione);

        int databaseSizeBeforeUpdate = autorizzazioneRepository.findAll().size();

        // Update the autorizzazione
        Autorizzazione updatedAutorizzazione = autorizzazioneRepository.findById(autorizzazione.getId()).get();
        // Disconnect from session so that the updates on updatedAutorizzazione are not directly saved in db
        em.detach(updatedAutorizzazione);
        updatedAutorizzazione.nome(UPDATED_NOME).descrizione(UPDATED_DESCRIZIONE).stato(UPDATED_STATO);

        restAutorizzazioneMockMvc
            .perform(
                put("/api/autorizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAutorizzazione))
            )
            .andExpect(status().isOk());

        // Validate the Autorizzazione in the database
        List<Autorizzazione> autorizzazioneList = autorizzazioneRepository.findAll();
        assertThat(autorizzazioneList).hasSize(databaseSizeBeforeUpdate);
        Autorizzazione testAutorizzazione = autorizzazioneList.get(autorizzazioneList.size() - 1);
        assertThat(testAutorizzazione.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAutorizzazione.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testAutorizzazione.getStato()).isEqualTo(UPDATED_STATO);
    }

    @Test
    @Transactional
    public void updateNonExistingAutorizzazione() throws Exception {
        int databaseSizeBeforeUpdate = autorizzazioneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutorizzazioneMockMvc
            .perform(
                put("/api/autorizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autorizzazione))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autorizzazione in the database
        List<Autorizzazione> autorizzazioneList = autorizzazioneRepository.findAll();
        assertThat(autorizzazioneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutorizzazione() throws Exception {
        // Initialize the database
        autorizzazioneService.save(autorizzazione);

        int databaseSizeBeforeDelete = autorizzazioneRepository.findAll().size();

        // Delete the autorizzazione
        restAutorizzazioneMockMvc
            .perform(delete("/api/autorizzaziones/{id}", autorizzazione.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Autorizzazione> autorizzazioneList = autorizzazioneRepository.findAll();
        assertThat(autorizzazioneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
