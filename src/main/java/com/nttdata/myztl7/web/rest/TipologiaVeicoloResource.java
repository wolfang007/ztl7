package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.TipologiaVeicolo;
import com.nttdata.myztl7.service.TipologiaVeicoloQueryService;
import com.nttdata.myztl7.service.TipologiaVeicoloService;
import com.nttdata.myztl7.service.dto.TipologiaVeicoloCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.TipologiaVeicolo}.
 */
@RestController
@RequestMapping("/api")
public class TipologiaVeicoloResource {
    private final Logger log = LoggerFactory.getLogger(TipologiaVeicoloResource.class);

    private static final String ENTITY_NAME = "tipologiaVeicolo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipologiaVeicoloService tipologiaVeicoloService;

    private final TipologiaVeicoloQueryService tipologiaVeicoloQueryService;

    public TipologiaVeicoloResource(
        TipologiaVeicoloService tipologiaVeicoloService,
        TipologiaVeicoloQueryService tipologiaVeicoloQueryService
    ) {
        this.tipologiaVeicoloService = tipologiaVeicoloService;
        this.tipologiaVeicoloQueryService = tipologiaVeicoloQueryService;
    }

    /**
     * {@code POST  /tipologia-veicolos} : Create a new tipologiaVeicolo.
     *
     * @param tipologiaVeicolo the tipologiaVeicolo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipologiaVeicolo, or with status {@code 400 (Bad Request)} if the tipologiaVeicolo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipologia-veicolos")
    public ResponseEntity<TipologiaVeicolo> createTipologiaVeicolo(@Valid @RequestBody TipologiaVeicolo tipologiaVeicolo)
        throws URISyntaxException {
        log.debug("REST request to save TipologiaVeicolo : {}", tipologiaVeicolo);
        if (tipologiaVeicolo.getId() != null) {
            throw new BadRequestAlertException("A new tipologiaVeicolo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipologiaVeicolo result = tipologiaVeicoloService.save(tipologiaVeicolo);
        return ResponseEntity
            .created(new URI("/api/tipologia-veicolos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipologia-veicolos} : Updates an existing tipologiaVeicolo.
     *
     * @param tipologiaVeicolo the tipologiaVeicolo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipologiaVeicolo,
     * or with status {@code 400 (Bad Request)} if the tipologiaVeicolo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipologiaVeicolo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipologia-veicolos")
    public ResponseEntity<TipologiaVeicolo> updateTipologiaVeicolo(@Valid @RequestBody TipologiaVeicolo tipologiaVeicolo)
        throws URISyntaxException {
        log.debug("REST request to update TipologiaVeicolo : {}", tipologiaVeicolo);
        if (tipologiaVeicolo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipologiaVeicolo result = tipologiaVeicoloService.save(tipologiaVeicolo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipologiaVeicolo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipologia-veicolos} : get all the tipologiaVeicolos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipologiaVeicolos in body.
     */
    @GetMapping("/tipologia-veicolos")
    public ResponseEntity<List<TipologiaVeicolo>> getAllTipologiaVeicolos(TipologiaVeicoloCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipologiaVeicolos by criteria: {}", criteria);
        Page<TipologiaVeicolo> page = tipologiaVeicoloQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipologia-veicolos/count} : count all the tipologiaVeicolos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipologia-veicolos/count")
    public ResponseEntity<Long> countTipologiaVeicolos(TipologiaVeicoloCriteria criteria) {
        log.debug("REST request to count TipologiaVeicolos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipologiaVeicoloQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipologia-veicolos/:id} : get the "id" tipologiaVeicolo.
     *
     * @param id the id of the tipologiaVeicolo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipologiaVeicolo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipologia-veicolos/{id}")
    public ResponseEntity<TipologiaVeicolo> getTipologiaVeicolo(@PathVariable Long id) {
        log.debug("REST request to get TipologiaVeicolo : {}", id);
        Optional<TipologiaVeicolo> tipologiaVeicolo = tipologiaVeicoloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipologiaVeicolo);
    }

    /**
     * {@code DELETE  /tipologia-veicolos/:id} : delete the "id" tipologiaVeicolo.
     *
     * @param id the id of the tipologiaVeicolo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipologia-veicolos/{id}")
    public ResponseEntity<Void> deleteTipologiaVeicolo(@PathVariable Long id) {
        log.debug("REST request to delete TipologiaVeicolo : {}", id);
        tipologiaVeicoloService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
