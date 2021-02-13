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

import co.kr.crm.domain.StockConsultingHis;
import co.kr.crm.domain.*; // for static metamodels
import co.kr.crm.repository.StockConsultingHisRepository;
import co.kr.crm.service.dto.StockConsultingHisCriteria;

/**
 * Service for executing complex queries for {@link StockConsultingHis} entities in the database.
 * The main input is a {@link StockConsultingHisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockConsultingHis} or a {@link Page} of {@link StockConsultingHis} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockConsultingHisQueryService extends QueryService<StockConsultingHis> {

    private final Logger log = LoggerFactory.getLogger(StockConsultingHisQueryService.class);

    private final StockConsultingHisRepository stockConsultingHisRepository;

    public StockConsultingHisQueryService(StockConsultingHisRepository stockConsultingHisRepository) {
        this.stockConsultingHisRepository = stockConsultingHisRepository;
    }

    /**
     * Return a {@link List} of {@link StockConsultingHis} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockConsultingHis> findByCriteria(StockConsultingHisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockConsultingHis> specification = createSpecification(criteria);
        return stockConsultingHisRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StockConsultingHis} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockConsultingHis> findByCriteria(StockConsultingHisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockConsultingHis> specification = createSpecification(criteria);
        return stockConsultingHisRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockConsultingHisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockConsultingHis> specification = createSpecification(criteria);
        return stockConsultingHisRepository.count(specification);
    }

    /**
     * Function to convert {@link StockConsultingHisCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockConsultingHis> createSpecification(StockConsultingHisCriteria criteria) {
        Specification<StockConsultingHis> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockConsultingHis_.id));
            }
            if (criteria.getConsultingMemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConsultingMemo(), StockConsultingHis_.consultingMemo));
            }
            if (criteria.getRegDtm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegDtm(), StockConsultingHis_.regDtm));
            }
            if (criteria.getUseYn() != null) {
                specification = specification.and(buildSpecification(criteria.getUseYn(), StockConsultingHis_.useYn));
            }
            if (criteria.getCrmCustomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCrmCustomId(),
                    root -> root.join(StockConsultingHis_.crmCustom, JoinType.LEFT).get(CrmCustom_.id)));
            }
        }
        return specification;
    }
}
