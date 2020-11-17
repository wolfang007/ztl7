package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.RegolaOraria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RegolaOraria entity.
 */
@Repository
public interface RegolaOrariaRepository extends JpaRepository<RegolaOraria, Long>, JpaSpecificationExecutor<RegolaOraria> {
    @Query(
        value = "select distinct regolaOraria from RegolaOraria regolaOraria left join fetch regolaOraria.tipologiaVeicolos",
        countQuery = "select count(distinct regolaOraria) from RegolaOraria regolaOraria"
    )
    Page<RegolaOraria> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct regolaOraria from RegolaOraria regolaOraria left join fetch regolaOraria.tipologiaVeicolos")
    List<RegolaOraria> findAllWithEagerRelationships();

    @Query("select regolaOraria from RegolaOraria regolaOraria left join fetch regolaOraria.tipologiaVeicolos where regolaOraria.id =:id")
    Optional<RegolaOraria> findOneWithEagerRelationships(@Param("id") Long id);
}
