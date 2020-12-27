package co.kr.crm.repository;

import co.kr.crm.domain.StockConsultingHis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StockConsultingHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockConsultingHisRepository extends JpaRepository<StockConsultingHis, Long> {
}
