package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.Autorizzazione;
import com.nttdata.myztl7.repository.AutorizzazioneRepository;
import com.nttdata.myztl7.service.dto.AutorizzazioneCriteria;
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
 * Service for executing complex queries for {@link Autorizzazione} entities in the database.
 * The main input is a {@link AutorizzazioneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Autorizzazione} or a {@link Page} of {@link Autorizzazione} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutorizzazioneQueryService extends QueryService<Autorizzazione> {
    private final Logger log = LoggerFactory.getLogger(AutorizzazioneQueryService.class);

    private final AutorizzazioneRepository autorizzazioneRepository;

    public AutorizzazioneQueryService(AutorizzazioneRepository autorizzazioneRepository) {
        this.autorizzazioneRepository = autorizzazioneRepository;
    }

    /**
     * Return a {@link List} of {@link Autorizzazione} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Autorizzazione> findByCriteria(AutorizzazioneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Autorizzazione> specification = createSpecification(criteria);
        return autorizzazioneRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Autorizzazione} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Autorizzazione> findByCriteria(AutorizzazioneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Autorizzazione> specification = createSpecification(criteria);
        return autorizzazioneRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutorizzazioneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Autorizzazione> specification = createSpecification(criteria);
        return autorizzazioneRepository.count(specification);
    }

    /**
     * Function to convert {@link AutorizzazioneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Autorizzazione> createSpecification(AutorizzazioneCriteria criteria) {
        Specification<Autorizzazione> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Autorizzazione_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Autorizzazione_.nome));
            }
            if (criteria.getDescrizione() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescrizione(), Autorizzazione_.descrizione));
            }
            if (criteria.getStato() != null) {
                specification = specification.and(buildSpecification(criteria.getStato(), Autorizzazione_.stato));
            }
            if (criteria.getPermessoTemporaneoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPermessoTemporaneoId(),
                            root -> root.join(Autorizzazione_.permessoTemporaneos, JoinType.LEFT).get(PermessoTemporaneo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
