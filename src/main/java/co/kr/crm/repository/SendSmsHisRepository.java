package co.kr.crm.repository;

import co.kr.crm.domain.SendSmsHis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SendSmsHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SendSmsHisRepository extends JpaRepository<SendSmsHis, Long> {
}
