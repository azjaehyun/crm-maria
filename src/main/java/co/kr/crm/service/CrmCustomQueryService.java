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

import co.kr.crm.domain.CrmCustom;
import co.kr.crm.domain.*; // for static metamodels
import co.kr.crm.repository.CrmCustomRepository;
import co.kr.crm.service.dto.CrmCustomCriteria;

/**
 * Service for executing complex queries for {@link CrmCustom} entities in the database.
 * The main input is a {@link CrmCustomCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CrmCustom} or a {@link Page} of {@link CrmCustom} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CrmCustomQueryService extends QueryService<CrmCustom> {

    private final Logger log = LoggerFactory.getLogger(CrmCustomQueryService.class);

    private final CrmCustomRepository crmCustomRepository;

    public CrmCustomQueryService(CrmCustomRepository crmCustomRepository) {
        this.crmCustomRepository = crmCustomRepository;
    }

    /**
     * Return a {@link List} of {@link CrmCustom} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CrmCustom> findByCriteria(CrmCustomCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CrmCustom> specification = createSpecification(criteria);
        return crmCustomRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CrmCustom} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CrmCustom> findByCriteria(CrmCustomCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CrmCustom> specification = createSpecification(criteria);
        return crmCustomRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CrmCustomCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CrmCustom> specification = createSpecification(criteria);
        return crmCustomRepository.count(specification);
    }

    /**
     * Function to convert {@link CrmCustomCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CrmCustom> createSpecification(CrmCustomCriteria criteria) {
        Specification<CrmCustom> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CrmCustom_.id));
            }
            if (criteria.getCorpCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorpCode(), CrmCustom_.corpCode));
            }
            if (criteria.getCrmName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCrmName(), CrmCustom_.crmName));
            }
            if (criteria.getPhoneNum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNum(), CrmCustom_.phoneNum));
            }
            if (criteria.getFiveDayfreeYn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFiveDayfreeYn(), CrmCustom_.fiveDayfreeYn));
            }
            if (criteria.getSalesStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getSalesStatus(), CrmCustom_.salesStatus));
            }
            if (criteria.getSmsReceptionYn() != null) {
                specification = specification.and(buildSpecification(criteria.getSmsReceptionYn(), CrmCustom_.smsReceptionYn));
            }
            if (criteria.getCallStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getCallStatus(), CrmCustom_.callStatus));
            }
            if (criteria.getCustomStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomStatus(), CrmCustom_.customStatus));
            }
            if (criteria.getTempOneStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTempOneStatus(), CrmCustom_.tempOneStatus));
            }
            if (criteria.getTempTwoStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTempTwoStatus(), CrmCustom_.tempTwoStatus));
            }
            if (criteria.getDbInsertType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDbInsertType(), CrmCustom_.dbInsertType));
            }
            if (criteria.getUseYn() != null) {
                specification = specification.and(buildSpecification(criteria.getUseYn(), CrmCustom_.useYn));
            }
            if (criteria.getMemoHisId() != null) {
                specification = specification.and(buildSpecification(criteria.getMemoHisId(),
                    root -> root.join(CrmCustom_.memoHis, JoinType.LEFT).get(MemoHis_.id)));
            }
            if (criteria.getSendSmsHisId() != null) {
                specification = specification.and(buildSpecification(criteria.getSendSmsHisId(),
                    root -> root.join(CrmCustom_.sendSmsHis, JoinType.LEFT).get(SendSmsHis_.id)));
            }
            if (criteria.getStockContractHisId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockContractHisId(),
                    root -> root.join(CrmCustom_.stockContractHis, JoinType.LEFT).get(StockContractHis_.id)));
            }
            if (criteria.getStockConsultingHisId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockConsultingHisId(),
                    root -> root.join(CrmCustom_.stockConsultingHis, JoinType.LEFT).get(StockConsultingHis_.id)));
            }
            if (criteria.getManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getManagerId(),
                    root -> root.join(CrmCustom_.manager, JoinType.LEFT).get(Manager_.id)));
            }
            if (criteria.getTmManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getTmManagerId(),
                    root -> root.join(CrmCustom_.tmManager, JoinType.LEFT).get(TmManager_.id)));
            }
        }
        return specification;
    }
}
