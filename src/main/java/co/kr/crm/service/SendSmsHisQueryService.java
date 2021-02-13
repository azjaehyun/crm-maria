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

import co.kr.crm.domain.SendSmsHis;
import co.kr.crm.domain.*; // for static metamodels
import co.kr.crm.repository.SendSmsHisRepository;
import co.kr.crm.service.dto.SendSmsHisCriteria;

/**
 * Service for executing complex queries for {@link SendSmsHis} entities in the database.
 * The main input is a {@link SendSmsHisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SendSmsHis} or a {@link Page} of {@link SendSmsHis} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SendSmsHisQueryService extends QueryService<SendSmsHis> {

    private final Logger log = LoggerFactory.getLogger(SendSmsHisQueryService.class);

    private final SendSmsHisRepository sendSmsHisRepository;

    public SendSmsHisQueryService(SendSmsHisRepository sendSmsHisRepository) {
        this.sendSmsHisRepository = sendSmsHisRepository;
    }

    /**
     * Return a {@link List} of {@link SendSmsHis} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SendSmsHis> findByCriteria(SendSmsHisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SendSmsHis> specification = createSpecification(criteria);
        return sendSmsHisRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SendSmsHis} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SendSmsHis> findByCriteria(SendSmsHisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SendSmsHis> specification = createSpecification(criteria);
        return sendSmsHisRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SendSmsHisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SendSmsHis> specification = createSpecification(criteria);
        return sendSmsHisRepository.count(specification);
    }

    /**
     * Function to convert {@link SendSmsHisCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SendSmsHis> createSpecification(SendSmsHisCriteria criteria) {
        Specification<SendSmsHis> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SendSmsHis_.id));
            }
            if (criteria.getSendDtm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSendDtm(), SendSmsHis_.sendDtm));
            }
            if (criteria.getFromPhoneNum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFromPhoneNum(), SendSmsHis_.fromPhoneNum));
            }
            if (criteria.getToPhoneNum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToPhoneNum(), SendSmsHis_.toPhoneNum));
            }
            if (criteria.getUseYn() != null) {
                specification = specification.and(buildSpecification(criteria.getUseYn(), SendSmsHis_.useYn));
            }
            if (criteria.getCrmCustomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCrmCustomId(),
                    root -> root.join(SendSmsHis_.crmCustom, JoinType.LEFT).get(CrmCustom_.id)));
            }
        }
        return specification;
    }
}
