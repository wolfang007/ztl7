package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.TipologiaZona;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.repository.TipologiaZonaRepository;
import com.nttdata.myztl7.service.TipologiaZonaQueryService;
import com.nttdata.myztl7.service.TipologiaZonaService;
import com.nttdata.myztl7.service.dto.TipologiaZonaCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TipologiaZonaResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipologiaZonaResourceIT {
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_REGOLE_CIRCOLAZIONE = "AAAAAAAAAA";
    private static final String UPDATED_REGOLE_CIRCOLAZIONE = "BBBBBBBBBB";

    private static final EntityStatus DEFAULT_STATO = EntityStatus.ATTIVO;
    private static final EntityStatus UPDATED_STATO = EntityStatus.DISATTIVO;

    @Autowired
    private TipologiaZonaRepository tipologiaZonaRepository;

    @Autowired
    private TipologiaZonaService tipologiaZonaService;

    @Autowired
    private TipologiaZonaQueryService tipologiaZonaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipologiaZonaMockMvc;

    private TipologiaZona tipologiaZona;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipologiaZona createEntity(EntityManager em) {
        TipologiaZona tipologiaZona = new TipologiaZona()
            .nome(DEFAULT_NOME)
            .regoleCircolazione(DEFAULT_REGOLE_CIRCOLAZIONE)
            .stato(DEFAULT_STATO);
        return tipologiaZona;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipologiaZona createUpdatedEntity(EntityManager em) {
        TipologiaZona tipologiaZona = new TipologiaZona()
            .nome(UPDATED_NOME)
            .regoleCircolazione(UPDATED_REGOLE_CIRCOLAZIONE)
            .stato(UPDATED_STATO);
        return tipologiaZona;
    }

    @BeforeEach
    public void initTest() {
        tipologiaZona = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipologiaZona() throws Exception {
        int databaseSizeBeforeCreate = tipologiaZonaRepository.findAll().size();
        // Create the TipologiaZona
        restTipologiaZonaMockMvc
            .perform(
                post("/api/tipologia-zonas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaZona))
            )
            .andExpect(status().isCreated());

        // Validate the TipologiaZona in the database
        List<TipologiaZona> tipologiaZonaList = tipologiaZonaRepository.findAll();
        assertThat(tipologiaZonaList).hasSize(databaseSizeBeforeCreate + 1);
        TipologiaZona testTipologiaZona = tipologiaZonaList.get(tipologiaZonaList.size() - 1);
        assertThat(testTipologiaZona.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipologiaZona.getRegoleCircolazione()).isEqualTo(DEFAULT_REGOLE_CIRCOLAZIONE);
        assertThat(testTipologiaZona.getStato()).isEqualTo(DEFAULT_STATO);
    }

    @Test
    @Transactional
    public void createTipologiaZonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipologiaZonaRepository.findAll().size();

        // Create the TipologiaZona with an existing ID
        tipologiaZona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipologiaZonaMockMvc
            .perform(
                post("/api/tipologia-zonas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaZona))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipologiaZona in the database
        List<TipologiaZona> tipologiaZonaList = tipologiaZonaRepository.findAll();
        assertThat(tipologiaZonaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipologiaZonaRepository.findAll().size();
        // set the field null
        tipologiaZona.setNome(null);

        // Create the TipologiaZona, which fails.

        restTipologiaZonaMockMvc
            .perform(
                post("/api/tipologia-zonas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaZona))
            )
            .andExpect(status().isBadRequest());

        List<TipologiaZona> tipologiaZonaList = tipologiaZonaRepository.findAll();
        assertThat(tipologiaZonaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipologiaZonaRepository.findAll().size();
        // set the field null
        tipologiaZona.setStato(null);

        // Create the TipologiaZona, which fails.

        restTipologiaZonaMockMvc
            .perform(
                post("/api/tipologia-zonas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaZona))
            )
            .andExpect(status().isBadRequest());

        List<TipologiaZona> tipologiaZonaList = tipologiaZonaRepository.findAll();
        assertThat(tipologiaZonaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonas() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList
        restTipologiaZonaMockMvc
            .perform(get("/api/tipologia-zonas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipologiaZona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].regoleCircolazione").value(hasItem(DEFAULT_REGOLE_CIRCOLAZIONE.toString())))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));
    }

    @Test
    @Transactional
    public void getTipologiaZona() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get the tipologiaZona
        restTipologiaZonaMockMvc
            .perform(get("/api/tipologia-zonas/{id}", tipologiaZona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipologiaZona.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.regoleCircolazione").value(DEFAULT_REGOLE_CIRCOLAZIONE.toString()))
            .andExpect(jsonPath("$.stato").value(DEFAULT_STATO.toString()));
    }

    @Test
    @Transactional
    public void getTipologiaZonasByIdFiltering() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        Long id = tipologiaZona.getId();

        defaultTipologiaZonaShouldBeFound("id.equals=" + id);
        defaultTipologiaZonaShouldNotBeFound("id.notEquals=" + id);

        defaultTipologiaZonaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipologiaZonaShouldNotBeFound("id.greaterThan=" + id);

        defaultTipologiaZonaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipologiaZonaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where nome equals to DEFAULT_NOME
        defaultTipologiaZonaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the tipologiaZonaList where nome equals to UPDATED_NOME
        defaultTipologiaZonaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where nome not equals to DEFAULT_NOME
        defaultTipologiaZonaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the tipologiaZonaList where nome not equals to UPDATED_NOME
        defaultTipologiaZonaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultTipologiaZonaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the tipologiaZonaList where nome equals to UPDATED_NOME
        defaultTipologiaZonaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where nome is not null
        defaultTipologiaZonaShouldBeFound("nome.specified=true");

        // Get all the tipologiaZonaList where nome is null
        defaultTipologiaZonaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByNomeContainsSomething() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where nome contains DEFAULT_NOME
        defaultTipologiaZonaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the tipologiaZonaList where nome contains UPDATED_NOME
        defaultTipologiaZonaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where nome does not contain DEFAULT_NOME
        defaultTipologiaZonaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the tipologiaZonaList where nome does not contain UPDATED_NOME
        defaultTipologiaZonaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByStatoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where stato equals to DEFAULT_STATO
        defaultTipologiaZonaShouldBeFound("stato.equals=" + DEFAULT_STATO);

        // Get all the tipologiaZonaList where stato equals to UPDATED_STATO
        defaultTipologiaZonaShouldNotBeFound("stato.equals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByStatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where stato not equals to DEFAULT_STATO
        defaultTipologiaZonaShouldNotBeFound("stato.notEquals=" + DEFAULT_STATO);

        // Get all the tipologiaZonaList where stato not equals to UPDATED_STATO
        defaultTipologiaZonaShouldBeFound("stato.notEquals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByStatoIsInShouldWork() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where stato in DEFAULT_STATO or UPDATED_STATO
        defaultTipologiaZonaShouldBeFound("stato.in=" + DEFAULT_STATO + "," + UPDATED_STATO);

        // Get all the tipologiaZonaList where stato equals to UPDATED_STATO
        defaultTipologiaZonaShouldNotBeFound("stato.in=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllTipologiaZonasByStatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipologiaZonaRepository.saveAndFlush(tipologiaZona);

        // Get all the tipologiaZonaList where stato is not null
        defaultTipologiaZonaShouldBeFound("stato.specified=true");

        // Get all the tipologiaZonaList where stato is null
        defaultTipologiaZonaShouldNotBeFound("stato.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipologiaZonaShouldBeFound(String filter) throws Exception {
        restTipologiaZonaMockMvc
            .perform(get("/api/tipologia-zonas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipologiaZona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].regoleCircolazione").value(hasItem(DEFAULT_REGOLE_CIRCOLAZIONE.toString())))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));

        // Check, that the count call also returns 1
        restTipologiaZonaMockMvc
            .perform(get("/api/tipologia-zonas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipologiaZonaShouldNotBeFound(String filter) throws Exception {
        restTipologiaZonaMockMvc
            .perform(get("/api/tipologia-zonas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipologiaZonaMockMvc
            .perform(get("/api/tipologia-zonas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipologiaZona() throws Exception {
        // Get the tipologiaZona
        restTipologiaZonaMockMvc.perform(get("/api/tipologia-zonas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipologiaZona() throws Exception {
        // Initialize the database
        tipologiaZonaService.save(tipologiaZona);

        int databaseSizeBeforeUpdate = tipologiaZonaRepository.findAll().size();

        // Update the tipologiaZona
        TipologiaZona updatedTipologiaZona = tipologiaZonaRepository.findById(tipologiaZona.getId()).get();
        // Disconnect from session so that the updates on updatedTipologiaZona are not directly saved in db
        em.detach(updatedTipologiaZona);
        updatedTipologiaZona.nome(UPDATED_NOME).regoleCircolazione(UPDATED_REGOLE_CIRCOLAZIONE).stato(UPDATED_STATO);

        restTipologiaZonaMockMvc
            .perform(
                put("/api/tipologia-zonas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipologiaZona))
            )
            .andExpect(status().isOk());

        // Validate the TipologiaZona in the database
        List<TipologiaZona> tipologiaZonaList = tipologiaZonaRepository.findAll();
        assertThat(tipologiaZonaList).hasSize(databaseSizeBeforeUpdate);
        TipologiaZona testTipologiaZona = tipologiaZonaList.get(tipologiaZonaList.size() - 1);
        assertThat(testTipologiaZona.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipologiaZona.getRegoleCircolazione()).isEqualTo(UPDATED_REGOLE_CIRCOLAZIONE);
        assertThat(testTipologiaZona.getStato()).isEqualTo(UPDATED_STATO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipologiaZona() throws Exception {
        int databaseSizeBeforeUpdate = tipologiaZonaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipologiaZonaMockMvc
            .perform(
                put("/api/tipologia-zonas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaZona))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipologiaZona in the database
        List<TipologiaZona> tipologiaZonaList = tipologiaZonaRepository.findAll();
        assertThat(tipologiaZonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipologiaZona() throws Exception {
        // Initialize the database
        tipologiaZonaService.save(tipologiaZona);

        int databaseSizeBeforeDelete = tipologiaZonaRepository.findAll().size();

        // Delete the tipologiaZona
        restTipologiaZonaMockMvc
            .perform(delete("/api/tipologia-zonas/{id}", tipologiaZona.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipologiaZona> tipologiaZonaList = tipologiaZonaRepository.findAll();
        assertThat(tipologiaZonaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
