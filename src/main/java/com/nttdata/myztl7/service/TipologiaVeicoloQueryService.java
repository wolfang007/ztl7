package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.TipologiaVeicolo;
import com.nttdata.myztl7.repository.TipologiaVeicoloRepository;
import com.nttdata.myztl7.service.dto.TipologiaVeicoloCriteria;
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
 * Service for executing complex queries for {@link TipologiaVeicolo} entities in the database.
 * The main input is a {@link TipologiaVeicoloCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipologiaVeicolo} or a {@link Page} of {@link TipologiaVeicolo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipologiaVeicoloQueryService extends QueryService<TipologiaVeicolo> {
    private final Logger log = LoggerFactory.getLogger(TipologiaVeicoloQueryService.class);

    private final TipologiaVeicoloRepository tipologiaVeicoloRepository;

    public TipologiaVeicoloQueryService(TipologiaVeicoloRepository tipologiaVeicoloRepository) {
        this.tipologiaVeicoloRepository = tipologiaVeicoloRepository;
    }

    /**
     * Return a {@link List} of {@link TipologiaVeicolo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipologiaVeicolo> findByCriteria(TipologiaVeicoloCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipologiaVeicolo> specification = createSpecification(criteria);
        return tipologiaVeicoloRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipologiaVeicolo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipologiaVeicolo> findByCriteria(TipologiaVeicoloCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipologiaVeicolo> specification = createSpecification(criteria);
        return tipologiaVeicoloRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipologiaVeicoloCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipologiaVeicolo> specification = createSpecification(criteria);
        return tipologiaVeicoloRepository.count(specification);
    }

    /**
     * Function to convert {@link TipologiaVeicoloCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipologiaVeicolo> createSpecification(TipologiaVeicoloCriteria criteria) {
        Specification<TipologiaVeicolo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipologiaVeicolo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), TipologiaVeicolo_.nome));
            }
            if (criteria.getStato() != null) {
                specification = specification.and(buildSpecification(criteria.getStato(), TipologiaVeicolo_.stato));
            }
            if (criteria.getRegolaOrariaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRegolaOrariaId(),
                            root -> root.join(TipologiaVeicolo_.regolaOrarias, JoinType.LEFT).get(RegolaOraria_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
