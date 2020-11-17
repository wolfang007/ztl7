package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.Varco;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Varco}.
 */
public interface VarcoService {
    /**
     * Save a varco.
     *
     * @param varco the entity to save.
     * @return the persisted entity.
     */
    Varco save(Varco varco);

    /**
     * Get all the varcos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Varco> findAll(Pageable pageable);

    /**
     * Get the "id" varco.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Varco> findOne(Long id);

    /**
     * Delete the "id" varco.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
