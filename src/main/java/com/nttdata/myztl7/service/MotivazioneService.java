package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.Motivazione;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Motivazione}.
 */
public interface MotivazioneService {
    /**
     * Save a motivazione.
     *
     * @param motivazione the entity to save.
     * @return the persisted entity.
     */
    Motivazione save(Motivazione motivazione);

    /**
     * Get all the motivaziones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Motivazione> findAll(Pageable pageable);

    /**
     * Get the "id" motivazione.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Motivazione> findOne(Long id);

    /**
     * Delete the "id" motivazione.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
