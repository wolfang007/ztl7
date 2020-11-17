package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.Festivita;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Festivita}.
 */
public interface FestivitaService {
    /**
     * Save a festivita.
     *
     * @param festivita the entity to save.
     * @return the persisted entity.
     */
    Festivita save(Festivita festivita);

    /**
     * Get all the festivitas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Festivita> findAll(Pageable pageable);

    /**
     * Get the "id" festivita.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Festivita> findOne(Long id);

    /**
     * Delete the "id" festivita.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
