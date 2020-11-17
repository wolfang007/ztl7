package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.PermessoTemporaneo;
import com.nttdata.myztl7.service.PermessoTemporaneoQueryService;
import com.nttdata.myztl7.service.PermessoTemporaneoService;
import com.nttdata.myztl7.service.dto.PermessoTemporaneoCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.PermessoTemporaneo}.
 */
@RestController
@RequestMapping("/api")
public class PermessoTemporaneoResource {
    private final Logger log = LoggerFactory.getLogger(PermessoTemporaneoResource.class);

    private static final String ENTITY_NAME = "permessoTemporaneo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermessoTemporaneoService permessoTemporaneoService;

    private final PermessoTemporaneoQueryService permessoTemporaneoQueryService;

    public PermessoTemporaneoResource(
        PermessoTemporaneoService permessoTemporaneoService,
        PermessoTemporaneoQueryService permessoTemporaneoQueryService
    ) {
        this.permessoTemporaneoService = permessoTemporaneoService;
        this.permessoTemporaneoQueryService = permessoTemporaneoQueryService;
    }

    /**
     * {@code POST  /permesso-temporaneos} : Create a new permessoTemporaneo.
     *
     * @param permessoTemporaneo the permessoTemporaneo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new permessoTemporaneo, or with status {@code 400 (Bad Request)} if the permessoTemporaneo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/permesso-temporaneos")
    public ResponseEntity<PermessoTemporaneo> createPermessoTemporaneo(@Valid @RequestBody PermessoTemporaneo permessoTemporaneo)
        throws URISyntaxException {
        log.debug("REST request to save PermessoTemporaneo : {}", permessoTemporaneo);
        if (permessoTemporaneo.getId() != null) {
            throw new BadRequestAlertException("A new permessoTemporaneo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PermessoTemporaneo result = permessoTemporaneoService.save(permessoTemporaneo);
        return ResponseEntity
            .created(new URI("/api/permesso-temporaneos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /permesso-temporaneos} : Updates an existing permessoTemporaneo.
     *
     * @param permessoTemporaneo the permessoTemporaneo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permessoTemporaneo,
     * or with status {@code 400 (Bad Request)} if the permessoTemporaneo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the permessoTemporaneo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/permesso-temporaneos")
    public ResponseEntity<PermessoTemporaneo> updatePermessoTemporaneo(@Valid @RequestBody PermessoTemporaneo permessoTemporaneo)
        throws URISyntaxException {
        log.debug("REST request to update PermessoTemporaneo : {}", permessoTemporaneo);
        if (permessoTemporaneo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PermessoTemporaneo result = permessoTemporaneoService.save(permessoTemporaneo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, permessoTemporaneo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /permesso-temporaneos} : get all the permessoTemporaneos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of permessoTemporaneos in body.
     */
    @GetMapping("/permesso-temporaneos")
    public ResponseEntity<List<PermessoTemporaneo>> getAllPermessoTemporaneos(PermessoTemporaneoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PermessoTemporaneos by criteria: {}", criteria);
        Page<PermessoTemporaneo> page = permessoTemporaneoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /permesso-temporaneos/count} : count all the permessoTemporaneos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/permesso-temporaneos/count")
    public ResponseEntity<Long> countPermessoTemporaneos(PermessoTemporaneoCriteria criteria) {
        log.debug("REST request to count PermessoTemporaneos by criteria: {}", criteria);
        return ResponseEntity.ok().body(permessoTemporaneoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /permesso-temporaneos/:id} : get the "id" permessoTemporaneo.
     *
     * @param id the id of the permessoTemporaneo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the permessoTemporaneo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/permesso-temporaneos/{id}")
    public ResponseEntity<PermessoTemporaneo> getPermessoTemporaneo(@PathVariable Long id) {
        log.debug("REST request to get PermessoTemporaneo : {}", id);
        Optional<PermessoTemporaneo> permessoTemporaneo = permessoTemporaneoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permessoTemporaneo);
    }

    /**
     * {@code DELETE  /permesso-temporaneos/:id} : delete the "id" permessoTemporaneo.
     *
     * @param id the id of the permessoTemporaneo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/permesso-temporaneos/{id}")
    public ResponseEntity<Void> deletePermessoTemporaneo(@PathVariable Long id) {
        log.debug("REST request to delete PermessoTemporaneo : {}", id);
        permessoTemporaneoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
