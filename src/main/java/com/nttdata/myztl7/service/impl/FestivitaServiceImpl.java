package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.Festivita;
import com.nttdata.myztl7.repository.FestivitaRepository;
import com.nttdata.myztl7.service.FestivitaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Festivita}.
 */
@Service
@Transactional
public class FestivitaServiceImpl implements FestivitaService {
    private final Logger log = LoggerFactory.getLogger(FestivitaServiceImpl.class);

    private final FestivitaRepository festivitaRepository;

    public FestivitaServiceImpl(FestivitaRepository festivitaRepository) {
        this.festivitaRepository = festivitaRepository;
    }

    @Override
    public Festivita save(Festivita festivita) {
        log.debug("Request to save Festivita : {}", festivita);
        return festivitaRepository.save(festivita);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Festivita> findAll(Pageable pageable) {
        log.debug("Request to get all Festivitas");
        return festivitaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Festivita> findOne(Long id) {
        log.debug("Request to get Festivita : {}", id);
        return festivitaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Festivita : {}", id);
        festivitaRepository.deleteById(id);
    }
}
