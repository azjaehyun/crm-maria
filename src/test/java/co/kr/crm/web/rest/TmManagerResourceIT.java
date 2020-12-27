package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.TmManager;
import co.kr.crm.repository.TmManagerRepository;
import co.kr.crm.service.TmManagerService;

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
 * Integration tests for the {@link TmManagerResource} REST controller.
 */
@SpringBootTest(classes = CrmMariaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TmManagerResourceIT {

    private static final String DEFAULT_CORP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CORP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TM_MANAGER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TM_MANAGER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TM_MANAGER_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_TM_MANAGER_PHONE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CRM_MANAGE_CNT = 1;
    private static final Integer UPDATED_CRM_MANAGE_CNT = 2;

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private TmManagerRepository tmManagerRepository;

    @Autowired
    private TmManagerService tmManagerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTmManagerMockMvc;

    private TmManager tmManager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TmManager createEntity(EntityManager em) {
        TmManager tmManager = new TmManager()
            .corpCode(DEFAULT_CORP_CODE)
            .tmManagerName(DEFAULT_TM_MANAGER_NAME)
            .tmManagerPhoneNum(DEFAULT_TM_MANAGER_PHONE_NUM)
            .teamCode(DEFAULT_TEAM_CODE)
            .crmManageCnt(DEFAULT_CRM_MANAGE_CNT)
            .useYn(DEFAULT_USE_YN);
        return tmManager;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TmManager createUpdatedEntity(EntityManager em) {
        TmManager tmManager = new TmManager()
            .corpCode(UPDATED_CORP_CODE)
            .tmManagerName(UPDATED_TM_MANAGER_NAME)
            .tmManagerPhoneNum(UPDATED_TM_MANAGER_PHONE_NUM)
            .teamCode(UPDATED_TEAM_CODE)
            .crmManageCnt(UPDATED_CRM_MANAGE_CNT)
            .useYn(UPDATED_USE_YN);
        return tmManager;
    }

    @BeforeEach
    public void initTest() {
        tmManager = createEntity(em);
    }

    @Test
    @Transactional
    public void createTmManager() throws Exception {
        int databaseSizeBeforeCreate = tmManagerRepository.findAll().size();
        // Create the TmManager
        restTmManagerMockMvc.perform(post("/api/tm-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tmManager)))
            .andExpect(status().isCreated());

        // Validate the TmManager in the database
        List<TmManager> tmManagerList = tmManagerRepository.findAll();
        assertThat(tmManagerList).hasSize(databaseSizeBeforeCreate + 1);
        TmManager testTmManager = tmManagerList.get(tmManagerList.size() - 1);
        assertThat(testTmManager.getCorpCode()).isEqualTo(DEFAULT_CORP_CODE);
        assertThat(testTmManager.getTmManagerName()).isEqualTo(DEFAULT_TM_MANAGER_NAME);
        assertThat(testTmManager.getTmManagerPhoneNum()).isEqualTo(DEFAULT_TM_MANAGER_PHONE_NUM);
        assertThat(testTmManager.getTeamCode()).isEqualTo(DEFAULT_TEAM_CODE);
        assertThat(testTmManager.getCrmManageCnt()).isEqualTo(DEFAULT_CRM_MANAGE_CNT);
        assertThat(testTmManager.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    public void createTmManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tmManagerRepository.findAll().size();

        // Create the TmManager with an existing ID
        tmManager.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTmManagerMockMvc.perform(post("/api/tm-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tmManager)))
            .andExpect(status().isBadRequest());

        // Validate the TmManager in the database
        List<TmManager> tmManagerList = tmManagerRepository.findAll();
        assertThat(tmManagerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTmManagerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tmManagerRepository.findAll().size();
        // set the field null
        tmManager.setTmManagerName(null);

        // Create the TmManager, which fails.


        restTmManagerMockMvc.perform(post("/api/tm-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tmManager)))
            .andExpect(status().isBadRequest());

        List<TmManager> tmManagerList = tmManagerRepository.findAll();
        assertThat(tmManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTmManagers() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList
        restTmManagerMockMvc.perform(get("/api/tm-managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tmManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpCode").value(hasItem(DEFAULT_CORP_CODE)))
            .andExpect(jsonPath("$.[*].tmManagerName").value(hasItem(DEFAULT_TM_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].tmManagerPhoneNum").value(hasItem(DEFAULT_TM_MANAGER_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].teamCode").value(hasItem(DEFAULT_TEAM_CODE)))
            .andExpect(jsonPath("$.[*].crmManageCnt").value(hasItem(DEFAULT_CRM_MANAGE_CNT)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));
    }
    
    @Test
    @Transactional
    public void getTmManager() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get the tmManager
        restTmManagerMockMvc.perform(get("/api/tm-managers/{id}", tmManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tmManager.getId().intValue()))
            .andExpect(jsonPath("$.corpCode").value(DEFAULT_CORP_CODE))
            .andExpect(jsonPath("$.tmManagerName").value(DEFAULT_TM_MANAGER_NAME))
            .andExpect(jsonPath("$.tmManagerPhoneNum").value(DEFAULT_TM_MANAGER_PHONE_NUM))
            .andExpect(jsonPath("$.teamCode").value(DEFAULT_TEAM_CODE))
            .andExpect(jsonPath("$.crmManageCnt").value(DEFAULT_CRM_MANAGE_CNT))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTmManager() throws Exception {
        // Get the tmManager
        restTmManagerMockMvc.perform(get("/api/tm-managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTmManager() throws Exception {
        // Initialize the database
        tmManagerService.save(tmManager);

        int databaseSizeBeforeUpdate = tmManagerRepository.findAll().size();

        // Update the tmManager
        TmManager updatedTmManager = tmManagerRepository.findById(tmManager.getId()).get();
        // Disconnect from session so that the updates on updatedTmManager are not directly saved in db
        em.detach(updatedTmManager);
        updatedTmManager
            .corpCode(UPDATED_CORP_CODE)
            .tmManagerName(UPDATED_TM_MANAGER_NAME)
            .tmManagerPhoneNum(UPDATED_TM_MANAGER_PHONE_NUM)
            .teamCode(UPDATED_TEAM_CODE)
            .crmManageCnt(UPDATED_CRM_MANAGE_CNT)
            .useYn(UPDATED_USE_YN);

        restTmManagerMockMvc.perform(put("/api/tm-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTmManager)))
            .andExpect(status().isOk());

        // Validate the TmManager in the database
        List<TmManager> tmManagerList = tmManagerRepository.findAll();
        assertThat(tmManagerList).hasSize(databaseSizeBeforeUpdate);
        TmManager testTmManager = tmManagerList.get(tmManagerList.size() - 1);
        assertThat(testTmManager.getCorpCode()).isEqualTo(UPDATED_CORP_CODE);
        assertThat(testTmManager.getTmManagerName()).isEqualTo(UPDATED_TM_MANAGER_NAME);
        assertThat(testTmManager.getTmManagerPhoneNum()).isEqualTo(UPDATED_TM_MANAGER_PHONE_NUM);
        assertThat(testTmManager.getTeamCode()).isEqualTo(UPDATED_TEAM_CODE);
        assertThat(testTmManager.getCrmManageCnt()).isEqualTo(UPDATED_CRM_MANAGE_CNT);
        assertThat(testTmManager.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingTmManager() throws Exception {
        int databaseSizeBeforeUpdate = tmManagerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTmManagerMockMvc.perform(put("/api/tm-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tmManager)))
            .andExpect(status().isBadRequest());

        // Validate the TmManager in the database
        List<TmManager> tmManagerList = tmManagerRepository.findAll();
        assertThat(tmManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTmManager() throws Exception {
        // Initialize the database
        tmManagerService.save(tmManager);

        int databaseSizeBeforeDelete = tmManagerRepository.findAll().size();

        // Delete the tmManager
        restTmManagerMockMvc.perform(delete("/api/tm-managers/{id}", tmManager.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TmManager> tmManagerList = tmManagerRepository.findAll();
        assertThat(tmManagerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
