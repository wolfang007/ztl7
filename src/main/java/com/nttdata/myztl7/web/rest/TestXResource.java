package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.TestX;
import com.nttdata.myztl7.service.TestXService;
import com.nttdata.myztl7.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.nttdata.myztl7.domain.TestX}.
 */
@RestController
@RequestMapping("/api")
public class TestXResource {
    private final Logger log = LoggerFactory.getLogger(TestXResource.class);

    private static final String ENTITY_NAME = "testX";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestXService testXService;

    public TestXResource(TestXService testXService) {
        this.testXService = testXService;
    }

    /**
     * {@code POST  /test-xes} : Create a new testX.
     *
     * @param testX the testX to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testX, or with status {@code 400 (Bad Request)} if the testX has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/test-xes")
    public ResponseEntity<TestX> createTestX(@RequestBody TestX testX) throws URISyntaxException {
        log.debug("REST request to save TestX : {}", testX);
        if (testX.getId() != null) {
            throw new BadRequestAlertException("A new testX cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestX result = testXService.save(testX);
        return ResponseEntity
            .created(new URI("/api/test-xes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /test-xes} : Updates an existing testX.
     *
     * @param testX the testX to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testX,
     * or with status {@code 400 (Bad Request)} if the testX is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testX couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/test-xes")
    public ResponseEntity<TestX> updateTestX(@RequestBody TestX testX) throws URISyntaxException {
        log.debug("REST request to update TestX : {}", testX);
        if (testX.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TestX result = testXService.save(testX);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, testX.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /test-xes} : get all the testXES.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testXES in body.
     */
    @GetMapping("/test-xes")
    public List<TestX> getAllTestXES() {
        log.debug("REST request to get all TestXES");
        return testXService.findAll();
    }

    /**
     * {@code GET  /test-xes/:id} : get the "id" testX.
     *
     * @param id the id of the testX to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testX, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/test-xes/{id}")
    public ResponseEntity<TestX> getTestX(@PathVariable Long id) {
        log.debug("REST request to get TestX : {}", id);
        Optional<TestX> testX = testXService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testX);
    }

    /**
     * {@code DELETE  /test-xes/:id} : delete the "id" testX.
     *
     * @param id the id of the testX to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/test-xes/{id}")
    public ResponseEntity<Void> deleteTestX(@PathVariable Long id) {
        log.debug("REST request to delete TestX : {}", id);
        testXService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
