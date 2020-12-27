package co.kr.crm.service.impl;

import co.kr.crm.service.CrmCustomService;
import co.kr.crm.domain.CrmCustom;
import co.kr.crm.repository.CrmCustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CrmCustom}.
 */
@Service
@Transactional
public class CrmCustomServiceImpl implements CrmCustomService {

    private final Logger log = LoggerFactory.getLogger(CrmCustomServiceImpl.class);

    private final CrmCustomRepository crmCustomRepository;

    public CrmCustomServiceImpl(CrmCustomRepository crmCustomRepository) {
        this.crmCustomRepository = crmCustomRepository;
    }

    @Override
    public CrmCustom save(CrmCustom crmCustom) {
        log.debug("Request to save CrmCustom : {}", crmCustom);
        return crmCustomRepository.save(crmCustom);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CrmCustom> findAll(Pageable pageable) {
        log.debug("Request to get all CrmCustoms");
        return crmCustomRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CrmCustom> findOne(Long id) {
        log.debug("Request to get CrmCustom : {}", id);
        return crmCustomRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CrmCustom : {}", id);
        crmCustomRepository.deleteById(id);
    }
}
