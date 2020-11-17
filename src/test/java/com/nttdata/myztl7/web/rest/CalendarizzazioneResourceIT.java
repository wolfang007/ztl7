package com.nttdata.myztl7.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.Calendarizzazione;
import com.nttdata.myztl7.repository.CalendarizzazioneRepository;
import com.nttdata.myztl7.service.CalendarizzazioneQueryService;
import com.nttdata.myztl7.service.CalendarizzazioneService;
import com.nttdata.myztl7.service.dto.CalendarizzazioneCriteria;
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
 * Integration tests for the {@link CalendarizzazioneResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CalendarizzazioneResourceIT {
    private static final Boolean DEFAULT_LUNEDI = false;
    private static final Boolean UPDATED_LUNEDI = true;

    private static final Boolean DEFAULT_MARTEDI = false;
    private static final Boolean UPDATED_MARTEDI = true;

    private static final Boolean DEFAULT_MERCOLEDI = false;
    private static final Boolean UPDATED_MERCOLEDI = true;

    private static final Boolean DEFAULT_GIOVEDI = false;
    private static final Boolean UPDATED_GIOVEDI = true;

    private static final Boolean DEFAULT_VENERDI = false;
    private static final Boolean UPDATED_VENERDI = true;

    private static final Boolean DEFAULT_SABATO = false;
    private static final Boolean UPDATED_SABATO = true;

    private static final Boolean DEFAULT_DOMENICA = false;
    private static final Boolean UPDATED_DOMENICA = true;

    private static final Boolean DEFAULT_SI_RIPETE = false;
    private static final Boolean UPDATED_SI_RIPETE = true;

    private static final Boolean DEFAULT_FESTIVI = false;
    private static final Boolean UPDATED_FESTIVI = true;

    @Autowired
    private CalendarizzazioneRepository calendarizzazioneRepository;

    @Autowired
    private CalendarizzazioneService calendarizzazioneService;

    @Autowired
    private CalendarizzazioneQueryService calendarizzazioneQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalendarizzazioneMockMvc;

    private Calendarizzazione calendarizzazione;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendarizzazione createEntity(EntityManager em) {
        Calendarizzazione calendarizzazione = new Calendarizzazione()
            .lunedi(DEFAULT_LUNEDI)
            .martedi(DEFAULT_MARTEDI)
            .mercoledi(DEFAULT_MERCOLEDI)
            .giovedi(DEFAULT_GIOVEDI)
            .venerdi(DEFAULT_VENERDI)
            .sabato(DEFAULT_SABATO)
            .domenica(DEFAULT_DOMENICA)
            .siRipete(DEFAULT_SI_RIPETE)
            .festivi(DEFAULT_FESTIVI);
        return calendarizzazione;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendarizzazione createUpdatedEntity(EntityManager em) {
        Calendarizzazione calendarizzazione = new Calendarizzazione()
            .lunedi(UPDATED_LUNEDI)
            .martedi(UPDATED_MARTEDI)
            .mercoledi(UPDATED_MERCOLEDI)
            .giovedi(UPDATED_GIOVEDI)
            .venerdi(UPDATED_VENERDI)
            .sabato(UPDATED_SABATO)
            .domenica(UPDATED_DOMENICA)
            .siRipete(UPDATED_SI_RIPETE)
            .festivi(UPDATED_FESTIVI);
        return calendarizzazione;
    }

    @BeforeEach
    public void initTest() {
        calendarizzazione = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendarizzazione() throws Exception {
        int databaseSizeBeforeCreate = calendarizzazioneRepository.findAll().size();
        // Create the Calendarizzazione
        restCalendarizzazioneMockMvc
            .perform(
                post("/api/calendarizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarizzazione))
            )
            .andExpect(status().isCreated());

        // Validate the Calendarizzazione in the database
        List<Calendarizzazione> calendarizzazioneList = calendarizzazioneRepository.findAll();
        assertThat(calendarizzazioneList).hasSize(databaseSizeBeforeCreate + 1);
        Calendarizzazione testCalendarizzazione = calendarizzazioneList.get(calendarizzazioneList.size() - 1);
        assertThat(testCalendarizzazione.isLunedi()).isEqualTo(DEFAULT_LUNEDI);
        assertThat(testCalendarizzazione.isMartedi()).isEqualTo(DEFAULT_MARTEDI);
        assertThat(testCalendarizzazione.isMercoledi()).isEqualTo(DEFAULT_MERCOLEDI);
        assertThat(testCalendarizzazione.isGiovedi()).isEqualTo(DEFAULT_GIOVEDI);
        assertThat(testCalendarizzazione.isVenerdi()).isEqualTo(DEFAULT_VENERDI);
        assertThat(testCalendarizzazione.isSabato()).isEqualTo(DEFAULT_SABATO);
        assertThat(testCalendarizzazione.isDomenica()).isEqualTo(DEFAULT_DOMENICA);
        assertThat(testCalendarizzazione.isSiRipete()).isEqualTo(DEFAULT_SI_RIPETE);
        assertThat(testCalendarizzazione.isFestivi()).isEqualTo(DEFAULT_FESTIVI);
    }

    @Test
    @Transactional
    public void createCalendarizzazioneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarizzazioneRepository.findAll().size();

        // Create the Calendarizzazione with an existing ID
        calendarizzazione.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarizzazioneMockMvc
            .perform(
                post("/api/calendarizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarizzazione))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendarizzazione in the database
        List<Calendarizzazione> calendarizzazioneList = calendarizzazioneRepository.findAll();
        assertThat(calendarizzazioneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCalendarizzaziones() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList
        restCalendarizzazioneMockMvc
            .perform(get("/api/calendarizzaziones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarizzazione.getId().intValue())))
            .andExpect(jsonPath("$.[*].lunedi").value(hasItem(DEFAULT_LUNEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].martedi").value(hasItem(DEFAULT_MARTEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].mercoledi").value(hasItem(DEFAULT_MERCOLEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].giovedi").value(hasItem(DEFAULT_GIOVEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].venerdi").value(hasItem(DEFAULT_VENERDI.booleanValue())))
            .andExpect(jsonPath("$.[*].sabato").value(hasItem(DEFAULT_SABATO.booleanValue())))
            .andExpect(jsonPath("$.[*].domenica").value(hasItem(DEFAULT_DOMENICA.booleanValue())))
            .andExpect(jsonPath("$.[*].siRipete").value(hasItem(DEFAULT_SI_RIPETE.booleanValue())))
            .andExpect(jsonPath("$.[*].festivi").value(hasItem(DEFAULT_FESTIVI.booleanValue())));
    }

    @Test
    @Transactional
    public void getCalendarizzazione() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get the calendarizzazione
        restCalendarizzazioneMockMvc
            .perform(get("/api/calendarizzaziones/{id}", calendarizzazione.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendarizzazione.getId().intValue()))
            .andExpect(jsonPath("$.lunedi").value(DEFAULT_LUNEDI.booleanValue()))
            .andExpect(jsonPath("$.martedi").value(DEFAULT_MARTEDI.booleanValue()))
            .andExpect(jsonPath("$.mercoledi").value(DEFAULT_MERCOLEDI.booleanValue()))
            .andExpect(jsonPath("$.giovedi").value(DEFAULT_GIOVEDI.booleanValue()))
            .andExpect(jsonPath("$.venerdi").value(DEFAULT_VENERDI.booleanValue()))
            .andExpect(jsonPath("$.sabato").value(DEFAULT_SABATO.booleanValue()))
            .andExpect(jsonPath("$.domenica").value(DEFAULT_DOMENICA.booleanValue()))
            .andExpect(jsonPath("$.siRipete").value(DEFAULT_SI_RIPETE.booleanValue()))
            .andExpect(jsonPath("$.festivi").value(DEFAULT_FESTIVI.booleanValue()));
    }

    @Test
    @Transactional
    public void getCalendarizzazionesByIdFiltering() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        Long id = calendarizzazione.getId();

        defaultCalendarizzazioneShouldBeFound("id.equals=" + id);
        defaultCalendarizzazioneShouldNotBeFound("id.notEquals=" + id);

        defaultCalendarizzazioneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCalendarizzazioneShouldNotBeFound("id.greaterThan=" + id);

        defaultCalendarizzazioneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCalendarizzazioneShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByLunediIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where lunedi equals to DEFAULT_LUNEDI
        defaultCalendarizzazioneShouldBeFound("lunedi.equals=" + DEFAULT_LUNEDI);

        // Get all the calendarizzazioneList where lunedi equals to UPDATED_LUNEDI
        defaultCalendarizzazioneShouldNotBeFound("lunedi.equals=" + UPDATED_LUNEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByLunediIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where lunedi not equals to DEFAULT_LUNEDI
        defaultCalendarizzazioneShouldNotBeFound("lunedi.notEquals=" + DEFAULT_LUNEDI);

        // Get all the calendarizzazioneList where lunedi not equals to UPDATED_LUNEDI
        defaultCalendarizzazioneShouldBeFound("lunedi.notEquals=" + UPDATED_LUNEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByLunediIsInShouldWork() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where lunedi in DEFAULT_LUNEDI or UPDATED_LUNEDI
        defaultCalendarizzazioneShouldBeFound("lunedi.in=" + DEFAULT_LUNEDI + "," + UPDATED_LUNEDI);

        // Get all the calendarizzazioneList where lunedi equals to UPDATED_LUNEDI
        defaultCalendarizzazioneShouldNotBeFound("lunedi.in=" + UPDATED_LUNEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByLunediIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where lunedi is not null
        defaultCalendarizzazioneShouldBeFound("lunedi.specified=true");

        // Get all the calendarizzazioneList where lunedi is null
        defaultCalendarizzazioneShouldNotBeFound("lunedi.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByMartediIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where martedi equals to DEFAULT_MARTEDI
        defaultCalendarizzazioneShouldBeFound("martedi.equals=" + DEFAULT_MARTEDI);

        // Get all the calendarizzazioneList where martedi equals to UPDATED_MARTEDI
        defaultCalendarizzazioneShouldNotBeFound("martedi.equals=" + UPDATED_MARTEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByMartediIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where martedi not equals to DEFAULT_MARTEDI
        defaultCalendarizzazioneShouldNotBeFound("martedi.notEquals=" + DEFAULT_MARTEDI);

        // Get all the calendarizzazioneList where martedi not equals to UPDATED_MARTEDI
        defaultCalendarizzazioneShouldBeFound("martedi.notEquals=" + UPDATED_MARTEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByMartediIsInShouldWork() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where martedi in DEFAULT_MARTEDI or UPDATED_MARTEDI
        defaultCalendarizzazioneShouldBeFound("martedi.in=" + DEFAULT_MARTEDI + "," + UPDATED_MARTEDI);

        // Get all the calendarizzazioneList where martedi equals to UPDATED_MARTEDI
        defaultCalendarizzazioneShouldNotBeFound("martedi.in=" + UPDATED_MARTEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByMartediIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where martedi is not null
        defaultCalendarizzazioneShouldBeFound("martedi.specified=true");

        // Get all the calendarizzazioneList where martedi is null
        defaultCalendarizzazioneShouldNotBeFound("martedi.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByMercolediIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where mercoledi equals to DEFAULT_MERCOLEDI
        defaultCalendarizzazioneShouldBeFound("mercoledi.equals=" + DEFAULT_MERCOLEDI);

        // Get all the calendarizzazioneList where mercoledi equals to UPDATED_MERCOLEDI
        defaultCalendarizzazioneShouldNotBeFound("mercoledi.equals=" + UPDATED_MERCOLEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByMercolediIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where mercoledi not equals to DEFAULT_MERCOLEDI
        defaultCalendarizzazioneShouldNotBeFound("mercoledi.notEquals=" + DEFAULT_MERCOLEDI);

        // Get all the calendarizzazioneList where mercoledi not equals to UPDATED_MERCOLEDI
        defaultCalendarizzazioneShouldBeFound("mercoledi.notEquals=" + UPDATED_MERCOLEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByMercolediIsInShouldWork() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where mercoledi in DEFAULT_MERCOLEDI or UPDATED_MERCOLEDI
        defaultCalendarizzazioneShouldBeFound("mercoledi.in=" + DEFAULT_MERCOLEDI + "," + UPDATED_MERCOLEDI);

        // Get all the calendarizzazioneList where mercoledi equals to UPDATED_MERCOLEDI
        defaultCalendarizzazioneShouldNotBeFound("mercoledi.in=" + UPDATED_MERCOLEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByMercolediIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where mercoledi is not null
        defaultCalendarizzazioneShouldBeFound("mercoledi.specified=true");

        // Get all the calendarizzazioneList where mercoledi is null
        defaultCalendarizzazioneShouldNotBeFound("mercoledi.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByGiovediIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where giovedi equals to DEFAULT_GIOVEDI
        defaultCalendarizzazioneShouldBeFound("giovedi.equals=" + DEFAULT_GIOVEDI);

        // Get all the calendarizzazioneList where giovedi equals to UPDATED_GIOVEDI
        defaultCalendarizzazioneShouldNotBeFound("giovedi.equals=" + UPDATED_GIOVEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByGiovediIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where giovedi not equals to DEFAULT_GIOVEDI
        defaultCalendarizzazioneShouldNotBeFound("giovedi.notEquals=" + DEFAULT_GIOVEDI);

        // Get all the calendarizzazioneList where giovedi not equals to UPDATED_GIOVEDI
        defaultCalendarizzazioneShouldBeFound("giovedi.notEquals=" + UPDATED_GIOVEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByGiovediIsInShouldWork() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where giovedi in DEFAULT_GIOVEDI or UPDATED_GIOVEDI
        defaultCalendarizzazioneShouldBeFound("giovedi.in=" + DEFAULT_GIOVEDI + "," + UPDATED_GIOVEDI);

        // Get all the calendarizzazioneList where giovedi equals to UPDATED_GIOVEDI
        defaultCalendarizzazioneShouldNotBeFound("giovedi.in=" + UPDATED_GIOVEDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByGiovediIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where giovedi is not null
        defaultCalendarizzazioneShouldBeFound("giovedi.specified=true");

        // Get all the calendarizzazioneList where giovedi is null
        defaultCalendarizzazioneShouldNotBeFound("giovedi.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByVenerdiIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where venerdi equals to DEFAULT_VENERDI
        defaultCalendarizzazioneShouldBeFound("venerdi.equals=" + DEFAULT_VENERDI);

        // Get all the calendarizzazioneList where venerdi equals to UPDATED_VENERDI
        defaultCalendarizzazioneShouldNotBeFound("venerdi.equals=" + UPDATED_VENERDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByVenerdiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where venerdi not equals to DEFAULT_VENERDI
        defaultCalendarizzazioneShouldNotBeFound("venerdi.notEquals=" + DEFAULT_VENERDI);

        // Get all the calendarizzazioneList where venerdi not equals to UPDATED_VENERDI
        defaultCalendarizzazioneShouldBeFound("venerdi.notEquals=" + UPDATED_VENERDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByVenerdiIsInShouldWork() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where venerdi in DEFAULT_VENERDI or UPDATED_VENERDI
        defaultCalendarizzazioneShouldBeFound("venerdi.in=" + DEFAULT_VENERDI + "," + UPDATED_VENERDI);

        // Get all the calendarizzazioneList where venerdi equals to UPDATED_VENERDI
        defaultCalendarizzazioneShouldNotBeFound("venerdi.in=" + UPDATED_VENERDI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByVenerdiIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where venerdi is not null
        defaultCalendarizzazioneShouldBeFound("venerdi.specified=true");

        // Get all the calendarizzazioneList where venerdi is null
        defaultCalendarizzazioneShouldNotBeFound("venerdi.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesBySabatoIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where sabato equals to DEFAULT_SABATO
        defaultCalendarizzazioneShouldBeFound("sabato.equals=" + DEFAULT_SABATO);

        // Get all the calendarizzazioneList where sabato equals to UPDATED_SABATO
        defaultCalendarizzazioneShouldNotBeFound("sabato.equals=" + UPDATED_SABATO);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesBySabatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where sabato not equals to DEFAULT_SABATO
        defaultCalendarizzazioneShouldNotBeFound("sabato.notEquals=" + DEFAULT_SABATO);

        // Get all the calendarizzazioneList where sabato not equals to UPDATED_SABATO
        defaultCalendarizzazioneShouldBeFound("sabato.notEquals=" + UPDATED_SABATO);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesBySabatoIsInShouldWork() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where sabato in DEFAULT_SABATO or UPDATED_SABATO
        defaultCalendarizzazioneShouldBeFound("sabato.in=" + DEFAULT_SABATO + "," + UPDATED_SABATO);

        // Get all the calendarizzazioneList where sabato equals to UPDATED_SABATO
        defaultCalendarizzazioneShouldNotBeFound("sabato.in=" + UPDATED_SABATO);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesBySabatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where sabato is not null
        defaultCalendarizzazioneShouldBeFound("sabato.specified=true");

        // Get all the calendarizzazioneList where sabato is null
        defaultCalendarizzazioneShouldNotBeFound("sabato.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByDomenicaIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where domenica equals to DEFAULT_DOMENICA
        defaultCalendarizzazioneShouldBeFound("domenica.equals=" + DEFAULT_DOMENICA);

        // Get all the calendarizzazioneList where domenica equals to UPDATED_DOMENICA
        defaultCalendarizzazioneShouldNotBeFound("domenica.equals=" + UPDATED_DOMENICA);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByDomenicaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where domenica not equals to DEFAULT_DOMENICA
        defaultCalendarizzazioneShouldNotBeFound("domenica.notEquals=" + DEFAULT_DOMENICA);

        // Get all the calendarizzazioneList where domenica not equals to UPDATED_DOMENICA
        defaultCalendarizzazioneShouldBeFound("domenica.notEquals=" + UPDATED_DOMENICA);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByDomenicaIsInShouldWork() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where domenica in DEFAULT_DOMENICA or UPDATED_DOMENICA
        defaultCalendarizzazioneShouldBeFound("domenica.in=" + DEFAULT_DOMENICA + "," + UPDATED_DOMENICA);

        // Get all the calendarizzazioneList where domenica equals to UPDATED_DOMENICA
        defaultCalendarizzazioneShouldNotBeFound("domenica.in=" + UPDATED_DOMENICA);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByDomenicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where domenica is not null
        defaultCalendarizzazioneShouldBeFound("domenica.specified=true");

        // Get all the calendarizzazioneList where domenica is null
        defaultCalendarizzazioneShouldNotBeFound("domenica.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesBySiRipeteIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where siRipete equals to DEFAULT_SI_RIPETE
        defaultCalendarizzazioneShouldBeFound("siRipete.equals=" + DEFAULT_SI_RIPETE);

        // Get all the calendarizzazioneList where siRipete equals to UPDATED_SI_RIPETE
        defaultCalendarizzazioneShouldNotBeFound("siRipete.equals=" + UPDATED_SI_RIPETE);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesBySiRipeteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where siRipete not equals to DEFAULT_SI_RIPETE
        defaultCalendarizzazioneShouldNotBeFound("siRipete.notEquals=" + DEFAULT_SI_RIPETE);

        // Get all the calendarizzazioneList where siRipete not equals to UPDATED_SI_RIPETE
        defaultCalendarizzazioneShouldBeFound("siRipete.notEquals=" + UPDATED_SI_RIPETE);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesBySiRipeteIsInShouldWork() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where siRipete in DEFAULT_SI_RIPETE or UPDATED_SI_RIPETE
        defaultCalendarizzazioneShouldBeFound("siRipete.in=" + DEFAULT_SI_RIPETE + "," + UPDATED_SI_RIPETE);

        // Get all the calendarizzazioneList where siRipete equals to UPDATED_SI_RIPETE
        defaultCalendarizzazioneShouldNotBeFound("siRipete.in=" + UPDATED_SI_RIPETE);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesBySiRipeteIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where siRipete is not null
        defaultCalendarizzazioneShouldBeFound("siRipete.specified=true");

        // Get all the calendarizzazioneList where siRipete is null
        defaultCalendarizzazioneShouldNotBeFound("siRipete.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByFestiviIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where festivi equals to DEFAULT_FESTIVI
        defaultCalendarizzazioneShouldBeFound("festivi.equals=" + DEFAULT_FESTIVI);

        // Get all the calendarizzazioneList where festivi equals to UPDATED_FESTIVI
        defaultCalendarizzazioneShouldNotBeFound("festivi.equals=" + UPDATED_FESTIVI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByFestiviIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where festivi not equals to DEFAULT_FESTIVI
        defaultCalendarizzazioneShouldNotBeFound("festivi.notEquals=" + DEFAULT_FESTIVI);

        // Get all the calendarizzazioneList where festivi not equals to UPDATED_FESTIVI
        defaultCalendarizzazioneShouldBeFound("festivi.notEquals=" + UPDATED_FESTIVI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByFestiviIsInShouldWork() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where festivi in DEFAULT_FESTIVI or UPDATED_FESTIVI
        defaultCalendarizzazioneShouldBeFound("festivi.in=" + DEFAULT_FESTIVI + "," + UPDATED_FESTIVI);

        // Get all the calendarizzazioneList where festivi equals to UPDATED_FESTIVI
        defaultCalendarizzazioneShouldNotBeFound("festivi.in=" + UPDATED_FESTIVI);
    }

    @Test
    @Transactional
    public void getAllCalendarizzazionesByFestiviIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarizzazioneRepository.saveAndFlush(calendarizzazione);

        // Get all the calendarizzazioneList where festivi is not null
        defaultCalendarizzazioneShouldBeFound("festivi.specified=true");

        // Get all the calendarizzazioneList where festivi is null
        defaultCalendarizzazioneShouldNotBeFound("festivi.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCalendarizzazioneShouldBeFound(String filter) throws Exception {
        restCalendarizzazioneMockMvc
            .perform(get("/api/calendarizzaziones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarizzazione.getId().intValue())))
            .andExpect(jsonPath("$.[*].lunedi").value(hasItem(DEFAULT_LUNEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].martedi").value(hasItem(DEFAULT_MARTEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].mercoledi").value(hasItem(DEFAULT_MERCOLEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].giovedi").value(hasItem(DEFAULT_GIOVEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].venerdi").value(hasItem(DEFAULT_VENERDI.booleanValue())))
            .andExpect(jsonPath("$.[*].sabato").value(hasItem(DEFAULT_SABATO.booleanValue())))
            .andExpect(jsonPath("$.[*].domenica").value(hasItem(DEFAULT_DOMENICA.booleanValue())))
            .andExpect(jsonPath("$.[*].siRipete").value(hasItem(DEFAULT_SI_RIPETE.booleanValue())))
            .andExpect(jsonPath("$.[*].festivi").value(hasItem(DEFAULT_FESTIVI.booleanValue())));

        // Check, that the count call also returns 1
        restCalendarizzazioneMockMvc
            .perform(get("/api/calendarizzaziones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCalendarizzazioneShouldNotBeFound(String filter) throws Exception {
        restCalendarizzazioneMockMvc
            .perform(get("/api/calendarizzaziones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCalendarizzazioneMockMvc
            .perform(get("/api/calendarizzaziones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCalendarizzazione() throws Exception {
        // Get the calendarizzazione
        restCalendarizzazioneMockMvc.perform(get("/api/calendarizzaziones/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendarizzazione() throws Exception {
        // Initialize the database
        calendarizzazioneService.save(calendarizzazione);

        int databaseSizeBeforeUpdate = calendarizzazioneRepository.findAll().size();

        // Update the calendarizzazione
        Calendarizzazione updatedCalendarizzazione = calendarizzazioneRepository.findById(calendarizzazione.getId()).get();
        // Disconnect from session so that the updates on updatedCalendarizzazione are not directly saved in db
        em.detach(updatedCalendarizzazione);
        updatedCalendarizzazione
            .lunedi(UPDATED_LUNEDI)
            .martedi(UPDATED_MARTEDI)
            .mercoledi(UPDATED_MERCOLEDI)
            .giovedi(UPDATED_GIOVEDI)
            .venerdi(UPDATED_VENERDI)
            .sabato(UPDATED_SABATO)
            .domenica(UPDATED_DOMENICA)
            .siRipete(UPDATED_SI_RIPETE)
            .festivi(UPDATED_FESTIVI);

        restCalendarizzazioneMockMvc
            .perform(
                put("/api/calendarizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCalendarizzazione))
            )
            .andExpect(status().isOk());

        // Validate the Calendarizzazione in the database
        List<Calendarizzazione> calendarizzazioneList = calendarizzazioneRepository.findAll();
        assertThat(calendarizzazioneList).hasSize(databaseSizeBeforeUpdate);
        Calendarizzazione testCalendarizzazione = calendarizzazioneList.get(calendarizzazioneList.size() - 1);
        assertThat(testCalendarizzazione.isLunedi()).isEqualTo(UPDATED_LUNEDI);
        assertThat(testCalendarizzazione.isMartedi()).isEqualTo(UPDATED_MARTEDI);
        assertThat(testCalendarizzazione.isMercoledi()).isEqualTo(UPDATED_MERCOLEDI);
        assertThat(testCalendarizzazione.isGiovedi()).isEqualTo(UPDATED_GIOVEDI);
        assertThat(testCalendarizzazione.isVenerdi()).isEqualTo(UPDATED_VENERDI);
        assertThat(testCalendarizzazione.isSabato()).isEqualTo(UPDATED_SABATO);
        assertThat(testCalendarizzazione.isDomenica()).isEqualTo(UPDATED_DOMENICA);
        assertThat(testCalendarizzazione.isSiRipete()).isEqualTo(UPDATED_SI_RIPETE);
        assertThat(testCalendarizzazione.isFestivi()).isEqualTo(UPDATED_FESTIVI);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendarizzazione() throws Exception {
        int databaseSizeBeforeUpdate = calendarizzazioneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarizzazioneMockMvc
            .perform(
                put("/api/calendarizzaziones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarizzazione))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendarizzazione in the database
        List<Calendarizzazione> calendarizzazioneList = calendarizzazioneRepository.findAll();
        assertThat(calendarizzazioneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendarizzazione() throws Exception {
        // Initialize the database
        calendarizzazioneService.save(calendarizzazione);

        int databaseSizeBeforeDelete = calendarizzazioneRepository.findAll().size();

        // Delete the calendarizzazione
        restCalendarizzazioneMockMvc
            .perform(delete("/api/calendarizzaziones/{id}", calendarizzazione.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Calendarizzazione> calendarizzazioneList = calendarizzazioneRepository.findAll();
        assertThat(calendarizzazioneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
