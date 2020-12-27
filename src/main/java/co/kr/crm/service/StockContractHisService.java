package co.kr.crm.service;

import co.kr.crm.domain.StockContractHis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link StockContractHis}.
 */
public interface StockContractHisService {

    /**
     * Save a stockContractHis.
     *
     * @param stockContractHis the entity to save.
     * @return the persisted entity.
     */
    StockContractHis save(StockContractHis stockContractHis);

    /**
     * Get all the stockContractHis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockContractHis> findAll(Pageable pageable);


    /**
     * Get the "id" stockContractHis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockContractHis> findOne(Long id);

    /**
     * Delete the "id" stockContractHis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
