package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.ProfiloOrario;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProfiloOrario}.
 */
public interface ProfiloOrarioService {
    /**
     * Save a profiloOrario.
     *
     * @param profiloOrario the entity to save.
     * @return the persisted entity.
     */
    ProfiloOrario save(ProfiloOrario profiloOrario);

    /**
     * Get all the profiloOrarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfiloOrario> findAll(Pageable pageable);

    /**
     * Get all the profiloOrarios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ProfiloOrario> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" profiloOrario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfiloOrario> findOne(Long id);

    /**
     * Delete the "id" profiloOrario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
