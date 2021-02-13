package co.kr.crm.service.impl;

import co.kr.crm.service.CorpService;
import co.kr.crm.domain.Corp;
import co.kr.crm.repository.CorpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Corp}.
 */
@Service
@Transactional
public class CorpServiceImpl implements CorpService {

    private final Logger log = LoggerFactory.getLogger(CorpServiceImpl.class);

    private final CorpRepository corpRepository;

    public CorpServiceImpl(CorpRepository corpRepository) {
        this.corpRepository = corpRepository;
    }

    @Override
    public Corp save(Corp corp) {
        log.debug("Request to save Corp : {}", corp);
        return corpRepository.save(corp);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Corp> findAll(Pageable pageable) {
        log.debug("Request to get all Corps");
        return corpRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Corp> findOne(Long id) {
        log.debug("Request to get Corp : {}", id);
        return corpRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Corp : {}", id);
        corpRepository.deleteById(id);
    }
}
