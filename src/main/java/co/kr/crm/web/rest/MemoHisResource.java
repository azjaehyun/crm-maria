package co.kr.crm.web.rest;

import co.kr.crm.domain.MemoHis;
import co.kr.crm.service.MemoHisService;
import co.kr.crm.web.rest.errors.BadRequestAlertException;
import co.kr.crm.service.dto.MemoHisCriteria;
import co.kr.crm.service.MemoHisQueryService;

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
 * REST controller for managing {@link co.kr.crm.domain.MemoHis}.
 */
@RestController
@RequestMapping("/api")
public class MemoHisResource {

    private final Logger log = LoggerFactory.getLogger(MemoHisResource.class);

    private static final String ENTITY_NAME = "memoHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemoHisService memoHisService;

    private final MemoHisQueryService memoHisQueryService;

    public MemoHisResource(MemoHisService memoHisService, MemoHisQueryService memoHisQueryService) {
        this.memoHisService = memoHisService;
        this.memoHisQueryService = memoHisQueryService;
    }

    /**
     * {@code POST  /memo-his} : Create a new memoHis.
     *
     * @param memoHis the memoHis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memoHis, or with status {@code 400 (Bad Request)} if the memoHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/memo-his")
    public ResponseEntity<MemoHis> createMemoHis(@RequestBody MemoHis memoHis) throws URISyntaxException {
        log.debug("REST request to save MemoHis : {}", memoHis);
        if (memoHis.getId() != null) {
            throw new BadRequestAlertException("A new memoHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemoHis result = memoHisService.save(memoHis);
        return ResponseEntity.created(new URI("/api/memo-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /memo-his} : Updates an existing memoHis.
     *
     * @param memoHis the memoHis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memoHis,
     * or with status {@code 400 (Bad Request)} if the memoHis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memoHis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/memo-his")
    public ResponseEntity<MemoHis> updateMemoHis(@RequestBody MemoHis memoHis) throws URISyntaxException {
        log.debug("REST request to update MemoHis : {}", memoHis);
        if (memoHis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MemoHis result = memoHisService.save(memoHis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, memoHis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /memo-his} : get all the memoHis.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memoHis in body.
     */
    @GetMapping("/memo-his")
    public ResponseEntity<List<MemoHis>> getAllMemoHis(MemoHisCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MemoHis by criteria: {}", criteria);
        Page<MemoHis> page = memoHisQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /memo-his/count} : count all the memoHis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/memo-his/count")
    public ResponseEntity<Long> countMemoHis(MemoHisCriteria criteria) {
        log.debug("REST request to count MemoHis by criteria: {}", criteria);
        return ResponseEntity.ok().body(memoHisQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /memo-his/:id} : get the "id" memoHis.
     *
     * @param id the id of the memoHis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memoHis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/memo-his/{id}")
    public ResponseEntity<MemoHis> getMemoHis(@PathVariable Long id) {
        log.debug("REST request to get MemoHis : {}", id);
        Optional<MemoHis> memoHis = memoHisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(memoHis);
    }

    /**
     * {@code DELETE  /memo-his/:id} : delete the "id" memoHis.
     *
     * @param id the id of the memoHis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/memo-his/{id}")
    public ResponseEntity<Void> deleteMemoHis(@PathVariable Long id) {
        log.debug("REST request to delete MemoHis : {}", id);
        memoHisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
