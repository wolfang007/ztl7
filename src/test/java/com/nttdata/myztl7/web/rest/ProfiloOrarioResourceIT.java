package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.ProfiloOrario;
import com.nttdata.myztl7.domain.RegolaOraria;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.repository.ProfiloOrarioRepository;
import com.nttdata.myztl7.service.ProfiloOrarioQueryService;
import com.nttdata.myztl7.service.ProfiloOrarioService;
import com.nttdata.myztl7.service.dto.ProfiloOrarioCriteria;
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
 * Integration tests for the {@link ProfiloOrarioResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfiloOrarioResourceIT {
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final EntityStatus DEFAULT_STATO = EntityStatus.ATTIVO;
    private static final EntityStatus UPDATED_STATO = EntityStatus.DISATTIVO;

    @Autowired
    private ProfiloOrarioRepository profiloOrarioRepository;

    @Mock
    private ProfiloOrarioRepository profiloOrarioRepositoryMock;

    @Mock
    private ProfiloOrarioService profiloOrarioServiceMock;

    @Autowired
    private ProfiloOrarioService profiloOrarioService;

    @Autowired
    private ProfiloOrarioQueryService profiloOrarioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfiloOrarioMockMvc;

    private ProfiloOrario profiloOrario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfiloOrario createEntity(EntityManager em) {
        ProfiloOrario profiloOrario = new ProfiloOrario().nome(DEFAULT_NOME).stato(DEFAULT_STATO);
        return profiloOrario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfiloOrario createUpdatedEntity(EntityManager em) {
        ProfiloOrario profiloOrario = new ProfiloOrario().nome(UPDATED_NOME).stato(UPDATED_STATO);
        return profiloOrario;
    }

    @BeforeEach
    public void initTest() {
        profiloOrario = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfiloOrario() throws Exception {
        int databaseSizeBeforeCreate = profiloOrarioRepository.findAll().size();
        // Create the ProfiloOrario
        restProfiloOrarioMockMvc
            .perform(
                post("/api/profilo-orarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profiloOrario))
            )
            .andExpect(status().isCreated());

        // Validate the ProfiloOrario in the database
        List<ProfiloOrario> profiloOrarioList = profiloOrarioRepository.findAll();
        assertThat(profiloOrarioList).hasSize(databaseSizeBeforeCreate + 1);
        ProfiloOrario testProfiloOrario = profiloOrarioList.get(profiloOrarioList.size() - 1);
        assertThat(testProfiloOrario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProfiloOrario.getStato()).isEqualTo(DEFAULT_STATO);
    }

    @Test
    @Transactional
    public void createProfiloOrarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profiloOrarioRepository.findAll().size();

        // Create the ProfiloOrario with an existing ID
        profiloOrario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfiloOrarioMockMvc
            .perform(
                post("/api/profilo-orarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profiloOrario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfiloOrario in the database
        List<ProfiloOrario> profiloOrarioList = profiloOrarioRepository.findAll();
        assertThat(profiloOrarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = profiloOrarioRepository.findAll().size();
        // set the field null
        profiloOrario.setNome(null);

        // Create the ProfiloOrario, which fails.

        restProfiloOrarioMockMvc
            .perform(
                post("/api/profilo-orarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profiloOrario))
            )
            .andExpect(status().isBadRequest());

        List<ProfiloOrario> profiloOrarioList = profiloOrarioRepository.findAll();
        assertThat(profiloOrarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = profiloOrarioRepository.findAll().size();
        // set the field null
        profiloOrario.setStato(null);

        // Create the ProfiloOrario, which fails.

        restProfiloOrarioMockMvc
            .perform(
                post("/api/profilo-orarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profiloOrario))
            )
            .andExpect(status().isBadRequest());

        List<ProfiloOrario> profiloOrarioList = profiloOrarioRepository.findAll();
        assertThat(profiloOrarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfiloOrarios() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList
        restProfiloOrarioMockMvc
            .perform(get("/api/profilo-orarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profiloOrario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllProfiloOrariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(profiloOrarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfiloOrarioMockMvc.perform(get("/api/profilo-orarios?eagerload=true")).andExpect(status().isOk());

        verify(profiloOrarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllProfiloOrariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(profiloOrarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfiloOrarioMockMvc.perform(get("/api/profilo-orarios?eagerload=true")).andExpect(status().isOk());

        verify(profiloOrarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProfiloOrario() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get the profiloOrario
        restProfiloOrarioMockMvc
            .perform(get("/api/profilo-orarios/{id}", profiloOrario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profiloOrario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.stato").value(DEFAULT_STATO.toString()));
    }

    @Test
    @Transactional
    public void getProfiloOrariosByIdFiltering() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        Long id = profiloOrario.getId();

        defaultProfiloOrarioShouldBeFound("id.equals=" + id);
        defaultProfiloOrarioShouldNotBeFound("id.notEquals=" + id);

        defaultProfiloOrarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProfiloOrarioShouldNotBeFound("id.greaterThan=" + id);

        defaultProfiloOrarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProfiloOrarioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where nome equals to DEFAULT_NOME
        defaultProfiloOrarioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the profiloOrarioList where nome equals to UPDATED_NOME
        defaultProfiloOrarioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where nome not equals to DEFAULT_NOME
        defaultProfiloOrarioShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the profiloOrarioList where nome not equals to UPDATED_NOME
        defaultProfiloOrarioShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultProfiloOrarioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the profiloOrarioList where nome equals to UPDATED_NOME
        defaultProfiloOrarioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where nome is not null
        defaultProfiloOrarioShouldBeFound("nome.specified=true");

        // Get all the profiloOrarioList where nome is null
        defaultProfiloOrarioShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByNomeContainsSomething() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where nome contains DEFAULT_NOME
        defaultProfiloOrarioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the profiloOrarioList where nome contains UPDATED_NOME
        defaultProfiloOrarioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where nome does not contain DEFAULT_NOME
        defaultProfiloOrarioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the profiloOrarioList where nome does not contain UPDATED_NOME
        defaultProfiloOrarioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByStatoIsEqualToSomething() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where stato equals to DEFAULT_STATO
        defaultProfiloOrarioShouldBeFound("stato.equals=" + DEFAULT_STATO);

        // Get all the profiloOrarioList where stato equals to UPDATED_STATO
        defaultProfiloOrarioShouldNotBeFound("stato.equals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByStatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where stato not equals to DEFAULT_STATO
        defaultProfiloOrarioShouldNotBeFound("stato.notEquals=" + DEFAULT_STATO);

        // Get all the profiloOrarioList where stato not equals to UPDATED_STATO
        defaultProfiloOrarioShouldBeFound("stato.notEquals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByStatoIsInShouldWork() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where stato in DEFAULT_STATO or UPDATED_STATO
        defaultProfiloOrarioShouldBeFound("stato.in=" + DEFAULT_STATO + "," + UPDATED_STATO);

        // Get all the profiloOrarioList where stato equals to UPDATED_STATO
        defaultProfiloOrarioShouldNotBeFound("stato.in=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByStatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);

        // Get all the profiloOrarioList where stato is not null
        defaultProfiloOrarioShouldBeFound("stato.specified=true");

        // Get all the profiloOrarioList where stato is null
        defaultProfiloOrarioShouldNotBeFound("stato.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfiloOrariosByRegolaOrariaIsEqualToSomething() throws Exception {
        // Initialize the database
        profiloOrarioRepository.saveAndFlush(profiloOrario);
        RegolaOraria regolaOraria = RegolaOrariaResourceIT.createEntity(em);
        em.persist(regolaOraria);
        em.flush();
        profiloOrario.addRegolaOraria(regolaOraria);
        profiloOrarioRepository.saveAndFlush(profiloOrario);
        Long regolaOrariaId = regolaOraria.getId();

        // Get all the profiloOrarioList where regolaOraria equals to regolaOrariaId
        defaultProfiloOrarioShouldBeFound("regolaOrariaId.equals=" + regolaOrariaId);

        // Get all the profiloOrarioList where regolaOraria equals to regolaOrariaId + 1
        defaultProfiloOrarioShouldNotBeFound("regolaOrariaId.equals=" + (regolaOrariaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProfiloOrarioShouldBeFound(String filter) throws Exception {
        restProfiloOrarioMockMvc
            .perform(get("/api/profilo-orarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profiloOrario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));

        // Check, that the count call also returns 1
        restProfiloOrarioMockMvc
            .perform(get("/api/profilo-orarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProfiloOrarioShouldNotBeFound(String filter) throws Exception {
        restProfiloOrarioMockMvc
            .perform(get("/api/profilo-orarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfiloOrarioMockMvc
            .perform(get("/api/profilo-orarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProfiloOrario() throws Exception {
        // Get the profiloOrario
        restProfiloOrarioMockMvc.perform(get("/api/profilo-orarios/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfiloOrario() throws Exception {
        // Initialize the database
        profiloOrarioService.save(profiloOrario);

        int databaseSizeBeforeUpdate = profiloOrarioRepository.findAll().size();

        // Update the profiloOrario
        ProfiloOrario updatedProfiloOrario = profiloOrarioRepository.findById(profiloOrario.getId()).get();
        // Disconnect from session so that the updates on updatedProfiloOrario are not directly saved in db
        em.detach(updatedProfiloOrario);
        updatedProfiloOrario.nome(UPDATED_NOME).stato(UPDATED_STATO);

        restProfiloOrarioMockMvc
            .perform(
                put("/api/profilo-orarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProfiloOrario))
            )
            .andExpect(status().isOk());

        // Validate the ProfiloOrario in the database
        List<ProfiloOrario> profiloOrarioList = profiloOrarioRepository.findAll();
        assertThat(profiloOrarioList).hasSize(databaseSizeBeforeUpdate);
        ProfiloOrario testProfiloOrario = profiloOrarioList.get(profiloOrarioList.size() - 1);
        assertThat(testProfiloOrario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProfiloOrario.getStato()).isEqualTo(UPDATED_STATO);
    }

    @Test
    @Transactional
    public void updateNonExistingProfiloOrario() throws Exception {
        int databaseSizeBeforeUpdate = profiloOrarioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfiloOrarioMockMvc
            .perform(
                put("/api/profilo-orarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profiloOrario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfiloOrario in the database
        List<ProfiloOrario> profiloOrarioList = profiloOrarioRepository.findAll();
        assertThat(profiloOrarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfiloOrario() throws Exception {
        // Initialize the database
        profiloOrarioService.save(profiloOrario);

        int databaseSizeBeforeDelete = profiloOrarioRepository.findAll().size();

        // Delete the profiloOrario
        restProfiloOrarioMockMvc
            .perform(delete("/api/profilo-orarios/{id}", profiloOrario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfiloOrario> profiloOrarioList = profiloOrarioRepository.findAll();
        assertThat(profiloOrarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
