package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.RegolaOraria;
import com.nttdata.myztl7.repository.RegolaOrariaRepository;
import com.nttdata.myztl7.service.dto.RegolaOrariaCriteria;
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
 * Service for executing complex queries for {@link RegolaOraria} entities in the database.
 * The main input is a {@link RegolaOrariaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegolaOraria} or a {@link Page} of {@link RegolaOraria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegolaOrariaQueryService extends QueryService<RegolaOraria> {
    private final Logger log = LoggerFactory.getLogger(RegolaOrariaQueryService.class);

    private final RegolaOrariaRepository regolaOrariaRepository;

    public RegolaOrariaQueryService(RegolaOrariaRepository regolaOrariaRepository) {
        this.regolaOrariaRepository = regolaOrariaRepository;
    }

    /**
     * Return a {@link List} of {@link RegolaOraria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegolaOraria> findByCriteria(RegolaOrariaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegolaOraria> specification = createSpecification(criteria);
        return regolaOrariaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RegolaOraria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegolaOraria> findByCriteria(RegolaOrariaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegolaOraria> specification = createSpecification(criteria);
        return regolaOrariaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegolaOrariaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegolaOraria> specification = createSpecification(criteria);
        return regolaOrariaRepository.count(specification);
    }

    /**
     * Function to convert {@link RegolaOrariaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RegolaOraria> createSpecification(RegolaOrariaCriteria criteria) {
        Specification<RegolaOraria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RegolaOraria_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), RegolaOraria_.nome));
            }
            if (criteria.getOraInizio() != null) {
                specification = specification.and(buildSpecification(criteria.getOraInizio(), RegolaOraria_.oraInizio));
            }
            if (criteria.getOraFine() != null) {
                specification = specification.and(buildSpecification(criteria.getOraFine(), RegolaOraria_.oraFine));
            }
            if (criteria.getMinutiInizio() != null) {
                specification = specification.and(buildSpecification(criteria.getMinutiInizio(), RegolaOraria_.minutiInizio));
            }
            if (criteria.getMinutiFine() != null) {
                specification = specification.and(buildSpecification(criteria.getMinutiFine(), RegolaOraria_.minutiFine));
            }
            if (criteria.getLunedi() != null) {
                specification = specification.and(buildSpecification(criteria.getLunedi(), RegolaOraria_.lunedi));
            }
            if (criteria.getMartedi() != null) {
                specification = specification.and(buildSpecification(criteria.getMartedi(), RegolaOraria_.martedi));
            }
            if (criteria.getMercoledi() != null) {
                specification = specification.and(buildSpecification(criteria.getMercoledi(), RegolaOraria_.mercoledi));
            }
            if (criteria.getGiovedi() != null) {
                specification = specification.and(buildSpecification(criteria.getGiovedi(), RegolaOraria_.giovedi));
            }
            if (criteria.getVenerdi() != null) {
                specification = specification.and(buildSpecification(criteria.getVenerdi(), RegolaOraria_.venerdi));
            }
            if (criteria.getSabato() != null) {
                specification = specification.and(buildSpecification(criteria.getSabato(), RegolaOraria_.sabato));
            }
            if (criteria.getDomenica() != null) {
                specification = specification.and(buildSpecification(criteria.getDomenica(), RegolaOraria_.domenica));
            }
            if (criteria.getFestivi() != null) {
                specification = specification.and(buildSpecification(criteria.getFestivi(), RegolaOraria_.festivi));
            }
            if (criteria.getStato() != null) {
                specification = specification.and(buildSpecification(criteria.getStato(), RegolaOraria_.stato));
            }
            if (criteria.getTipologiaVeicoloId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipologiaVeicoloId(),
                            root -> root.join(RegolaOraria_.tipologiaVeicolos, JoinType.LEFT).get(TipologiaVeicolo_.id)
                        )
                    );
            }
            if (criteria.getProfiloOrarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProfiloOrarioId(),
                            root -> root.join(RegolaOraria_.profiloOrarios, JoinType.LEFT).get(ProfiloOrario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
