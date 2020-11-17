package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.PermessoTemporaneo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PermessoTemporaneo}.
 */
public interface PermessoTemporaneoService {
    /**
     * Save a permessoTemporaneo.
     *
     * @param permessoTemporaneo the entity to save.
     * @return the persisted entity.
     */
    PermessoTemporaneo save(PermessoTemporaneo permessoTemporaneo);

    /**
     * Get all the permessoTemporaneos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PermessoTemporaneo> findAll(Pageable pageable);

    /**
     * Get all the permessoTemporaneos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PermessoTemporaneo> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" permessoTemporaneo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PermessoTemporaneo> findOne(Long id);

    /**
     * Delete the "id" permessoTemporaneo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
