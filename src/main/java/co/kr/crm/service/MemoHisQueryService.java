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

import co.kr.crm.domain.MemoHis;
import co.kr.crm.domain.*; // for static metamodels
import co.kr.crm.repository.MemoHisRepository;
import co.kr.crm.service.dto.MemoHisCriteria;

/**
 * Service for executing complex queries for {@link MemoHis} entities in the database.
 * The main input is a {@link MemoHisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemoHis} or a {@link Page} of {@link MemoHis} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemoHisQueryService extends QueryService<MemoHis> {

    private final Logger log = LoggerFactory.getLogger(MemoHisQueryService.class);

    private final MemoHisRepository memoHisRepository;

    public MemoHisQueryService(MemoHisRepository memoHisRepository) {
        this.memoHisRepository = memoHisRepository;
    }

    /**
     * Return a {@link List} of {@link MemoHis} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemoHis> findByCriteria(MemoHisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MemoHis> specification = createSpecification(criteria);
        return memoHisRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MemoHis} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemoHis> findByCriteria(MemoHisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MemoHis> specification = createSpecification(criteria);
        return memoHisRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemoHisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MemoHis> specification = createSpecification(criteria);
        return memoHisRepository.count(specification);
    }

    /**
     * Function to convert {@link MemoHisCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MemoHis> createSpecification(MemoHisCriteria criteria) {
        Specification<MemoHis> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MemoHis_.id));
            }
            if (criteria.getMemoContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemoContent(), MemoHis_.memoContent));
            }
            if (criteria.getRegDtm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegDtm(), MemoHis_.regDtm));
            }
            if (criteria.getUseYn() != null) {
                specification = specification.and(buildSpecification(criteria.getUseYn(), MemoHis_.useYn));
            }
            if (criteria.getCrmCustomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCrmCustomId(),
                    root -> root.join(MemoHis_.crmCustom, JoinType.LEFT).get(CrmCustom_.id)));
            }
        }
        return specification;
    }
}
