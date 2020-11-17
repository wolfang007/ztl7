package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.DurataCosto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DurataCosto}.
 */
public interface DurataCostoService {
    /**
     * Save a durataCosto.
     *
     * @param durataCosto the entity to save.
     * @return the persisted entity.
     */
    DurataCosto save(DurataCosto durataCosto);

    /**
     * Get all the durataCostos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DurataCosto> findAll(Pageable pageable);

    /**
     * Get the "id" durataCosto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DurataCosto> findOne(Long id);

    /**
     * Delete the "id" durataCosto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
