package co.kr.crm.web.rest;

import co.kr.crm.domain.StockConsultingHis;
import co.kr.crm.service.StockConsultingHisService;
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
 * REST controller for managing {@link co.kr.crm.domain.StockConsultingHis}.
 */
@RestController
@RequestMapping("/api")
public class StockConsultingHisResource {

    private final Logger log = LoggerFactory.getLogger(StockConsultingHisResource.class);

    private static final String ENTITY_NAME = "stockConsultingHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockConsultingHisService stockConsultingHisService;

    public StockConsultingHisResource(StockConsultingHisService stockConsultingHisService) {
        this.stockConsultingHisService = stockConsultingHisService;
    }

    /**
     * {@code POST  /stock-consulting-his} : Create a new stockConsultingHis.
     *
     * @param stockConsultingHis the stockConsultingHis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockConsultingHis, or with status {@code 400 (Bad Request)} if the stockConsultingHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-consulting-his")
    public ResponseEntity<StockConsultingHis> createStockConsultingHis(@RequestBody StockConsultingHis stockConsultingHis) throws URISyntaxException {
        log.debug("REST request to save StockConsultingHis : {}", stockConsultingHis);
        if (stockConsultingHis.getId() != null) {
            throw new BadRequestAlertException("A new stockConsultingHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockConsultingHis result = stockConsultingHisService.save(stockConsultingHis);
        return ResponseEntity.created(new URI("/api/stock-consulting-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-consulting-his} : Updates an existing stockConsultingHis.
     *
     * @param stockConsultingHis the stockConsultingHis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockConsultingHis,
     * or with status {@code 400 (Bad Request)} if the stockConsultingHis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockConsultingHis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-consulting-his")
    public ResponseEntity<StockConsultingHis> updateStockConsultingHis(@RequestBody StockConsultingHis stockConsultingHis) throws URISyntaxException {
        log.debug("REST request to update StockConsultingHis : {}", stockConsultingHis);
        if (stockConsultingHis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockConsultingHis result = stockConsultingHisService.save(stockConsultingHis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stockConsultingHis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-consulting-his} : get all the stockConsultingHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockConsultingHis in body.
     */
    @GetMapping("/stock-consulting-his")
    public ResponseEntity<List<StockConsultingHis>> getAllStockConsultingHis(Pageable pageable) {
        log.debug("REST request to get a page of StockConsultingHis");
        Page<StockConsultingHis> page = stockConsultingHisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-consulting-his/:id} : get the "id" stockConsultingHis.
     *
     * @param id the id of the stockConsultingHis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockConsultingHis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-consulting-his/{id}")
    public ResponseEntity<StockConsultingHis> getStockConsultingHis(@PathVariable Long id) {
        log.debug("REST request to get StockConsultingHis : {}", id);
        Optional<StockConsultingHis> stockConsultingHis = stockConsultingHisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockConsultingHis);
    }

    /**
     * {@code DELETE  /stock-consulting-his/:id} : delete the "id" stockConsultingHis.
     *
     * @param id the id of the stockConsultingHis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-consulting-his/{id}")
    public ResponseEntity<Void> deleteStockConsultingHis(@PathVariable Long id) {
        log.debug("REST request to delete StockConsultingHis : {}", id);
        stockConsultingHisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
