package co.kr.crm.service.impl;

import co.kr.crm.service.SendSmsHisService;
import co.kr.crm.domain.SendSmsHis;
import co.kr.crm.repository.SendSmsHisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SendSmsHis}.
 */
@Service
@Transactional
public class SendSmsHisServiceImpl implements SendSmsHisService {

    private final Logger log = LoggerFactory.getLogger(SendSmsHisServiceImpl.class);

    private final SendSmsHisRepository sendSmsHisRepository;

    public SendSmsHisServiceImpl(SendSmsHisRepository sendSmsHisRepository) {
        this.sendSmsHisRepository = sendSmsHisRepository;
    }

    @Override
    public SendSmsHis save(SendSmsHis sendSmsHis) {
        log.debug("Request to save SendSmsHis : {}", sendSmsHis);
        return sendSmsHisRepository.save(sendSmsHis);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SendSmsHis> findAll(Pageable pageable) {
        log.debug("Request to get all SendSmsHis");
        return sendSmsHisRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SendSmsHis> findOne(Long id) {
        log.debug("Request to get SendSmsHis : {}", id);
        return sendSmsHisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SendSmsHis : {}", id);
        sendSmsHisRepository.deleteById(id);
    }
}
