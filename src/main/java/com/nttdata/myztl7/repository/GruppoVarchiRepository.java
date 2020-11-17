package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.GruppoVarchi;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GruppoVarchi entity.
 */
@Repository
public interface GruppoVarchiRepository extends JpaRepository<GruppoVarchi, Long>, JpaSpecificationExecutor<GruppoVarchi> {
    @Query(
        value = "select distinct gruppoVarchi from GruppoVarchi gruppoVarchi left join fetch gruppoVarchi.posiziones",
        countQuery = "select count(distinct gruppoVarchi) from GruppoVarchi gruppoVarchi"
    )
    Page<GruppoVarchi> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct gruppoVarchi from GruppoVarchi gruppoVarchi left join fetch gruppoVarchi.posiziones")
    List<GruppoVarchi> findAllWithEagerRelationships();

    @Query("select gruppoVarchi from GruppoVarchi gruppoVarchi left join fetch gruppoVarchi.posiziones where gruppoVarchi.id =:id")
    Optional<GruppoVarchi> findOneWithEagerRelationships(@Param("id") Long id);
}
