package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.Festivita;
import com.nttdata.myztl7.repository.FestivitaRepository;
import com.nttdata.myztl7.service.FestivitaQueryService;
import com.nttdata.myztl7.service.FestivitaService;
import com.nttdata.myztl7.service.dto.FestivitaCriteria;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FestivitaResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class FestivitaResourceIT {
    private static final LocalDate DEFAULT_NOME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NOME = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_NOME = LocalDate.ofEpochDay(-1L);

    @Autowired
    private FestivitaRepository festivitaRepository;

    @Autowired
    private FestivitaService festivitaService;

    @Autowired
    private FestivitaQueryService festivitaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFestivitaMockMvc;

    private Festivita festivita;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Festivita createEntity(EntityManager em) {
        Festivita festivita = new Festivita().nome(DEFAULT_NOME);
        return festivita;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Festivita createUpdatedEntity(EntityManager em) {
        Festivita festivita = new Festivita().nome(UPDATED_NOME);
        return festivita;
    }

    @BeforeEach
    public void initTest() {
        festivita = createEntity(em);
    }

    @Test
    @Transactional
    public void createFestivita() throws Exception {
        int databaseSizeBeforeCreate = festivitaRepository.findAll().size();
        // Create the Festivita
        restFestivitaMockMvc
            .perform(post("/api/festivitas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(festivita)))
            .andExpect(status().isCreated());

        // Validate the Festivita in the database
        List<Festivita> festivitaList = festivitaRepository.findAll();
        assertThat(festivitaList).hasSize(databaseSizeBeforeCreate + 1);
        Festivita testFestivita = festivitaList.get(festivitaList.size() - 1);
        assertThat(testFestivita.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createFestivitaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = festivitaRepository.findAll().size();

        // Create the Festivita with an existing ID
        festivita.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFestivitaMockMvc
            .perform(post("/api/festivitas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(festivita)))
            .andExpect(status().isBadRequest());

        // Validate the Festivita in the database
        List<Festivita> festivitaList = festivitaRepository.findAll();
        assertThat(festivitaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = festivitaRepository.findAll().size();
        // set the field null
        festivita.setNome(null);

        // Create the Festivita, which fails.

        restFestivitaMockMvc
            .perform(post("/api/festivitas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(festivita)))
            .andExpect(status().isBadRequest());

        List<Festivita> festivitaList = festivitaRepository.findAll();
        assertThat(festivitaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFestivitas() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get all the festivitaList
        restFestivitaMockMvc
            .perform(get("/api/festivitas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festivita.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getFestivita() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get the festivita
        restFestivitaMockMvc
            .perform(get("/api/festivitas/{id}", festivita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(festivita.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getFestivitasByIdFiltering() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        Long id = festivita.getId();

        defaultFestivitaShouldBeFound("id.equals=" + id);
        defaultFestivitaShouldNotBeFound("id.notEquals=" + id);

        defaultFestivitaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFestivitaShouldNotBeFound("id.greaterThan=" + id);

        defaultFestivitaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFestivitaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllFestivitasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get all the festivitaList where nome equals to DEFAULT_NOME
        defaultFestivitaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the festivitaList where nome equals to UPDATED_NOME
        defaultFestivitaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFestivitasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get all the festivitaList where nome not equals to DEFAULT_NOME
        defaultFestivitaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the festivitaList where nome not equals to UPDATED_NOME
        defaultFestivitaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFestivitasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get all the festivitaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultFestivitaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the festivitaList where nome equals to UPDATED_NOME
        defaultFestivitaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFestivitasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get all the festivitaList where nome is not null
        defaultFestivitaShouldBeFound("nome.specified=true");

        // Get all the festivitaList where nome is null
        defaultFestivitaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllFestivitasByNomeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get all the festivitaList where nome is greater than or equal to DEFAULT_NOME
        defaultFestivitaShouldBeFound("nome.greaterThanOrEqual=" + DEFAULT_NOME);

        // Get all the festivitaList where nome is greater than or equal to UPDATED_NOME
        defaultFestivitaShouldNotBeFound("nome.greaterThanOrEqual=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFestivitasByNomeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get all the festivitaList where nome is less than or equal to DEFAULT_NOME
        defaultFestivitaShouldBeFound("nome.lessThanOrEqual=" + DEFAULT_NOME);

        // Get all the festivitaList where nome is less than or equal to SMALLER_NOME
        defaultFestivitaShouldNotBeFound("nome.lessThanOrEqual=" + SMALLER_NOME);
    }

    @Test
    @Transactional
    public void getAllFestivitasByNomeIsLessThanSomething() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get all the festivitaList where nome is less than DEFAULT_NOME
        defaultFestivitaShouldNotBeFound("nome.lessThan=" + DEFAULT_NOME);

        // Get all the festivitaList where nome is less than UPDATED_NOME
        defaultFestivitaShouldBeFound("nome.lessThan=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFestivitasByNomeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        festivitaRepository.saveAndFlush(festivita);

        // Get all the festivitaList where nome is greater than DEFAULT_NOME
        defaultFestivitaShouldNotBeFound("nome.greaterThan=" + DEFAULT_NOME);

        // Get all the festivitaList where nome is greater than SMALLER_NOME
        defaultFestivitaShouldBeFound("nome.greaterThan=" + SMALLER_NOME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFestivitaShouldBeFound(String filter) throws Exception {
        restFestivitaMockMvc
            .perform(get("/api/festivitas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festivita.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));

        // Check, that the count call also returns 1
        restFestivitaMockMvc
            .perform(get("/api/festivitas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFestivitaShouldNotBeFound(String filter) throws Exception {
        restFestivitaMockMvc
            .perform(get("/api/festivitas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFestivitaMockMvc
            .perform(get("/api/festivitas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFestivita() throws Exception {
        // Get the festivita
        restFestivitaMockMvc.perform(get("/api/festivitas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFestivita() throws Exception {
        // Initialize the database
        festivitaService.save(festivita);

        int databaseSizeBeforeUpdate = festivitaRepository.findAll().size();

        // Update the festivita
        Festivita updatedFestivita = festivitaRepository.findById(festivita.getId()).get();
        // Disconnect from session so that the updates on updatedFestivita are not directly saved in db
        em.detach(updatedFestivita);
        updatedFestivita.nome(UPDATED_NOME);

        restFestivitaMockMvc
            .perform(
                put("/api/festivitas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedFestivita))
            )
            .andExpect(status().isOk());

        // Validate the Festivita in the database
        List<Festivita> festivitaList = festivitaRepository.findAll();
        assertThat(festivitaList).hasSize(databaseSizeBeforeUpdate);
        Festivita testFestivita = festivitaList.get(festivitaList.size() - 1);
        assertThat(testFestivita.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingFestivita() throws Exception {
        int databaseSizeBeforeUpdate = festivitaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFestivitaMockMvc
            .perform(put("/api/festivitas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(festivita)))
            .andExpect(status().isBadRequest());

        // Validate the Festivita in the database
        List<Festivita> festivitaList = festivitaRepository.findAll();
        assertThat(festivitaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFestivita() throws Exception {
        // Initialize the database
        festivitaService.save(festivita);

        int databaseSizeBeforeDelete = festivitaRepository.findAll().size();

        // Delete the festivita
        restFestivitaMockMvc
            .perform(delete("/api/festivitas/{id}", festivita.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Festivita> festivitaList = festivitaRepository.findAll();
        assertThat(festivitaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
