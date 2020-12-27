package co.kr.crm.repository;

import co.kr.crm.domain.CrmCustom;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CrmCustom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrmCustomRepository extends JpaRepository<CrmCustom, Long> {
}
