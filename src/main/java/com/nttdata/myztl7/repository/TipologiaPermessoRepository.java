package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.TipologiaPermesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipologiaPermesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipologiaPermessoRepository extends JpaRepository<TipologiaPermesso, Long>, JpaSpecificationExecutor<TipologiaPermesso> {}
