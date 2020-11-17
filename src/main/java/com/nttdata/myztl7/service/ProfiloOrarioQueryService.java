package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.ProfiloOrario;
import com.nttdata.myztl7.repository.ProfiloOrarioRepository;
import com.nttdata.myztl7.service.dto.ProfiloOrarioCriteria;
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
 * Service for executing complex queries for {@link ProfiloOrario} entities in the database.
 * The main input is a {@link ProfiloOrarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProfiloOrario} or a {@link Page} of {@link ProfiloOrario} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProfiloOrarioQueryService extends QueryService<ProfiloOrario> {
    private final Logger log = LoggerFactory.getLogger(ProfiloOrarioQueryService.class);

    private final ProfiloOrarioRepository profiloOrarioRepository;

    public ProfiloOrarioQueryService(ProfiloOrarioRepository profiloOrarioRepository) {
        this.profiloOrarioRepository = profiloOrarioRepository;
    }

    /**
     * Return a {@link List} of {@link ProfiloOrario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProfiloOrario> findByCriteria(ProfiloOrarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProfiloOrario> specification = createSpecification(criteria);
        return profiloOrarioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProfiloOrario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfiloOrario> findByCriteria(ProfiloOrarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProfiloOrario> specification = createSpecification(criteria);
        return profiloOrarioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProfiloOrarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProfiloOrario> specification = createSpecification(criteria);
        return profiloOrarioRepository.count(specification);
    }

    /**
     * Function to convert {@link ProfiloOrarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProfiloOrario> createSpecification(ProfiloOrarioCriteria criteria) {
        Specification<ProfiloOrario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProfiloOrario_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), ProfiloOrario_.nome));
            }
            if (criteria.getStato() != null) {
                specification = specification.and(buildSpecification(criteria.getStato(), ProfiloOrario_.stato));
            }
            if (criteria.getRegolaOrariaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRegolaOrariaId(),
                            root -> root.join(ProfiloOrario_.regolaOrarias, JoinType.LEFT).get(RegolaOraria_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
