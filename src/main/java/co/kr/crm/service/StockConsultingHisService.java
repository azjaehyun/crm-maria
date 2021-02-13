package co.kr.crm.service;

import co.kr.crm.domain.StockConsultingHis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link StockConsultingHis}.
 */
public interface StockConsultingHisService {

    /**
     * Save a stockConsultingHis.
     *
     * @param stockConsultingHis the entity to save.
     * @return the persisted entity.
     */
    StockConsultingHis save(StockConsultingHis stockConsultingHis);

    /**
     * Get all the stockConsultingHis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockConsultingHis> findAll(Pageable pageable);


    /**
     * Get the "id" stockConsultingHis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockConsultingHis> findOne(Long id);

    /**
     * Delete the "id" stockConsultingHis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
