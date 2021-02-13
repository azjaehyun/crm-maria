package co.kr.crm.web.rest;

import co.kr.crm.domain.Corp;
import co.kr.crm.service.CorpService;
import co.kr.crm.web.rest.errors.BadRequestAlertException;
import co.kr.crm.service.dto.CorpCriteria;
import co.kr.crm.service.CorpQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.kr.crm.domain.Corp}.
 */
@RestController
@RequestMapping("/api")
public class CorpResource {

    private final Logger log = LoggerFactory.getLogger(CorpResource.class);

    private static final String ENTITY_NAME = "corp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorpService corpService;

    private final CorpQueryService corpQueryService;

    public CorpResource(CorpService corpService, CorpQueryService corpQueryService) {
        this.corpService = corpService;
        this.corpQueryService = corpQueryService;
    }

    /**
     * {@code POST  /corps} : Create a new corp.
     *
     * @param corp the corp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new corp, or with status {@code 400 (Bad Request)} if the corp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/corps")
    public ResponseEntity<Corp> createCorp(@Valid @RequestBody Corp corp) throws URISyntaxException {
        log.debug("REST request to save Corp : {}", corp);
        if (corp.getId() != null) {
            throw new BadRequestAlertException("A new corp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Corp result = corpService.save(corp);
        return ResponseEntity.created(new URI("/api/corps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /corps} : Updates an existing corp.
     *
     * @param corp the corp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated corp,
     * or with status {@code 400 (Bad Request)} if the corp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the corp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/corps")
    public ResponseEntity<Corp> updateCorp(@Valid @RequestBody Corp corp) throws URISyntaxException {
        log.debug("REST request to update Corp : {}", corp);
        if (corp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Corp result = corpService.save(corp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, corp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /corps} : get all the corps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of corps in body.
     */
    @GetMapping("/corps")
    public ResponseEntity<List<Corp>> getAllCorps(CorpCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Corps by criteria: {}", criteria);
        Page<Corp> page = corpQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /corps/count} : count all the corps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/corps/count")
    public ResponseEntity<Long> countCorps(CorpCriteria criteria) {
        log.debug("REST request to count Corps by criteria: {}", criteria);
        return ResponseEntity.ok().body(corpQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /corps/:id} : get the "id" corp.
     *
     * @param id the id of the corp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the corp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/corps/{id}")
    public ResponseEntity<Corp> getCorp(@PathVariable Long id) {
        log.debug("REST request to get Corp : {}", id);
        Optional<Corp> corp = corpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(corp);
    }

    /**
     * {@code DELETE  /corps/:id} : delete the "id" corp.
     *
     * @param id the id of the corp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/corps/{id}")
    public ResponseEntity<Void> deleteCorp(@PathVariable Long id) {
        log.debug("REST request to delete Corp : {}", id);
        corpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
