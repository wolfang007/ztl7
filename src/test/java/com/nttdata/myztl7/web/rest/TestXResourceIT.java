package com.nttdata.myztl7.web.rest;

import static com.nttdata.myztl7.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nttdata.myztl7.Myztl7App;
import com.nttdata.myztl7.domain.TestX;
import com.nttdata.myztl7.repository.TestXRepository;
import com.nttdata.myztl7.service.TestXService;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link TestXResource} REST controller.
 */
@SpringBootTest(classes = Myztl7App.class)
@AutoConfigureMockMvc
@WithMockUser
public class TestXResourceIT {
    private static final String DEFAULT_X_STRING = "AAAAAAAAAA";
    private static final String UPDATED_X_STRING = "BBBBBBBBBB";

    private static final Integer DEFAULT_X_INTEGER = 1;
    private static final Integer UPDATED_X_INTEGER = 2;

    private static final Long DEFAULT_X_LONG = 1L;
    private static final Long UPDATED_X_LONG = 2L;

    private static final BigDecimal DEFAULT_X_BIG_DECIMAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_X_BIG_DECIMAL = new BigDecimal(2);

    private static final Float DEFAULT_X_FLOAT = 1F;
    private static final Float UPDATED_X_FLOAT = 2F;

    private static final Double DEFAULT_X_DOUBLE = 1D;
    private static final Double UPDATED_X_DOUBLE = 2D;

    private static final Boolean DEFAULT_X_BOOLEAN = false;
    private static final Boolean UPDATED_X_BOOLEAN = true;

    private static final LocalDate DEFAULT_X_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_X_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_X_ZONED_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_X_ZONED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Instant DEFAULT_X_INSTANT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_X_INSTANT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final UUID DEFAULT_X_UUID = UUID.randomUUID();
    private static final UUID UPDATED_X_UUID = UUID.randomUUID();

    private static final byte[] DEFAULT_X_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_X_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_X_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_X_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_X_ANY_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_X_ANY_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_X_ANY_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_X_ANY_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_X_IMAGE_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_X_IMAGE_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_X_IMAGE_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_X_IMAGE_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_X_TEXT_BLOB = "AAAAAAAAAA";
    private static final String UPDATED_X_TEXT_BLOB = "BBBBBBBBBB";

    @Autowired
    private TestXRepository testXRepository;

    @Autowired
    private TestXService testXService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTestXMockMvc;

    private TestX testX;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestX createEntity(EntityManager em) {
        TestX testX = new TestX()
            .xString(DEFAULT_X_STRING)
            .xInteger(DEFAULT_X_INTEGER)
            .xLong(DEFAULT_X_LONG)
            .xBigDecimal(DEFAULT_X_BIG_DECIMAL)
            .xFloat(DEFAULT_X_FLOAT)
            .xDouble(DEFAULT_X_DOUBLE)
            .xBoolean(DEFAULT_X_BOOLEAN)
            .xLocalDate(DEFAULT_X_LOCAL_DATE)
            .xZonedDateTime(DEFAULT_X_ZONED_DATE_TIME)
            .xInstant(DEFAULT_X_INSTANT)
            .xUUID(DEFAULT_X_UUID)
            .xBlob(DEFAULT_X_BLOB)
            .xBlobContentType(DEFAULT_X_BLOB_CONTENT_TYPE)
            .xAnyBlob(DEFAULT_X_ANY_BLOB)
            .xAnyBlobContentType(DEFAULT_X_ANY_BLOB_CONTENT_TYPE)
            .xImageBlob(DEFAULT_X_IMAGE_BLOB)
            .xImageBlobContentType(DEFAULT_X_IMAGE_BLOB_CONTENT_TYPE)
            .xTextBlob(DEFAULT_X_TEXT_BLOB);
        return testX;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestX createUpdatedEntity(EntityManager em) {
        TestX testX = new TestX()
            .xString(UPDATED_X_STRING)
            .xInteger(UPDATED_X_INTEGER)
            .xLong(UPDATED_X_LONG)
            .xBigDecimal(UPDATED_X_BIG_DECIMAL)
            .xFloat(UPDATED_X_FLOAT)
            .xDouble(UPDATED_X_DOUBLE)
            .xBoolean(UPDATED_X_BOOLEAN)
            .xLocalDate(UPDATED_X_LOCAL_DATE)
            .xZonedDateTime(UPDATED_X_ZONED_DATE_TIME)
            .xInstant(UPDATED_X_INSTANT)
            .xUUID(UPDATED_X_UUID)
            .xBlob(UPDATED_X_BLOB)
            .xBlobContentType(UPDATED_X_BLOB_CONTENT_TYPE)
            .xAnyBlob(UPDATED_X_ANY_BLOB)
            .xAnyBlobContentType(UPDATED_X_ANY_BLOB_CONTENT_TYPE)
            .xImageBlob(UPDATED_X_IMAGE_BLOB)
            .xImageBlobContentType(UPDATED_X_IMAGE_BLOB_CONTENT_TYPE)
            .xTextBlob(UPDATED_X_TEXT_BLOB);
        return testX;
    }

    @BeforeEach
    public void initTest() {
        testX = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestX() throws Exception {
        int databaseSizeBeforeCreate = testXRepository.findAll().size();
        // Create the TestX
        restTestXMockMvc
            .perform(post("/api/test-xes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testX)))
            .andExpect(status().isCreated());

        // Validate the TestX in the database
        List<TestX> testXList = testXRepository.findAll();
        assertThat(testXList).hasSize(databaseSizeBeforeCreate + 1);
        TestX testTestX = testXList.get(testXList.size() - 1);
        assertThat(testTestX.getxString()).isEqualTo(DEFAULT_X_STRING);
        assertThat(testTestX.getxInteger()).isEqualTo(DEFAULT_X_INTEGER);
        assertThat(testTestX.getxLong()).isEqualTo(DEFAULT_X_LONG);
        assertThat(testTestX.getxBigDecimal()).isEqualTo(DEFAULT_X_BIG_DECIMAL);
        assertThat(testTestX.getxFloat()).isEqualTo(DEFAULT_X_FLOAT);
        assertThat(testTestX.getxDouble()).isEqualTo(DEFAULT_X_DOUBLE);
        assertThat(testTestX.isxBoolean()).isEqualTo(DEFAULT_X_BOOLEAN);
        assertThat(testTestX.getxLocalDate()).isEqualTo(DEFAULT_X_LOCAL_DATE);
        assertThat(testTestX.getxZonedDateTime()).isEqualTo(DEFAULT_X_ZONED_DATE_TIME);
        assertThat(testTestX.getxInstant()).isEqualTo(DEFAULT_X_INSTANT);
        assertThat(testTestX.getxUUID()).isEqualTo(DEFAULT_X_UUID);
        assertThat(testTestX.getxBlob()).isEqualTo(DEFAULT_X_BLOB);
        assertThat(testTestX.getxBlobContentType()).isEqualTo(DEFAULT_X_BLOB_CONTENT_TYPE);
        assertThat(testTestX.getxAnyBlob()).isEqualTo(DEFAULT_X_ANY_BLOB);
        assertThat(testTestX.getxAnyBlobContentType()).isEqualTo(DEFAULT_X_ANY_BLOB_CONTENT_TYPE);
        assertThat(testTestX.getxImageBlob()).isEqualTo(DEFAULT_X_IMAGE_BLOB);
        assertThat(testTestX.getxImageBlobContentType()).isEqualTo(DEFAULT_X_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testTestX.getxTextBlob()).isEqualTo(DEFAULT_X_TEXT_BLOB);
    }

    @Test
    @Transactional
    public void createTestXWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testXRepository.findAll().size();

        // Create the TestX with an existing ID
        testX.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestXMockMvc
            .perform(post("/api/test-xes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testX)))
            .andExpect(status().isBadRequest());

        // Validate the TestX in the database
        List<TestX> testXList = testXRepository.findAll();
        assertThat(testXList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTestXES() throws Exception {
        // Initialize the database
        testXRepository.saveAndFlush(testX);

        // Get all the testXList
        restTestXMockMvc
            .perform(get("/api/test-xes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testX.getId().intValue())))
            .andExpect(jsonPath("$.[*].xString").value(hasItem(DEFAULT_X_STRING)))
            .andExpect(jsonPath("$.[*].xInteger").value(hasItem(DEFAULT_X_INTEGER)))
            .andExpect(jsonPath("$.[*].xLong").value(hasItem(DEFAULT_X_LONG.intValue())))
            .andExpect(jsonPath("$.[*].xBigDecimal").value(hasItem(DEFAULT_X_BIG_DECIMAL.intValue())))
            .andExpect(jsonPath("$.[*].xFloat").value(hasItem(DEFAULT_X_FLOAT.doubleValue())))
            .andExpect(jsonPath("$.[*].xDouble").value(hasItem(DEFAULT_X_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].xBoolean").value(hasItem(DEFAULT_X_BOOLEAN.booleanValue())))
            .andExpect(jsonPath("$.[*].xLocalDate").value(hasItem(DEFAULT_X_LOCAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].xZonedDateTime").value(hasItem(sameInstant(DEFAULT_X_ZONED_DATE_TIME))))
            .andExpect(jsonPath("$.[*].xInstant").value(hasItem(DEFAULT_X_INSTANT.toString())))
            .andExpect(jsonPath("$.[*].xUUID").value(hasItem(DEFAULT_X_UUID.toString())))
            .andExpect(jsonPath("$.[*].xBlobContentType").value(hasItem(DEFAULT_X_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].xBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_X_BLOB))))
            .andExpect(jsonPath("$.[*].xAnyBlobContentType").value(hasItem(DEFAULT_X_ANY_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].xAnyBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_X_ANY_BLOB))))
            .andExpect(jsonPath("$.[*].xImageBlobContentType").value(hasItem(DEFAULT_X_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].xImageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_X_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].xTextBlob").value(hasItem(DEFAULT_X_TEXT_BLOB.toString())));
    }

    @Test
    @Transactional
    public void getTestX() throws Exception {
        // Initialize the database
        testXRepository.saveAndFlush(testX);

        // Get the testX
        restTestXMockMvc
            .perform(get("/api/test-xes/{id}", testX.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(testX.getId().intValue()))
            .andExpect(jsonPath("$.xString").value(DEFAULT_X_STRING))
            .andExpect(jsonPath("$.xInteger").value(DEFAULT_X_INTEGER))
            .andExpect(jsonPath("$.xLong").value(DEFAULT_X_LONG.intValue()))
            .andExpect(jsonPath("$.xBigDecimal").value(DEFAULT_X_BIG_DECIMAL.intValue()))
            .andExpect(jsonPath("$.xFloat").value(DEFAULT_X_FLOAT.doubleValue()))
            .andExpect(jsonPath("$.xDouble").value(DEFAULT_X_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.xBoolean").value(DEFAULT_X_BOOLEAN.booleanValue()))
            .andExpect(jsonPath("$.xLocalDate").value(DEFAULT_X_LOCAL_DATE.toString()))
            .andExpect(jsonPath("$.xZonedDateTime").value(sameInstant(DEFAULT_X_ZONED_DATE_TIME)))
            .andExpect(jsonPath("$.xInstant").value(DEFAULT_X_INSTANT.toString()))
            .andExpect(jsonPath("$.xUUID").value(DEFAULT_X_UUID.toString()))
            .andExpect(jsonPath("$.xBlobContentType").value(DEFAULT_X_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.xBlob").value(Base64Utils.encodeToString(DEFAULT_X_BLOB)))
            .andExpect(jsonPath("$.xAnyBlobContentType").value(DEFAULT_X_ANY_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.xAnyBlob").value(Base64Utils.encodeToString(DEFAULT_X_ANY_BLOB)))
            .andExpect(jsonPath("$.xImageBlobContentType").value(DEFAULT_X_IMAGE_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.xImageBlob").value(Base64Utils.encodeToString(DEFAULT_X_IMAGE_BLOB)))
            .andExpect(jsonPath("$.xTextBlob").value(DEFAULT_X_TEXT_BLOB.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTestX() throws Exception {
        // Get the testX
        restTestXMockMvc.perform(get("/api/test-xes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestX() throws Exception {
        // Initialize the database
        testXService.save(testX);

        int databaseSizeBeforeUpdate = testXRepository.findAll().size();

        // Update the testX
        TestX updatedTestX = testXRepository.findById(testX.getId()).get();
        // Disconnect from session so that the updates on updatedTestX are not directly saved in db
        em.detach(updatedTestX);
        updatedTestX
            .xString(UPDATED_X_STRING)
            .xInteger(UPDATED_X_INTEGER)
            .xLong(UPDATED_X_LONG)
            .xBigDecimal(UPDATED_X_BIG_DECIMAL)
            .xFloat(UPDATED_X_FLOAT)
            .xDouble(UPDATED_X_DOUBLE)
            .xBoolean(UPDATED_X_BOOLEAN)
            .xLocalDate(UPDATED_X_LOCAL_DATE)
            .xZonedDateTime(UPDATED_X_ZONED_DATE_TIME)
            .xInstant(UPDATED_X_INSTANT)
            .xUUID(UPDATED_X_UUID)
            .xBlob(UPDATED_X_BLOB)
            .xBlobContentType(UPDATED_X_BLOB_CONTENT_TYPE)
            .xAnyBlob(UPDATED_X_ANY_BLOB)
            .xAnyBlobContentType(UPDATED_X_ANY_BLOB_CONTENT_TYPE)
            .xImageBlob(UPDATED_X_IMAGE_BLOB)
            .xImageBlobContentType(UPDATED_X_IMAGE_BLOB_CONTENT_TYPE)
            .xTextBlob(UPDATED_X_TEXT_BLOB);

        restTestXMockMvc
            .perform(put("/api/test-xes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedTestX)))
            .andExpect(status().isOk());

        // Validate the TestX in the database
        List<TestX> testXList = testXRepository.findAll();
        assertThat(testXList).hasSize(databaseSizeBeforeUpdate);
        TestX testTestX = testXList.get(testXList.size() - 1);
        assertThat(testTestX.getxString()).isEqualTo(UPDATED_X_STRING);
        assertThat(testTestX.getxInteger()).isEqualTo(UPDATED_X_INTEGER);
        assertThat(testTestX.getxLong()).isEqualTo(UPDATED_X_LONG);
        assertThat(testTestX.getxBigDecimal()).isEqualTo(UPDATED_X_BIG_DECIMAL);
        assertThat(testTestX.getxFloat()).isEqualTo(UPDATED_X_FLOAT);
        assertThat(testTestX.getxDouble()).isEqualTo(UPDATED_X_DOUBLE);
        assertThat(testTestX.isxBoolean()).isEqualTo(UPDATED_X_BOOLEAN);
        assertThat(testTestX.getxLocalDate()).isEqualTo(UPDATED_X_LOCAL_DATE);
        assertThat(testTestX.getxZonedDateTime()).isEqualTo(UPDATED_X_ZONED_DATE_TIME);
        assertThat(testTestX.getxInstant()).isEqualTo(UPDATED_X_INSTANT);
        assertThat(testTestX.getxUUID()).isEqualTo(UPDATED_X_UUID);
        assertThat(testTestX.getxBlob()).isEqualTo(UPDATED_X_BLOB);
        assertThat(testTestX.getxBlobContentType()).isEqualTo(UPDATED_X_BLOB_CONTENT_TYPE);
        assertThat(testTestX.getxAnyBlob()).isEqualTo(UPDATED_X_ANY_BLOB);
        assertThat(testTestX.getxAnyBlobContentType()).isEqualTo(UPDATED_X_ANY_BLOB_CONTENT_TYPE);
        assertThat(testTestX.getxImageBlob()).isEqualTo(UPDATED_X_IMAGE_BLOB);
        assertThat(testTestX.getxImageBlobContentType()).isEqualTo(UPDATED_X_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testTestX.getxTextBlob()).isEqualTo(UPDATED_X_TEXT_BLOB);
    }

    @Test
    @Transactional
    public void updateNonExistingTestX() throws Exception {
        int databaseSizeBeforeUpdate = testXRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestXMockMvc
            .perform(put("/api/test-xes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testX)))
            .andExpect(status().isBadRequest());

        // Validate the TestX in the database
        List<TestX> testXList = testXRepository.findAll();
        assertThat(testXList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTestX() throws Exception {
        // Initialize the database
        testXService.save(testX);

        int databaseSizeBeforeDelete = testXRepository.findAll().size();

        // Delete the testX
        restTestXMockMvc
            .perform(delete("/api/test-xes/{id}", testX.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TestX> testXList = testXRepository.findAll();
        assertThat(testXList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
