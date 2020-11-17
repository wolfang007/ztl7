package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.GruppoVarchi;
import com.nttdata.myztl7.repository.GruppoVarchiRepository;
import com.nttdata.myztl7.service.GruppoVarchiService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GruppoVarchi}.
 */
@Service
@Transactional
public class GruppoVarchiServiceImpl implements GruppoVarchiService {
    private final Logger log = LoggerFactory.getLogger(GruppoVarchiServiceImpl.class);

    private final GruppoVarchiRepository gruppoVarchiRepository;

    public GruppoVarchiServiceImpl(GruppoVarchiRepository gruppoVarchiRepository) {
        this.gruppoVarchiRepository = gruppoVarchiRepository;
    }

    @Override
    public GruppoVarchi save(GruppoVarchi gruppoVarchi) {
        log.debug("Request to save GruppoVarchi : {}", gruppoVarchi);
        return gruppoVarchiRepository.save(gruppoVarchi);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GruppoVarchi> findAll(Pageable pageable) {
        log.debug("Request to get all GruppoVarchis");
        return gruppoVarchiRepository.findAll(pageable);
    }

    public Page<GruppoVarchi> findAllWithEagerRelationships(Pageable pageable) {
        return gruppoVarchiRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GruppoVarchi> findOne(Long id) {
        log.debug("Request to get GruppoVarchi : {}", id);
        return gruppoVarchiRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GruppoVarchi : {}", id);
        gruppoVarchiRepository.deleteById(id);
    }
}
