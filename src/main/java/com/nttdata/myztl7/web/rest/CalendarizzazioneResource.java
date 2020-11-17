package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.Calendarizzazione;
import com.nttdata.myztl7.service.CalendarizzazioneQueryService;
import com.nttdata.myztl7.service.CalendarizzazioneService;
import com.nttdata.myztl7.service.dto.CalendarizzazioneCriteria;
import com.nttdata.myztl7.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.Calendarizzazione}.
 */
@RestController
@RequestMapping("/api")
public class CalendarizzazioneResource {
    private final Logger log = LoggerFactory.getLogger(CalendarizzazioneResource.class);

    private static final String ENTITY_NAME = "calendarizzazione";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalendarizzazioneService calendarizzazioneService;

    private final CalendarizzazioneQueryService calendarizzazioneQueryService;

    public CalendarizzazioneResource(
        CalendarizzazioneService calendarizzazioneService,
        CalendarizzazioneQueryService calendarizzazioneQueryService
    ) {
        this.calendarizzazioneService = calendarizzazioneService;
        this.calendarizzazioneQueryService = calendarizzazioneQueryService;
    }

    /**
     * {@code POST  /calendarizzaziones} : Create a new calendarizzazione.
     *
     * @param calendarizzazione the calendarizzazione to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calendarizzazione, or with status {@code 400 (Bad Request)} if the calendarizzazione has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calendarizzaziones")
    public ResponseEntity<Calendarizzazione> createCalendarizzazione(@RequestBody Calendarizzazione calendarizzazione)
        throws URISyntaxException {
        log.debug("REST request to save Calendarizzazione : {}", calendarizzazione);
        if (calendarizzazione.getId() != null) {
            throw new BadRequestAlertException("A new calendarizzazione cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Calendarizzazione result = calendarizzazioneService.save(calendarizzazione);
        return ResponseEntity
            .created(new URI("/api/calendarizzaziones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calendarizzaziones} : Updates an existing calendarizzazione.
     *
     * @param calendarizzazione the calendarizzazione to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendarizzazione,
     * or with status {@code 400 (Bad Request)} if the calendarizzazione is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calendarizzazione couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calendarizzaziones")
    public ResponseEntity<Calendarizzazione> updateCalendarizzazione(@RequestBody Calendarizzazione calendarizzazione)
        throws URISyntaxException {
        log.debug("REST request to update Calendarizzazione : {}", calendarizzazione);
        if (calendarizzazione.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Calendarizzazione result = calendarizzazioneService.save(calendarizzazione);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calendarizzazione.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calendarizzaziones} : get all the calendarizzaziones.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calendarizzaziones in body.
     */
    @GetMapping("/calendarizzaziones")
    public ResponseEntity<List<Calendarizzazione>> getAllCalendarizzaziones(CalendarizzazioneCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Calendarizzaziones by criteria: {}", criteria);
        Page<Calendarizzazione> page = calendarizzazioneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calendarizzaziones/count} : count all the calendarizzaziones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/calendarizzaziones/count")
    public ResponseEntity<Long> countCalendarizzaziones(CalendarizzazioneCriteria criteria) {
        log.debug("REST request to count Calendarizzaziones by criteria: {}", criteria);
        return ResponseEntity.ok().body(calendarizzazioneQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /calendarizzaziones/:id} : get the "id" calendarizzazione.
     *
     * @param id the id of the calendarizzazione to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendarizzazione, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calendarizzaziones/{id}")
    public ResponseEntity<Calendarizzazione> getCalendarizzazione(@PathVariable Long id) {
        log.debug("REST request to get Calendarizzazione : {}", id);
        Optional<Calendarizzazione> calendarizzazione = calendarizzazioneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendarizzazione);
    }

    /**
     * {@code DELETE  /calendarizzaziones/:id} : delete the "id" calendarizzazione.
     *
     * @param id the id of the calendarizzazione to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calendarizzaziones/{id}")
    public ResponseEntity<Void> deleteCalendarizzazione(@PathVariable Long id) {
        log.debug("REST request to delete Calendarizzazione : {}", id);
        calendarizzazioneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
