package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.TipologiaZona;
import com.nttdata.myztl7.service.TipologiaZonaQueryService;
import com.nttdata.myztl7.service.TipologiaZonaService;
import com.nttdata.myztl7.service.dto.TipologiaZonaCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.TipologiaZona}.
 */
@RestController
@RequestMapping("/api")
public class TipologiaZonaResource {
    private final Logger log = LoggerFactory.getLogger(TipologiaZonaResource.class);

    private static final String ENTITY_NAME = "tipologiaZona";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipologiaZonaService tipologiaZonaService;

    private final TipologiaZonaQueryService tipologiaZonaQueryService;

    public TipologiaZonaResource(TipologiaZonaService tipologiaZonaService, TipologiaZonaQueryService tipologiaZonaQueryService) {
        this.tipologiaZonaService = tipologiaZonaService;
        this.tipologiaZonaQueryService = tipologiaZonaQueryService;
    }

    /**
     * {@code POST  /tipologia-zonas} : Create a new tipologiaZona.
     *
     * @param tipologiaZona the tipologiaZona to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipologiaZona, or with status {@code 400 (Bad Request)} if the tipologiaZona has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipologia-zonas")
    public ResponseEntity<TipologiaZona> createTipologiaZona(@Valid @RequestBody TipologiaZona tipologiaZona) throws URISyntaxException {
        log.debug("REST request to save TipologiaZona : {}", tipologiaZona);
        if (tipologiaZona.getId() != null) {
            throw new BadRequestAlertException("A new tipologiaZona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipologiaZona result = tipologiaZonaService.save(tipologiaZona);
        return ResponseEntity
            .created(new URI("/api/tipologia-zonas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipologia-zonas} : Updates an existing tipologiaZona.
     *
     * @param tipologiaZona the tipologiaZona to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipologiaZona,
     * or with status {@code 400 (Bad Request)} if the tipologiaZona is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipologiaZona couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipologia-zonas")
    public ResponseEntity<TipologiaZona> updateTipologiaZona(@Valid @RequestBody TipologiaZona tipologiaZona) throws URISyntaxException {
        log.debug("REST request to update TipologiaZona : {}", tipologiaZona);
        if (tipologiaZona.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipologiaZona result = tipologiaZonaService.save(tipologiaZona);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipologiaZona.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipologia-zonas} : get all the tipologiaZonas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipologiaZonas in body.
     */
    @GetMapping("/tipologia-zonas")
    public ResponseEntity<List<TipologiaZona>> getAllTipologiaZonas(TipologiaZonaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipologiaZonas by criteria: {}", criteria);
        Page<TipologiaZona> page = tipologiaZonaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipologia-zonas/count} : count all the tipologiaZonas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipologia-zonas/count")
    public ResponseEntity<Long> countTipologiaZonas(TipologiaZonaCriteria criteria) {
        log.debug("REST request to count TipologiaZonas by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipologiaZonaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipologia-zonas/:id} : get the "id" tipologiaZona.
     *
     * @param id the id of the tipologiaZona to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipologiaZona, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipologia-zonas/{id}")
    public ResponseEntity<TipologiaZona> getTipologiaZona(@PathVariable Long id) {
        log.debug("REST request to get TipologiaZona : {}", id);
        Optional<TipologiaZona> tipologiaZona = tipologiaZonaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipologiaZona);
    }

    /**
     * {@code DELETE  /tipologia-zonas/:id} : delete the "id" tipologiaZona.
     *
     * @param id the id of the tipologiaZona to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipologia-zonas/{id}")
    public ResponseEntity<Void> deleteTipologiaZona(@PathVariable Long id) {
        log.debug("REST request to delete TipologiaZona : {}", id);
        tipologiaZonaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
