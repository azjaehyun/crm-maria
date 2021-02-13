package co.kr.crm.repository;

import co.kr.crm.domain.TmManager;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TmManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TmManagerRepository extends JpaRepository<TmManager, Long>, JpaSpecificationExecutor<TmManager> {
}
