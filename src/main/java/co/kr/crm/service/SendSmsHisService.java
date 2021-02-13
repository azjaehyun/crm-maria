package co.kr.crm.service;

import co.kr.crm.domain.SendSmsHis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SendSmsHis}.
 */
public interface SendSmsHisService {

    /**
     * Save a sendSmsHis.
     *
     * @param sendSmsHis the entity to save.
     * @return the persisted entity.
     */
    SendSmsHis save(SendSmsHis sendSmsHis);

    /**
     * Get all the sendSmsHis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SendSmsHis> findAll(Pageable pageable);


    /**
     * Get the "id" sendSmsHis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SendSmsHis> findOne(Long id);

    /**
     * Delete the "id" sendSmsHis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
