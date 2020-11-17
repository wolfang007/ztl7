package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.TestX;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TestX}.
 */
public interface TestXService {
    /**
     * Save a testX.
     *
     * @param testX the entity to save.
     * @return the persisted entity.
     */
    TestX save(TestX testX);

    /**
     * Get all the testXES.
     *
     * @return the list of entities.
     */
    List<TestX> findAll();

    /**
     * Get the "id" testX.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestX> findOne(Long id);

    /**
     * Delete the "id" testX.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
