package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.TmManager;
import co.kr.crm.domain.CrmCustom;
import co.kr.crm.domain.TeamGrp;
import co.kr.crm.repository.TmManagerRepository;
import co.kr.crm.service.TmManagerService;
import co.kr.crm.service.dto.TmManagerCriteria;
import co.kr.crm.service.TmManagerQueryService;

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
    private static final Integer SMALLER_CRM_MANAGE_CNT = 1 - 1;

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private TmManagerRepository tmManagerRepository;

    @Autowired
    private TmManagerService tmManagerService;

    @Autowired
    private TmManagerQueryService tmManagerQueryService;

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
    public void getTmManagersByIdFiltering() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        Long id = tmManager.getId();

        defaultTmManagerShouldBeFound("id.equals=" + id);
        defaultTmManagerShouldNotBeFound("id.notEquals=" + id);

        defaultTmManagerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTmManagerShouldNotBeFound("id.greaterThan=" + id);

        defaultTmManagerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTmManagerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTmManagersByCorpCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where corpCode equals to DEFAULT_CORP_CODE
        defaultTmManagerShouldBeFound("corpCode.equals=" + DEFAULT_CORP_CODE);

        // Get all the tmManagerList where corpCode equals to UPDATED_CORP_CODE
        defaultTmManagerShouldNotBeFound("corpCode.equals=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCorpCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where corpCode not equals to DEFAULT_CORP_CODE
        defaultTmManagerShouldNotBeFound("corpCode.notEquals=" + DEFAULT_CORP_CODE);

        // Get all the tmManagerList where corpCode not equals to UPDATED_CORP_CODE
        defaultTmManagerShouldBeFound("corpCode.notEquals=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCorpCodeIsInShouldWork() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where corpCode in DEFAULT_CORP_CODE or UPDATED_CORP_CODE
        defaultTmManagerShouldBeFound("corpCode.in=" + DEFAULT_CORP_CODE + "," + UPDATED_CORP_CODE);

        // Get all the tmManagerList where corpCode equals to UPDATED_CORP_CODE
        defaultTmManagerShouldNotBeFound("corpCode.in=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCorpCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where corpCode is not null
        defaultTmManagerShouldBeFound("corpCode.specified=true");

        // Get all the tmManagerList where corpCode is null
        defaultTmManagerShouldNotBeFound("corpCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllTmManagersByCorpCodeContainsSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where corpCode contains DEFAULT_CORP_CODE
        defaultTmManagerShouldBeFound("corpCode.contains=" + DEFAULT_CORP_CODE);

        // Get all the tmManagerList where corpCode contains UPDATED_CORP_CODE
        defaultTmManagerShouldNotBeFound("corpCode.contains=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCorpCodeNotContainsSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where corpCode does not contain DEFAULT_CORP_CODE
        defaultTmManagerShouldNotBeFound("corpCode.doesNotContain=" + DEFAULT_CORP_CODE);

        // Get all the tmManagerList where corpCode does not contain UPDATED_CORP_CODE
        defaultTmManagerShouldBeFound("corpCode.doesNotContain=" + UPDATED_CORP_CODE);
    }


    @Test
    @Transactional
    public void getAllTmManagersByTmManagerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerName equals to DEFAULT_TM_MANAGER_NAME
        defaultTmManagerShouldBeFound("tmManagerName.equals=" + DEFAULT_TM_MANAGER_NAME);

        // Get all the tmManagerList where tmManagerName equals to UPDATED_TM_MANAGER_NAME
        defaultTmManagerShouldNotBeFound("tmManagerName.equals=" + UPDATED_TM_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTmManagerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerName not equals to DEFAULT_TM_MANAGER_NAME
        defaultTmManagerShouldNotBeFound("tmManagerName.notEquals=" + DEFAULT_TM_MANAGER_NAME);

        // Get all the tmManagerList where tmManagerName not equals to UPDATED_TM_MANAGER_NAME
        defaultTmManagerShouldBeFound("tmManagerName.notEquals=" + UPDATED_TM_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTmManagerNameIsInShouldWork() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerName in DEFAULT_TM_MANAGER_NAME or UPDATED_TM_MANAGER_NAME
        defaultTmManagerShouldBeFound("tmManagerName.in=" + DEFAULT_TM_MANAGER_NAME + "," + UPDATED_TM_MANAGER_NAME);

        // Get all the tmManagerList where tmManagerName equals to UPDATED_TM_MANAGER_NAME
        defaultTmManagerShouldNotBeFound("tmManagerName.in=" + UPDATED_TM_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTmManagerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerName is not null
        defaultTmManagerShouldBeFound("tmManagerName.specified=true");

        // Get all the tmManagerList where tmManagerName is null
        defaultTmManagerShouldNotBeFound("tmManagerName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTmManagersByTmManagerNameContainsSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerName contains DEFAULT_TM_MANAGER_NAME
        defaultTmManagerShouldBeFound("tmManagerName.contains=" + DEFAULT_TM_MANAGER_NAME);

        // Get all the tmManagerList where tmManagerName contains UPDATED_TM_MANAGER_NAME
        defaultTmManagerShouldNotBeFound("tmManagerName.contains=" + UPDATED_TM_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTmManagerNameNotContainsSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerName does not contain DEFAULT_TM_MANAGER_NAME
        defaultTmManagerShouldNotBeFound("tmManagerName.doesNotContain=" + DEFAULT_TM_MANAGER_NAME);

        // Get all the tmManagerList where tmManagerName does not contain UPDATED_TM_MANAGER_NAME
        defaultTmManagerShouldBeFound("tmManagerName.doesNotContain=" + UPDATED_TM_MANAGER_NAME);
    }


    @Test
    @Transactional
    public void getAllTmManagersByTmManagerPhoneNumIsEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerPhoneNum equals to DEFAULT_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldBeFound("tmManagerPhoneNum.equals=" + DEFAULT_TM_MANAGER_PHONE_NUM);

        // Get all the tmManagerList where tmManagerPhoneNum equals to UPDATED_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldNotBeFound("tmManagerPhoneNum.equals=" + UPDATED_TM_MANAGER_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTmManagerPhoneNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerPhoneNum not equals to DEFAULT_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldNotBeFound("tmManagerPhoneNum.notEquals=" + DEFAULT_TM_MANAGER_PHONE_NUM);

        // Get all the tmManagerList where tmManagerPhoneNum not equals to UPDATED_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldBeFound("tmManagerPhoneNum.notEquals=" + UPDATED_TM_MANAGER_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTmManagerPhoneNumIsInShouldWork() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerPhoneNum in DEFAULT_TM_MANAGER_PHONE_NUM or UPDATED_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldBeFound("tmManagerPhoneNum.in=" + DEFAULT_TM_MANAGER_PHONE_NUM + "," + UPDATED_TM_MANAGER_PHONE_NUM);

        // Get all the tmManagerList where tmManagerPhoneNum equals to UPDATED_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldNotBeFound("tmManagerPhoneNum.in=" + UPDATED_TM_MANAGER_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTmManagerPhoneNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerPhoneNum is not null
        defaultTmManagerShouldBeFound("tmManagerPhoneNum.specified=true");

        // Get all the tmManagerList where tmManagerPhoneNum is null
        defaultTmManagerShouldNotBeFound("tmManagerPhoneNum.specified=false");
    }
                @Test
    @Transactional
    public void getAllTmManagersByTmManagerPhoneNumContainsSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerPhoneNum contains DEFAULT_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldBeFound("tmManagerPhoneNum.contains=" + DEFAULT_TM_MANAGER_PHONE_NUM);

        // Get all the tmManagerList where tmManagerPhoneNum contains UPDATED_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldNotBeFound("tmManagerPhoneNum.contains=" + UPDATED_TM_MANAGER_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTmManagerPhoneNumNotContainsSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where tmManagerPhoneNum does not contain DEFAULT_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldNotBeFound("tmManagerPhoneNum.doesNotContain=" + DEFAULT_TM_MANAGER_PHONE_NUM);

        // Get all the tmManagerList where tmManagerPhoneNum does not contain UPDATED_TM_MANAGER_PHONE_NUM
        defaultTmManagerShouldBeFound("tmManagerPhoneNum.doesNotContain=" + UPDATED_TM_MANAGER_PHONE_NUM);
    }


    @Test
    @Transactional
    public void getAllTmManagersByTeamCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where teamCode equals to DEFAULT_TEAM_CODE
        defaultTmManagerShouldBeFound("teamCode.equals=" + DEFAULT_TEAM_CODE);

        // Get all the tmManagerList where teamCode equals to UPDATED_TEAM_CODE
        defaultTmManagerShouldNotBeFound("teamCode.equals=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTeamCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where teamCode not equals to DEFAULT_TEAM_CODE
        defaultTmManagerShouldNotBeFound("teamCode.notEquals=" + DEFAULT_TEAM_CODE);

        // Get all the tmManagerList where teamCode not equals to UPDATED_TEAM_CODE
        defaultTmManagerShouldBeFound("teamCode.notEquals=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTeamCodeIsInShouldWork() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where teamCode in DEFAULT_TEAM_CODE or UPDATED_TEAM_CODE
        defaultTmManagerShouldBeFound("teamCode.in=" + DEFAULT_TEAM_CODE + "," + UPDATED_TEAM_CODE);

        // Get all the tmManagerList where teamCode equals to UPDATED_TEAM_CODE
        defaultTmManagerShouldNotBeFound("teamCode.in=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTeamCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where teamCode is not null
        defaultTmManagerShouldBeFound("teamCode.specified=true");

        // Get all the tmManagerList where teamCode is null
        defaultTmManagerShouldNotBeFound("teamCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllTmManagersByTeamCodeContainsSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where teamCode contains DEFAULT_TEAM_CODE
        defaultTmManagerShouldBeFound("teamCode.contains=" + DEFAULT_TEAM_CODE);

        // Get all the tmManagerList where teamCode contains UPDATED_TEAM_CODE
        defaultTmManagerShouldNotBeFound("teamCode.contains=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllTmManagersByTeamCodeNotContainsSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where teamCode does not contain DEFAULT_TEAM_CODE
        defaultTmManagerShouldNotBeFound("teamCode.doesNotContain=" + DEFAULT_TEAM_CODE);

        // Get all the tmManagerList where teamCode does not contain UPDATED_TEAM_CODE
        defaultTmManagerShouldBeFound("teamCode.doesNotContain=" + UPDATED_TEAM_CODE);
    }


    @Test
    @Transactional
    public void getAllTmManagersByCrmManageCntIsEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where crmManageCnt equals to DEFAULT_CRM_MANAGE_CNT
        defaultTmManagerShouldBeFound("crmManageCnt.equals=" + DEFAULT_CRM_MANAGE_CNT);

        // Get all the tmManagerList where crmManageCnt equals to UPDATED_CRM_MANAGE_CNT
        defaultTmManagerShouldNotBeFound("crmManageCnt.equals=" + UPDATED_CRM_MANAGE_CNT);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCrmManageCntIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where crmManageCnt not equals to DEFAULT_CRM_MANAGE_CNT
        defaultTmManagerShouldNotBeFound("crmManageCnt.notEquals=" + DEFAULT_CRM_MANAGE_CNT);

        // Get all the tmManagerList where crmManageCnt not equals to UPDATED_CRM_MANAGE_CNT
        defaultTmManagerShouldBeFound("crmManageCnt.notEquals=" + UPDATED_CRM_MANAGE_CNT);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCrmManageCntIsInShouldWork() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where crmManageCnt in DEFAULT_CRM_MANAGE_CNT or UPDATED_CRM_MANAGE_CNT
        defaultTmManagerShouldBeFound("crmManageCnt.in=" + DEFAULT_CRM_MANAGE_CNT + "," + UPDATED_CRM_MANAGE_CNT);

        // Get all the tmManagerList where crmManageCnt equals to UPDATED_CRM_MANAGE_CNT
        defaultTmManagerShouldNotBeFound("crmManageCnt.in=" + UPDATED_CRM_MANAGE_CNT);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCrmManageCntIsNullOrNotNull() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where crmManageCnt is not null
        defaultTmManagerShouldBeFound("crmManageCnt.specified=true");

        // Get all the tmManagerList where crmManageCnt is null
        defaultTmManagerShouldNotBeFound("crmManageCnt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTmManagersByCrmManageCntIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where crmManageCnt is greater than or equal to DEFAULT_CRM_MANAGE_CNT
        defaultTmManagerShouldBeFound("crmManageCnt.greaterThanOrEqual=" + DEFAULT_CRM_MANAGE_CNT);

        // Get all the tmManagerList where crmManageCnt is greater than or equal to UPDATED_CRM_MANAGE_CNT
        defaultTmManagerShouldNotBeFound("crmManageCnt.greaterThanOrEqual=" + UPDATED_CRM_MANAGE_CNT);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCrmManageCntIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where crmManageCnt is less than or equal to DEFAULT_CRM_MANAGE_CNT
        defaultTmManagerShouldBeFound("crmManageCnt.lessThanOrEqual=" + DEFAULT_CRM_MANAGE_CNT);

        // Get all the tmManagerList where crmManageCnt is less than or equal to SMALLER_CRM_MANAGE_CNT
        defaultTmManagerShouldNotBeFound("crmManageCnt.lessThanOrEqual=" + SMALLER_CRM_MANAGE_CNT);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCrmManageCntIsLessThanSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where crmManageCnt is less than DEFAULT_CRM_MANAGE_CNT
        defaultTmManagerShouldNotBeFound("crmManageCnt.lessThan=" + DEFAULT_CRM_MANAGE_CNT);

        // Get all the tmManagerList where crmManageCnt is less than UPDATED_CRM_MANAGE_CNT
        defaultTmManagerShouldBeFound("crmManageCnt.lessThan=" + UPDATED_CRM_MANAGE_CNT);
    }

    @Test
    @Transactional
    public void getAllTmManagersByCrmManageCntIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where crmManageCnt is greater than DEFAULT_CRM_MANAGE_CNT
        defaultTmManagerShouldNotBeFound("crmManageCnt.greaterThan=" + DEFAULT_CRM_MANAGE_CNT);

        // Get all the tmManagerList where crmManageCnt is greater than SMALLER_CRM_MANAGE_CNT
        defaultTmManagerShouldBeFound("crmManageCnt.greaterThan=" + SMALLER_CRM_MANAGE_CNT);
    }


    @Test
    @Transactional
    public void getAllTmManagersByUseYnIsEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where useYn equals to DEFAULT_USE_YN
        defaultTmManagerShouldBeFound("useYn.equals=" + DEFAULT_USE_YN);

        // Get all the tmManagerList where useYn equals to UPDATED_USE_YN
        defaultTmManagerShouldNotBeFound("useYn.equals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllTmManagersByUseYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where useYn not equals to DEFAULT_USE_YN
        defaultTmManagerShouldNotBeFound("useYn.notEquals=" + DEFAULT_USE_YN);

        // Get all the tmManagerList where useYn not equals to UPDATED_USE_YN
        defaultTmManagerShouldBeFound("useYn.notEquals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllTmManagersByUseYnIsInShouldWork() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where useYn in DEFAULT_USE_YN or UPDATED_USE_YN
        defaultTmManagerShouldBeFound("useYn.in=" + DEFAULT_USE_YN + "," + UPDATED_USE_YN);

        // Get all the tmManagerList where useYn equals to UPDATED_USE_YN
        defaultTmManagerShouldNotBeFound("useYn.in=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllTmManagersByUseYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);

        // Get all the tmManagerList where useYn is not null
        defaultTmManagerShouldBeFound("useYn.specified=true");

        // Get all the tmManagerList where useYn is null
        defaultTmManagerShouldNotBeFound("useYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllTmManagersByCrmCustomIsEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);
        CrmCustom crmCustom = CrmCustomResourceIT.createEntity(em);
        em.persist(crmCustom);
        em.flush();
        tmManager.addCrmCustom(crmCustom);
        tmManagerRepository.saveAndFlush(tmManager);
        Long crmCustomId = crmCustom.getId();

        // Get all the tmManagerList where crmCustom equals to crmCustomId
        defaultTmManagerShouldBeFound("crmCustomId.equals=" + crmCustomId);

        // Get all the tmManagerList where crmCustom equals to crmCustomId + 1
        defaultTmManagerShouldNotBeFound("crmCustomId.equals=" + (crmCustomId + 1));
    }


    @Test
    @Transactional
    public void getAllTmManagersByTeamIsEqualToSomething() throws Exception {
        // Initialize the database
        tmManagerRepository.saveAndFlush(tmManager);
        TeamGrp team = TeamGrpResourceIT.createEntity(em);
        em.persist(team);
        em.flush();
        tmManager.setTeam(team);
        tmManagerRepository.saveAndFlush(tmManager);
        Long teamId = team.getId();

        // Get all the tmManagerList where team equals to teamId
        defaultTmManagerShouldBeFound("teamId.equals=" + teamId);

        // Get all the tmManagerList where team equals to teamId + 1
        defaultTmManagerShouldNotBeFound("teamId.equals=" + (teamId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTmManagerShouldBeFound(String filter) throws Exception {
        restTmManagerMockMvc.perform(get("/api/tm-managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tmManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpCode").value(hasItem(DEFAULT_CORP_CODE)))
            .andExpect(jsonPath("$.[*].tmManagerName").value(hasItem(DEFAULT_TM_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].tmManagerPhoneNum").value(hasItem(DEFAULT_TM_MANAGER_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].teamCode").value(hasItem(DEFAULT_TEAM_CODE)))
            .andExpect(jsonPath("$.[*].crmManageCnt").value(hasItem(DEFAULT_CRM_MANAGE_CNT)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));

        // Check, that the count call also returns 1
        restTmManagerMockMvc.perform(get("/api/tm-managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTmManagerShouldNotBeFound(String filter) throws Exception {
        restTmManagerMockMvc.perform(get("/api/tm-managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTmManagerMockMvc.perform(get("/api/tm-managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
