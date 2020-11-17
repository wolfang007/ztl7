package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.TipologiaZona;
import com.nttdata.myztl7.repository.TipologiaZonaRepository;
import com.nttdata.myztl7.service.dto.TipologiaZonaCriteria;
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
 * Service for executing complex queries for {@link TipologiaZona} entities in the database.
 * The main input is a {@link TipologiaZonaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipologiaZona} or a {@link Page} of {@link TipologiaZona} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipologiaZonaQueryService extends QueryService<TipologiaZona> {
    private final Logger log = LoggerFactory.getLogger(TipologiaZonaQueryService.class);

    private final TipologiaZonaRepository tipologiaZonaRepository;

    public TipologiaZonaQueryService(TipologiaZonaRepository tipologiaZonaRepository) {
        this.tipologiaZonaRepository = tipologiaZonaRepository;
    }

    /**
     * Return a {@link List} of {@link TipologiaZona} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipologiaZona> findByCriteria(TipologiaZonaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipologiaZona> specification = createSpecification(criteria);
        return tipologiaZonaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipologiaZona} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipologiaZona> findByCriteria(TipologiaZonaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipologiaZona> specification = createSpecification(criteria);
        return tipologiaZonaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipologiaZonaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipologiaZona> specification = createSpecification(criteria);
        return tipologiaZonaRepository.count(specification);
    }

    /**
     * Function to convert {@link TipologiaZonaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipologiaZona> createSpecification(TipologiaZonaCriteria criteria) {
        Specification<TipologiaZona> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipologiaZona_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), TipologiaZona_.nome));
            }
            if (criteria.getStato() != null) {
                specification = specification.and(buildSpecification(criteria.getStato(), TipologiaZona_.stato));
            }
        }
        return specification;
    }
}
