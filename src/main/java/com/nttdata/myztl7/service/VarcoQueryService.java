package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.Varco;
import com.nttdata.myztl7.repository.VarcoRepository;
import com.nttdata.myztl7.service.dto.VarcoCriteria;
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
 * Service for executing complex queries for {@link Varco} entities in the database.
 * The main input is a {@link VarcoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Varco} or a {@link Page} of {@link Varco} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VarcoQueryService extends QueryService<Varco> {
    private final Logger log = LoggerFactory.getLogger(VarcoQueryService.class);

    private final VarcoRepository varcoRepository;

    public VarcoQueryService(VarcoRepository varcoRepository) {
        this.varcoRepository = varcoRepository;
    }

    /**
     * Return a {@link List} of {@link Varco} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Varco> findByCriteria(VarcoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Varco> specification = createSpecification(criteria);
        return varcoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Varco} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Varco> findByCriteria(VarcoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Varco> specification = createSpecification(criteria);
        return varcoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VarcoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Varco> specification = createSpecification(criteria);
        return varcoRepository.count(specification);
    }

    /**
     * Function to convert {@link VarcoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Varco> createSpecification(VarcoCriteria criteria) {
        Specification<Varco> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Varco_.id));
            }
            if (criteria.getCodice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodice(), Varco_.codice));
            }
            if (criteria.getDescrizionePosizione() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDescrizionePosizione(), Varco_.descrizionePosizione));
            }
            if (criteria.getStato() != null) {
                specification = specification.and(buildSpecification(criteria.getStato(), Varco_.stato));
            }
            if (criteria.getGruppoVarchiId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGruppoVarchiId(),
                            root -> root.join(Varco_.gruppoVarchis, JoinType.LEFT).get(GruppoVarchi_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
