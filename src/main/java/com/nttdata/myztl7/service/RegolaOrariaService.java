package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.RegolaOraria;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RegolaOraria}.
 */
public interface RegolaOrariaService {
    /**
     * Save a regolaOraria.
     *
     * @param regolaOraria the entity to save.
     * @return the persisted entity.
     */
    RegolaOraria save(RegolaOraria regolaOraria);

    /**
     * Get all the regolaOrarias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegolaOraria> findAll(Pageable pageable);

    /**
     * Get all the regolaOrarias with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<RegolaOraria> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" regolaOraria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegolaOraria> findOne(Long id);

    /**
     * Delete the "id" regolaOraria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
