package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.Zona;
import com.nttdata.myztl7.service.ZonaQueryService;
import com.nttdata.myztl7.service.ZonaService;
import com.nttdata.myztl7.service.dto.ZonaCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.Zona}.
 */
@RestController
@RequestMapping("/api")
public class ZonaResource {
    private final Logger log = LoggerFactory.getLogger(ZonaResource.class);

    private static final String ENTITY_NAME = "zona";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZonaService zonaService;

    private final ZonaQueryService zonaQueryService;

    public ZonaResource(ZonaService zonaService, ZonaQueryService zonaQueryService) {
        this.zonaService = zonaService;
        this.zonaQueryService = zonaQueryService;
    }

    /**
     * {@code POST  /zonas} : Create a new zona.
     *
     * @param zona the zona to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zona, or with status {@code 400 (Bad Request)} if the zona has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zonas")
    public ResponseEntity<Zona> createZona(@Valid @RequestBody Zona zona) throws URISyntaxException {
        log.debug("REST request to save Zona : {}", zona);
        if (zona.getId() != null) {
            throw new BadRequestAlertException("A new zona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Zona result = zonaService.save(zona);
        return ResponseEntity
            .created(new URI("/api/zonas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zonas} : Updates an existing zona.
     *
     * @param zona the zona to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zona,
     * or with status {@code 400 (Bad Request)} if the zona is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zona couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zonas")
    public ResponseEntity<Zona> updateZona(@Valid @RequestBody Zona zona) throws URISyntaxException {
        log.debug("REST request to update Zona : {}", zona);
        if (zona.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Zona result = zonaService.save(zona);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zona.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /zonas} : get all the zonas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zonas in body.
     */
    @GetMapping("/zonas")
    public ResponseEntity<List<Zona>> getAllZonas(ZonaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Zonas by criteria: {}", criteria);
        Page<Zona> page = zonaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /zonas/count} : count all the zonas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/zonas/count")
    public ResponseEntity<Long> countZonas(ZonaCriteria criteria) {
        log.debug("REST request to count Zonas by criteria: {}", criteria);
        return ResponseEntity.ok().body(zonaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /zonas/:id} : get the "id" zona.
     *
     * @param id the id of the zona to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zona, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zonas/{id}")
    public ResponseEntity<Zona> getZona(@PathVariable Long id) {
        log.debug("REST request to get Zona : {}", id);
        Optional<Zona> zona = zonaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zona);
    }

    /**
     * {@code DELETE  /zonas/:id} : delete the "id" zona.
     *
     * @param id the id of the zona to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zonas/{id}")
    public ResponseEntity<Void> deleteZona(@PathVariable Long id) {
        log.debug("REST request to delete Zona : {}", id);
        zonaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
