package co.kr.crm.service;

import co.kr.crm.domain.TeamGrp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TeamGrp}.
 */
public interface TeamGrpService {

    /**
     * Save a teamGrp.
     *
     * @param teamGrp the entity to save.
     * @return the persisted entity.
     */
    TeamGrp save(TeamGrp teamGrp);

    /**
     * Get all the teamGrps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeamGrp> findAll(Pageable pageable);


    /**
     * Get the "id" teamGrp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeamGrp> findOne(Long id);

    /**
     * Delete the "id" teamGrp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
