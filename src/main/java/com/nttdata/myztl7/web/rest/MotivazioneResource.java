package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.Motivazione;
import com.nttdata.myztl7.service.MotivazioneQueryService;
import com.nttdata.myztl7.service.MotivazioneService;
import com.nttdata.myztl7.service.dto.MotivazioneCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.Motivazione}.
 */
@RestController
@RequestMapping("/api")
public class MotivazioneResource {
    private final Logger log = LoggerFactory.getLogger(MotivazioneResource.class);

    private static final String ENTITY_NAME = "motivazione";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotivazioneService motivazioneService;

    private final MotivazioneQueryService motivazioneQueryService;

    public MotivazioneResource(MotivazioneService motivazioneService, MotivazioneQueryService motivazioneQueryService) {
        this.motivazioneService = motivazioneService;
        this.motivazioneQueryService = motivazioneQueryService;
    }

    /**
     * {@code POST  /motivaziones} : Create a new motivazione.
     *
     * @param motivazione the motivazione to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motivazione, or with status {@code 400 (Bad Request)} if the motivazione has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/motivaziones")
    public ResponseEntity<Motivazione> createMotivazione(@Valid @RequestBody Motivazione motivazione) throws URISyntaxException {
        log.debug("REST request to save Motivazione : {}", motivazione);
        if (motivazione.getId() != null) {
            throw new BadRequestAlertException("A new motivazione cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Motivazione result = motivazioneService.save(motivazione);
        return ResponseEntity
            .created(new URI("/api/motivaziones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /motivaziones} : Updates an existing motivazione.
     *
     * @param motivazione the motivazione to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivazione,
     * or with status {@code 400 (Bad Request)} if the motivazione is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motivazione couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/motivaziones")
    public ResponseEntity<Motivazione> updateMotivazione(@Valid @RequestBody Motivazione motivazione) throws URISyntaxException {
        log.debug("REST request to update Motivazione : {}", motivazione);
        if (motivazione.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Motivazione result = motivazioneService.save(motivazione);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, motivazione.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /motivaziones} : get all the motivaziones.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of motivaziones in body.
     */
    @GetMapping("/motivaziones")
    public ResponseEntity<List<Motivazione>> getAllMotivaziones(MotivazioneCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Motivaziones by criteria: {}", criteria);
        Page<Motivazione> page = motivazioneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /motivaziones/count} : count all the motivaziones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/motivaziones/count")
    public ResponseEntity<Long> countMotivaziones(MotivazioneCriteria criteria) {
        log.debug("REST request to count Motivaziones by criteria: {}", criteria);
        return ResponseEntity.ok().body(motivazioneQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /motivaziones/:id} : get the "id" motivazione.
     *
     * @param id the id of the motivazione to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motivazione, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/motivaziones/{id}")
    public ResponseEntity<Motivazione> getMotivazione(@PathVariable Long id) {
        log.debug("REST request to get Motivazione : {}", id);
        Optional<Motivazione> motivazione = motivazioneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(motivazione);
    }

    /**
     * {@code DELETE  /motivaziones/:id} : delete the "id" motivazione.
     *
     * @param id the id of the motivazione to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/motivaziones/{id}")
    public ResponseEntity<Void> deleteMotivazione(@PathVariable Long id) {
        log.debug("REST request to delete Motivazione : {}", id);
        motivazioneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
