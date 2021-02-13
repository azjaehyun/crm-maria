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

import co.kr.crm.domain.StockContractHis;
import co.kr.crm.domain.*; // for static metamodels
import co.kr.crm.repository.StockContractHisRepository;
import co.kr.crm.service.dto.StockContractHisCriteria;

/**
 * Service for executing complex queries for {@link StockContractHis} entities in the database.
 * The main input is a {@link StockContractHisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockContractHis} or a {@link Page} of {@link StockContractHis} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockContractHisQueryService extends QueryService<StockContractHis> {

    private final Logger log = LoggerFactory.getLogger(StockContractHisQueryService.class);

    private final StockContractHisRepository stockContractHisRepository;

    public StockContractHisQueryService(StockContractHisRepository stockContractHisRepository) {
        this.stockContractHisRepository = stockContractHisRepository;
    }

    /**
     * Return a {@link List} of {@link StockContractHis} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockContractHis> findByCriteria(StockContractHisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockContractHis> specification = createSpecification(criteria);
        return stockContractHisRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StockContractHis} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockContractHis> findByCriteria(StockContractHisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockContractHis> specification = createSpecification(criteria);
        return stockContractHisRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockContractHisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockContractHis> specification = createSpecification(criteria);
        return stockContractHisRepository.count(specification);
    }

    /**
     * Function to convert {@link StockContractHisCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockContractHis> createSpecification(StockContractHisCriteria criteria) {
        Specification<StockContractHis> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockContractHis_.id));
            }
            if (criteria.getFromContractDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromContractDt(), StockContractHis_.fromContractDt));
            }
            if (criteria.getToContractDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToContractDt(), StockContractHis_.toContractDt));
            }
            if (criteria.getContractAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractAmount(), StockContractHis_.contractAmount));
            }
            if (criteria.getRegDtm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegDtm(), StockContractHis_.regDtm));
            }
            if (criteria.getUseYn() != null) {
                specification = specification.and(buildSpecification(criteria.getUseYn(), StockContractHis_.useYn));
            }
            if (criteria.getCrmCustomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCrmCustomId(),
                    root -> root.join(StockContractHis_.crmCustom, JoinType.LEFT).get(CrmCustom_.id)));
            }
        }
        return specification;
    }
}
