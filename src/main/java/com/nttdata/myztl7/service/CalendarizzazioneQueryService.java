package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.Calendarizzazione;
import com.nttdata.myztl7.repository.CalendarizzazioneRepository;
import com.nttdata.myztl7.service.dto.CalendarizzazioneCriteria;
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
 * Service for executing complex queries for {@link Calendarizzazione} entities in the database.
 * The main input is a {@link CalendarizzazioneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Calendarizzazione} or a {@link Page} of {@link Calendarizzazione} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CalendarizzazioneQueryService extends QueryService<Calendarizzazione> {
    private final Logger log = LoggerFactory.getLogger(CalendarizzazioneQueryService.class);

    private final CalendarizzazioneRepository calendarizzazioneRepository;

    public CalendarizzazioneQueryService(CalendarizzazioneRepository calendarizzazioneRepository) {
        this.calendarizzazioneRepository = calendarizzazioneRepository;
    }

    /**
     * Return a {@link List} of {@link Calendarizzazione} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Calendarizzazione> findByCriteria(CalendarizzazioneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Calendarizzazione> specification = createSpecification(criteria);
        return calendarizzazioneRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Calendarizzazione} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Calendarizzazione> findByCriteria(CalendarizzazioneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Calendarizzazione> specification = createSpecification(criteria);
        return calendarizzazioneRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CalendarizzazioneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Calendarizzazione> specification = createSpecification(criteria);
        return calendarizzazioneRepository.count(specification);
    }

    /**
     * Function to convert {@link CalendarizzazioneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Calendarizzazione> createSpecification(CalendarizzazioneCriteria criteria) {
        Specification<Calendarizzazione> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Calendarizzazione_.id));
            }
            if (criteria.getLunedi() != null) {
                specification = specification.and(buildSpecification(criteria.getLunedi(), Calendarizzazione_.lunedi));
            }
            if (criteria.getMartedi() != null) {
                specification = specification.and(buildSpecification(criteria.getMartedi(), Calendarizzazione_.martedi));
            }
            if (criteria.getMercoledi() != null) {
                specification = specification.and(buildSpecification(criteria.getMercoledi(), Calendarizzazione_.mercoledi));
            }
            if (criteria.getGiovedi() != null) {
                specification = specification.and(buildSpecification(criteria.getGiovedi(), Calendarizzazione_.giovedi));
            }
            if (criteria.getVenerdi() != null) {
                specification = specification.and(buildSpecification(criteria.getVenerdi(), Calendarizzazione_.venerdi));
            }
            if (criteria.getSabato() != null) {
                specification = specification.and(buildSpecification(criteria.getSabato(), Calendarizzazione_.sabato));
            }
            if (criteria.getDomenica() != null) {
                specification = specification.and(buildSpecification(criteria.getDomenica(), Calendarizzazione_.domenica));
            }
            if (criteria.getSiRipete() != null) {
                specification = specification.and(buildSpecification(criteria.getSiRipete(), Calendarizzazione_.siRipete));
            }
            if (criteria.getFestivi() != null) {
                specification = specification.and(buildSpecification(criteria.getFestivi(), Calendarizzazione_.festivi));
            }
        }
        return specification;
    }
}
