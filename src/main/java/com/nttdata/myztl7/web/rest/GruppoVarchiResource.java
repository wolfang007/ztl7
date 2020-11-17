package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.GruppoVarchi;
import com.nttdata.myztl7.service.GruppoVarchiQueryService;
import com.nttdata.myztl7.service.GruppoVarchiService;
import com.nttdata.myztl7.service.dto.GruppoVarchiCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.GruppoVarchi}.
 */
@RestController
@RequestMapping("/api")
public class GruppoVarchiResource {
    private final Logger log = LoggerFactory.getLogger(GruppoVarchiResource.class);

    private static final String ENTITY_NAME = "gruppoVarchi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GruppoVarchiService gruppoVarchiService;

    private final GruppoVarchiQueryService gruppoVarchiQueryService;

    public GruppoVarchiResource(GruppoVarchiService gruppoVarchiService, GruppoVarchiQueryService gruppoVarchiQueryService) {
        this.gruppoVarchiService = gruppoVarchiService;
        this.gruppoVarchiQueryService = gruppoVarchiQueryService;
    }

    /**
     * {@code POST  /gruppo-varchis} : Create a new gruppoVarchi.
     *
     * @param gruppoVarchi the gruppoVarchi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gruppoVarchi, or with status {@code 400 (Bad Request)} if the gruppoVarchi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gruppo-varchis")
    public ResponseEntity<GruppoVarchi> createGruppoVarchi(@Valid @RequestBody GruppoVarchi gruppoVarchi) throws URISyntaxException {
        log.debug("REST request to save GruppoVarchi : {}", gruppoVarchi);
        if (gruppoVarchi.getId() != null) {
            throw new BadRequestAlertException("A new gruppoVarchi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GruppoVarchi result = gruppoVarchiService.save(gruppoVarchi);
        return ResponseEntity
            .created(new URI("/api/gruppo-varchis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gruppo-varchis} : Updates an existing gruppoVarchi.
     *
     * @param gruppoVarchi the gruppoVarchi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gruppoVarchi,
     * or with status {@code 400 (Bad Request)} if the gruppoVarchi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gruppoVarchi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gruppo-varchis")
    public ResponseEntity<GruppoVarchi> updateGruppoVarchi(@Valid @RequestBody GruppoVarchi gruppoVarchi) throws URISyntaxException {
        log.debug("REST request to update GruppoVarchi : {}", gruppoVarchi);
        if (gruppoVarchi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GruppoVarchi result = gruppoVarchiService.save(gruppoVarchi);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gruppoVarchi.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gruppo-varchis} : get all the gruppoVarchis.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gruppoVarchis in body.
     */
    @GetMapping("/gruppo-varchis")
    public ResponseEntity<List<GruppoVarchi>> getAllGruppoVarchis(GruppoVarchiCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GruppoVarchis by criteria: {}", criteria);
        Page<GruppoVarchi> page = gruppoVarchiQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gruppo-varchis/count} : count all the gruppoVarchis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/gruppo-varchis/count")
    public ResponseEntity<Long> countGruppoVarchis(GruppoVarchiCriteria criteria) {
        log.debug("REST request to count GruppoVarchis by criteria: {}", criteria);
        return ResponseEntity.ok().body(gruppoVarchiQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /gruppo-varchis/:id} : get the "id" gruppoVarchi.
     *
     * @param id the id of the gruppoVarchi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gruppoVarchi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gruppo-varchis/{id}")
    public ResponseEntity<GruppoVarchi> getGruppoVarchi(@PathVariable Long id) {
        log.debug("REST request to get GruppoVarchi : {}", id);
        Optional<GruppoVarchi> gruppoVarchi = gruppoVarchiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gruppoVarchi);
    }

    /**
     * {@code DELETE  /gruppo-varchis/:id} : delete the "id" gruppoVarchi.
     *
     * @param id the id of the gruppoVarchi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gruppo-varchis/{id}")
    public ResponseEntity<Void> deleteGruppoVarchi(@PathVariable Long id) {
        log.debug("REST request to delete GruppoVarchi : {}", id);
        gruppoVarchiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
