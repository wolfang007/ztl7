package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.DurataCosto;
import com.nttdata.myztl7.repository.DurataCostoRepository;
import com.nttdata.myztl7.service.DurataCostoQueryService;
import com.nttdata.myztl7.service.DurataCostoService;
import com.nttdata.myztl7.service.dto.DurataCostoCriteria;
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
 * Integration tests for the {@link DurataCostoResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class DurataCostoResourceIT {
    private static final String DEFAULT_DURATA = "AAAAAAAAAA";
    private static final String UPDATED_DURATA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final Double DEFAULT_COSTO = 1D;
    private static final Double UPDATED_COSTO = 2D;
    private static final Double SMALLER_COSTO = 1D - 1D;

    @Autowired
    private DurataCostoRepository durataCostoRepository;

    @Autowired
    private DurataCostoService durataCostoService;

    @Autowired
    private DurataCostoQueryService durataCostoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDurataCostoMockMvc;

    private DurataCosto durataCosto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DurataCosto createEntity(EntityManager em) {
        DurataCosto durataCosto = new DurataCosto().durata(DEFAULT_DURATA).descrizione(DEFAULT_DESCRIZIONE).costo(DEFAULT_COSTO);
        return durataCosto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DurataCosto createUpdatedEntity(EntityManager em) {
        DurataCosto durataCosto = new DurataCosto().durata(UPDATED_DURATA).descrizione(UPDATED_DESCRIZIONE).costo(UPDATED_COSTO);
        return durataCosto;
    }

    @BeforeEach
    public void initTest() {
        durataCosto = createEntity(em);
    }

    @Test
    @Transactional
    public void createDurataCosto() throws Exception {
        int databaseSizeBeforeCreate = durataCostoRepository.findAll().size();
        // Create the DurataCosto
        restDurataCostoMockMvc
            .perform(
                post("/api/durata-costos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(durataCosto))
            )
            .andExpect(status().isCreated());

        // Validate the DurataCosto in the database
        List<DurataCosto> durataCostoList = durataCostoRepository.findAll();
        assertThat(durataCostoList).hasSize(databaseSizeBeforeCreate + 1);
        DurataCosto testDurataCosto = durataCostoList.get(durataCostoList.size() - 1);
        assertThat(testDurataCosto.getDurata()).isEqualTo(DEFAULT_DURATA);
        assertThat(testDurataCosto.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testDurataCosto.getCosto()).isEqualTo(DEFAULT_COSTO);
    }

    @Test
    @Transactional
    public void createDurataCostoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = durataCostoRepository.findAll().size();

        // Create the DurataCosto with an existing ID
        durataCosto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDurataCostoMockMvc
            .perform(
                post("/api/durata-costos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(durataCosto))
            )
            .andExpect(status().isBadRequest());

        // Validate the DurataCosto in the database
        List<DurataCosto> durataCostoList = durataCostoRepository.findAll();
        assertThat(durataCostoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDurataIsRequired() throws Exception {
        int databaseSizeBeforeTest = durataCostoRepository.findAll().size();
        // set the field null
        durataCosto.setDurata(null);

        // Create the DurataCosto, which fails.

        restDurataCostoMockMvc
            .perform(
                post("/api/durata-costos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(durataCosto))
            )
            .andExpect(status().isBadRequest());

        List<DurataCosto> durataCostoList = durataCostoRepository.findAll();
        assertThat(durataCostoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDurataCostos() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList
        restDurataCostoMockMvc
            .perform(get("/api/durata-costos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(durataCosto.getId().intValue())))
            .andExpect(jsonPath("$.[*].durata").value(hasItem(DEFAULT_DURATA)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())));
    }

    @Test
    @Transactional
    public void getDurataCosto() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get the durataCosto
        restDurataCostoMockMvc
            .perform(get("/api/durata-costos/{id}", durataCosto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(durataCosto.getId().intValue()))
            .andExpect(jsonPath("$.durata").value(DEFAULT_DURATA))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO.doubleValue()));
    }

    @Test
    @Transactional
    public void getDurataCostosByIdFiltering() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        Long id = durataCosto.getId();

        defaultDurataCostoShouldBeFound("id.equals=" + id);
        defaultDurataCostoShouldNotBeFound("id.notEquals=" + id);

        defaultDurataCostoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDurataCostoShouldNotBeFound("id.greaterThan=" + id);

        defaultDurataCostoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDurataCostoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDurataIsEqualToSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where durata equals to DEFAULT_DURATA
        defaultDurataCostoShouldBeFound("durata.equals=" + DEFAULT_DURATA);

        // Get all the durataCostoList where durata equals to UPDATED_DURATA
        defaultDurataCostoShouldNotBeFound("durata.equals=" + UPDATED_DURATA);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDurataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where durata not equals to DEFAULT_DURATA
        defaultDurataCostoShouldNotBeFound("durata.notEquals=" + DEFAULT_DURATA);

        // Get all the durataCostoList where durata not equals to UPDATED_DURATA
        defaultDurataCostoShouldBeFound("durata.notEquals=" + UPDATED_DURATA);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDurataIsInShouldWork() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where durata in DEFAULT_DURATA or UPDATED_DURATA
        defaultDurataCostoShouldBeFound("durata.in=" + DEFAULT_DURATA + "," + UPDATED_DURATA);

        // Get all the durataCostoList where durata equals to UPDATED_DURATA
        defaultDurataCostoShouldNotBeFound("durata.in=" + UPDATED_DURATA);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDurataIsNullOrNotNull() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where durata is not null
        defaultDurataCostoShouldBeFound("durata.specified=true");

        // Get all the durataCostoList where durata is null
        defaultDurataCostoShouldNotBeFound("durata.specified=false");
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDurataContainsSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where durata contains DEFAULT_DURATA
        defaultDurataCostoShouldBeFound("durata.contains=" + DEFAULT_DURATA);

        // Get all the durataCostoList where durata contains UPDATED_DURATA
        defaultDurataCostoShouldNotBeFound("durata.contains=" + UPDATED_DURATA);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDurataNotContainsSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where durata does not contain DEFAULT_DURATA
        defaultDurataCostoShouldNotBeFound("durata.doesNotContain=" + DEFAULT_DURATA);

        // Get all the durataCostoList where durata does not contain UPDATED_DURATA
        defaultDurataCostoShouldBeFound("durata.doesNotContain=" + UPDATED_DURATA);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDescrizioneIsEqualToSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where descrizione equals to DEFAULT_DESCRIZIONE
        defaultDurataCostoShouldBeFound("descrizione.equals=" + DEFAULT_DESCRIZIONE);

        // Get all the durataCostoList where descrizione equals to UPDATED_DESCRIZIONE
        defaultDurataCostoShouldNotBeFound("descrizione.equals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDescrizioneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where descrizione not equals to DEFAULT_DESCRIZIONE
        defaultDurataCostoShouldNotBeFound("descrizione.notEquals=" + DEFAULT_DESCRIZIONE);

        // Get all the durataCostoList where descrizione not equals to UPDATED_DESCRIZIONE
        defaultDurataCostoShouldBeFound("descrizione.notEquals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDescrizioneIsInShouldWork() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where descrizione in DEFAULT_DESCRIZIONE or UPDATED_DESCRIZIONE
        defaultDurataCostoShouldBeFound("descrizione.in=" + DEFAULT_DESCRIZIONE + "," + UPDATED_DESCRIZIONE);

        // Get all the durataCostoList where descrizione equals to UPDATED_DESCRIZIONE
        defaultDurataCostoShouldNotBeFound("descrizione.in=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDescrizioneIsNullOrNotNull() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where descrizione is not null
        defaultDurataCostoShouldBeFound("descrizione.specified=true");

        // Get all the durataCostoList where descrizione is null
        defaultDurataCostoShouldNotBeFound("descrizione.specified=false");
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDescrizioneContainsSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where descrizione contains DEFAULT_DESCRIZIONE
        defaultDurataCostoShouldBeFound("descrizione.contains=" + DEFAULT_DESCRIZIONE);

        // Get all the durataCostoList where descrizione contains UPDATED_DESCRIZIONE
        defaultDurataCostoShouldNotBeFound("descrizione.contains=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByDescrizioneNotContainsSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where descrizione does not contain DEFAULT_DESCRIZIONE
        defaultDurataCostoShouldNotBeFound("descrizione.doesNotContain=" + DEFAULT_DESCRIZIONE);

        // Get all the durataCostoList where descrizione does not contain UPDATED_DESCRIZIONE
        defaultDurataCostoShouldBeFound("descrizione.doesNotContain=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByCostoIsEqualToSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where costo equals to DEFAULT_COSTO
        defaultDurataCostoShouldBeFound("costo.equals=" + DEFAULT_COSTO);

        // Get all the durataCostoList where costo equals to UPDATED_COSTO
        defaultDurataCostoShouldNotBeFound("costo.equals=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByCostoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where costo not equals to DEFAULT_COSTO
        defaultDurataCostoShouldNotBeFound("costo.notEquals=" + DEFAULT_COSTO);

        // Get all the durataCostoList where costo not equals to UPDATED_COSTO
        defaultDurataCostoShouldBeFound("costo.notEquals=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByCostoIsInShouldWork() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where costo in DEFAULT_COSTO or UPDATED_COSTO
        defaultDurataCostoShouldBeFound("costo.in=" + DEFAULT_COSTO + "," + UPDATED_COSTO);

        // Get all the durataCostoList where costo equals to UPDATED_COSTO
        defaultDurataCostoShouldNotBeFound("costo.in=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByCostoIsNullOrNotNull() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where costo is not null
        defaultDurataCostoShouldBeFound("costo.specified=true");

        // Get all the durataCostoList where costo is null
        defaultDurataCostoShouldNotBeFound("costo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDurataCostosByCostoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where costo is greater than or equal to DEFAULT_COSTO
        defaultDurataCostoShouldBeFound("costo.greaterThanOrEqual=" + DEFAULT_COSTO);

        // Get all the durataCostoList where costo is greater than or equal to UPDATED_COSTO
        defaultDurataCostoShouldNotBeFound("costo.greaterThanOrEqual=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByCostoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where costo is less than or equal to DEFAULT_COSTO
        defaultDurataCostoShouldBeFound("costo.lessThanOrEqual=" + DEFAULT_COSTO);

        // Get all the durataCostoList where costo is less than or equal to SMALLER_COSTO
        defaultDurataCostoShouldNotBeFound("costo.lessThanOrEqual=" + SMALLER_COSTO);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByCostoIsLessThanSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where costo is less than DEFAULT_COSTO
        defaultDurataCostoShouldNotBeFound("costo.lessThan=" + DEFAULT_COSTO);

        // Get all the durataCostoList where costo is less than UPDATED_COSTO
        defaultDurataCostoShouldBeFound("costo.lessThan=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllDurataCostosByCostoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        durataCostoRepository.saveAndFlush(durataCosto);

        // Get all the durataCostoList where costo is greater than DEFAULT_COSTO
        defaultDurataCostoShouldNotBeFound("costo.greaterThan=" + DEFAULT_COSTO);

        // Get all the durataCostoList where costo is greater than SMALLER_COSTO
        defaultDurataCostoShouldBeFound("costo.greaterThan=" + SMALLER_COSTO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDurataCostoShouldBeFound(String filter) throws Exception {
        restDurataCostoMockMvc
            .perform(get("/api/durata-costos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(durataCosto.getId().intValue())))
            .andExpect(jsonPath("$.[*].durata").value(hasItem(DEFAULT_DURATA)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())));

        // Check, that the count call also returns 1
        restDurataCostoMockMvc
            .perform(get("/api/durata-costos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDurataCostoShouldNotBeFound(String filter) throws Exception {
        restDurataCostoMockMvc
            .perform(get("/api/durata-costos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDurataCostoMockMvc
            .perform(get("/api/durata-costos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDurataCosto() throws Exception {
        // Get the durataCosto
        restDurataCostoMockMvc.perform(get("/api/durata-costos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDurataCosto() throws Exception {
        // Initialize the database
        durataCostoService.save(durataCosto);

        int databaseSizeBeforeUpdate = durataCostoRepository.findAll().size();

        // Update the durataCosto
        DurataCosto updatedDurataCosto = durataCostoRepository.findById(durataCosto.getId()).get();
        // Disconnect from session so that the updates on updatedDurataCosto are not directly saved in db
        em.detach(updatedDurataCosto);
        updatedDurataCosto.durata(UPDATED_DURATA).descrizione(UPDATED_DESCRIZIONE).costo(UPDATED_COSTO);

        restDurataCostoMockMvc
            .perform(
                put("/api/durata-costos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDurataCosto))
            )
            .andExpect(status().isOk());

        // Validate the DurataCosto in the database
        List<DurataCosto> durataCostoList = durataCostoRepository.findAll();
        assertThat(durataCostoList).hasSize(databaseSizeBeforeUpdate);
        DurataCosto testDurataCosto = durataCostoList.get(durataCostoList.size() - 1);
        assertThat(testDurataCosto.getDurata()).isEqualTo(UPDATED_DURATA);
        assertThat(testDurataCosto.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testDurataCosto.getCosto()).isEqualTo(UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void updateNonExistingDurataCosto() throws Exception {
        int databaseSizeBeforeUpdate = durataCostoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDurataCostoMockMvc
            .perform(
                put("/api/durata-costos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(durataCosto))
            )
            .andExpect(status().isBadRequest());

        // Validate the DurataCosto in the database
        List<DurataCosto> durataCostoList = durataCostoRepository.findAll();
        assertThat(durataCostoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDurataCosto() throws Exception {
        // Initialize the database
        durataCostoService.save(durataCosto);

        int databaseSizeBeforeDelete = durataCostoRepository.findAll().size();

        // Delete the durataCosto
        restDurataCostoMockMvc
            .perform(delete("/api/durata-costos/{id}", durataCosto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DurataCosto> durataCostoList = durataCostoRepository.findAll();
        assertThat(durataCostoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
