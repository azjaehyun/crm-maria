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

import co.kr.crm.domain.TmManager;
import co.kr.crm.domain.*; // for static metamodels
import co.kr.crm.repository.TmManagerRepository;
import co.kr.crm.service.dto.TmManagerCriteria;

/**
 * Service for executing complex queries for {@link TmManager} entities in the database.
 * The main input is a {@link TmManagerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TmManager} or a {@link Page} of {@link TmManager} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TmManagerQueryService extends QueryService<TmManager> {

    private final Logger log = LoggerFactory.getLogger(TmManagerQueryService.class);

    private final TmManagerRepository tmManagerRepository;

    public TmManagerQueryService(TmManagerRepository tmManagerRepository) {
        this.tmManagerRepository = tmManagerRepository;
    }

    /**
     * Return a {@link List} of {@link TmManager} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TmManager> findByCriteria(TmManagerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TmManager> specification = createSpecification(criteria);
        return tmManagerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TmManager} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TmManager> findByCriteria(TmManagerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TmManager> specification = createSpecification(criteria);
        return tmManagerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TmManagerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TmManager> specification = createSpecification(criteria);
        return tmManagerRepository.count(specification);
    }

    /**
     * Function to convert {@link TmManagerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TmManager> createSpecification(TmManagerCriteria criteria) {
        Specification<TmManager> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TmManager_.id));
            }
            if (criteria.getCorpCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorpCode(), TmManager_.corpCode));
            }
            if (criteria.getTmManagerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTmManagerName(), TmManager_.tmManagerName));
            }
            if (criteria.getTmManagerPhoneNum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTmManagerPhoneNum(), TmManager_.tmManagerPhoneNum));
            }
            if (criteria.getTeamCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeamCode(), TmManager_.teamCode));
            }
            if (criteria.getCrmManageCnt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCrmManageCnt(), TmManager_.crmManageCnt));
            }
            if (criteria.getUseYn() != null) {
                specification = specification.and(buildSpecification(criteria.getUseYn(), TmManager_.useYn));
            }
            if (criteria.getCrmCustomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCrmCustomId(),
                    root -> root.join(TmManager_.crmCustoms, JoinType.LEFT).get(CrmCustom_.id)));
            }
            if (criteria.getTeamId() != null) {
                specification = specification.and(buildSpecification(criteria.getTeamId(),
                    root -> root.join(TmManager_.team, JoinType.LEFT).get(TeamGrp_.id)));
            }
        }
        return specification;
    }
}
