package co.kr.crm.service.impl;

import co.kr.crm.service.ManagerService;
import co.kr.crm.domain.Manager;
import co.kr.crm.repository.ManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Manager}.
 */
@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);

    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public Manager save(Manager manager) {
        log.debug("Request to save Manager : {}", manager);
        return managerRepository.save(manager);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Manager> findAll(Pageable pageable) {
        log.debug("Request to get all Managers");
        return managerRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Manager> findOne(Long id) {
        log.debug("Request to get Manager : {}", id);
        return managerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Manager : {}", id);
        managerRepository.deleteById(id);
    }
}
