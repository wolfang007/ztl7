package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.Varco;
import com.nttdata.myztl7.repository.VarcoRepository;
import com.nttdata.myztl7.service.VarcoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Varco}.
 */
@Service
@Transactional
public class VarcoServiceImpl implements VarcoService {
    private final Logger log = LoggerFactory.getLogger(VarcoServiceImpl.class);

    private final VarcoRepository varcoRepository;

    public VarcoServiceImpl(VarcoRepository varcoRepository) {
        this.varcoRepository = varcoRepository;
    }

    @Override
    public Varco save(Varco varco) {
        log.debug("Request to save Varco : {}", varco);
        return varcoRepository.save(varco);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Varco> findAll(Pageable pageable) {
        log.debug("Request to get all Varcos");
        return varcoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Varco> findOne(Long id) {
        log.debug("Request to get Varco : {}", id);
        return varcoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Varco : {}", id);
        varcoRepository.deleteById(id);
    }
}
