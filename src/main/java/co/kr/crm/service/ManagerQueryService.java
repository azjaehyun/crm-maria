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

import co.kr.crm.domain.Manager;
import co.kr.crm.domain.*; // for static metamodels
import co.kr.crm.repository.ManagerRepository;
import co.kr.crm.service.dto.ManagerCriteria;

/**
 * Service for executing complex queries for {@link Manager} entities in the database.
 * The main input is a {@link ManagerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Manager} or a {@link Page} of {@link Manager} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ManagerQueryService extends QueryService<Manager> {

    private final Logger log = LoggerFactory.getLogger(ManagerQueryService.class);

    private final ManagerRepository managerRepository;

    public ManagerQueryService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    /**
     * Return a {@link List} of {@link Manager} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Manager> findByCriteria(ManagerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Manager> specification = createSpecification(criteria);
        return managerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Manager} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Manager> findByCriteria(ManagerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Manager> specification = createSpecification(criteria);
        return managerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ManagerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Manager> specification = createSpecification(criteria);
        return managerRepository.count(specification);
    }

    /**
     * Function to convert {@link ManagerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Manager> createSpecification(ManagerCriteria criteria) {
        Specification<Manager> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Manager_.id));
            }
            if (criteria.getCorpCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorpCode(), Manager_.corpCode));
            }
            if (criteria.getManagerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerName(), Manager_.managerName));
            }
            if (criteria.getManagerPhoneNum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerPhoneNum(), Manager_.managerPhoneNum));
            }
            if (criteria.getTeamCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeamCode(), Manager_.teamCode));
            }
            if (criteria.getTotalSalesAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalSalesAmount(), Manager_.totalSalesAmount));
            }
            if (criteria.getEnterDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnterDay(), Manager_.enterDay));
            }
            if (criteria.getOutDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutDay(), Manager_.outDay));
            }
            if (criteria.getUseYn() != null) {
                specification = specification.and(buildSpecification(criteria.getUseYn(), Manager_.useYn));
            }
            if (criteria.getCrmCustomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCrmCustomId(),
                    root -> root.join(Manager_.crmCustoms, JoinType.LEFT).get(CrmCustom_.id)));
            }
            if (criteria.getTeamId() != null) {
                specification = specification.and(buildSpecification(criteria.getTeamId(),
                    root -> root.join(Manager_.team, JoinType.LEFT).get(TeamGrp_.id)));
            }
        }
        return specification;
    }
}
