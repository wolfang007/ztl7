package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.Autorizzazione;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Autorizzazione}.
 */
public interface AutorizzazioneService {
    /**
     * Save a autorizzazione.
     *
     * @param autorizzazione the entity to save.
     * @return the persisted entity.
     */
    Autorizzazione save(Autorizzazione autorizzazione);

    /**
     * Get all the autorizzaziones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Autorizzazione> findAll(Pageable pageable);

    /**
     * Get the "id" autorizzazione.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Autorizzazione> findOne(Long id);

    /**
     * Delete the "id" autorizzazione.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
