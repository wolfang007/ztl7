package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.ProfiloOrario;
import com.nttdata.myztl7.repository.ProfiloOrarioRepository;
import com.nttdata.myztl7.service.ProfiloOrarioService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProfiloOrario}.
 */
@Service
@Transactional
public class ProfiloOrarioServiceImpl implements ProfiloOrarioService {
    private final Logger log = LoggerFactory.getLogger(ProfiloOrarioServiceImpl.class);

    private final ProfiloOrarioRepository profiloOrarioRepository;

    public ProfiloOrarioServiceImpl(ProfiloOrarioRepository profiloOrarioRepository) {
        this.profiloOrarioRepository = profiloOrarioRepository;
    }

    @Override
    public ProfiloOrario save(ProfiloOrario profiloOrario) {
        log.debug("Request to save ProfiloOrario : {}", profiloOrario);
        return profiloOrarioRepository.save(profiloOrario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfiloOrario> findAll(Pageable pageable) {
        log.debug("Request to get all ProfiloOrarios");
        return profiloOrarioRepository.findAll(pageable);
    }

    public Page<ProfiloOrario> findAllWithEagerRelationships(Pageable pageable) {
        return profiloOrarioRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProfiloOrario> findOne(Long id) {
        log.debug("Request to get ProfiloOrario : {}", id);
        return profiloOrarioRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfiloOrario : {}", id);
        profiloOrarioRepository.deleteById(id);
    }
}
