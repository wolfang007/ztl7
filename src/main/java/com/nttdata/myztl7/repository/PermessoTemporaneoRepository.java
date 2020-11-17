package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.PermessoTemporaneo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PermessoTemporaneo entity.
 */
@Repository
public interface PermessoTemporaneoRepository
    extends JpaRepository<PermessoTemporaneo, Long>, JpaSpecificationExecutor<PermessoTemporaneo> {
    @Query(
        value = "select distinct permessoTemporaneo from PermessoTemporaneo permessoTemporaneo left join fetch permessoTemporaneo.autorizzazionis",
        countQuery = "select count(distinct permessoTemporaneo) from PermessoTemporaneo permessoTemporaneo"
    )
    Page<PermessoTemporaneo> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct permessoTemporaneo from PermessoTemporaneo permessoTemporaneo left join fetch permessoTemporaneo.autorizzazionis"
    )
    List<PermessoTemporaneo> findAllWithEagerRelationships();

    @Query(
        "select permessoTemporaneo from PermessoTemporaneo permessoTemporaneo left join fetch permessoTemporaneo.autorizzazionis where permessoTemporaneo.id =:id"
    )
    Optional<PermessoTemporaneo> findOneWithEagerRelationships(@Param("id") Long id);
}
