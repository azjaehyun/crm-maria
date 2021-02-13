package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.TeamGrp;
import co.kr.crm.domain.Manager;
import co.kr.crm.domain.TmManager;
import co.kr.crm.domain.Corp;
import co.kr.crm.repository.TeamGrpRepository;
import co.kr.crm.service.TeamGrpService;
import co.kr.crm.service.dto.TeamGrpCriteria;
import co.kr.crm.service.TeamGrpQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.kr.crm.domain.enumeration.Yn;
/**
 * Integration tests for the {@link TeamGrpResource} REST controller.
 */
@SpringBootTest(classes = CrmMariaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TeamGrpResourceIT {

    private static final String DEFAULT_TEAM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_NAME = "BBBBBBBBBB";

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private TeamGrpRepository teamGrpRepository;

    @Autowired
    private TeamGrpService teamGrpService;

    @Autowired
    private TeamGrpQueryService teamGrpQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamGrpMockMvc;

    private TeamGrp teamGrp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamGrp createEntity(EntityManager em) {
        TeamGrp teamGrp = new TeamGrp()
            .teamCode(DEFAULT_TEAM_CODE)
            .teamName(DEFAULT_TEAM_NAME)
            .useYn(DEFAULT_USE_YN);
        return teamGrp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamGrp createUpdatedEntity(EntityManager em) {
        TeamGrp teamGrp = new TeamGrp()
            .teamCode(UPDATED_TEAM_CODE)
            .teamName(UPDATED_TEAM_NAME)
            .useYn(UPDATED_USE_YN);
        return teamGrp;
    }

    @BeforeEach
    public void initTest() {
        teamGrp = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamGrp() throws Exception {
        int databaseSizeBeforeCreate = teamGrpRepository.findAll().size();
        // Create the TeamGrp
        restTeamGrpMockMvc.perform(post("/api/team-grps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamGrp)))
            .andExpect(status().isCreated());

        // Validate the TeamGrp in the database
        List<TeamGrp> teamGrpList = teamGrpRepository.findAll();
        assertThat(teamGrpList).hasSize(databaseSizeBeforeCreate + 1);
        TeamGrp testTeamGrp = teamGrpList.get(teamGrpList.size() - 1);
        assertThat(testTeamGrp.getTeamCode()).isEqualTo(DEFAULT_TEAM_CODE);
        assertThat(testTeamGrp.getTeamName()).isEqualTo(DEFAULT_TEAM_NAME);
        assertThat(testTeamGrp.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    public void createTeamGrpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamGrpRepository.findAll().size();

        // Create the TeamGrp with an existing ID
        teamGrp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamGrpMockMvc.perform(post("/api/team-grps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamGrp)))
            .andExpect(status().isBadRequest());

        // Validate the TeamGrp in the database
        List<TeamGrp> teamGrpList = teamGrpRepository.findAll();
        assertThat(teamGrpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTeamGrps() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList
        restTeamGrpMockMvc.perform(get("/api/team-grps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamGrp.getId().intValue())))
            .andExpect(jsonPath("$.[*].teamCode").value(hasItem(DEFAULT_TEAM_CODE)))
            .andExpect(jsonPath("$.[*].teamName").value(hasItem(DEFAULT_TEAM_NAME)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));
    }
    
    @Test
    @Transactional
    public void getTeamGrp() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get the teamGrp
        restTeamGrpMockMvc.perform(get("/api/team-grps/{id}", teamGrp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamGrp.getId().intValue()))
            .andExpect(jsonPath("$.teamCode").value(DEFAULT_TEAM_CODE))
            .andExpect(jsonPath("$.teamName").value(DEFAULT_TEAM_NAME))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.toString()));
    }


    @Test
    @Transactional
    public void getTeamGrpsByIdFiltering() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        Long id = teamGrp.getId();

        defaultTeamGrpShouldBeFound("id.equals=" + id);
        defaultTeamGrpShouldNotBeFound("id.notEquals=" + id);

        defaultTeamGrpShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTeamGrpShouldNotBeFound("id.greaterThan=" + id);

        defaultTeamGrpShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTeamGrpShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTeamGrpsByTeamCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamCode equals to DEFAULT_TEAM_CODE
        defaultTeamGrpShouldBeFound("teamCode.equals=" + DEFAULT_TEAM_CODE);

        // Get all the teamGrpList where teamCode equals to UPDATED_TEAM_CODE
        defaultTeamGrpShouldNotBeFound("teamCode.equals=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByTeamCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamCode not equals to DEFAULT_TEAM_CODE
        defaultTeamGrpShouldNotBeFound("teamCode.notEquals=" + DEFAULT_TEAM_CODE);

        // Get all the teamGrpList where teamCode not equals to UPDATED_TEAM_CODE
        defaultTeamGrpShouldBeFound("teamCode.notEquals=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByTeamCodeIsInShouldWork() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamCode in DEFAULT_TEAM_CODE or UPDATED_TEAM_CODE
        defaultTeamGrpShouldBeFound("teamCode.in=" + DEFAULT_TEAM_CODE + "," + UPDATED_TEAM_CODE);

        // Get all the teamGrpList where teamCode equals to UPDATED_TEAM_CODE
        defaultTeamGrpShouldNotBeFound("teamCode.in=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByTeamCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamCode is not null
        defaultTeamGrpShouldBeFound("teamCode.specified=true");

        // Get all the teamGrpList where teamCode is null
        defaultTeamGrpShouldNotBeFound("teamCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllTeamGrpsByTeamCodeContainsSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamCode contains DEFAULT_TEAM_CODE
        defaultTeamGrpShouldBeFound("teamCode.contains=" + DEFAULT_TEAM_CODE);

        // Get all the teamGrpList where teamCode contains UPDATED_TEAM_CODE
        defaultTeamGrpShouldNotBeFound("teamCode.contains=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByTeamCodeNotContainsSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamCode does not contain DEFAULT_TEAM_CODE
        defaultTeamGrpShouldNotBeFound("teamCode.doesNotContain=" + DEFAULT_TEAM_CODE);

        // Get all the teamGrpList where teamCode does not contain UPDATED_TEAM_CODE
        defaultTeamGrpShouldBeFound("teamCode.doesNotContain=" + UPDATED_TEAM_CODE);
    }


    @Test
    @Transactional
    public void getAllTeamGrpsByTeamNameIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamName equals to DEFAULT_TEAM_NAME
        defaultTeamGrpShouldBeFound("teamName.equals=" + DEFAULT_TEAM_NAME);

        // Get all the teamGrpList where teamName equals to UPDATED_TEAM_NAME
        defaultTeamGrpShouldNotBeFound("teamName.equals=" + UPDATED_TEAM_NAME);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByTeamNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamName not equals to DEFAULT_TEAM_NAME
        defaultTeamGrpShouldNotBeFound("teamName.notEquals=" + DEFAULT_TEAM_NAME);

        // Get all the teamGrpList where teamName not equals to UPDATED_TEAM_NAME
        defaultTeamGrpShouldBeFound("teamName.notEquals=" + UPDATED_TEAM_NAME);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByTeamNameIsInShouldWork() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamName in DEFAULT_TEAM_NAME or UPDATED_TEAM_NAME
        defaultTeamGrpShouldBeFound("teamName.in=" + DEFAULT_TEAM_NAME + "," + UPDATED_TEAM_NAME);

        // Get all the teamGrpList where teamName equals to UPDATED_TEAM_NAME
        defaultTeamGrpShouldNotBeFound("teamName.in=" + UPDATED_TEAM_NAME);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByTeamNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamName is not null
        defaultTeamGrpShouldBeFound("teamName.specified=true");

        // Get all the teamGrpList where teamName is null
        defaultTeamGrpShouldNotBeFound("teamName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTeamGrpsByTeamNameContainsSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamName contains DEFAULT_TEAM_NAME
        defaultTeamGrpShouldBeFound("teamName.contains=" + DEFAULT_TEAM_NAME);

        // Get all the teamGrpList where teamName contains UPDATED_TEAM_NAME
        defaultTeamGrpShouldNotBeFound("teamName.contains=" + UPDATED_TEAM_NAME);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByTeamNameNotContainsSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where teamName does not contain DEFAULT_TEAM_NAME
        defaultTeamGrpShouldNotBeFound("teamName.doesNotContain=" + DEFAULT_TEAM_NAME);

        // Get all the teamGrpList where teamName does not contain UPDATED_TEAM_NAME
        defaultTeamGrpShouldBeFound("teamName.doesNotContain=" + UPDATED_TEAM_NAME);
    }


    @Test
    @Transactional
    public void getAllTeamGrpsByUseYnIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where useYn equals to DEFAULT_USE_YN
        defaultTeamGrpShouldBeFound("useYn.equals=" + DEFAULT_USE_YN);

        // Get all the teamGrpList where useYn equals to UPDATED_USE_YN
        defaultTeamGrpShouldNotBeFound("useYn.equals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByUseYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where useYn not equals to DEFAULT_USE_YN
        defaultTeamGrpShouldNotBeFound("useYn.notEquals=" + DEFAULT_USE_YN);

        // Get all the teamGrpList where useYn not equals to UPDATED_USE_YN
        defaultTeamGrpShouldBeFound("useYn.notEquals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByUseYnIsInShouldWork() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where useYn in DEFAULT_USE_YN or UPDATED_USE_YN
        defaultTeamGrpShouldBeFound("useYn.in=" + DEFAULT_USE_YN + "," + UPDATED_USE_YN);

        // Get all the teamGrpList where useYn equals to UPDATED_USE_YN
        defaultTeamGrpShouldNotBeFound("useYn.in=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByUseYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);

        // Get all the teamGrpList where useYn is not null
        defaultTeamGrpShouldBeFound("useYn.specified=true");

        // Get all the teamGrpList where useYn is null
        defaultTeamGrpShouldNotBeFound("useYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeamGrpsByManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);
        Manager manager = ManagerResourceIT.createEntity(em);
        em.persist(manager);
        em.flush();
        teamGrp.addManager(manager);
        teamGrpRepository.saveAndFlush(teamGrp);
        Long managerId = manager.getId();

        // Get all the teamGrpList where manager equals to managerId
        defaultTeamGrpShouldBeFound("managerId.equals=" + managerId);

        // Get all the teamGrpList where manager equals to managerId + 1
        defaultTeamGrpShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }


    @Test
    @Transactional
    public void getAllTeamGrpsByTmManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);
        TmManager tmManager = TmManagerResourceIT.createEntity(em);
        em.persist(tmManager);
        em.flush();
        teamGrp.addTmManager(tmManager);
        teamGrpRepository.saveAndFlush(teamGrp);
        Long tmManagerId = tmManager.getId();

        // Get all the teamGrpList where tmManager equals to tmManagerId
        defaultTeamGrpShouldBeFound("tmManagerId.equals=" + tmManagerId);

        // Get all the teamGrpList where tmManager equals to tmManagerId + 1
        defaultTeamGrpShouldNotBeFound("tmManagerId.equals=" + (tmManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllTeamGrpsByCorpIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGrpRepository.saveAndFlush(teamGrp);
        Corp corp = CorpResourceIT.createEntity(em);
        em.persist(corp);
        em.flush();
        teamGrp.setCorp(corp);
        teamGrpRepository.saveAndFlush(teamGrp);
        Long corpId = corp.getId();

        // Get all the teamGrpList where corp equals to corpId
        defaultTeamGrpShouldBeFound("corpId.equals=" + corpId);

        // Get all the teamGrpList where corp equals to corpId + 1
        defaultTeamGrpShouldNotBeFound("corpId.equals=" + (corpId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeamGrpShouldBeFound(String filter) throws Exception {
        restTeamGrpMockMvc.perform(get("/api/team-grps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamGrp.getId().intValue())))
            .andExpect(jsonPath("$.[*].teamCode").value(hasItem(DEFAULT_TEAM_CODE)))
            .andExpect(jsonPath("$.[*].teamName").value(hasItem(DEFAULT_TEAM_NAME)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));

        // Check, that the count call also returns 1
        restTeamGrpMockMvc.perform(get("/api/team-grps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeamGrpShouldNotBeFound(String filter) throws Exception {
        restTeamGrpMockMvc.perform(get("/api/team-grps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeamGrpMockMvc.perform(get("/api/team-grps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTeamGrp() throws Exception {
        // Get the teamGrp
        restTeamGrpMockMvc.perform(get("/api/team-grps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamGrp() throws Exception {
        // Initialize the database
        teamGrpService.save(teamGrp);

        int databaseSizeBeforeUpdate = teamGrpRepository.findAll().size();

        // Update the teamGrp
        TeamGrp updatedTeamGrp = teamGrpRepository.findById(teamGrp.getId()).get();
        // Disconnect from session so that the updates on updatedTeamGrp are not directly saved in db
        em.detach(updatedTeamGrp);
        updatedTeamGrp
            .teamCode(UPDATED_TEAM_CODE)
            .teamName(UPDATED_TEAM_NAME)
            .useYn(UPDATED_USE_YN);

        restTeamGrpMockMvc.perform(put("/api/team-grps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeamGrp)))
            .andExpect(status().isOk());

        // Validate the TeamGrp in the database
        List<TeamGrp> teamGrpList = teamGrpRepository.findAll();
        assertThat(teamGrpList).hasSize(databaseSizeBeforeUpdate);
        TeamGrp testTeamGrp = teamGrpList.get(teamGrpList.size() - 1);
        assertThat(testTeamGrp.getTeamCode()).isEqualTo(UPDATED_TEAM_CODE);
        assertThat(testTeamGrp.getTeamName()).isEqualTo(UPDATED_TEAM_NAME);
        assertThat(testTeamGrp.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingTeamGrp() throws Exception {
        int databaseSizeBeforeUpdate = teamGrpRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamGrpMockMvc.perform(put("/api/team-grps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamGrp)))
            .andExpect(status().isBadRequest());

        // Validate the TeamGrp in the database
        List<TeamGrp> teamGrpList = teamGrpRepository.findAll();
        assertThat(teamGrpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeamGrp() throws Exception {
        // Initialize the database
        teamGrpService.save(teamGrp);

        int databaseSizeBeforeDelete = teamGrpRepository.findAll().size();

        // Delete the teamGrp
        restTeamGrpMockMvc.perform(delete("/api/team-grps/{id}", teamGrp.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamGrp> teamGrpList = teamGrpRepository.findAll();
        assertThat(teamGrpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
