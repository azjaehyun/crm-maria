package co.kr.crm.service.impl;

import co.kr.crm.service.StockContractHisService;
import co.kr.crm.domain.StockContractHis;
import co.kr.crm.repository.StockContractHisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StockContractHis}.
 */
@Service
@Transactional
public class StockContractHisServiceImpl implements StockContractHisService {

    private final Logger log = LoggerFactory.getLogger(StockContractHisServiceImpl.class);

    private final StockContractHisRepository stockContractHisRepository;

    public StockContractHisServiceImpl(StockContractHisRepository stockContractHisRepository) {
        this.stockContractHisRepository = stockContractHisRepository;
    }

    @Override
    public StockContractHis save(StockContractHis stockContractHis) {
        log.debug("Request to save StockContractHis : {}", stockContractHis);
        return stockContractHisRepository.save(stockContractHis);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockContractHis> findAll(Pageable pageable) {
        log.debug("Request to get all StockContractHis");
        return stockContractHisRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<StockContractHis> findOne(Long id) {
        log.debug("Request to get StockContractHis : {}", id);
        return stockContractHisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockContractHis : {}", id);
        stockContractHisRepository.deleteById(id);
    }
}
