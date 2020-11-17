package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.DurataCosto;
import com.nttdata.myztl7.repository.DurataCostoRepository;
import com.nttdata.myztl7.service.dto.DurataCostoCriteria;
import io.github.jhipster.service.QueryService;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for executing complex queries for {@link DurataCosto} entities in the database.
 * The main input is a {@link DurataCostoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DurataCosto} or a {@link Page} of {@link DurataCosto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DurataCostoQueryService extends QueryService<DurataCosto> {
    private final Logger log = LoggerFactory.getLogger(DurataCostoQueryService.class);

    private final DurataCostoRepository durataCostoRepository;

    public DurataCostoQueryService(DurataCostoRepository durataCostoRepository) {
        this.durataCostoRepository = durataCostoRepository;
    }

    /**
     * Return a {@link List} of {@link DurataCosto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DurataCosto> findByCriteria(DurataCostoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DurataCosto> specification = createSpecification(criteria);
        return durataCostoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DurataCosto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DurataCosto> findByCriteria(DurataCostoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DurataCosto> specification = createSpecification(criteria);
        return durataCostoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DurataCostoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DurataCosto> specification = createSpecification(criteria);
        return durataCostoRepository.count(specification);
    }

    /**
     * Function to convert {@link DurataCostoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DurataCosto> createSpecification(DurataCostoCriteria criteria) {
        Specification<DurataCosto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DurataCosto_.id));
            }
            if (criteria.getDurata() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDurata(), DurataCosto_.durata));
            }
            if (criteria.getDescrizione() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescrizione(), DurataCosto_.descrizione));
            }
            if (criteria.getCosto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCosto(), DurataCosto_.costo));
            }
        }
        return specification;
    }
}
