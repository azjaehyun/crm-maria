package co.kr.crm.web.rest;

import co.kr.crm.domain.SendSmsHis;
import co.kr.crm.service.SendSmsHisService;
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
 * REST controller for managing {@link co.kr.crm.domain.SendSmsHis}.
 */
@RestController
@RequestMapping("/api")
public class SendSmsHisResource {

    private final Logger log = LoggerFactory.getLogger(SendSmsHisResource.class);

    private static final String ENTITY_NAME = "sendSmsHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SendSmsHisService sendSmsHisService;

    public SendSmsHisResource(SendSmsHisService sendSmsHisService) {
        this.sendSmsHisService = sendSmsHisService;
    }

    /**
     * {@code POST  /send-sms-his} : Create a new sendSmsHis.
     *
     * @param sendSmsHis the sendSmsHis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sendSmsHis, or with status {@code 400 (Bad Request)} if the sendSmsHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/send-sms-his")
    public ResponseEntity<SendSmsHis> createSendSmsHis(@RequestBody SendSmsHis sendSmsHis) throws URISyntaxException {
        log.debug("REST request to save SendSmsHis : {}", sendSmsHis);
        if (sendSmsHis.getId() != null) {
            throw new BadRequestAlertException("A new sendSmsHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SendSmsHis result = sendSmsHisService.save(sendSmsHis);
        return ResponseEntity.created(new URI("/api/send-sms-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /send-sms-his} : Updates an existing sendSmsHis.
     *
     * @param sendSmsHis the sendSmsHis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sendSmsHis,
     * or with status {@code 400 (Bad Request)} if the sendSmsHis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sendSmsHis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/send-sms-his")
    public ResponseEntity<SendSmsHis> updateSendSmsHis(@RequestBody SendSmsHis sendSmsHis) throws URISyntaxException {
        log.debug("REST request to update SendSmsHis : {}", sendSmsHis);
        if (sendSmsHis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SendSmsHis result = sendSmsHisService.save(sendSmsHis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sendSmsHis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /send-sms-his} : get all the sendSmsHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sendSmsHis in body.
     */
    @GetMapping("/send-sms-his")
    public ResponseEntity<List<SendSmsHis>> getAllSendSmsHis(Pageable pageable) {
        log.debug("REST request to get a page of SendSmsHis");
        Page<SendSmsHis> page = sendSmsHisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /send-sms-his/:id} : get the "id" sendSmsHis.
     *
     * @param id the id of the sendSmsHis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sendSmsHis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/send-sms-his/{id}")
    public ResponseEntity<SendSmsHis> getSendSmsHis(@PathVariable Long id) {
        log.debug("REST request to get SendSmsHis : {}", id);
        Optional<SendSmsHis> sendSmsHis = sendSmsHisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sendSmsHis);
    }

    /**
     * {@code DELETE  /send-sms-his/:id} : delete the "id" sendSmsHis.
     *
     * @param id the id of the sendSmsHis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/send-sms-his/{id}")
    public ResponseEntity<Void> deleteSendSmsHis(@PathVariable Long id) {
        log.debug("REST request to delete SendSmsHis : {}", id);
        sendSmsHisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
