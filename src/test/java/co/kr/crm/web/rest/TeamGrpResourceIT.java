package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.TeamGrp;
import co.kr.crm.repository.TeamGrpRepository;
import co.kr.crm.service.TeamGrpService;

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
