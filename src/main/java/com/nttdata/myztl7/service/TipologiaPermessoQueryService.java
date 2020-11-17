package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.TipologiaPermesso;
import com.nttdata.myztl7.repository.TipologiaPermessoRepository;
import com.nttdata.myztl7.service.dto.TipologiaPermessoCriteria;
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
 * Service for executing complex queries for {@link TipologiaPermesso} entities in the database.
 * The main input is a {@link TipologiaPermessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipologiaPermesso} or a {@link Page} of {@link TipologiaPermesso} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipologiaPermessoQueryService extends QueryService<TipologiaPermesso> {
    private final Logger log = LoggerFactory.getLogger(TipologiaPermessoQueryService.class);

    private final TipologiaPermessoRepository tipologiaPermessoRepository;

    public TipologiaPermessoQueryService(TipologiaPermessoRepository tipologiaPermessoRepository) {
        this.tipologiaPermessoRepository = tipologiaPermessoRepository;
    }

    /**
     * Return a {@link List} of {@link TipologiaPermesso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipologiaPermesso> findByCriteria(TipologiaPermessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipologiaPermesso> specification = createSpecification(criteria);
        return tipologiaPermessoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipologiaPermesso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipologiaPermesso> findByCriteria(TipologiaPermessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipologiaPermesso> specification = createSpecification(criteria);
        return tipologiaPermessoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipologiaPermessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipologiaPermesso> specification = createSpecification(criteria);
        return tipologiaPermessoRepository.count(specification);
    }

    /**
     * Function to convert {@link TipologiaPermessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipologiaPermesso> createSpecification(TipologiaPermessoCriteria criteria) {
        Specification<TipologiaPermesso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipologiaPermesso_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), TipologiaPermesso_.nome));
            }
            if (criteria.getCodice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodice(), TipologiaPermesso_.codice));
            }
        }
        return specification;
    }
}
