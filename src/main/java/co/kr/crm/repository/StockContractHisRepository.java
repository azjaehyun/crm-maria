package co.kr.crm.repository;

import co.kr.crm.domain.StockContractHis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StockContractHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockContractHisRepository extends JpaRepository<StockContractHis, Long>, JpaSpecificationExecutor<StockContractHis> {
}
