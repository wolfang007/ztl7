package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.Varco;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Varco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VarcoRepository extends JpaRepository<Varco, Long>, JpaSpecificationExecutor<Varco> {}
