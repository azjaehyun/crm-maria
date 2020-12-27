package co.kr.crm.web.rest;

import co.kr.crm.domain.StockContractHis;
import co.kr.crm.service.StockContractHisService;
import co.kr.crm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.kr.crm.domain.StockContractHis}.
 */
@RestController
@RequestMapping("/api")
public class StockContractHisResource {

    private final Logger log = LoggerFactory.getLogger(StockContractHisResource.class);

    private static final String ENTITY_NAME = "stockContractHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockContractHisService stockContractHisService;

    public StockContractHisResource(StockContractHisService stockContractHisService) {
        this.stockContractHisService = stockContractHisService;
    }

    /**
     * {@code POST  /stock-contract-his} : Create a new stockContractHis.
     *
     * @param stockContractHis the stockContractHis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockContractHis, or with status {@code 400 (Bad Request)} if the stockContractHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-contract-his")
    public ResponseEntity<StockContractHis> createStockContractHis(@RequestBody StockContractHis stockContractHis) throws URISyntaxException {
        log.debug("REST request to save StockContractHis : {}", stockContractHis);
        if (stockContractHis.getId() != null) {
            throw new BadRequestAlertException("A new stockContractHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockContractHis result = stockContractHisService.save(stockContractHis);
        return ResponseEntity.created(new URI("/api/stock-contract-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-contract-his} : Updates an existing stockContractHis.
     *
     * @param stockContractHis the stockContractHis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockContractHis,
     * or with status {@code 400 (Bad Request)} if the stockContractHis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockContractHis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-contract-his")
    public ResponseEntity<StockContractHis> updateStockContractHis(@RequestBody StockContractHis stockContractHis) throws URISyntaxException {
        log.debug("REST request to update StockContractHis : {}", stockContractHis);
        if (stockContractHis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockContractHis result = stockContractHisService.save(stockContractHis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stockContractHis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-contract-his} : get all the stockContractHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockContractHis in body.
     */
    @GetMapping("/stock-contract-his")
    public ResponseEntity<List<StockContractHis>> getAllStockContractHis(Pageable pageable) {
        log.debug("REST request to get a page of StockContractHis");
        Page<StockContractHis> page = stockContractHisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-contract-his/:id} : get the "id" stockContractHis.
     *
     * @param id the id of the stockContractHis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockContractHis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-contract-his/{id}")
    public ResponseEntity<StockContractHis> getStockContractHis(@PathVariable Long id) {
        log.debug("REST request to get StockContractHis : {}", id);
        Optional<StockContractHis> stockContractHis = stockContractHisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockContractHis);
    }

    /**
     * {@code DELETE  /stock-contract-his/:id} : delete the "id" stockContractHis.
     *
     * @param id the id of the stockContractHis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-contract-his/{id}")
    public ResponseEntity<Void> deleteStockContractHis(@PathVariable Long id) {
        log.debug("REST request to delete StockContractHis : {}", id);
        stockContractHisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
