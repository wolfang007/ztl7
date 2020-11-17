package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.TipologiaVeicolo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipologiaVeicolo}.
 */
public interface TipologiaVeicoloService {
    /**
     * Save a tipologiaVeicolo.
     *
     * @param tipologiaVeicolo the entity to save.
     * @return the persisted entity.
     */
    TipologiaVeicolo save(TipologiaVeicolo tipologiaVeicolo);

    /**
     * Get all the tipologiaVeicolos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipologiaVeicolo> findAll(Pageable pageable);

    /**
     * Get the "id" tipologiaVeicolo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipologiaVeicolo> findOne(Long id);

    /**
     * Delete the "id" tipologiaVeicolo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
