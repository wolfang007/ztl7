package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.DurataCosto;
import com.nttdata.myztl7.repository.DurataCostoRepository;
import com.nttdata.myztl7.service.DurataCostoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DurataCosto}.
 */
@Service
@Transactional
public class DurataCostoServiceImpl implements DurataCostoService {
    private final Logger log = LoggerFactory.getLogger(DurataCostoServiceImpl.class);

    private final DurataCostoRepository durataCostoRepository;

    public DurataCostoServiceImpl(DurataCostoRepository durataCostoRepository) {
        this.durataCostoRepository = durataCostoRepository;
    }

    @Override
    public DurataCosto save(DurataCosto durataCosto) {
        log.debug("Request to save DurataCosto : {}", durataCosto);
        return durataCostoRepository.save(durataCosto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DurataCosto> findAll(Pageable pageable) {
        log.debug("Request to get all DurataCostos");
        return durataCostoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DurataCosto> findOne(Long id) {
        log.debug("Request to get DurataCosto : {}", id);
        return durataCostoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DurataCosto : {}", id);
        durataCostoRepository.deleteById(id);
    }
}
