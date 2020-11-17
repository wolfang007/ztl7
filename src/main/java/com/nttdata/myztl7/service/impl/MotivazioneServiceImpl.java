package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.Motivazione;
import com.nttdata.myztl7.repository.MotivazioneRepository;
import com.nttdata.myztl7.service.MotivazioneService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Motivazione}.
 */
@Service
@Transactional
public class MotivazioneServiceImpl implements MotivazioneService {
    private final Logger log = LoggerFactory.getLogger(MotivazioneServiceImpl.class);

    private final MotivazioneRepository motivazioneRepository;

    public MotivazioneServiceImpl(MotivazioneRepository motivazioneRepository) {
        this.motivazioneRepository = motivazioneRepository;
    }

    @Override
    public Motivazione save(Motivazione motivazione) {
        log.debug("Request to save Motivazione : {}", motivazione);
        return motivazioneRepository.save(motivazione);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Motivazione> findAll(Pageable pageable) {
        log.debug("Request to get all Motivaziones");
        return motivazioneRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Motivazione> findOne(Long id) {
        log.debug("Request to get Motivazione : {}", id);
        return motivazioneRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Motivazione : {}", id);
        motivazioneRepository.deleteById(id);
    }
}
