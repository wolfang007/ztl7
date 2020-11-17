package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.RegolaOraria;
import com.nttdata.myztl7.domain.TipologiaVeicolo;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.repository.TipologiaVeicoloRepository;
import com.nttdata.myztl7.service.TipologiaVeicoloQueryService;
import com.nttdata.myztl7.service.TipologiaVeicoloService;
import com.nttdata.myztl7.service.dto.TipologiaVeicoloCriteria;
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
 * Integration tests for the {@link TipologiaVeicoloResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipologiaVeicoloResourceIT {
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final EntityStatus DEFAULT_STATO = EntityStatus.ATTIVO;
    private static final EntityStatus UPDATED_STATO = EntityStatus.DISATTIVO;

    @Autowired
    private TipologiaVeicoloRepository tipologiaVeicoloRepository;

    @Autowired
    private TipologiaVeicoloService tipologiaVeicoloService;

    @Autowired
    private TipologiaVeicoloQueryService tipologiaVeicoloQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipologiaVeicoloMockMvc;

    private TipologiaVeicolo tipologiaVeicolo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipologiaVeicolo createEntity(EntityManager em) {
        TipologiaVeicolo tipologiaVeicolo = new TipologiaVeicolo().nome(DEFAULT_NOME).stato(DEFAULT_STATO);
        return tipologiaVeicolo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipologiaVeicolo createUpdatedEntity(EntityManager em) {
        TipologiaVeicolo tipologiaVeicolo = new TipologiaVeicolo().nome(UPDATED_NOME).stato(UPDATED_STATO);
        return tipologiaVeicolo;
    }

    @BeforeEach
    public void initTest() {
        tipologiaVeicolo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipologiaVeicolo() throws Exception {
        int databaseSizeBeforeCreate = tipologiaVeicoloRepository.findAll().size();
        // Create the TipologiaVeicolo
        restTipologiaVeicoloMockMvc
            .perform(
                post("/api/tipologia-veicolos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaVeicolo))
            )
            .andExpect(status().isCreated());

        // Validate the TipologiaVeicolo in the database
        List<TipologiaVeicolo> tipologiaVeicoloList = tipologiaVeicoloRepository.findAll();
        assertThat(tipologiaVeicoloList).hasSize(databaseSizeBeforeCreate + 1);
        TipologiaVeicolo testTipologiaVeicolo = tipologiaVeicoloList.get(tipologiaVeicoloList.size() - 1);
        assertThat(testTipologiaVeicolo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipologiaVeicolo.getStato()).isEqualTo(DEFAULT_STATO);
    }

    @Test
    @Transactional
    public void createTipologiaVeicoloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipologiaVeicoloRepository.findAll().size();

        // Create the TipologiaVeicolo with an existing ID
        tipologiaVeicolo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipologiaVeicoloMockMvc
            .perform(
                post("/api/tipologia-veicolos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaVeicolo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipologiaVeicolo in the database
        List<TipologiaVeicolo> tipologiaVeicoloList = tipologiaVeicoloRepository.findAll();
        assertThat(tipologiaVeicoloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipologiaVeicoloRepository.findAll().size();
        // set the field null
        tipologiaVeicolo.setNome(null);

        // Create the TipologiaVeicolo, which fails.

        restTipologiaVeicoloMockMvc
            .perform(
                post("/api/tipologia-veicolos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaVeicolo))
            )
            .andExpect(status().isBadRequest());

        List<TipologiaVeicolo> tipologiaVeicoloList = tipologiaVeicoloRepository.findAll();
        assertThat(tipologiaVeicoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipologiaVeicoloRepository.findAll().size();
        // set the field null
        tipologiaVeicolo.setStato(null);

        // Create the TipologiaVeicolo, which fails.

        restTipologiaVeicoloMockMvc
            .perform(
                post("/api/tipologia-veicolos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaVeicolo))
            )
            .andExpect(status().isBadRequest());

        List<TipologiaVeicolo> tipologiaVeicoloList = tipologiaVeicoloRepository.findAll();
        assertThat(tipologiaVeicoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolos() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList
        restTipologiaVeicoloMockMvc
            .perform(get("/api/tipologia-veicolos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipologiaVeicolo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));
    }

    @Test
    @Transactional
    public void getTipologiaVeicolo() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get the tipologiaVeicolo
        restTipologiaVeicoloMockMvc
            .perform(get("/api/tipologia-veicolos/{id}", tipologiaVeicolo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipologiaVeicolo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.stato").value(DEFAULT_STATO.toString()));
    }

    @Test
    @Transactional
    public void getTipologiaVeicolosByIdFiltering() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        Long id = tipologiaVeicolo.getId();

        defaultTipologiaVeicoloShouldBeFound("id.equals=" + id);
        defaultTipologiaVeicoloShouldNotBeFound("id.notEquals=" + id);

        defaultTipologiaVeicoloShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipologiaVeicoloShouldNotBeFound("id.greaterThan=" + id);

        defaultTipologiaVeicoloShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipologiaVeicoloShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where nome equals to DEFAULT_NOME
        defaultTipologiaVeicoloShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the tipologiaVeicoloList where nome equals to UPDATED_NOME
        defaultTipologiaVeicoloShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where nome not equals to DEFAULT_NOME
        defaultTipologiaVeicoloShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the tipologiaVeicoloList where nome not equals to UPDATED_NOME
        defaultTipologiaVeicoloShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultTipologiaVeicoloShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the tipologiaVeicoloList where nome equals to UPDATED_NOME
        defaultTipologiaVeicoloShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where nome is not null
        defaultTipologiaVeicoloShouldBeFound("nome.specified=true");

        // Get all the tipologiaVeicoloList where nome is null
        defaultTipologiaVeicoloShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByNomeContainsSomething() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where nome contains DEFAULT_NOME
        defaultTipologiaVeicoloShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the tipologiaVeicoloList where nome contains UPDATED_NOME
        defaultTipologiaVeicoloShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where nome does not contain DEFAULT_NOME
        defaultTipologiaVeicoloShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the tipologiaVeicoloList where nome does not contain UPDATED_NOME
        defaultTipologiaVeicoloShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByStatoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where stato equals to DEFAULT_STATO
        defaultTipologiaVeicoloShouldBeFound("stato.equals=" + DEFAULT_STATO);

        // Get all the tipologiaVeicoloList where stato equals to UPDATED_STATO
        defaultTipologiaVeicoloShouldNotBeFound("stato.equals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByStatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where stato not equals to DEFAULT_STATO
        defaultTipologiaVeicoloShouldNotBeFound("stato.notEquals=" + DEFAULT_STATO);

        // Get all the tipologiaVeicoloList where stato not equals to UPDATED_STATO
        defaultTipologiaVeicoloShouldBeFound("stato.notEquals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByStatoIsInShouldWork() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where stato in DEFAULT_STATO or UPDATED_STATO
        defaultTipologiaVeicoloShouldBeFound("stato.in=" + DEFAULT_STATO + "," + UPDATED_STATO);

        // Get all the tipologiaVeicoloList where stato equals to UPDATED_STATO
        defaultTipologiaVeicoloShouldNotBeFound("stato.in=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByStatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);

        // Get all the tipologiaVeicoloList where stato is not null
        defaultTipologiaVeicoloShouldBeFound("stato.specified=true");

        // Get all the tipologiaVeicoloList where stato is null
        defaultTipologiaVeicoloShouldNotBeFound("stato.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipologiaVeicolosByRegolaOrariaIsEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);
        RegolaOraria regolaOraria = RegolaOrariaResourceIT.createEntity(em);
        em.persist(regolaOraria);
        em.flush();
        tipologiaVeicolo.addRegolaOraria(regolaOraria);
        tipologiaVeicoloRepository.saveAndFlush(tipologiaVeicolo);
        Long regolaOrariaId = regolaOraria.getId();

        // Get all the tipologiaVeicoloList where regolaOraria equals to regolaOrariaId
        defaultTipologiaVeicoloShouldBeFound("regolaOrariaId.equals=" + regolaOrariaId);

        // Get all the tipologiaVeicoloList where regolaOraria equals to regolaOrariaId + 1
        defaultTipologiaVeicoloShouldNotBeFound("regolaOrariaId.equals=" + (regolaOrariaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipologiaVeicoloShouldBeFound(String filter) throws Exception {
        restTipologiaVeicoloMockMvc
            .perform(get("/api/tipologia-veicolos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipologiaVeicolo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));

        // Check, that the count call also returns 1
        restTipologiaVeicoloMockMvc
            .perform(get("/api/tipologia-veicolos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipologiaVeicoloShouldNotBeFound(String filter) throws Exception {
        restTipologiaVeicoloMockMvc
            .perform(get("/api/tipologia-veicolos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipologiaVeicoloMockMvc
            .perform(get("/api/tipologia-veicolos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipologiaVeicolo() throws Exception {
        // Get the tipologiaVeicolo
        restTipologiaVeicoloMockMvc.perform(get("/api/tipologia-veicolos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipologiaVeicolo() throws Exception {
        // Initialize the database
        tipologiaVeicoloService.save(tipologiaVeicolo);

        int databaseSizeBeforeUpdate = tipologiaVeicoloRepository.findAll().size();

        // Update the tipologiaVeicolo
        TipologiaVeicolo updatedTipologiaVeicolo = tipologiaVeicoloRepository.findById(tipologiaVeicolo.getId()).get();
        // Disconnect from session so that the updates on updatedTipologiaVeicolo are not directly saved in db
        em.detach(updatedTipologiaVeicolo);
        updatedTipologiaVeicolo.nome(UPDATED_NOME).stato(UPDATED_STATO);

        restTipologiaVeicoloMockMvc
            .perform(
                put("/api/tipologia-veicolos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipologiaVeicolo))
            )
            .andExpect(status().isOk());

        // Validate the TipologiaVeicolo in the database
        List<TipologiaVeicolo> tipologiaVeicoloList = tipologiaVeicoloRepository.findAll();
        assertThat(tipologiaVeicoloList).hasSize(databaseSizeBeforeUpdate);
        TipologiaVeicolo testTipologiaVeicolo = tipologiaVeicoloList.get(tipologiaVeicoloList.size() - 1);
        assertThat(testTipologiaVeicolo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipologiaVeicolo.getStato()).isEqualTo(UPDATED_STATO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipologiaVeicolo() throws Exception {
        int databaseSizeBeforeUpdate = tipologiaVeicoloRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipologiaVeicoloMockMvc
            .perform(
                put("/api/tipologia-veicolos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaVeicolo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipologiaVeicolo in the database
        List<TipologiaVeicolo> tipologiaVeicoloList = tipologiaVeicoloRepository.findAll();
        assertThat(tipologiaVeicoloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipologiaVeicolo() throws Exception {
        // Initialize the database
        tipologiaVeicoloService.save(tipologiaVeicolo);

        int databaseSizeBeforeDelete = tipologiaVeicoloRepository.findAll().size();

        // Delete the tipologiaVeicolo
        restTipologiaVeicoloMockMvc
            .perform(delete("/api/tipologia-veicolos/{id}", tipologiaVeicolo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipologiaVeicolo> tipologiaVeicoloList = tipologiaVeicoloRepository.findAll();
        assertThat(tipologiaVeicoloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
