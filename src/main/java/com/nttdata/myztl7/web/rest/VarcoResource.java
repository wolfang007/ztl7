package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.Varco;
import com.nttdata.myztl7.service.VarcoQueryService;
import com.nttdata.myztl7.service.VarcoService;
import com.nttdata.myztl7.service.dto.VarcoCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.Varco}.
 */
@RestController
@RequestMapping("/api")
public class VarcoResource {
    private final Logger log = LoggerFactory.getLogger(VarcoResource.class);

    private static final String ENTITY_NAME = "varco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VarcoService varcoService;

    private final VarcoQueryService varcoQueryService;

    public VarcoResource(VarcoService varcoService, VarcoQueryService varcoQueryService) {
        this.varcoService = varcoService;
        this.varcoQueryService = varcoQueryService;
    }

    /**
     * {@code POST  /varcos} : Create a new varco.
     *
     * @param varco the varco to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new varco, or with status {@code 400 (Bad Request)} if the varco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/varcos")
    public ResponseEntity<Varco> createVarco(@Valid @RequestBody Varco varco) throws URISyntaxException {
        log.debug("REST request to save Varco : {}", varco);
        if (varco.getId() != null) {
            throw new BadRequestAlertException("A new varco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Varco result = varcoService.save(varco);
        return ResponseEntity
            .created(new URI("/api/varcos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /varcos} : Updates an existing varco.
     *
     * @param varco the varco to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated varco,
     * or with status {@code 400 (Bad Request)} if the varco is not valid,
     * or with status {@code 500 (Internal Server Error)} if the varco couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/varcos")
    public ResponseEntity<Varco> updateVarco(@Valid @RequestBody Varco varco) throws URISyntaxException {
        log.debug("REST request to update Varco : {}", varco);
        if (varco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Varco result = varcoService.save(varco);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, varco.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /varcos} : get all the varcos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of varcos in body.
     */
    @GetMapping("/varcos")
    public ResponseEntity<List<Varco>> getAllVarcos(VarcoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Varcos by criteria: {}", criteria);
        Page<Varco> page = varcoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /varcos/count} : count all the varcos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/varcos/count")
    public ResponseEntity<Long> countVarcos(VarcoCriteria criteria) {
        log.debug("REST request to count Varcos by criteria: {}", criteria);
        return ResponseEntity.ok().body(varcoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /varcos/:id} : get the "id" varco.
     *
     * @param id the id of the varco to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the varco, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/varcos/{id}")
    public ResponseEntity<Varco> getVarco(@PathVariable Long id) {
        log.debug("REST request to get Varco : {}", id);
        Optional<Varco> varco = varcoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(varco);
    }

    /**
     * {@code DELETE  /varcos/:id} : delete the "id" varco.
     *
     * @param id the id of the varco to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/varcos/{id}")
    public ResponseEntity<Void> deleteVarco(@PathVariable Long id) {
        log.debug("REST request to delete Varco : {}", id);
        varcoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
