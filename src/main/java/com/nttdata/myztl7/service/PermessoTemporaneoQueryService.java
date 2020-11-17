package com.nttdata.myztl7.service;

import com.nttdata.myztl7.domain.*; // for static metamodels
import com.nttdata.myztl7.domain.PermessoTemporaneo;
import com.nttdata.myztl7.repository.PermessoTemporaneoRepository;
import com.nttdata.myztl7.service.dto.PermessoTemporaneoCriteria;
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
 * Service for executing complex queries for {@link PermessoTemporaneo} entities in the database.
 * The main input is a {@link PermessoTemporaneoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PermessoTemporaneo} or a {@link Page} of {@link PermessoTemporaneo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PermessoTemporaneoQueryService extends QueryService<PermessoTemporaneo> {
    private final Logger log = LoggerFactory.getLogger(PermessoTemporaneoQueryService.class);

    private final PermessoTemporaneoRepository permessoTemporaneoRepository;

    public PermessoTemporaneoQueryService(PermessoTemporaneoRepository permessoTemporaneoRepository) {
        this.permessoTemporaneoRepository = permessoTemporaneoRepository;
    }

    /**
     * Return a {@link List} of {@link PermessoTemporaneo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PermessoTemporaneo> findByCriteria(PermessoTemporaneoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PermessoTemporaneo> specification = createSpecification(criteria);
        return permessoTemporaneoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PermessoTemporaneo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PermessoTemporaneo> findByCriteria(PermessoTemporaneoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PermessoTemporaneo> specification = createSpecification(criteria);
        return permessoTemporaneoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PermessoTemporaneoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PermessoTemporaneo> specification = createSpecification(criteria);
        return permessoTemporaneoRepository.count(specification);
    }

    /**
     * Function to convert {@link PermessoTemporaneoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PermessoTemporaneo> createSpecification(PermessoTemporaneoCriteria criteria) {
        Specification<PermessoTemporaneo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PermessoTemporaneo_.id));
            }
            if (criteria.getTarga() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTarga(), PermessoTemporaneo_.targa));
            }
            if (criteria.getDomicilioDigitale() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDomicilioDigitale(), PermessoTemporaneo_.domicilioDigitale));
            }
            if (criteria.getTipoPersona() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoPersona(), PermessoTemporaneo_.tipoPersona));
            }
            if (criteria.getNomeRichiedente() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNomeRichiedente(), PermessoTemporaneo_.nomeRichiedente));
            }
            if (criteria.getCognomeRichiedente() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCognomeRichiedente(), PermessoTemporaneo_.cognomeRichiedente));
            }
            if (criteria.getRagioneSociale() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRagioneSociale(), PermessoTemporaneo_.ragioneSociale));
            }
            if (criteria.getCodiceFiscaleRichiedente() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCodiceFiscaleRichiedente(), PermessoTemporaneo_.codiceFiscaleRichiedente)
                    );
            }
            if (criteria.getPartitaIva() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartitaIva(), PermessoTemporaneo_.partitaIva));
            }
            if (criteria.getDataInizio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInizio(), PermessoTemporaneo_.dataInizio));
            }
            if (criteria.getImpostaDiBollo() != null) {
                specification = specification.and(buildSpecification(criteria.getImpostaDiBollo(), PermessoTemporaneo_.impostaDiBollo));
            }
            if (criteria.getCosto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCosto(), PermessoTemporaneo_.costo));
            }
            if (criteria.getPagato() != null) {
                specification = specification.and(buildSpecification(criteria.getPagato(), PermessoTemporaneo_.pagato));
            }
            if (criteria.getProtocollo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProtocollo(), PermessoTemporaneo_.protocollo));
            }
            if (criteria.getCalendarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCalendarioId(),
                            root -> root.join(PermessoTemporaneo_.calendario, JoinType.LEFT).get(Calendarizzazione_.id)
                        )
                    );
            }
            if (criteria.getTipoPermessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoPermessoId(),
                            root -> root.join(PermessoTemporaneo_.tipoPermesso, JoinType.LEFT).get(TipologiaPermesso_.id)
                        )
                    );
            }
            if (criteria.getDurataId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDurataId(),
                            root -> root.join(PermessoTemporaneo_.durata, JoinType.LEFT).get(DurataCosto_.id)
                        )
                    );
            }
            if (criteria.getNomeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNomeId(), root -> root.join(PermessoTemporaneo_.nome, JoinType.LEFT).get(Zona_.id))
                    );
            }
            if (criteria.getMotivazioneId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMotivazioneId(),
                            root -> root.join(PermessoTemporaneo_.motivazione, JoinType.LEFT).get(Motivazione_.id)
                        )
                    );
            }
            if (criteria.getAutorizzazioniId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAutorizzazioniId(),
                            root -> root.join(PermessoTemporaneo_.autorizzazionis, JoinType.LEFT).get(Autorizzazione_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
