package co.kr.crm.service;

import co.kr.crm.domain.Corp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Corp}.
 */
public interface CorpService {

    /**
     * Save a corp.
     *
     * @param corp the entity to save.
     * @return the persisted entity.
     */
    Corp save(Corp corp);

    /**
     * Get all the corps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Corp> findAll(Pageable pageable);


    /**
     * Get the "id" corp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Corp> findOne(Long id);

    /**
     * Delete the "id" corp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
