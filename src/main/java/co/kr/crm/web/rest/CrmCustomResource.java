package co.kr.crm.web.rest;

import co.kr.crm.domain.CrmCustom;
import co.kr.crm.service.CrmCustomService;
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
 * REST controller for managing {@link co.kr.crm.domain.CrmCustom}.
 */
@RestController
@RequestMapping("/api")
public class CrmCustomResource {

    private final Logger log = LoggerFactory.getLogger(CrmCustomResource.class);

    private static final String ENTITY_NAME = "crmCustom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrmCustomService crmCustomService;

    public CrmCustomResource(CrmCustomService crmCustomService) {
        this.crmCustomService = crmCustomService;
    }

    /**
     * {@code POST  /crm-customs} : Create a new crmCustom.
     *
     * @param crmCustom the crmCustom to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crmCustom, or with status {@code 400 (Bad Request)} if the crmCustom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crm-customs")
    public ResponseEntity<CrmCustom> createCrmCustom(@Valid @RequestBody CrmCustom crmCustom) throws URISyntaxException {
        log.debug("REST request to save CrmCustom : {}", crmCustom);
        if (crmCustom.getId() != null) {
            throw new BadRequestAlertException("A new crmCustom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CrmCustom result = crmCustomService.save(crmCustom);
        return ResponseEntity.created(new URI("/api/crm-customs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crm-customs} : Updates an existing crmCustom.
     *
     * @param crmCustom the crmCustom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crmCustom,
     * or with status {@code 400 (Bad Request)} if the crmCustom is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crmCustom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crm-customs")
    public ResponseEntity<CrmCustom> updateCrmCustom(@Valid @RequestBody CrmCustom crmCustom) throws URISyntaxException {
        log.debug("REST request to update CrmCustom : {}", crmCustom);
        if (crmCustom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CrmCustom result = crmCustomService.save(crmCustom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, crmCustom.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /crm-customs} : get all the crmCustoms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crmCustoms in body.
     */
    @GetMapping("/crm-customs")
    public ResponseEntity<List<CrmCustom>> getAllCrmCustoms(Pageable pageable) {
        log.debug("REST request to get a page of CrmCustoms");
        Page<CrmCustom> page = crmCustomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crm-customs/:id} : get the "id" crmCustom.
     *
     * @param id the id of the crmCustom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crmCustom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crm-customs/{id}")
    public ResponseEntity<CrmCustom> getCrmCustom(@PathVariable Long id) {
        log.debug("REST request to get CrmCustom : {}", id);
        Optional<CrmCustom> crmCustom = crmCustomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(crmCustom);
    }

    /**
     * {@code DELETE  /crm-customs/:id} : delete the "id" crmCustom.
     *
     * @param id the id of the crmCustom to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crm-customs/{id}")
    public ResponseEntity<Void> deleteCrmCustom(@PathVariable Long id) {
        log.debug("REST request to delete CrmCustom : {}", id);
        crmCustomService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
