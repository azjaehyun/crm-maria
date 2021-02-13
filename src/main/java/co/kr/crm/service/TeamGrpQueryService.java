package co.kr.crm.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import co.kr.crm.domain.TeamGrp;
import co.kr.crm.domain.*; // for static metamodels
import co.kr.crm.repository.TeamGrpRepository;
import co.kr.crm.service.dto.TeamGrpCriteria;

/**
 * Service for executing complex queries for {@link TeamGrp} entities in the database.
 * The main input is a {@link TeamGrpCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeamGrp} or a {@link Page} of {@link TeamGrp} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeamGrpQueryService extends QueryService<TeamGrp> {

    private final Logger log = LoggerFactory.getLogger(TeamGrpQueryService.class);

    private final TeamGrpRepository teamGrpRepository;

    public TeamGrpQueryService(TeamGrpRepository teamGrpRepository) {
        this.teamGrpRepository = teamGrpRepository;
    }

    /**
     * Return a {@link List} of {@link TeamGrp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeamGrp> findByCriteria(TeamGrpCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TeamGrp> specification = createSpecification(criteria);
        return teamGrpRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TeamGrp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamGrp> findByCriteria(TeamGrpCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TeamGrp> specification = createSpecification(criteria);
        return teamGrpRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TeamGrpCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TeamGrp> specification = createSpecification(criteria);
        return teamGrpRepository.count(specification);
    }

    /**
     * Function to convert {@link TeamGrpCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TeamGrp> createSpecification(TeamGrpCriteria criteria) {
        Specification<TeamGrp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TeamGrp_.id));
            }
            if (criteria.getTeamCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeamCode(), TeamGrp_.teamCode));
            }
            if (criteria.getTeamName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeamName(), TeamGrp_.teamName));
            }
            if (criteria.getUseYn() != null) {
                specification = specification.and(buildSpecification(criteria.getUseYn(), TeamGrp_.useYn));
            }
            if (criteria.getManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getManagerId(),
                    root -> root.join(TeamGrp_.managers, JoinType.LEFT).get(Manager_.id)));
            }
            if (criteria.getTmManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getTmManagerId(),
                    root -> root.join(TeamGrp_.tmManagers, JoinType.LEFT).get(TmManager_.id)));
            }
            if (criteria.getCorpId() != null) {
                specification = specification.and(buildSpecification(criteria.getCorpId(),
                    root -> root.join(TeamGrp_.corp, JoinType.LEFT).get(Corp_.id)));
            }
        }
        return specification;
    }
}
