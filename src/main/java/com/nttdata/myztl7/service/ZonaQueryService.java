package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.Zona;
import com.nttdata.myztl7.repository.ZonaRepository;
import com.nttdata.myztl7.service.dto.ZonaCriteria;
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
 * Service for executing complex queries for {@link Zona} entities in the database.
 * The main input is a {@link ZonaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Zona} or a {@link Page} of {@link Zona} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZonaQueryService extends QueryService<Zona> {
    private final Logger log = LoggerFactory.getLogger(ZonaQueryService.class);

    private final ZonaRepository zonaRepository;

    public ZonaQueryService(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    /**
     * Return a {@link List} of {@link Zona} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Zona> findByCriteria(ZonaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Zona> specification = createSpecification(criteria);
        return zonaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Zona} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Zona> findByCriteria(ZonaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Zona> specification = createSpecification(criteria);
        return zonaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ZonaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Zona> specification = createSpecification(criteria);
        return zonaRepository.count(specification);
    }

    /**
     * Function to convert {@link ZonaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Zona> createSpecification(ZonaCriteria criteria) {
        Specification<Zona> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Zona_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Zona_.nome));
            }
            if (criteria.getStato() != null) {
                specification = specification.and(buildSpecification(criteria.getStato(), Zona_.stato));
            }
            if (criteria.getProfiloOrarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProfiloOrarioId(),
                            root -> root.join(Zona_.profiloOrario, JoinType.LEFT).get(ProfiloOrario_.id)
                        )
                    );
            }
            if (criteria.getTipologiaZonaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipologiaZonaId(),
                            root -> root.join(Zona_.tipologiaZona, JoinType.LEFT).get(TipologiaZona_.id)
                        )
                    );
            }
            if (criteria.getGruppoVarchiId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGruppoVarchiId(),
                            root -> root.join(Zona_.gruppoVarchis, JoinType.LEFT).get(GruppoVarchi_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
