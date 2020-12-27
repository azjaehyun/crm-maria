package co.kr.crm.service;

import co.kr.crm.domain.TmManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TmManager}.
 */
public interface TmManagerService {

    /**
     * Save a tmManager.
     *
     * @param tmManager the entity to save.
     * @return the persisted entity.
     */
    TmManager save(TmManager tmManager);

    /**
     * Get all the tmManagers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TmManager> findAll(Pageable pageable);


    /**
     * Get the "id" tmManager.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TmManager> findOne(Long id);

    /**
     * Delete the "id" tmManager.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
