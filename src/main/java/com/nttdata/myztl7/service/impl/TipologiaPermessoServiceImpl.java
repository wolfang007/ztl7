package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.TipologiaPermesso;
import com.nttdata.myztl7.repository.TipologiaPermessoRepository;
import com.nttdata.myztl7.service.TipologiaPermessoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipologiaPermesso}.
 */
@Service
@Transactional
public class TipologiaPermessoServiceImpl implements TipologiaPermessoService {
    private final Logger log = LoggerFactory.getLogger(TipologiaPermessoServiceImpl.class);

    private final TipologiaPermessoRepository tipologiaPermessoRepository;

    public TipologiaPermessoServiceImpl(TipologiaPermessoRepository tipologiaPermessoRepository) {
        this.tipologiaPermessoRepository = tipologiaPermessoRepository;
    }

    @Override
    public TipologiaPermesso save(TipologiaPermesso tipologiaPermesso) {
        log.debug("Request to save TipologiaPermesso : {}", tipologiaPermesso);
        return tipologiaPermessoRepository.save(tipologiaPermesso);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipologiaPermesso> findAll(Pageable pageable) {
        log.debug("Request to get all TipologiaPermessos");
        return tipologiaPermessoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipologiaPermesso> findOne(Long id) {
        log.debug("Request to get TipologiaPermesso : {}", id);
        return tipologiaPermessoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipologiaPermesso : {}", id);
        tipologiaPermessoRepository.deleteById(id);
    }
}
