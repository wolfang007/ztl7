package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.TipologiaZona;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipologiaZona}.
 */
public interface TipologiaZonaService {
    /**
     * Save a tipologiaZona.
     *
     * @param tipologiaZona the entity to save.
     * @return the persisted entity.
     */
    TipologiaZona save(TipologiaZona tipologiaZona);

    /**
     * Get all the tipologiaZonas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipologiaZona> findAll(Pageable pageable);

    /**
     * Get the "id" tipologiaZona.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipologiaZona> findOne(Long id);

    /**
     * Delete the "id" tipologiaZona.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
