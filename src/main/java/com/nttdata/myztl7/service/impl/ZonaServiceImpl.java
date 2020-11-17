package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.Zona;
import com.nttdata.myztl7.repository.ZonaRepository;
import com.nttdata.myztl7.service.ZonaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Zona}.
 */
@Service
@Transactional
public class ZonaServiceImpl implements ZonaService {
    private final Logger log = LoggerFactory.getLogger(ZonaServiceImpl.class);

    private final ZonaRepository zonaRepository;

    public ZonaServiceImpl(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    @Override
    public Zona save(Zona zona) {
        log.debug("Request to save Zona : {}", zona);
        return zonaRepository.save(zona);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Zona> findAll(Pageable pageable) {
        log.debug("Request to get all Zonas");
        return zonaRepository.findAll(pageable);
    }

    public Page<Zona> findAllWithEagerRelationships(Pageable pageable) {
        return zonaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Zona> findOne(Long id) {
        log.debug("Request to get Zona : {}", id);
        return zonaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Zona : {}", id);
        zonaRepository.deleteById(id);
    }
}
