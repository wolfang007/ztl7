package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.Calendarizzazione;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Calendarizzazione entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarizzazioneRepository extends JpaRepository<Calendarizzazione, Long>, JpaSpecificationExecutor<Calendarizzazione> {}
