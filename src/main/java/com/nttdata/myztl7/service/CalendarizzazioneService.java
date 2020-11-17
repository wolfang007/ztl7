package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.Calendarizzazione;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Calendarizzazione}.
 */
public interface CalendarizzazioneService {
    /**
     * Save a calendarizzazione.
     *
     * @param calendarizzazione the entity to save.
     * @return the persisted entity.
     */
    Calendarizzazione save(Calendarizzazione calendarizzazione);

    /**
     * Get all the calendarizzaziones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Calendarizzazione> findAll(Pageable pageable);

    /**
     * Get the "id" calendarizzazione.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Calendarizzazione> findOne(Long id);

    /**
     * Delete the "id" calendarizzazione.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
