package co.kr.crm.web.rest;

import co.kr.crm.domain.TeamGrp;
import co.kr.crm.service.TeamGrpService;
import co.kr.crm.web.rest.errors.BadRequestAlertException;
import co.kr.crm.service.dto.TeamGrpCriteria;
import co.kr.crm.service.TeamGrpQueryService;

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
 * REST controller for managing {@link co.kr.crm.domain.TeamGrp}.
 */
@RestController
@RequestMapping("/api")
public class TeamGrpResource {

    private final Logger log = LoggerFactory.getLogger(TeamGrpResource.class);

    private static final String ENTITY_NAME = "teamGrp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamGrpService teamGrpService;

    private final TeamGrpQueryService teamGrpQueryService;

    public TeamGrpResource(TeamGrpService teamGrpService, TeamGrpQueryService teamGrpQueryService) {
        this.teamGrpService = teamGrpService;
        this.teamGrpQueryService = teamGrpQueryService;
    }

    /**
     * {@code POST  /team-grps} : Create a new teamGrp.
     *
     * @param teamGrp the teamGrp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamGrp, or with status {@code 400 (Bad Request)} if the teamGrp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-grps")
    public ResponseEntity<TeamGrp> createTeamGrp(@RequestBody TeamGrp teamGrp) throws URISyntaxException {
        log.debug("REST request to save TeamGrp : {}", teamGrp);
        if (teamGrp.getId() != null) {
            throw new BadRequestAlertException("A new teamGrp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamGrp result = teamGrpService.save(teamGrp);
        return ResponseEntity.created(new URI("/api/team-grps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-grps} : Updates an existing teamGrp.
     *
     * @param teamGrp the teamGrp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamGrp,
     * or with status {@code 400 (Bad Request)} if the teamGrp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamGrp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-grps")
    public ResponseEntity<TeamGrp> updateTeamGrp(@RequestBody TeamGrp teamGrp) throws URISyntaxException {
        log.debug("REST request to update TeamGrp : {}", teamGrp);
        if (teamGrp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeamGrp result = teamGrpService.save(teamGrp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teamGrp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /team-grps} : get all the teamGrps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamGrps in body.
     */
    @GetMapping("/team-grps")
    public ResponseEntity<List<TeamGrp>> getAllTeamGrps(TeamGrpCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TeamGrps by criteria: {}", criteria);
        Page<TeamGrp> page = teamGrpQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /team-grps/count} : count all the teamGrps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/team-grps/count")
    public ResponseEntity<Long> countTeamGrps(TeamGrpCriteria criteria) {
        log.debug("REST request to count TeamGrps by criteria: {}", criteria);
        return ResponseEntity.ok().body(teamGrpQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /team-grps/:id} : get the "id" teamGrp.
     *
     * @param id the id of the teamGrp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamGrp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-grps/{id}")
    public ResponseEntity<TeamGrp> getTeamGrp(@PathVariable Long id) {
        log.debug("REST request to get TeamGrp : {}", id);
        Optional<TeamGrp> teamGrp = teamGrpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamGrp);
    }

    /**
     * {@code DELETE  /team-grps/:id} : delete the "id" teamGrp.
     *
     * @param id the id of the teamGrp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-grps/{id}")
    public ResponseEntity<Void> deleteTeamGrp(@PathVariable Long id) {
        log.debug("REST request to delete TeamGrp : {}", id);
        teamGrpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
