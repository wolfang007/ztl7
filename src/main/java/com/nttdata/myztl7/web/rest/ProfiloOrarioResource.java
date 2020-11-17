package com.nttdata.myztl7.web.rest;

import com.nttdata.myztl7.domain.ProfiloOrario;
import com.nttdata.myztl7.service.ProfiloOrarioQueryService;
import com.nttdata.myztl7.service.ProfiloOrarioService;
import com.nttdata.myztl7.service.dto.ProfiloOrarioCriteria;
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
 * REST controller for managing {@link com.nttdata.myztl7.domain.ProfiloOrario}.
 */
@RestController
@RequestMapping("/api")
public class ProfiloOrarioResource {
    private final Logger log = LoggerFactory.getLogger(ProfiloOrarioResource.class);

    private static final String ENTITY_NAME = "profiloOrario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfiloOrarioService profiloOrarioService;

    private final ProfiloOrarioQueryService profiloOrarioQueryService;

    public ProfiloOrarioResource(ProfiloOrarioService profiloOrarioService, ProfiloOrarioQueryService profiloOrarioQueryService) {
        this.profiloOrarioService = profiloOrarioService;
        this.profiloOrarioQueryService = profiloOrarioQueryService;
    }

    /**
     * {@code POST  /profilo-orarios} : Create a new profiloOrario.
     *
     * @param profiloOrario the profiloOrario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profiloOrario, or with status {@code 400 (Bad Request)} if the profiloOrario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profilo-orarios")
    public ResponseEntity<ProfiloOrario> createProfiloOrario(@Valid @RequestBody ProfiloOrario profiloOrario) throws URISyntaxException {
        log.debug("REST request to save ProfiloOrario : {}", profiloOrario);
        if (profiloOrario.getId() != null) {
            throw new BadRequestAlertException("A new profiloOrario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfiloOrario result = profiloOrarioService.save(profiloOrario);
        return ResponseEntity
            .created(new URI("/api/profilo-orarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profilo-orarios} : Updates an existing profiloOrario.
     *
     * @param profiloOrario the profiloOrario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profiloOrario,
     * or with status {@code 400 (Bad Request)} if the profiloOrario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profiloOrario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profilo-orarios")
    public ResponseEntity<ProfiloOrario> updateProfiloOrario(@Valid @RequestBody ProfiloOrario profiloOrario) throws URISyntaxException {
        log.debug("REST request to update ProfiloOrario : {}", profiloOrario);
        if (profiloOrario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfiloOrario result = profiloOrarioService.save(profiloOrario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profiloOrario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profilo-orarios} : get all the profiloOrarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profiloOrarios in body.
     */
    @GetMapping("/profilo-orarios")
    public ResponseEntity<List<ProfiloOrario>> getAllProfiloOrarios(ProfiloOrarioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProfiloOrarios by criteria: {}", criteria);
        Page<ProfiloOrario> page = profiloOrarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profilo-orarios/count} : count all the profiloOrarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/profilo-orarios/count")
    public ResponseEntity<Long> countProfiloOrarios(ProfiloOrarioCriteria criteria) {
        log.debug("REST request to count ProfiloOrarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(profiloOrarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /profilo-orarios/:id} : get the "id" profiloOrario.
     *
     * @param id the id of the profiloOrario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profiloOrario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profilo-orarios/{id}")
    public ResponseEntity<ProfiloOrario> getProfiloOrario(@PathVariable Long id) {
        log.debug("REST request to get ProfiloOrario : {}", id);
        Optional<ProfiloOrario> profiloOrario = profiloOrarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profiloOrario);
    }

    /**
     * {@code DELETE  /profilo-orarios/:id} : delete the "id" profiloOrario.
     *
     * @param id the id of the profiloOrario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profilo-orarios/{id}")
    public ResponseEntity<Void> deleteProfiloOrario(@PathVariable Long id) {
        log.debug("REST request to delete ProfiloOrario : {}", id);
        profiloOrarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
