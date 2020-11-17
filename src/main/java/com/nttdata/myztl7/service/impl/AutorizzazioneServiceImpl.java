package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.Autorizzazione;
import com.nttdata.myztl7.repository.AutorizzazioneRepository;
import com.nttdata.myztl7.service.AutorizzazioneService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Autorizzazione}.
 */
@Service
@Transactional
public class AutorizzazioneServiceImpl implements AutorizzazioneService {
    private final Logger log = LoggerFactory.getLogger(AutorizzazioneServiceImpl.class);

    private final AutorizzazioneRepository autorizzazioneRepository;

    public AutorizzazioneServiceImpl(AutorizzazioneRepository autorizzazioneRepository) {
        this.autorizzazioneRepository = autorizzazioneRepository;
    }

    @Override
    public Autorizzazione save(Autorizzazione autorizzazione) {
        log.debug("Request to save Autorizzazione : {}", autorizzazione);
        return autorizzazioneRepository.save(autorizzazione);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Autorizzazione> findAll(Pageable pageable) {
        log.debug("Request to get all Autorizzaziones");
        return autorizzazioneRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Autorizzazione> findOne(Long id) {
        log.debug("Request to get Autorizzazione : {}", id);
        return autorizzazioneRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Autorizzazione : {}", id);
        autorizzazioneRepository.deleteById(id);
    }
}
