package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.Zona;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Zona entity.
 */
@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long>, JpaSpecificationExecutor<Zona> {
    @Query(
        value = "select distinct zona from Zona zona left join fetch zona.gruppoVarchis",
        countQuery = "select count(distinct zona) from Zona zona"
    )
    Page<Zona> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct zona from Zona zona left join fetch zona.gruppoVarchis")
    List<Zona> findAllWithEagerRelationships();

    @Query("select zona from Zona zona left join fetch zona.gruppoVarchis where zona.id =:id")
    Optional<Zona> findOneWithEagerRelationships(@Param("id") Long id);
}
