package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.GruppoVarchi;
import com.nttdata.myztl7.domain.Varco;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.repository.VarcoRepository;
import com.nttdata.myztl7.service.VarcoQueryService;
import com.nttdata.myztl7.service.VarcoService;
import com.nttdata.myztl7.service.dto.VarcoCriteria;
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
 * Integration tests for the {@link VarcoResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class VarcoResourceIT {
    private static final String DEFAULT_CODICE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE_POSIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE_POSIZIONE = "BBBBBBBBBB";

    private static final EntityStatus DEFAULT_STATO = EntityStatus.ATTIVO;
    private static final EntityStatus UPDATED_STATO = EntityStatus.DISATTIVO;

    @Autowired
    private VarcoRepository varcoRepository;

    @Autowired
    private VarcoService varcoService;

    @Autowired
    private VarcoQueryService varcoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVarcoMockMvc;

    private Varco varco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Varco createEntity(EntityManager em) {
        Varco varco = new Varco().codice(DEFAULT_CODICE).descrizionePosizione(DEFAULT_DESCRIZIONE_POSIZIONE).stato(DEFAULT_STATO);
        return varco;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Varco createUpdatedEntity(EntityManager em) {
        Varco varco = new Varco().codice(UPDATED_CODICE).descrizionePosizione(UPDATED_DESCRIZIONE_POSIZIONE).stato(UPDATED_STATO);
        return varco;
    }

    @BeforeEach
    public void initTest() {
        varco = createEntity(em);
    }

    @Test
    @Transactional
    public void createVarco() throws Exception {
        int databaseSizeBeforeCreate = varcoRepository.findAll().size();
        // Create the Varco
        restVarcoMockMvc
            .perform(post("/api/varcos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varco)))
            .andExpect(status().isCreated());

        // Validate the Varco in the database
        List<Varco> varcoList = varcoRepository.findAll();
        assertThat(varcoList).hasSize(databaseSizeBeforeCreate + 1);
        Varco testVarco = varcoList.get(varcoList.size() - 1);
        assertThat(testVarco.getCodice()).isEqualTo(DEFAULT_CODICE);
        assertThat(testVarco.getDescrizionePosizione()).isEqualTo(DEFAULT_DESCRIZIONE_POSIZIONE);
        assertThat(testVarco.getStato()).isEqualTo(DEFAULT_STATO);
    }

    @Test
    @Transactional
    public void createVarcoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = varcoRepository.findAll().size();

        // Create the Varco with an existing ID
        varco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVarcoMockMvc
            .perform(post("/api/varcos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varco)))
            .andExpect(status().isBadRequest());

        // Validate the Varco in the database
        List<Varco> varcoList = varcoRepository.findAll();
        assertThat(varcoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = varcoRepository.findAll().size();
        // set the field null
        varco.setCodice(null);

        // Create the Varco, which fails.

        restVarcoMockMvc
            .perform(post("/api/varcos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varco)))
            .andExpect(status().isBadRequest());

        List<Varco> varcoList = varcoRepository.findAll();
        assertThat(varcoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescrizionePosizioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = varcoRepository.findAll().size();
        // set the field null
        varco.setDescrizionePosizione(null);

        // Create the Varco, which fails.

        restVarcoMockMvc
            .perform(post("/api/varcos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varco)))
            .andExpect(status().isBadRequest());

        List<Varco> varcoList = varcoRepository.findAll();
        assertThat(varcoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = varcoRepository.findAll().size();
        // set the field null
        varco.setStato(null);

        // Create the Varco, which fails.

        restVarcoMockMvc
            .perform(post("/api/varcos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varco)))
            .andExpect(status().isBadRequest());

        List<Varco> varcoList = varcoRepository.findAll();
        assertThat(varcoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVarcos() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList
        restVarcoMockMvc
            .perform(get("/api/varcos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(varco.getId().intValue())))
            .andExpect(jsonPath("$.[*].codice").value(hasItem(DEFAULT_CODICE)))
            .andExpect(jsonPath("$.[*].descrizionePosizione").value(hasItem(DEFAULT_DESCRIZIONE_POSIZIONE)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));
    }

    @Test
    @Transactional
    public void getVarco() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get the varco
        restVarcoMockMvc
            .perform(get("/api/varcos/{id}", varco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(varco.getId().intValue()))
            .andExpect(jsonPath("$.codice").value(DEFAULT_CODICE))
            .andExpect(jsonPath("$.descrizionePosizione").value(DEFAULT_DESCRIZIONE_POSIZIONE))
            .andExpect(jsonPath("$.stato").value(DEFAULT_STATO.toString()));
    }

    @Test
    @Transactional
    public void getVarcosByIdFiltering() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        Long id = varco.getId();

        defaultVarcoShouldBeFound("id.equals=" + id);
        defaultVarcoShouldNotBeFound("id.notEquals=" + id);

        defaultVarcoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVarcoShouldNotBeFound("id.greaterThan=" + id);

        defaultVarcoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVarcoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllVarcosByCodiceIsEqualToSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where codice equals to DEFAULT_CODICE
        defaultVarcoShouldBeFound("codice.equals=" + DEFAULT_CODICE);

        // Get all the varcoList where codice equals to UPDATED_CODICE
        defaultVarcoShouldNotBeFound("codice.equals=" + UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void getAllVarcosByCodiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where codice not equals to DEFAULT_CODICE
        defaultVarcoShouldNotBeFound("codice.notEquals=" + DEFAULT_CODICE);

        // Get all the varcoList where codice not equals to UPDATED_CODICE
        defaultVarcoShouldBeFound("codice.notEquals=" + UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void getAllVarcosByCodiceIsInShouldWork() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where codice in DEFAULT_CODICE or UPDATED_CODICE
        defaultVarcoShouldBeFound("codice.in=" + DEFAULT_CODICE + "," + UPDATED_CODICE);

        // Get all the varcoList where codice equals to UPDATED_CODICE
        defaultVarcoShouldNotBeFound("codice.in=" + UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void getAllVarcosByCodiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where codice is not null
        defaultVarcoShouldBeFound("codice.specified=true");

        // Get all the varcoList where codice is null
        defaultVarcoShouldNotBeFound("codice.specified=false");
    }

    @Test
    @Transactional
    public void getAllVarcosByCodiceContainsSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where codice contains DEFAULT_CODICE
        defaultVarcoShouldBeFound("codice.contains=" + DEFAULT_CODICE);

        // Get all the varcoList where codice contains UPDATED_CODICE
        defaultVarcoShouldNotBeFound("codice.contains=" + UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void getAllVarcosByCodiceNotContainsSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where codice does not contain DEFAULT_CODICE
        defaultVarcoShouldNotBeFound("codice.doesNotContain=" + DEFAULT_CODICE);

        // Get all the varcoList where codice does not contain UPDATED_CODICE
        defaultVarcoShouldBeFound("codice.doesNotContain=" + UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void getAllVarcosByDescrizionePosizioneIsEqualToSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where descrizionePosizione equals to DEFAULT_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldBeFound("descrizionePosizione.equals=" + DEFAULT_DESCRIZIONE_POSIZIONE);

        // Get all the varcoList where descrizionePosizione equals to UPDATED_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldNotBeFound("descrizionePosizione.equals=" + UPDATED_DESCRIZIONE_POSIZIONE);
    }

    @Test
    @Transactional
    public void getAllVarcosByDescrizionePosizioneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where descrizionePosizione not equals to DEFAULT_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldNotBeFound("descrizionePosizione.notEquals=" + DEFAULT_DESCRIZIONE_POSIZIONE);

        // Get all the varcoList where descrizionePosizione not equals to UPDATED_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldBeFound("descrizionePosizione.notEquals=" + UPDATED_DESCRIZIONE_POSIZIONE);
    }

    @Test
    @Transactional
    public void getAllVarcosByDescrizionePosizioneIsInShouldWork() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where descrizionePosizione in DEFAULT_DESCRIZIONE_POSIZIONE or UPDATED_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldBeFound("descrizionePosizione.in=" + DEFAULT_DESCRIZIONE_POSIZIONE + "," + UPDATED_DESCRIZIONE_POSIZIONE);

        // Get all the varcoList where descrizionePosizione equals to UPDATED_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldNotBeFound("descrizionePosizione.in=" + UPDATED_DESCRIZIONE_POSIZIONE);
    }

    @Test
    @Transactional
    public void getAllVarcosByDescrizionePosizioneIsNullOrNotNull() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where descrizionePosizione is not null
        defaultVarcoShouldBeFound("descrizionePosizione.specified=true");

        // Get all the varcoList where descrizionePosizione is null
        defaultVarcoShouldNotBeFound("descrizionePosizione.specified=false");
    }

    @Test
    @Transactional
    public void getAllVarcosByDescrizionePosizioneContainsSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where descrizionePosizione contains DEFAULT_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldBeFound("descrizionePosizione.contains=" + DEFAULT_DESCRIZIONE_POSIZIONE);

        // Get all the varcoList where descrizionePosizione contains UPDATED_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldNotBeFound("descrizionePosizione.contains=" + UPDATED_DESCRIZIONE_POSIZIONE);
    }

    @Test
    @Transactional
    public void getAllVarcosByDescrizionePosizioneNotContainsSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where descrizionePosizione does not contain DEFAULT_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldNotBeFound("descrizionePosizione.doesNotContain=" + DEFAULT_DESCRIZIONE_POSIZIONE);

        // Get all the varcoList where descrizionePosizione does not contain UPDATED_DESCRIZIONE_POSIZIONE
        defaultVarcoShouldBeFound("descrizionePosizione.doesNotContain=" + UPDATED_DESCRIZIONE_POSIZIONE);
    }

    @Test
    @Transactional
    public void getAllVarcosByStatoIsEqualToSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where stato equals to DEFAULT_STATO
        defaultVarcoShouldBeFound("stato.equals=" + DEFAULT_STATO);

        // Get all the varcoList where stato equals to UPDATED_STATO
        defaultVarcoShouldNotBeFound("stato.equals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllVarcosByStatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where stato not equals to DEFAULT_STATO
        defaultVarcoShouldNotBeFound("stato.notEquals=" + DEFAULT_STATO);

        // Get all the varcoList where stato not equals to UPDATED_STATO
        defaultVarcoShouldBeFound("stato.notEquals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllVarcosByStatoIsInShouldWork() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where stato in DEFAULT_STATO or UPDATED_STATO
        defaultVarcoShouldBeFound("stato.in=" + DEFAULT_STATO + "," + UPDATED_STATO);

        // Get all the varcoList where stato equals to UPDATED_STATO
        defaultVarcoShouldNotBeFound("stato.in=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllVarcosByStatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);

        // Get all the varcoList where stato is not null
        defaultVarcoShouldBeFound("stato.specified=true");

        // Get all the varcoList where stato is null
        defaultVarcoShouldNotBeFound("stato.specified=false");
    }

    @Test
    @Transactional
    public void getAllVarcosByGruppoVarchiIsEqualToSomething() throws Exception {
        // Initialize the database
        varcoRepository.saveAndFlush(varco);
        GruppoVarchi gruppoVarchi = GruppoVarchiResourceIT.createEntity(em);
        em.persist(gruppoVarchi);
        em.flush();
        varco.addGruppoVarchi(gruppoVarchi);
        varcoRepository.saveAndFlush(varco);
        Long gruppoVarchiId = gruppoVarchi.getId();

        // Get all the varcoList where gruppoVarchi equals to gruppoVarchiId
        defaultVarcoShouldBeFound("gruppoVarchiId.equals=" + gruppoVarchiId);

        // Get all the varcoList where gruppoVarchi equals to gruppoVarchiId + 1
        defaultVarcoShouldNotBeFound("gruppoVarchiId.equals=" + (gruppoVarchiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVarcoShouldBeFound(String filter) throws Exception {
        restVarcoMockMvc
            .perform(get("/api/varcos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(varco.getId().intValue())))
            .andExpect(jsonPath("$.[*].codice").value(hasItem(DEFAULT_CODICE)))
            .andExpect(jsonPath("$.[*].descrizionePosizione").value(hasItem(DEFAULT_DESCRIZIONE_POSIZIONE)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));

        // Check, that the count call also returns 1
        restVarcoMockMvc
            .perform(get("/api/varcos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVarcoShouldNotBeFound(String filter) throws Exception {
        restVarcoMockMvc
            .perform(get("/api/varcos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVarcoMockMvc
            .perform(get("/api/varcos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVarco() throws Exception {
        // Get the varco
        restVarcoMockMvc.perform(get("/api/varcos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVarco() throws Exception {
        // Initialize the database
        varcoService.save(varco);

        int databaseSizeBeforeUpdate = varcoRepository.findAll().size();

        // Update the varco
        Varco updatedVarco = varcoRepository.findById(varco.getId()).get();
        // Disconnect from session so that the updates on updatedVarco are not directly saved in db
        em.detach(updatedVarco);
        updatedVarco.codice(UPDATED_CODICE).descrizionePosizione(UPDATED_DESCRIZIONE_POSIZIONE).stato(UPDATED_STATO);

        restVarcoMockMvc
            .perform(put("/api/varcos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedVarco)))
            .andExpect(status().isOk());

        // Validate the Varco in the database
        List<Varco> varcoList = varcoRepository.findAll();
        assertThat(varcoList).hasSize(databaseSizeBeforeUpdate);
        Varco testVarco = varcoList.get(varcoList.size() - 1);
        assertThat(testVarco.getCodice()).isEqualTo(UPDATED_CODICE);
        assertThat(testVarco.getDescrizionePosizione()).isEqualTo(UPDATED_DESCRIZIONE_POSIZIONE);
        assertThat(testVarco.getStato()).isEqualTo(UPDATED_STATO);
    }

    @Test
    @Transactional
    public void updateNonExistingVarco() throws Exception {
        int databaseSizeBeforeUpdate = varcoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVarcoMockMvc
            .perform(put("/api/varcos").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varco)))
            .andExpect(status().isBadRequest());

        // Validate the Varco in the database
        List<Varco> varcoList = varcoRepository.findAll();
        assertThat(varcoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVarco() throws Exception {
        // Initialize the database
        varcoService.save(varco);

        int databaseSizeBeforeDelete = varcoRepository.findAll().size();

        // Delete the varco
        restVarcoMockMvc
            .perform(delete("/api/varcos/{id}", varco.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Varco> varcoList = varcoRepository.findAll();
        assertThat(varcoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
