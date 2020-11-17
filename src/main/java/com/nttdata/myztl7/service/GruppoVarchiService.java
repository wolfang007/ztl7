package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.GruppoVarchi;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link GruppoVarchi}.
 */
public interface GruppoVarchiService {
    /**
     * Save a gruppoVarchi.
     *
     * @param gruppoVarchi the entity to save.
     * @return the persisted entity.
     */
    GruppoVarchi save(GruppoVarchi gruppoVarchi);

    /**
     * Get all the gruppoVarchis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GruppoVarchi> findAll(Pageable pageable);

    /**
     * Get all the gruppoVarchis with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<GruppoVarchi> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" gruppoVarchi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GruppoVarchi> findOne(Long id);

    /**
     * Delete the "id" gruppoVarchi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
