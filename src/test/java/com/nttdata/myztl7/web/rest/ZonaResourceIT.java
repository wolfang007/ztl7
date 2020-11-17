package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.GruppoVarchi;
import com.nttdata.myztl7.domain.ProfiloOrario;
import com.nttdata.myztl7.domain.TipologiaZona;
import com.nttdata.myztl7.domain.Zona;
import com.nttdata.myztl7.domain.enumeration.EntityStatus;
import com.nttdata.myztl7.repository.ZonaRepository;
import com.nttdata.myztl7.service.ZonaQueryService;
import com.nttdata.myztl7.service.ZonaService;
import com.nttdata.myztl7.service.dto.ZonaCriteria;
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
 * Integration tests for the {@link ZonaResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ZonaResourceIT {
    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final EntityStatus DEFAULT_STATO = EntityStatus.ATTIVO;
    private static final EntityStatus UPDATED_STATO = EntityStatus.DISATTIVO;

    @Autowired
    private ZonaRepository zonaRepository;

    @Mock
    private ZonaRepository zonaRepositoryMock;

    @Mock
    private ZonaService zonaServiceMock;

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private ZonaQueryService zonaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZonaMockMvc;

    private Zona zona;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zona createEntity(EntityManager em) {
        Zona zona = new Zona().nome(DEFAULT_NOME).stato(DEFAULT_STATO);
        return zona;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zona createUpdatedEntity(EntityManager em) {
        Zona zona = new Zona().nome(UPDATED_NOME).stato(UPDATED_STATO);
        return zona;
    }

    @BeforeEach
    public void initTest() {
        zona = createEntity(em);
    }

    @Test
    @Transactional
    public void createZona() throws Exception {
        int databaseSizeBeforeCreate = zonaRepository.findAll().size();
        // Create the Zona
        restZonaMockMvc
            .perform(post("/api/zonas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zona)))
            .andExpect(status().isCreated());

        // Validate the Zona in the database
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeCreate + 1);
        Zona testZona = zonaList.get(zonaList.size() - 1);
        assertThat(testZona.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testZona.getStato()).isEqualTo(DEFAULT_STATO);
    }

    @Test
    @Transactional
    public void createZonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zonaRepository.findAll().size();

        // Create the Zona with an existing ID
        zona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZonaMockMvc
            .perform(post("/api/zonas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zona)))
            .andExpect(status().isBadRequest());

        // Validate the Zona in the database
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = zonaRepository.findAll().size();
        // set the field null
        zona.setNome(null);

        // Create the Zona, which fails.

        restZonaMockMvc
            .perform(post("/api/zonas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zona)))
            .andExpect(status().isBadRequest());

        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = zonaRepository.findAll().size();
        // set the field null
        zona.setStato(null);

        // Create the Zona, which fails.

        restZonaMockMvc
            .perform(post("/api/zonas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zona)))
            .andExpect(status().isBadRequest());

        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZonas() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList
        restZonaMockMvc
            .perform(get("/api/zonas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllZonasWithEagerRelationshipsIsEnabled() throws Exception {
        when(zonaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restZonaMockMvc.perform(get("/api/zonas?eagerload=true")).andExpect(status().isOk());

        verify(zonaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllZonasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(zonaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restZonaMockMvc.perform(get("/api/zonas?eagerload=true")).andExpect(status().isOk());

        verify(zonaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getZona() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get the zona
        restZonaMockMvc
            .perform(get("/api/zonas/{id}", zona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zona.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.stato").value(DEFAULT_STATO.toString()));
    }

    @Test
    @Transactional
    public void getZonasByIdFiltering() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        Long id = zona.getId();

        defaultZonaShouldBeFound("id.equals=" + id);
        defaultZonaShouldNotBeFound("id.notEquals=" + id);

        defaultZonaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultZonaShouldNotBeFound("id.greaterThan=" + id);

        defaultZonaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultZonaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllZonasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where nome equals to DEFAULT_NOME
        defaultZonaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the zonaList where nome equals to UPDATED_NOME
        defaultZonaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllZonasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where nome not equals to DEFAULT_NOME
        defaultZonaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the zonaList where nome not equals to UPDATED_NOME
        defaultZonaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllZonasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultZonaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the zonaList where nome equals to UPDATED_NOME
        defaultZonaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllZonasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where nome is not null
        defaultZonaShouldBeFound("nome.specified=true");

        // Get all the zonaList where nome is null
        defaultZonaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllZonasByNomeContainsSomething() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where nome contains DEFAULT_NOME
        defaultZonaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the zonaList where nome contains UPDATED_NOME
        defaultZonaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllZonasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where nome does not contain DEFAULT_NOME
        defaultZonaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the zonaList where nome does not contain UPDATED_NOME
        defaultZonaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllZonasByStatoIsEqualToSomething() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where stato equals to DEFAULT_STATO
        defaultZonaShouldBeFound("stato.equals=" + DEFAULT_STATO);

        // Get all the zonaList where stato equals to UPDATED_STATO
        defaultZonaShouldNotBeFound("stato.equals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllZonasByStatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where stato not equals to DEFAULT_STATO
        defaultZonaShouldNotBeFound("stato.notEquals=" + DEFAULT_STATO);

        // Get all the zonaList where stato not equals to UPDATED_STATO
        defaultZonaShouldBeFound("stato.notEquals=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllZonasByStatoIsInShouldWork() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where stato in DEFAULT_STATO or UPDATED_STATO
        defaultZonaShouldBeFound("stato.in=" + DEFAULT_STATO + "," + UPDATED_STATO);

        // Get all the zonaList where stato equals to UPDATED_STATO
        defaultZonaShouldNotBeFound("stato.in=" + UPDATED_STATO);
    }

    @Test
    @Transactional
    public void getAllZonasByStatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList where stato is not null
        defaultZonaShouldBeFound("stato.specified=true");

        // Get all the zonaList where stato is null
        defaultZonaShouldNotBeFound("stato.specified=false");
    }

    @Test
    @Transactional
    public void getAllZonasByProfiloOrarioIsEqualToSomething() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);
        ProfiloOrario profiloOrario = ProfiloOrarioResourceIT.createEntity(em);
        em.persist(profiloOrario);
        em.flush();
        zona.setProfiloOrario(profiloOrario);
        zonaRepository.saveAndFlush(zona);
        Long profiloOrarioId = profiloOrario.getId();

        // Get all the zonaList where profiloOrario equals to profiloOrarioId
        defaultZonaShouldBeFound("profiloOrarioId.equals=" + profiloOrarioId);

        // Get all the zonaList where profiloOrario equals to profiloOrarioId + 1
        defaultZonaShouldNotBeFound("profiloOrarioId.equals=" + (profiloOrarioId + 1));
    }

    @Test
    @Transactional
    public void getAllZonasByTipologiaZonaIsEqualToSomething() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);
        TipologiaZona tipologiaZona = TipologiaZonaResourceIT.createEntity(em);
        em.persist(tipologiaZona);
        em.flush();
        zona.setTipologiaZona(tipologiaZona);
        zonaRepository.saveAndFlush(zona);
        Long tipologiaZonaId = tipologiaZona.getId();

        // Get all the zonaList where tipologiaZona equals to tipologiaZonaId
        defaultZonaShouldBeFound("tipologiaZonaId.equals=" + tipologiaZonaId);

        // Get all the zonaList where tipologiaZona equals to tipologiaZonaId + 1
        defaultZonaShouldNotBeFound("tipologiaZonaId.equals=" + (tipologiaZonaId + 1));
    }

    @Test
    @Transactional
    public void getAllZonasByGruppoVarchiIsEqualToSomething() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);
        GruppoVarchi gruppoVarchi = GruppoVarchiResourceIT.createEntity(em);
        em.persist(gruppoVarchi);
        em.flush();
        zona.addGruppoVarchi(gruppoVarchi);
        zonaRepository.saveAndFlush(zona);
        Long gruppoVarchiId = gruppoVarchi.getId();

        // Get all the zonaList where gruppoVarchi equals to gruppoVarchiId
        defaultZonaShouldBeFound("gruppoVarchiId.equals=" + gruppoVarchiId);

        // Get all the zonaList where gruppoVarchi equals to gruppoVarchiId + 1
        defaultZonaShouldNotBeFound("gruppoVarchiId.equals=" + (gruppoVarchiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultZonaShouldBeFound(String filter) throws Exception {
        restZonaMockMvc
            .perform(get("/api/zonas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())));

        // Check, that the count call also returns 1
        restZonaMockMvc
            .perform(get("/api/zonas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultZonaShouldNotBeFound(String filter) throws Exception {
        restZonaMockMvc
            .perform(get("/api/zonas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restZonaMockMvc
            .perform(get("/api/zonas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingZona() throws Exception {
        // Get the zona
        restZonaMockMvc.perform(get("/api/zonas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZona() throws Exception {
        // Initialize the database
        zonaService.save(zona);

        int databaseSizeBeforeUpdate = zonaRepository.findAll().size();

        // Update the zona
        Zona updatedZona = zonaRepository.findById(zona.getId()).get();
        // Disconnect from session so that the updates on updatedZona are not directly saved in db
        em.detach(updatedZona);
        updatedZona.nome(UPDATED_NOME).stato(UPDATED_STATO);

        restZonaMockMvc
            .perform(put("/api/zonas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedZona)))
            .andExpect(status().isOk());

        // Validate the Zona in the database
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeUpdate);
        Zona testZona = zonaList.get(zonaList.size() - 1);
        assertThat(testZona.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testZona.getStato()).isEqualTo(UPDATED_STATO);
    }

    @Test
    @Transactional
    public void updateNonExistingZona() throws Exception {
        int databaseSizeBeforeUpdate = zonaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZonaMockMvc
            .perform(put("/api/zonas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zona)))
            .andExpect(status().isBadRequest());

        // Validate the Zona in the database
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZona() throws Exception {
        // Initialize the database
        zonaService.save(zona);

        int databaseSizeBeforeDelete = zonaRepository.findAll().size();

        // Delete the zona
        restZonaMockMvc
            .perform(delete("/api/zonas/{id}", zona.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
