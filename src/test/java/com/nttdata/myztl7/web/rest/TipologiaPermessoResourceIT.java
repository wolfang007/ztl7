package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.TipologiaPermesso;
import com.nttdata.myztl7.repository.TipologiaPermessoRepository;
import com.nttdata.myztl7.service.TipologiaPermessoQueryService;
import com.nttdata.myztl7.service.TipologiaPermessoService;
import com.nttdata.myztl7.service.dto.TipologiaPermessoCriteria;
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
 * Integration tests for the {@link TipologiaPermessoResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipologiaPermessoResourceIT {
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CODICE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE = "BBBBBBBBBB";

    @Autowired
    private TipologiaPermessoRepository tipologiaPermessoRepository;

    @Autowired
    private TipologiaPermessoService tipologiaPermessoService;

    @Autowired
    private TipologiaPermessoQueryService tipologiaPermessoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipologiaPermessoMockMvc;

    private TipologiaPermesso tipologiaPermesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipologiaPermesso createEntity(EntityManager em) {
        TipologiaPermesso tipologiaPermesso = new TipologiaPermesso().nome(DEFAULT_NOME).codice(DEFAULT_CODICE);
        return tipologiaPermesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipologiaPermesso createUpdatedEntity(EntityManager em) {
        TipologiaPermesso tipologiaPermesso = new TipologiaPermesso().nome(UPDATED_NOME).codice(UPDATED_CODICE);
        return tipologiaPermesso;
    }

    @BeforeEach
    public void initTest() {
        tipologiaPermesso = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipologiaPermesso() throws Exception {
        int databaseSizeBeforeCreate = tipologiaPermessoRepository.findAll().size();
        // Create the TipologiaPermesso
        restTipologiaPermessoMockMvc
            .perform(
                post("/api/tipologia-permessos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaPermesso))
            )
            .andExpect(status().isCreated());

        // Validate the TipologiaPermesso in the database
        List<TipologiaPermesso> tipologiaPermessoList = tipologiaPermessoRepository.findAll();
        assertThat(tipologiaPermessoList).hasSize(databaseSizeBeforeCreate + 1);
        TipologiaPermesso testTipologiaPermesso = tipologiaPermessoList.get(tipologiaPermessoList.size() - 1);
        assertThat(testTipologiaPermesso.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipologiaPermesso.getCodice()).isEqualTo(DEFAULT_CODICE);
    }

    @Test
    @Transactional
    public void createTipologiaPermessoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipologiaPermessoRepository.findAll().size();

        // Create the TipologiaPermesso with an existing ID
        tipologiaPermesso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipologiaPermessoMockMvc
            .perform(
                post("/api/tipologia-permessos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaPermesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipologiaPermesso in the database
        List<TipologiaPermesso> tipologiaPermessoList = tipologiaPermessoRepository.findAll();
        assertThat(tipologiaPermessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipologiaPermessoRepository.findAll().size();
        // set the field null
        tipologiaPermesso.setNome(null);

        // Create the TipologiaPermesso, which fails.

        restTipologiaPermessoMockMvc
            .perform(
                post("/api/tipologia-permessos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaPermesso))
            )
            .andExpect(status().isBadRequest());

        List<TipologiaPermesso> tipologiaPermessoList = tipologiaPermessoRepository.findAll();
        assertThat(tipologiaPermessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipologiaPermessoRepository.findAll().size();
        // set the field null
        tipologiaPermesso.setCodice(null);

        // Create the TipologiaPermesso, which fails.

        restTipologiaPermessoMockMvc
            .perform(
                post("/api/tipologia-permessos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaPermesso))
            )
            .andExpect(status().isBadRequest());

        List<TipologiaPermesso> tipologiaPermessoList = tipologiaPermessoRepository.findAll();
        assertThat(tipologiaPermessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessos() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList
        restTipologiaPermessoMockMvc
            .perform(get("/api/tipologia-permessos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipologiaPermesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codice").value(hasItem(DEFAULT_CODICE)));
    }

    @Test
    @Transactional
    public void getTipologiaPermesso() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get the tipologiaPermesso
        restTipologiaPermessoMockMvc
            .perform(get("/api/tipologia-permessos/{id}", tipologiaPermesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipologiaPermesso.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.codice").value(DEFAULT_CODICE));
    }

    @Test
    @Transactional
    public void getTipologiaPermessosByIdFiltering() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        Long id = tipologiaPermesso.getId();

        defaultTipologiaPermessoShouldBeFound("id.equals=" + id);
        defaultTipologiaPermessoShouldNotBeFound("id.notEquals=" + id);

        defaultTipologiaPermessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipologiaPermessoShouldNotBeFound("id.greaterThan=" + id);

        defaultTipologiaPermessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipologiaPermessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where nome equals to DEFAULT_NOME
        defaultTipologiaPermessoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the tipologiaPermessoList where nome equals to UPDATED_NOME
        defaultTipologiaPermessoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where nome not equals to DEFAULT_NOME
        defaultTipologiaPermessoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the tipologiaPermessoList where nome not equals to UPDATED_NOME
        defaultTipologiaPermessoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultTipologiaPermessoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the tipologiaPermessoList where nome equals to UPDATED_NOME
        defaultTipologiaPermessoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where nome is not null
        defaultTipologiaPermessoShouldBeFound("nome.specified=true");

        // Get all the tipologiaPermessoList where nome is null
        defaultTipologiaPermessoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByNomeContainsSomething() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where nome contains DEFAULT_NOME
        defaultTipologiaPermessoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the tipologiaPermessoList where nome contains UPDATED_NOME
        defaultTipologiaPermessoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where nome does not contain DEFAULT_NOME
        defaultTipologiaPermessoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the tipologiaPermessoList where nome does not contain UPDATED_NOME
        defaultTipologiaPermessoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByCodiceIsEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where codice equals to DEFAULT_CODICE
        defaultTipologiaPermessoShouldBeFound("codice.equals=" + DEFAULT_CODICE);

        // Get all the tipologiaPermessoList where codice equals to UPDATED_CODICE
        defaultTipologiaPermessoShouldNotBeFound("codice.equals=" + UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByCodiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where codice not equals to DEFAULT_CODICE
        defaultTipologiaPermessoShouldNotBeFound("codice.notEquals=" + DEFAULT_CODICE);

        // Get all the tipologiaPermessoList where codice not equals to UPDATED_CODICE
        defaultTipologiaPermessoShouldBeFound("codice.notEquals=" + UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByCodiceIsInShouldWork() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where codice in DEFAULT_CODICE or UPDATED_CODICE
        defaultTipologiaPermessoShouldBeFound("codice.in=" + DEFAULT_CODICE + "," + UPDATED_CODICE);

        // Get all the tipologiaPermessoList where codice equals to UPDATED_CODICE
        defaultTipologiaPermessoShouldNotBeFound("codice.in=" + UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByCodiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where codice is not null
        defaultTipologiaPermessoShouldBeFound("codice.specified=true");

        // Get all the tipologiaPermessoList where codice is null
        defaultTipologiaPermessoShouldNotBeFound("codice.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByCodiceContainsSomething() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where codice contains DEFAULT_CODICE
        defaultTipologiaPermessoShouldBeFound("codice.contains=" + DEFAULT_CODICE);

        // Get all the tipologiaPermessoList where codice contains UPDATED_CODICE
        defaultTipologiaPermessoShouldNotBeFound("codice.contains=" + UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void getAllTipologiaPermessosByCodiceNotContainsSomething() throws Exception {
        // Initialize the database
        tipologiaPermessoRepository.saveAndFlush(tipologiaPermesso);

        // Get all the tipologiaPermessoList where codice does not contain DEFAULT_CODICE
        defaultTipologiaPermessoShouldNotBeFound("codice.doesNotContain=" + DEFAULT_CODICE);

        // Get all the tipologiaPermessoList where codice does not contain UPDATED_CODICE
        defaultTipologiaPermessoShouldBeFound("codice.doesNotContain=" + UPDATED_CODICE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipologiaPermessoShouldBeFound(String filter) throws Exception {
        restTipologiaPermessoMockMvc
            .perform(get("/api/tipologia-permessos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipologiaPermesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codice").value(hasItem(DEFAULT_CODICE)));

        // Check, that the count call also returns 1
        restTipologiaPermessoMockMvc
            .perform(get("/api/tipologia-permessos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipologiaPermessoShouldNotBeFound(String filter) throws Exception {
        restTipologiaPermessoMockMvc
            .perform(get("/api/tipologia-permessos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipologiaPermessoMockMvc
            .perform(get("/api/tipologia-permessos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipologiaPermesso() throws Exception {
        // Get the tipologiaPermesso
        restTipologiaPermessoMockMvc.perform(get("/api/tipologia-permessos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipologiaPermesso() throws Exception {
        // Initialize the database
        tipologiaPermessoService.save(tipologiaPermesso);

        int databaseSizeBeforeUpdate = tipologiaPermessoRepository.findAll().size();

        // Update the tipologiaPermesso
        TipologiaPermesso updatedTipologiaPermesso = tipologiaPermessoRepository.findById(tipologiaPermesso.getId()).get();
        // Disconnect from session so that the updates on updatedTipologiaPermesso are not directly saved in db
        em.detach(updatedTipologiaPermesso);
        updatedTipologiaPermesso.nome(UPDATED_NOME).codice(UPDATED_CODICE);

        restTipologiaPermessoMockMvc
            .perform(
                put("/api/tipologia-permessos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipologiaPermesso))
            )
            .andExpect(status().isOk());

        // Validate the TipologiaPermesso in the database
        List<TipologiaPermesso> tipologiaPermessoList = tipologiaPermessoRepository.findAll();
        assertThat(tipologiaPermessoList).hasSize(databaseSizeBeforeUpdate);
        TipologiaPermesso testTipologiaPermesso = tipologiaPermessoList.get(tipologiaPermessoList.size() - 1);
        assertThat(testTipologiaPermesso.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipologiaPermesso.getCodice()).isEqualTo(UPDATED_CODICE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipologiaPermesso() throws Exception {
        int databaseSizeBeforeUpdate = tipologiaPermessoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipologiaPermessoMockMvc
            .perform(
                put("/api/tipologia-permessos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipologiaPermesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipologiaPermesso in the database
        List<TipologiaPermesso> tipologiaPermessoList = tipologiaPermessoRepository.findAll();
        assertThat(tipologiaPermessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipologiaPermesso() throws Exception {
        // Initialize the database
        tipologiaPermessoService.save(tipologiaPermesso);

        int databaseSizeBeforeDelete = tipologiaPermessoRepository.findAll().size();

        // Delete the tipologiaPermesso
        restTipologiaPermessoMockMvc
            .perform(delete("/api/tipologia-permessos/{id}", tipologiaPermesso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipologiaPermesso> tipologiaPermessoList = tipologiaPermessoRepository.findAll();
        assertThat(tipologiaPermessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
