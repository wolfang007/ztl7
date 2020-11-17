package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.TipologiaZona;
import com.nttdata.myztl7.repository.TipologiaZonaRepository;
import com.nttdata.myztl7.service.TipologiaZonaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipologiaZona}.
 */
@Service
@Transactional
public class TipologiaZonaServiceImpl implements TipologiaZonaService {
    private final Logger log = LoggerFactory.getLogger(TipologiaZonaServiceImpl.class);

    private final TipologiaZonaRepository tipologiaZonaRepository;

    public TipologiaZonaServiceImpl(TipologiaZonaRepository tipologiaZonaRepository) {
        this.tipologiaZonaRepository = tipologiaZonaRepository;
    }

    @Override
    public TipologiaZona save(TipologiaZona tipologiaZona) {
        log.debug("Request to save TipologiaZona : {}", tipologiaZona);
        return tipologiaZonaRepository.save(tipologiaZona);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipologiaZona> findAll(Pageable pageable) {
        log.debug("Request to get all TipologiaZonas");
        return tipologiaZonaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipologiaZona> findOne(Long id) {
        log.debug("Request to get TipologiaZona : {}", id);
        return tipologiaZonaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipologiaZona : {}", id);
        tipologiaZonaRepository.deleteById(id);
    }
}
