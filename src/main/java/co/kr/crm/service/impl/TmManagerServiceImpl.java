package co.kr.crm.service.impl;

import co.kr.crm.service.TmManagerService;
import co.kr.crm.domain.TmManager;
import co.kr.crm.repository.TmManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TmManager}.
 */
@Service
@Transactional
public class TmManagerServiceImpl implements TmManagerService {

    private final Logger log = LoggerFactory.getLogger(TmManagerServiceImpl.class);

    private final TmManagerRepository tmManagerRepository;

    public TmManagerServiceImpl(TmManagerRepository tmManagerRepository) {
        this.tmManagerRepository = tmManagerRepository;
    }

    @Override
    public TmManager save(TmManager tmManager) {
        log.debug("Request to save TmManager : {}", tmManager);
        return tmManagerRepository.save(tmManager);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TmManager> findAll(Pageable pageable) {
        log.debug("Request to get all TmManagers");
        return tmManagerRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TmManager> findOne(Long id) {
        log.debug("Request to get TmManager : {}", id);
        return tmManagerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TmManager : {}", id);
        tmManagerRepository.deleteById(id);
    }
}
