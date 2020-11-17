package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.Festivita;
import com.nttdata.myztl7.repository.FestivitaRepository;
import com.nttdata.myztl7.service.dto.FestivitaCriteria;
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
 * Service for executing complex queries for {@link Festivita} entities in the database.
 * The main input is a {@link FestivitaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Festivita} or a {@link Page} of {@link Festivita} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FestivitaQueryService extends QueryService<Festivita> {
    private final Logger log = LoggerFactory.getLogger(FestivitaQueryService.class);

    private final FestivitaRepository festivitaRepository;

    public FestivitaQueryService(FestivitaRepository festivitaRepository) {
        this.festivitaRepository = festivitaRepository;
    }

    /**
     * Return a {@link List} of {@link Festivita} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Festivita> findByCriteria(FestivitaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Festivita> specification = createSpecification(criteria);
        return festivitaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Festivita} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Festivita> findByCriteria(FestivitaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Festivita> specification = createSpecification(criteria);
        return festivitaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FestivitaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Festivita> specification = createSpecification(criteria);
        return festivitaRepository.count(specification);
    }

    /**
     * Function to convert {@link FestivitaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Festivita> createSpecification(FestivitaCriteria criteria) {
        Specification<Festivita> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Festivita_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNome(), Festivita_.nome));
            }
        }
        return specification;
    }
}
