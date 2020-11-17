package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.Motivazione;
import com.nttdata.myztl7.repository.MotivazioneRepository;
import com.nttdata.myztl7.service.dto.MotivazioneCriteria;
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
 * Service for executing complex queries for {@link Motivazione} entities in the database.
 * The main input is a {@link MotivazioneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Motivazione} or a {@link Page} of {@link Motivazione} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MotivazioneQueryService extends QueryService<Motivazione> {
    private final Logger log = LoggerFactory.getLogger(MotivazioneQueryService.class);

    private final MotivazioneRepository motivazioneRepository;

    public MotivazioneQueryService(MotivazioneRepository motivazioneRepository) {
        this.motivazioneRepository = motivazioneRepository;
    }

    /**
     * Return a {@link List} of {@link Motivazione} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Motivazione> findByCriteria(MotivazioneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Motivazione> specification = createSpecification(criteria);
        return motivazioneRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Motivazione} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Motivazione> findByCriteria(MotivazioneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Motivazione> specification = createSpecification(criteria);
        return motivazioneRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MotivazioneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Motivazione> specification = createSpecification(criteria);
        return motivazioneRepository.count(specification);
    }

    /**
     * Function to convert {@link MotivazioneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Motivazione> createSpecification(MotivazioneCriteria criteria) {
        Specification<Motivazione> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Motivazione_.id));
            }
            if (criteria.getDescrizione() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescrizione(), Motivazione_.descrizione));
            }
        }
        return specification;
    }
}
