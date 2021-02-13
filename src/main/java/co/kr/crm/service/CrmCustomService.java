package co.kr.crm.service;

import co.kr.crm.domain.CrmCustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CrmCustom}.
 */
public interface CrmCustomService {

    /**
     * Save a crmCustom.
     *
     * @param crmCustom the entity to save.
     * @return the persisted entity.
     */
    CrmCustom save(CrmCustom crmCustom);

    /**
     * Get all the crmCustoms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CrmCustom> findAll(Pageable pageable);


    /**
     * Get the "id" crmCustom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CrmCustom> findOne(Long id);

    /**
     * Delete the "id" crmCustom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
