package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.TipologiaPermesso;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipologiaPermesso}.
 */
public interface TipologiaPermessoService {
    /**
     * Save a tipologiaPermesso.
     *
     * @param tipologiaPermesso the entity to save.
     * @return the persisted entity.
     */
    TipologiaPermesso save(TipologiaPermesso tipologiaPermesso);

    /**
     * Get all the tipologiaPermessos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipologiaPermesso> findAll(Pageable pageable);

    /**
     * Get the "id" tipologiaPermesso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipologiaPermesso> findOne(Long id);

    /**
     * Delete the "id" tipologiaPermesso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
