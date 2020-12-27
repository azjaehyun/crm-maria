package co.kr.crm.repository;

import co.kr.crm.domain.Corp;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Corp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorpRepository extends JpaRepository<Corp, Long> {
}
