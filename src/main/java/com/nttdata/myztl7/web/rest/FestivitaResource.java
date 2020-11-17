package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.Festivita;
import com.nttdata.myztl7.service.FestivitaQueryService;
import com.nttdata.myztl7.service.FestivitaService;
import com.nttdata.myztl7.service.dto.FestivitaCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.Festivita}.
 */
@RestController
@RequestMapping("/api")
public class FestivitaResource {
    private final Logger log = LoggerFactory.getLogger(FestivitaResource.class);

    private static final String ENTITY_NAME = "festivita";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FestivitaService festivitaService;

    private final FestivitaQueryService festivitaQueryService;

    public FestivitaResource(FestivitaService festivitaService, FestivitaQueryService festivitaQueryService) {
        this.festivitaService = festivitaService;
        this.festivitaQueryService = festivitaQueryService;
    }

    /**
     * {@code POST  /festivitas} : Create a new festivita.
     *
     * @param festivita the festivita to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new festivita, or with status {@code 400 (Bad Request)} if the festivita has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/festivitas")
    public ResponseEntity<Festivita> createFestivita(@Valid @RequestBody Festivita festivita) throws URISyntaxException {
        log.debug("REST request to save Festivita : {}", festivita);
        if (festivita.getId() != null) {
            throw new BadRequestAlertException("A new festivita cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Festivita result = festivitaService.save(festivita);
        return ResponseEntity
            .created(new URI("/api/festivitas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /festivitas} : Updates an existing festivita.
     *
     * @param festivita the festivita to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated festivita,
     * or with status {@code 400 (Bad Request)} if the festivita is not valid,
     * or with status {@code 500 (Internal Server Error)} if the festivita couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/festivitas")
    public ResponseEntity<Festivita> updateFestivita(@Valid @RequestBody Festivita festivita) throws URISyntaxException {
        log.debug("REST request to update Festivita : {}", festivita);
        if (festivita.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Festivita result = festivitaService.save(festivita);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, festivita.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /festivitas} : get all the festivitas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of festivitas in body.
     */
    @GetMapping("/festivitas")
    public ResponseEntity<List<Festivita>> getAllFestivitas(FestivitaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Festivitas by criteria: {}", criteria);
        Page<Festivita> page = festivitaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /festivitas/count} : count all the festivitas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/festivitas/count")
    public ResponseEntity<Long> countFestivitas(FestivitaCriteria criteria) {
        log.debug("REST request to count Festivitas by criteria: {}", criteria);
        return ResponseEntity.ok().body(festivitaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /festivitas/:id} : get the "id" festivita.
     *
     * @param id the id of the festivita to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the festivita, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/festivitas/{id}")
    public ResponseEntity<Festivita> getFestivita(@PathVariable Long id) {
        log.debug("REST request to get Festivita : {}", id);
        Optional<Festivita> festivita = festivitaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(festivita);
    }

    /**
     * {@code DELETE  /festivitas/:id} : delete the "id" festivita.
     *
     * @param id the id of the festivita to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/festivitas/{id}")
    public ResponseEntity<Void> deleteFestivita(@PathVariable Long id) {
        log.debug("REST request to delete Festivita : {}", id);
        festivitaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
