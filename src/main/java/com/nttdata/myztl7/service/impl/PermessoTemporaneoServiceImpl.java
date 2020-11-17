package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.PermessoTemporaneo;
import com.nttdata.myztl7.repository.PermessoTemporaneoRepository;
import com.nttdata.myztl7.service.PermessoTemporaneoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PermessoTemporaneo}.
 */
@Service
@Transactional
public class PermessoTemporaneoServiceImpl implements PermessoTemporaneoService {
    private final Logger log = LoggerFactory.getLogger(PermessoTemporaneoServiceImpl.class);

    private final PermessoTemporaneoRepository permessoTemporaneoRepository;

    public PermessoTemporaneoServiceImpl(PermessoTemporaneoRepository permessoTemporaneoRepository) {
        this.permessoTemporaneoRepository = permessoTemporaneoRepository;
    }

    @Override
    public PermessoTemporaneo save(PermessoTemporaneo permessoTemporaneo) {
        log.debug("Request to save PermessoTemporaneo : {}", permessoTemporaneo);
        return permessoTemporaneoRepository.save(permessoTemporaneo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermessoTemporaneo> findAll(Pageable pageable) {
        log.debug("Request to get all PermessoTemporaneos");
        return permessoTemporaneoRepository.findAll(pageable);
    }

    public Page<PermessoTemporaneo> findAllWithEagerRelationships(Pageable pageable) {
        return permessoTemporaneoRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PermessoTemporaneo> findOne(Long id) {
        log.debug("Request to get PermessoTemporaneo : {}", id);
        return permessoTemporaneoRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PermessoTemporaneo : {}", id);
        permessoTemporaneoRepository.deleteById(id);
    }
}
