package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.DurataCosto;
import com.nttdata.myztl7.service.DurataCostoQueryService;
import com.nttdata.myztl7.service.DurataCostoService;
import com.nttdata.myztl7.service.dto.DurataCostoCriteria;
import com.nttdata.myztl7.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.nttdata.myztl7.domain.DurataCosto}.
 */
@RestController
@RequestMapping("/api")
public class DurataCostoResource {
    private final Logger log = LoggerFactory.getLogger(DurataCostoResource.class);

    private static final String ENTITY_NAME = "durataCosto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DurataCostoService durataCostoService;

    private final DurataCostoQueryService durataCostoQueryService;

    public DurataCostoResource(DurataCostoService durataCostoService, DurataCostoQueryService durataCostoQueryService) {
        this.durataCostoService = durataCostoService;
        this.durataCostoQueryService = durataCostoQueryService;
    }

    /**
     * {@code POST  /durata-costos} : Create a new durataCosto.
     *
     * @param durataCosto the durataCosto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new durataCosto, or with status {@code 400 (Bad Request)} if the durataCosto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/durata-costos")
    public ResponseEntity<DurataCosto> createDurataCosto(@Valid @RequestBody DurataCosto durataCosto) throws URISyntaxException {
        log.debug("REST request to save DurataCosto : {}", durataCosto);
        if (durataCosto.getId() != null) {
            throw new BadRequestAlertException("A new durataCosto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DurataCosto result = durataCostoService.save(durataCosto);
        return ResponseEntity
            .created(new URI("/api/durata-costos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /durata-costos} : Updates an existing durataCosto.
     *
     * @param durataCosto the durataCosto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated durataCosto,
     * or with status {@code 400 (Bad Request)} if the durataCosto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the durataCosto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/durata-costos")
    public ResponseEntity<DurataCosto> updateDurataCosto(@Valid @RequestBody DurataCosto durataCosto) throws URISyntaxException {
        log.debug("REST request to update DurataCosto : {}", durataCosto);
        if (durataCosto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DurataCosto result = durataCostoService.save(durataCosto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, durataCosto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /durata-costos} : get all the durataCostos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of durataCostos in body.
     */
    @GetMapping("/durata-costos")
    public ResponseEntity<List<DurataCosto>> getAllDurataCostos(DurataCostoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DurataCostos by criteria: {}", criteria);
        Page<DurataCosto> page = durataCostoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /durata-costos/count} : count all the durataCostos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/durata-costos/count")
    public ResponseEntity<Long> countDurataCostos(DurataCostoCriteria criteria) {
        log.debug("REST request to count DurataCostos by criteria: {}", criteria);
        return ResponseEntity.ok().body(durataCostoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /durata-costos/:id} : get the "id" durataCosto.
     *
     * @param id the id of the durataCosto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the durataCosto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/durata-costos/{id}")
    public ResponseEntity<DurataCosto> getDurataCosto(@PathVariable Long id) {
        log.debug("REST request to get DurataCosto : {}", id);
        Optional<DurataCosto> durataCosto = durataCostoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(durataCosto);
    }

    /**
     * {@code DELETE  /durata-costos/:id} : delete the "id" durataCosto.
     *
     * @param id the id of the durataCosto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/durata-costos/{id}")
    public ResponseEntity<Void> deleteDurataCosto(@PathVariable Long id) {
        log.debug("REST request to delete DurataCosto : {}", id);
        durataCostoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
