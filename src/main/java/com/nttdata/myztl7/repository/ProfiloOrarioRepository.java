package com.nttdata.myztl7.repository;

import com.nttdata.myztl7.domain.ProfiloOrario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProfiloOrario entity.
 */
@Repository
public interface ProfiloOrarioRepository extends JpaRepository<ProfiloOrario, Long>, JpaSpecificationExecutor<ProfiloOrario> {
    @Query(
        value = "select distinct profiloOrario from ProfiloOrario profiloOrario left join fetch profiloOrario.regolaOrarias",
        countQuery = "select count(distinct profiloOrario) from ProfiloOrario profiloOrario"
    )
    Page<ProfiloOrario> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct profiloOrario from ProfiloOrario profiloOrario left join fetch profiloOrario.regolaOrarias")
    List<ProfiloOrario> findAllWithEagerRelationships();

    @Query("select profiloOrario from ProfiloOrario profiloOrario left join fetch profiloOrario.regolaOrarias where profiloOrario.id =:id")
    Optional<ProfiloOrario> findOneWithEagerRelationships(@Param("id") Long id);
}
