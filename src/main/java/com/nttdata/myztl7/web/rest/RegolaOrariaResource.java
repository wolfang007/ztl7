package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.RegolaOraria;
import com.nttdata.myztl7.service.RegolaOrariaQueryService;
import com.nttdata.myztl7.service.RegolaOrariaService;
import com.nttdata.myztl7.service.dto.RegolaOrariaCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.RegolaOraria}.
 */
@RestController
@RequestMapping("/api")
public class RegolaOrariaResource {
    private final Logger log = LoggerFactory.getLogger(RegolaOrariaResource.class);

    private static final String ENTITY_NAME = "regolaOraria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegolaOrariaService regolaOrariaService;

    private final RegolaOrariaQueryService regolaOrariaQueryService;

    public RegolaOrariaResource(RegolaOrariaService regolaOrariaService, RegolaOrariaQueryService regolaOrariaQueryService) {
        this.regolaOrariaService = regolaOrariaService;
        this.regolaOrariaQueryService = regolaOrariaQueryService;
    }

    /**
     * {@code POST  /regola-orarias} : Create a new regolaOraria.
     *
     * @param regolaOraria the regolaOraria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regolaOraria, or with status {@code 400 (Bad Request)} if the regolaOraria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regola-orarias")
    public ResponseEntity<RegolaOraria> createRegolaOraria(@Valid @RequestBody RegolaOraria regolaOraria) throws URISyntaxException {
        log.debug("REST request to save RegolaOraria : {}", regolaOraria);
        if (regolaOraria.getId() != null) {
            throw new BadRequestAlertException("A new regolaOraria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegolaOraria result = regolaOrariaService.save(regolaOraria);
        return ResponseEntity
            .created(new URI("/api/regola-orarias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regola-orarias} : Updates an existing regolaOraria.
     *
     * @param regolaOraria the regolaOraria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regolaOraria,
     * or with status {@code 400 (Bad Request)} if the regolaOraria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regolaOraria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regola-orarias")
    public ResponseEntity<RegolaOraria> updateRegolaOraria(@Valid @RequestBody RegolaOraria regolaOraria) throws URISyntaxException {
        log.debug("REST request to update RegolaOraria : {}", regolaOraria);
        if (regolaOraria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegolaOraria result = regolaOrariaService.save(regolaOraria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regolaOraria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regola-orarias} : get all the regolaOrarias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regolaOrarias in body.
     */
    @GetMapping("/regola-orarias")
    public ResponseEntity<List<RegolaOraria>> getAllRegolaOrarias(RegolaOrariaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RegolaOrarias by criteria: {}", criteria);
        Page<RegolaOraria> page = regolaOrariaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regola-orarias/count} : count all the regolaOrarias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/regola-orarias/count")
    public ResponseEntity<Long> countRegolaOrarias(RegolaOrariaCriteria criteria) {
        log.debug("REST request to count RegolaOrarias by criteria: {}", criteria);
        return ResponseEntity.ok().body(regolaOrariaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /regola-orarias/:id} : get the "id" regolaOraria.
     *
     * @param id the id of the regolaOraria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regolaOraria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regola-orarias/{id}")
    public ResponseEntity<RegolaOraria> getRegolaOraria(@PathVariable Long id) {
        log.debug("REST request to get RegolaOraria : {}", id);
        Optional<RegolaOraria> regolaOraria = regolaOrariaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regolaOraria);
    }

    /**
     * {@code DELETE  /regola-orarias/:id} : delete the "id" regolaOraria.
     *
     * @param id the id of the regolaOraria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regola-orarias/{id}")
    public ResponseEntity<Void> deleteRegolaOraria(@PathVariable Long id) {
        log.debug("REST request to delete RegolaOraria : {}", id);
        regolaOrariaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
