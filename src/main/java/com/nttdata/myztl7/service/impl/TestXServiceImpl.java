package com.nttdata.myztl7.service.impl;

import com.nttdata.myztl7.domain.TestX;
import com.nttdata.myztl7.repository.TestXRepository;
import com.nttdata.myztl7.service.TestXService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TestX}.
 */
@Service
@Transactional
public class TestXServiceImpl implements TestXService {
    private final Logger log = LoggerFactory.getLogger(TestXServiceImpl.class);

    private final TestXRepository testXRepository;

    public TestXServiceImpl(TestXRepository testXRepository) {
        this.testXRepository = testXRepository;
    }

    @Override
    public TestX save(TestX testX) {
        log.debug("Request to save TestX : {}", testX);
        return testXRepository.save(testX);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestX> findAll() {
        log.debug("Request to get all TestXES");
        return testXRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TestX> findOne(Long id) {
        log.debug("Request to get TestX : {}", id);
        return testXRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestX : {}", id);
        testXRepository.deleteById(id);
    }
}
