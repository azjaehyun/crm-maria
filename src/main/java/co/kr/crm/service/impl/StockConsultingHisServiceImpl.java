package co.kr.crm.service.impl;

import co.kr.crm.service.StockConsultingHisService;
import co.kr.crm.domain.StockConsultingHis;
import co.kr.crm.repository.StockConsultingHisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StockConsultingHis}.
 */
@Service
@Transactional
public class StockConsultingHisServiceImpl implements StockConsultingHisService {

    private final Logger log = LoggerFactory.getLogger(StockConsultingHisServiceImpl.class);

    private final StockConsultingHisRepository stockConsultingHisRepository;

    public StockConsultingHisServiceImpl(StockConsultingHisRepository stockConsultingHisRepository) {
        this.stockConsultingHisRepository = stockConsultingHisRepository;
    }

    @Override
    public StockConsultingHis save(StockConsultingHis stockConsultingHis) {
        log.debug("Request to save StockConsultingHis : {}", stockConsultingHis);
        return stockConsultingHisRepository.save(stockConsultingHis);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockConsultingHis> findAll(Pageable pageable) {
        log.debug("Request to get all StockConsultingHis");
        return stockConsultingHisRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<StockConsultingHis> findOne(Long id) {
        log.debug("Request to get StockConsultingHis : {}", id);
        return stockConsultingHisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockConsultingHis : {}", id);
        stockConsultingHisRepository.deleteById(id);
    }
}
