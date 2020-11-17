package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.TipologiaZona;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipologiaZona entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipologiaZonaRepository extends JpaRepository<TipologiaZona, Long>, JpaSpecificationExecutor<TipologiaZona> {}
