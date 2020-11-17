package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.TipologiaVeicolo;
import com.nttdata.myztl7.repository.TipologiaVeicoloRepository;
import com.nttdata.myztl7.service.TipologiaVeicoloService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipologiaVeicolo}.
 */
@Service
@Transactional
public class TipologiaVeicoloServiceImpl implements TipologiaVeicoloService {
    private final Logger log = LoggerFactory.getLogger(TipologiaVeicoloServiceImpl.class);

    private final TipologiaVeicoloRepository tipologiaVeicoloRepository;

    public TipologiaVeicoloServiceImpl(TipologiaVeicoloRepository tipologiaVeicoloRepository) {
        this.tipologiaVeicoloRepository = tipologiaVeicoloRepository;
    }

    @Override
    public TipologiaVeicolo save(TipologiaVeicolo tipologiaVeicolo) {
        log.debug("Request to save TipologiaVeicolo : {}", tipologiaVeicolo);
        return tipologiaVeicoloRepository.save(tipologiaVeicolo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipologiaVeicolo> findAll(Pageable pageable) {
        log.debug("Request to get all TipologiaVeicolos");
        return tipologiaVeicoloRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipologiaVeicolo> findOne(Long id) {
        log.debug("Request to get TipologiaVeicolo : {}", id);
        return tipologiaVeicoloRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipologiaVeicolo : {}", id);
        tipologiaVeicoloRepository.deleteById(id);
    }
}
