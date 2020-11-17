package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.TestX;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TestX entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestXRepository extends JpaRepository<TestX, Long> {}
