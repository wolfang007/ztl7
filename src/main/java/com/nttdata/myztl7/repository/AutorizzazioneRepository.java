package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.Autorizzazione;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Autorizzazione entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutorizzazioneRepository extends JpaRepository<Autorizzazione, Long>, JpaSpecificationExecutor<Autorizzazione> {}
