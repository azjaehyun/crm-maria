package co.kr.crm.web.rest;

import co.kr.crm.domain.TmManager;
import co.kr.crm.service.TmManagerService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.kr.crm.domain.TmManager}.
 */
@RestController
@RequestMapping("/api")
public class TmManagerResource {

    private final Logger log = LoggerFactory.getLogger(TmManagerResource.class);

    private static final String ENTITY_NAME = "tmManager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TmManagerService tmManagerService;

    public TmManagerResource(TmManagerService tmManagerService) {
        this.tmManagerService = tmManagerService;
    }

    /**
     * {@code POST  /tm-managers} : Create a new tmManager.
     *
     * @param tmManager the tmManager to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tmManager, or with status {@code 400 (Bad Request)} if the tmManager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tm-managers")
    public ResponseEntity<TmManager> createTmManager(@Valid @RequestBody TmManager tmManager) throws URISyntaxException {
        log.debug("REST request to save TmManager : {}", tmManager);
        if (tmManager.getId() != null) {
            throw new BadRequestAlertException("A new tmManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TmManager result = tmManagerService.save(tmManager);
        return ResponseEntity.created(new URI("/api/tm-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tm-managers} : Updates an existing tmManager.
     *
     * @param tmManager the tmManager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tmManager,
     * or with status {@code 400 (Bad Request)} if the tmManager is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tmManager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tm-managers")
    public ResponseEntity<TmManager> updateTmManager(@Valid @RequestBody TmManager tmManager) throws URISyntaxException {
        log.debug("REST request to update TmManager : {}", tmManager);
        if (tmManager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TmManager result = tmManagerService.save(tmManager);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tmManager.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tm-managers} : get all the tmManagers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tmManagers in body.
     */
    @GetMapping("/tm-managers")
    public ResponseEntity<List<TmManager>> getAllTmManagers(Pageable pageable) {
        log.debug("REST request to get a page of TmManagers");
        Page<TmManager> page = tmManagerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tm-managers/:id} : get the "id" tmManager.
     *
     * @param id the id of the tmManager to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tmManager, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tm-managers/{id}")
    public ResponseEntity<TmManager> getTmManager(@PathVariable Long id) {
        log.debug("REST request to get TmManager : {}", id);
        Optional<TmManager> tmManager = tmManagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tmManager);
    }

    /**
     * {@code DELETE  /tm-managers/:id} : delete the "id" tmManager.
     *
     * @param id the id of the tmManager to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tm-managers/{id}")
    public ResponseEntity<Void> deleteTmManager(@PathVariable Long id) {
        log.debug("REST request to delete TmManager : {}", id);
        tmManagerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
