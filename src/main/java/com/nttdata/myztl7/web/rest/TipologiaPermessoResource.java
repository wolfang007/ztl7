package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.TipologiaPermesso;
import com.nttdata.myztl7.service.TipologiaPermessoQueryService;
import com.nttdata.myztl7.service.TipologiaPermessoService;
import com.nttdata.myztl7.service.dto.TipologiaPermessoCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.TipologiaPermesso}.
 */
@RestController
@RequestMapping("/api")
public class TipologiaPermessoResource {
    private final Logger log = LoggerFactory.getLogger(TipologiaPermessoResource.class);

    private static final String ENTITY_NAME = "tipologiaPermesso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipologiaPermessoService tipologiaPermessoService;

    private final TipologiaPermessoQueryService tipologiaPermessoQueryService;

    public TipologiaPermessoResource(
        TipologiaPermessoService tipologiaPermessoService,
        TipologiaPermessoQueryService tipologiaPermessoQueryService
    ) {
        this.tipologiaPermessoService = tipologiaPermessoService;
        this.tipologiaPermessoQueryService = tipologiaPermessoQueryService;
    }

    /**
     * {@code POST  /tipologia-permessos} : Create a new tipologiaPermesso.
     *
     * @param tipologiaPermesso the tipologiaPermesso to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipologiaPermesso, or with status {@code 400 (Bad Request)} if the tipologiaPermesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipologia-permessos")
    public ResponseEntity<TipologiaPermesso> createTipologiaPermesso(@Valid @RequestBody TipologiaPermesso tipologiaPermesso)
        throws URISyntaxException {
        log.debug("REST request to save TipologiaPermesso : {}", tipologiaPermesso);
        if (tipologiaPermesso.getId() != null) {
            throw new BadRequestAlertException("A new tipologiaPermesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipologiaPermesso result = tipologiaPermessoService.save(tipologiaPermesso);
        return ResponseEntity
            .created(new URI("/api/tipologia-permessos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipologia-permessos} : Updates an existing tipologiaPermesso.
     *
     * @param tipologiaPermesso the tipologiaPermesso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipologiaPermesso,
     * or with status {@code 400 (Bad Request)} if the tipologiaPermesso is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipologiaPermesso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipologia-permessos")
    public ResponseEntity<TipologiaPermesso> updateTipologiaPermesso(@Valid @RequestBody TipologiaPermesso tipologiaPermesso)
        throws URISyntaxException {
        log.debug("REST request to update TipologiaPermesso : {}", tipologiaPermesso);
        if (tipologiaPermesso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipologiaPermesso result = tipologiaPermessoService.save(tipologiaPermesso);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipologiaPermesso.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipologia-permessos} : get all the tipologiaPermessos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipologiaPermessos in body.
     */
    @GetMapping("/tipologia-permessos")
    public ResponseEntity<List<TipologiaPermesso>> getAllTipologiaPermessos(TipologiaPermessoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipologiaPermessos by criteria: {}", criteria);
        Page<TipologiaPermesso> page = tipologiaPermessoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipologia-permessos/count} : count all the tipologiaPermessos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipologia-permessos/count")
    public ResponseEntity<Long> countTipologiaPermessos(TipologiaPermessoCriteria criteria) {
        log.debug("REST request to count TipologiaPermessos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipologiaPermessoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipologia-permessos/:id} : get the "id" tipologiaPermesso.
     *
     * @param id the id of the tipologiaPermesso to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipologiaPermesso, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipologia-permessos/{id}")
    public ResponseEntity<TipologiaPermesso> getTipologiaPermesso(@PathVariable Long id) {
        log.debug("REST request to get TipologiaPermesso : {}", id);
        Optional<TipologiaPermesso> tipologiaPermesso = tipologiaPermessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipologiaPermesso);
    }

    /**
     * {@code DELETE  /tipologia-permessos/:id} : delete the "id" tipologiaPermesso.
     *
     * @param id the id of the tipologiaPermesso to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipologia-permessos/{id}")
    public ResponseEntity<Void> deleteTipologiaPermesso(@PathVariable Long id) {
        log.debug("REST request to delete TipologiaPermesso : {}", id);
        tipologiaPermessoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
