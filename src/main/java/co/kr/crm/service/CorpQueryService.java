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

import co.kr.crm.domain.Corp;
import co.kr.crm.domain.*; // for static metamodels
import co.kr.crm.repository.CorpRepository;
import co.kr.crm.service.dto.CorpCriteria;

/**
 * Service for executing complex queries for {@link Corp} entities in the database.
 * The main input is a {@link CorpCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Corp} or a {@link Page} of {@link Corp} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorpQueryService extends QueryService<Corp> {

    private final Logger log = LoggerFactory.getLogger(CorpQueryService.class);

    private final CorpRepository corpRepository;

    public CorpQueryService(CorpRepository corpRepository) {
        this.corpRepository = corpRepository;
    }

    /**
     * Return a {@link List} of {@link Corp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Corp> findByCriteria(CorpCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Corp> specification = createSpecification(criteria);
        return corpRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Corp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Corp> findByCriteria(CorpCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Corp> specification = createSpecification(criteria);
        return corpRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CorpCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Corp> specification = createSpecification(criteria);
        return corpRepository.count(specification);
    }

    /**
     * Function to convert {@link CorpCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Corp> createSpecification(CorpCriteria criteria) {
        Specification<Corp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Corp_.id));
            }
            if (criteria.getCorpCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorpCode(), Corp_.corpCode));
            }
            if (criteria.getCorpName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorpName(), Corp_.corpName));
            }
            if (criteria.getUseYn() != null) {
                specification = specification.and(buildSpecification(criteria.getUseYn(), Corp_.useYn));
            }
            if (criteria.getTeamGrpId() != null) {
                specification = specification.and(buildSpecification(criteria.getTeamGrpId(),
                    root -> root.join(Corp_.teamGrps, JoinType.LEFT).get(TeamGrp_.id)));
            }
        }
        return specification;
    }
}
