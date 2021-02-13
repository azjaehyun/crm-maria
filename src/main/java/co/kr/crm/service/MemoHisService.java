package co.kr.crm.service;

import co.kr.crm.domain.MemoHis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MemoHis}.
 */
public interface MemoHisService {

    /**
     * Save a memoHis.
     *
     * @param memoHis the entity to save.
     * @return the persisted entity.
     */
    MemoHis save(MemoHis memoHis);

    /**
     * Get all the memoHis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MemoHis> findAll(Pageable pageable);


    /**
     * Get the "id" memoHis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MemoHis> findOne(Long id);

    /**
     * Delete the "id" memoHis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
