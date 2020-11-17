package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.RegolaOraria;
import com.nttdata.myztl7.repository.RegolaOrariaRepository;
import com.nttdata.myztl7.service.RegolaOrariaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RegolaOraria}.
 */
@Service
@Transactional
public class RegolaOrariaServiceImpl implements RegolaOrariaService {
    private final Logger log = LoggerFactory.getLogger(RegolaOrariaServiceImpl.class);

    private final RegolaOrariaRepository regolaOrariaRepository;

    public RegolaOrariaServiceImpl(RegolaOrariaRepository regolaOrariaRepository) {
        this.regolaOrariaRepository = regolaOrariaRepository;
    }

    @Override
    public RegolaOraria save(RegolaOraria regolaOraria) {
        log.debug("Request to save RegolaOraria : {}", regolaOraria);
        return regolaOrariaRepository.save(regolaOraria);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegolaOraria> findAll(Pageable pageable) {
        log.debug("Request to get all RegolaOrarias");
        return regolaOrariaRepository.findAll(pageable);
    }

    public Page<RegolaOraria> findAllWithEagerRelationships(Pageable pageable) {
        return regolaOrariaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegolaOraria> findOne(Long id) {
        log.debug("Request to get RegolaOraria : {}", id);
        return regolaOrariaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegolaOraria : {}", id);
        regolaOrariaRepository.deleteById(id);
    }
}
