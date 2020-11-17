package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.GruppoVarchi;
import com.nttdata.myztl7.repository.GruppoVarchiRepository;
import com.nttdata.myztl7.service.dto.GruppoVarchiCriteria;
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
 * Service for executing complex queries for {@link GruppoVarchi} entities in the database.
 * The main input is a {@link GruppoVarchiCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GruppoVarchi} or a {@link Page} of {@link GruppoVarchi} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GruppoVarchiQueryService extends QueryService<GruppoVarchi> {
    private final Logger log = LoggerFactory.getLogger(GruppoVarchiQueryService.class);

    private final GruppoVarchiRepository gruppoVarchiRepository;

    public GruppoVarchiQueryService(GruppoVarchiRepository gruppoVarchiRepository) {
        this.gruppoVarchiRepository = gruppoVarchiRepository;
    }

    /**
     * Return a {@link List} of {@link GruppoVarchi} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GruppoVarchi> findByCriteria(GruppoVarchiCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GruppoVarchi> specification = createSpecification(criteria);
        return gruppoVarchiRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GruppoVarchi} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GruppoVarchi> findByCriteria(GruppoVarchiCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GruppoVarchi> specification = createSpecification(criteria);
        return gruppoVarchiRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GruppoVarchiCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GruppoVarchi> specification = createSpecification(criteria);
        return gruppoVarchiRepository.count(specification);
    }

    /**
     * Function to convert {@link GruppoVarchiCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GruppoVarchi> createSpecification(GruppoVarchiCriteria criteria) {
        Specification<GruppoVarchi> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GruppoVarchi_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), GruppoVarchi_.nome));
            }
            if (criteria.getDescrizione() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescrizione(), GruppoVarchi_.descrizione));
            }
            if (criteria.getDescrioneIntervalli() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDescrioneIntervalli(), GruppoVarchi_.descrioneIntervalli));
            }
            if (criteria.getStato() != null) {
                specification = specification.and(buildSpecification(criteria.getStato(), GruppoVarchi_.stato));
            }
            if (criteria.getPosizioneId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPosizioneId(),
                            root -> root.join(GruppoVarchi_.posiziones, JoinType.LEFT).get(Varco_.id)
                        )
                    );
            }
            if (criteria.getZonaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getZonaId(), root -> root.join(GruppoVarchi_.zonas, JoinType.LEFT).get(Zona_.id))
                    );
            }
        }
        return specification;
    }
}
