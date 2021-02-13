package co.kr.crm.repository;

import co.kr.crm.domain.TeamGrp;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TeamGrp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamGrpRepository extends JpaRepository<TeamGrp, Long>, JpaSpecificationExecutor<TeamGrp> {
}
