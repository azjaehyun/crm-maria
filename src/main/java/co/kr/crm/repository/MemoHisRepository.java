package co.kr.crm.repository;

import co.kr.crm.domain.MemoHis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MemoHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemoHisRepository extends JpaRepository<MemoHis, Long>, JpaSpecificationExecutor<MemoHis> {
}
