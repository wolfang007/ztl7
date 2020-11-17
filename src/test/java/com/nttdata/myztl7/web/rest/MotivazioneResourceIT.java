package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.Motivazione;
import com.nttdata.myztl7.repository.MotivazioneRepository;
import com.nttdata.myztl7.service.MotivazioneQueryService;
import com.nttdata.myztl7.service.MotivazioneService;
import com.nttdata.myztl7.service.dto.MotivazioneCriteria;
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
 * Integration tests for the {@link MotivazioneResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class MotivazioneResourceIT {
    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    @Autowired
    private MotivazioneRepository motivazioneRepository;

    @Autowired
    private MotivazioneService motivazioneService;

    @Autowired
    private MotivazioneQueryService motivazioneQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMotivazioneMockMvc;

    private Motivazione motivazione;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Motivazione createEntity(EntityManager em) {
        Motivazione motivazione = new Motivazione().descrizione(DEFAULT_DESCRIZIONE);
        return motivazione;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Motivazione createUpdatedEntity(EntityManager em) {
        Motivazione motivazione = new Motivazione().descrizione(UPDATED_DESCRIZIONE);
        return motivazione;
    }

    @BeforeEach
    public void initTest() {
        motivazione = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotivazione() throws Exception {
        int databaseSizeBeforeCreate = motivazioneRepository.findAll().size();
        // Create the Motivazione
        restMotivazioneMockMvc
            .perform(
                post("/api/motivaziones").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(motivazione))
            )
            .andExpect(status().isCreated());

        // Validate the Motivazione in the database
        List<Motivazione> motivazioneList = motivazioneRepository.findAll();
        assertThat(motivazioneList).hasSize(databaseSizeBeforeCreate + 1);
        Motivazione testMotivazione = motivazioneList.get(motivazioneList.size() - 1);
        assertThat(testMotivazione.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void createMotivazioneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motivazioneRepository.findAll().size();

        // Create the Motivazione with an existing ID
        motivazione.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotivazioneMockMvc
            .perform(
                post("/api/motivaziones").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(motivazione))
            )
            .andExpect(status().isBadRequest());

        // Validate the Motivazione in the database
        List<Motivazione> motivazioneList = motivazioneRepository.findAll();
        assertThat(motivazioneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescrizioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = motivazioneRepository.findAll().size();
        // set the field null
        motivazione.setDescrizione(null);

        // Create the Motivazione, which fails.

        restMotivazioneMockMvc
            .perform(
                post("/api/motivaziones").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(motivazione))
            )
            .andExpect(status().isBadRequest());

        List<Motivazione> motivazioneList = motivazioneRepository.findAll();
        assertThat(motivazioneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMotivaziones() throws Exception {
        // Initialize the database
        motivazioneRepository.saveAndFlush(motivazione);

        // Get all the motivazioneList
        restMotivazioneMockMvc
            .perform(get("/api/motivaziones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivazione.getId().intValue())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)));
    }

    @Test
    @Transactional
    public void getMotivazione() throws Exception {
        // Initialize the database
        motivazioneRepository.saveAndFlush(motivazione);

        // Get the motivazione
        restMotivazioneMockMvc
            .perform(get("/api/motivaziones/{id}", motivazione.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(motivazione.getId().intValue()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE));
    }

    @Test
    @Transactional
    public void getMotivazionesByIdFiltering() throws Exception {
        // Initialize the database
        motivazioneRepository.saveAndFlush(motivazione);

        Long id = motivazione.getId();

        defaultMotivazioneShouldBeFound("id.equals=" + id);
        defaultMotivazioneShouldNotBeFound("id.notEquals=" + id);

        defaultMotivazioneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMotivazioneShouldNotBeFound("id.greaterThan=" + id);

        defaultMotivazioneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMotivazioneShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllMotivazionesByDescrizioneIsEqualToSomething() throws Exception {
        // Initialize the database
        motivazioneRepository.saveAndFlush(motivazione);

        // Get all the motivazioneList where descrizione equals to DEFAULT_DESCRIZIONE
        defaultMotivazioneShouldBeFound("descrizione.equals=" + DEFAULT_DESCRIZIONE);

        // Get all the motivazioneList where descrizione equals to UPDATED_DESCRIZIONE
        defaultMotivazioneShouldNotBeFound("descrizione.equals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllMotivazionesByDescrizioneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        motivazioneRepository.saveAndFlush(motivazione);

        // Get all the motivazioneList where descrizione not equals to DEFAULT_DESCRIZIONE
        defaultMotivazioneShouldNotBeFound("descrizione.notEquals=" + DEFAULT_DESCRIZIONE);

        // Get all the motivazioneList where descrizione not equals to UPDATED_DESCRIZIONE
        defaultMotivazioneShouldBeFound("descrizione.notEquals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllMotivazionesByDescrizioneIsInShouldWork() throws Exception {
        // Initialize the database
        motivazioneRepository.saveAndFlush(motivazione);

        // Get all the motivazioneList where descrizione in DEFAULT_DESCRIZIONE or UPDATED_DESCRIZIONE
        defaultMotivazioneShouldBeFound("descrizione.in=" + DEFAULT_DESCRIZIONE + "," + UPDATED_DESCRIZIONE);

        // Get all the motivazioneList where descrizione equals to UPDATED_DESCRIZIONE
        defaultMotivazioneShouldNotBeFound("descrizione.in=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllMotivazionesByDescrizioneIsNullOrNotNull() throws Exception {
        // Initialize the database
        motivazioneRepository.saveAndFlush(motivazione);

        // Get all the motivazioneList where descrizione is not null
        defaultMotivazioneShouldBeFound("descrizione.specified=true");

        // Get all the motivazioneList where descrizione is null
        defaultMotivazioneShouldNotBeFound("descrizione.specified=false");
    }

    @Test
    @Transactional
    public void getAllMotivazionesByDescrizioneContainsSomething() throws Exception {
        // Initialize the database
        motivazioneRepository.saveAndFlush(motivazione);

        // Get all the motivazioneList where descrizione contains DEFAULT_DESCRIZIONE
        defaultMotivazioneShouldBeFound("descrizione.contains=" + DEFAULT_DESCRIZIONE);

        // Get all the motivazioneList where descrizione contains UPDATED_DESCRIZIONE
        defaultMotivazioneShouldNotBeFound("descrizione.contains=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllMotivazionesByDescrizioneNotContainsSomething() throws Exception {
        // Initialize the database
        motivazioneRepository.saveAndFlush(motivazione);

        // Get all the motivazioneList where descrizione does not contain DEFAULT_DESCRIZIONE
        defaultMotivazioneShouldNotBeFound("descrizione.doesNotContain=" + DEFAULT_DESCRIZIONE);

        // Get all the motivazioneList where descrizione does not contain UPDATED_DESCRIZIONE
        defaultMotivazioneShouldBeFound("descrizione.doesNotContain=" + UPDATED_DESCRIZIONE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMotivazioneShouldBeFound(String filter) throws Exception {
        restMotivazioneMockMvc
            .perform(get("/api/motivaziones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivazione.getId().intValue())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)));

        // Check, that the count call also returns 1
        restMotivazioneMockMvc
            .perform(get("/api/motivaziones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMotivazioneShouldNotBeFound(String filter) throws Exception {
        restMotivazioneMockMvc
            .perform(get("/api/motivaziones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMotivazioneMockMvc
            .perform(get("/api/motivaziones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMotivazione() throws Exception {
        // Get the motivazione
        restMotivazioneMockMvc.perform(get("/api/motivaziones/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotivazione() throws Exception {
        // Initialize the database
        motivazioneService.save(motivazione);

        int databaseSizeBeforeUpdate = motivazioneRepository.findAll().size();

        // Update the motivazione
        Motivazione updatedMotivazione = motivazioneRepository.findById(motivazione.getId()).get();
        // Disconnect from session so that the updates on updatedMotivazione are not directly saved in db
        em.detach(updatedMotivazione);
        updatedMotivazione.descrizione(UPDATED_DESCRIZIONE);

        restMotivazioneMockMvc
            .perform(
                put("/api/motivaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMotivazione))
            )
            .andExpect(status().isOk());

        // Validate the Motivazione in the database
        List<Motivazione> motivazioneList = motivazioneRepository.findAll();
        assertThat(motivazioneList).hasSize(databaseSizeBeforeUpdate);
        Motivazione testMotivazione = motivazioneList.get(motivazioneList.size() - 1);
        assertThat(testMotivazione.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingMotivazione() throws Exception {
        int databaseSizeBeforeUpdate = motivazioneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotivazioneMockMvc
            .perform(
                put("/api/motivaziones").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(motivazione))
            )
            .andExpect(status().isBadRequest());

        // Validate the Motivazione in the database
        List<Motivazione> motivazioneList = motivazioneRepository.findAll();
        assertThat(motivazioneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMotivazione() throws Exception {
        // Initialize the database
        motivazioneService.save(motivazione);

        int databaseSizeBeforeDelete = motivazioneRepository.findAll().size();

        // Delete the motivazione
        restMotivazioneMockMvc
            .perform(delete("/api/motivaziones/{id}", motivazione.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Motivazione> motivazioneList = motivazioneRepository.findAll();
        assertThat(motivazioneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
