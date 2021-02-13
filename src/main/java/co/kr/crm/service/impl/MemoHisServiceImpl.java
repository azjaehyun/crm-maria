package co.kr.crm.service.impl;

import co.kr.crm.service.MemoHisService;
import co.kr.crm.domain.MemoHis;
import co.kr.crm.repository.MemoHisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MemoHis}.
 */
@Service
@Transactional
public class MemoHisServiceImpl implements MemoHisService {

    private final Logger log = LoggerFactory.getLogger(MemoHisServiceImpl.class);

    private final MemoHisRepository memoHisRepository;

    public MemoHisServiceImpl(MemoHisRepository memoHisRepository) {
        this.memoHisRepository = memoHisRepository;
    }

    @Override
    public MemoHis save(MemoHis memoHis) {
        log.debug("Request to save MemoHis : {}", memoHis);
        return memoHisRepository.save(memoHis);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemoHis> findAll(Pageable pageable) {
        log.debug("Request to get all MemoHis");
        return memoHisRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MemoHis> findOne(Long id) {
        log.debug("Request to get MemoHis : {}", id);
        return memoHisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MemoHis : {}", id);
        memoHisRepository.deleteById(id);
    }
}
