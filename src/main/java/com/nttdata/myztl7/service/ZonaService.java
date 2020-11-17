package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.Zona;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Zona}.
 */
public interface ZonaService {
    /**
     * Save a zona.
     *
     * @param zona the entity to save.
     * @return the persisted entity.
     */
    Zona save(Zona zona);

    /**
     * Get all the zonas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Zona> findAll(Pageable pageable);

    /**
     * Get all the zonas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Zona> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" zona.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Zona> findOne(Long id);

    /**
     * Delete the "id" zona.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
