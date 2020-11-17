package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.Calendarizzazione;
import com.nttdata.myztl7.repository.CalendarizzazioneRepository;
import com.nttdata.myztl7.service.CalendarizzazioneService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Calendarizzazione}.
 */
@Service
@Transactional
public class CalendarizzazioneServiceImpl implements CalendarizzazioneService {
    private final Logger log = LoggerFactory.getLogger(CalendarizzazioneServiceImpl.class);

    private final CalendarizzazioneRepository calendarizzazioneRepository;

    public CalendarizzazioneServiceImpl(CalendarizzazioneRepository calendarizzazioneRepository) {
        this.calendarizzazioneRepository = calendarizzazioneRepository;
    }

    @Override
    public Calendarizzazione save(Calendarizzazione calendarizzazione) {
        log.debug("Request to save Calendarizzazione : {}", calendarizzazione);
        return calendarizzazioneRepository.save(calendarizzazione);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Calendarizzazione> findAll(Pageable pageable) {
        log.debug("Request to get all Calendarizzaziones");
        return calendarizzazioneRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Calendarizzazione> findOne(Long id) {
        log.debug("Request to get Calendarizzazione : {}", id);
        return calendarizzazioneRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Calendarizzazione : {}", id);
        calendarizzazioneRepository.deleteById(id);
    }
}
