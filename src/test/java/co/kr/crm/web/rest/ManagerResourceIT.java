package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.Manager;
import co.kr.crm.domain.CrmCustom;
import co.kr.crm.domain.TeamGrp;
import co.kr.crm.repository.ManagerRepository;
import co.kr.crm.service.ManagerService;
import co.kr.crm.service.dto.ManagerCriteria;
import co.kr.crm.service.ManagerQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.kr.crm.domain.enumeration.Yn;
/**
 * Integration tests for the {@link ManagerResource} REST controller.
 */
@SpringBootTest(classes = CrmMariaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ManagerResourceIT {

    private static final String DEFAULT_CORP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CORP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_PHONE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_SALES_AMOUNT = 1;
    private static final Integer UPDATED_TOTAL_SALES_AMOUNT = 2;
    private static final Integer SMALLER_TOTAL_SALES_AMOUNT = 1 - 1;

    private static final LocalDate DEFAULT_ENTER_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENTER_DAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ENTER_DAY = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_OUT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OUT_DAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_OUT_DAY = LocalDate.ofEpochDay(-1L);

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerQueryService managerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManagerMockMvc;

    private Manager manager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manager createEntity(EntityManager em) {
        Manager manager = new Manager()
            .corpCode(DEFAULT_CORP_CODE)
            .managerName(DEFAULT_MANAGER_NAME)
            .managerPhoneNum(DEFAULT_MANAGER_PHONE_NUM)
            .teamCode(DEFAULT_TEAM_CODE)
            .totalSalesAmount(DEFAULT_TOTAL_SALES_AMOUNT)
            .enterDay(DEFAULT_ENTER_DAY)
            .outDay(DEFAULT_OUT_DAY)
            .useYn(DEFAULT_USE_YN);
        return manager;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manager createUpdatedEntity(EntityManager em) {
        Manager manager = new Manager()
            .corpCode(UPDATED_CORP_CODE)
            .managerName(UPDATED_MANAGER_NAME)
            .managerPhoneNum(UPDATED_MANAGER_PHONE_NUM)
            .teamCode(UPDATED_TEAM_CODE)
            .totalSalesAmount(UPDATED_TOTAL_SALES_AMOUNT)
            .enterDay(UPDATED_ENTER_DAY)
            .outDay(UPDATED_OUT_DAY)
            .useYn(UPDATED_USE_YN);
        return manager;
    }

    @BeforeEach
    public void initTest() {
        manager = createEntity(em);
    }

    @Test
    @Transactional
    public void createManager() throws Exception {
        int databaseSizeBeforeCreate = managerRepository.findAll().size();
        // Create the Manager
        restManagerMockMvc.perform(post("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(manager)))
            .andExpect(status().isCreated());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeCreate + 1);
        Manager testManager = managerList.get(managerList.size() - 1);
        assertThat(testManager.getCorpCode()).isEqualTo(DEFAULT_CORP_CODE);
        assertThat(testManager.getManagerName()).isEqualTo(DEFAULT_MANAGER_NAME);
        assertThat(testManager.getManagerPhoneNum()).isEqualTo(DEFAULT_MANAGER_PHONE_NUM);
        assertThat(testManager.getTeamCode()).isEqualTo(DEFAULT_TEAM_CODE);
        assertThat(testManager.getTotalSalesAmount()).isEqualTo(DEFAULT_TOTAL_SALES_AMOUNT);
        assertThat(testManager.getEnterDay()).isEqualTo(DEFAULT_ENTER_DAY);
        assertThat(testManager.getOutDay()).isEqualTo(DEFAULT_OUT_DAY);
        assertThat(testManager.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    public void createManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = managerRepository.findAll().size();

        // Create the Manager with an existing ID
        manager.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManagerMockMvc.perform(post("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(manager)))
            .andExpect(status().isBadRequest());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkManagerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = managerRepository.findAll().size();
        // set the field null
        manager.setManagerName(null);

        // Create the Manager, which fails.


        restManagerMockMvc.perform(post("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(manager)))
            .andExpect(status().isBadRequest());

        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllManagers() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList
        restManagerMockMvc.perform(get("/api/managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manager.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpCode").value(hasItem(DEFAULT_CORP_CODE)))
            .andExpect(jsonPath("$.[*].managerName").value(hasItem(DEFAULT_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].managerPhoneNum").value(hasItem(DEFAULT_MANAGER_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].teamCode").value(hasItem(DEFAULT_TEAM_CODE)))
            .andExpect(jsonPath("$.[*].totalSalesAmount").value(hasItem(DEFAULT_TOTAL_SALES_AMOUNT)))
            .andExpect(jsonPath("$.[*].enterDay").value(hasItem(DEFAULT_ENTER_DAY.toString())))
            .andExpect(jsonPath("$.[*].outDay").value(hasItem(DEFAULT_OUT_DAY.toString())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));
    }
    
    @Test
    @Transactional
    public void getManager() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get the manager
        restManagerMockMvc.perform(get("/api/managers/{id}", manager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(manager.getId().intValue()))
            .andExpect(jsonPath("$.corpCode").value(DEFAULT_CORP_CODE))
            .andExpect(jsonPath("$.managerName").value(DEFAULT_MANAGER_NAME))
            .andExpect(jsonPath("$.managerPhoneNum").value(DEFAULT_MANAGER_PHONE_NUM))
            .andExpect(jsonPath("$.teamCode").value(DEFAULT_TEAM_CODE))
            .andExpect(jsonPath("$.totalSalesAmount").value(DEFAULT_TOTAL_SALES_AMOUNT))
            .andExpect(jsonPath("$.enterDay").value(DEFAULT_ENTER_DAY.toString()))
            .andExpect(jsonPath("$.outDay").value(DEFAULT_OUT_DAY.toString()))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.toString()));
    }


    @Test
    @Transactional
    public void getManagersByIdFiltering() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        Long id = manager.getId();

        defaultManagerShouldBeFound("id.equals=" + id);
        defaultManagerShouldNotBeFound("id.notEquals=" + id);

        defaultManagerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultManagerShouldNotBeFound("id.greaterThan=" + id);

        defaultManagerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultManagerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllManagersByCorpCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where corpCode equals to DEFAULT_CORP_CODE
        defaultManagerShouldBeFound("corpCode.equals=" + DEFAULT_CORP_CODE);

        // Get all the managerList where corpCode equals to UPDATED_CORP_CODE
        defaultManagerShouldNotBeFound("corpCode.equals=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllManagersByCorpCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where corpCode not equals to DEFAULT_CORP_CODE
        defaultManagerShouldNotBeFound("corpCode.notEquals=" + DEFAULT_CORP_CODE);

        // Get all the managerList where corpCode not equals to UPDATED_CORP_CODE
        defaultManagerShouldBeFound("corpCode.notEquals=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllManagersByCorpCodeIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where corpCode in DEFAULT_CORP_CODE or UPDATED_CORP_CODE
        defaultManagerShouldBeFound("corpCode.in=" + DEFAULT_CORP_CODE + "," + UPDATED_CORP_CODE);

        // Get all the managerList where corpCode equals to UPDATED_CORP_CODE
        defaultManagerShouldNotBeFound("corpCode.in=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllManagersByCorpCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where corpCode is not null
        defaultManagerShouldBeFound("corpCode.specified=true");

        // Get all the managerList where corpCode is null
        defaultManagerShouldNotBeFound("corpCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllManagersByCorpCodeContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where corpCode contains DEFAULT_CORP_CODE
        defaultManagerShouldBeFound("corpCode.contains=" + DEFAULT_CORP_CODE);

        // Get all the managerList where corpCode contains UPDATED_CORP_CODE
        defaultManagerShouldNotBeFound("corpCode.contains=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllManagersByCorpCodeNotContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where corpCode does not contain DEFAULT_CORP_CODE
        defaultManagerShouldNotBeFound("corpCode.doesNotContain=" + DEFAULT_CORP_CODE);

        // Get all the managerList where corpCode does not contain UPDATED_CORP_CODE
        defaultManagerShouldBeFound("corpCode.doesNotContain=" + UPDATED_CORP_CODE);
    }


    @Test
    @Transactional
    public void getAllManagersByManagerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerName equals to DEFAULT_MANAGER_NAME
        defaultManagerShouldBeFound("managerName.equals=" + DEFAULT_MANAGER_NAME);

        // Get all the managerList where managerName equals to UPDATED_MANAGER_NAME
        defaultManagerShouldNotBeFound("managerName.equals=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllManagersByManagerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerName not equals to DEFAULT_MANAGER_NAME
        defaultManagerShouldNotBeFound("managerName.notEquals=" + DEFAULT_MANAGER_NAME);

        // Get all the managerList where managerName not equals to UPDATED_MANAGER_NAME
        defaultManagerShouldBeFound("managerName.notEquals=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllManagersByManagerNameIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerName in DEFAULT_MANAGER_NAME or UPDATED_MANAGER_NAME
        defaultManagerShouldBeFound("managerName.in=" + DEFAULT_MANAGER_NAME + "," + UPDATED_MANAGER_NAME);

        // Get all the managerList where managerName equals to UPDATED_MANAGER_NAME
        defaultManagerShouldNotBeFound("managerName.in=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllManagersByManagerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerName is not null
        defaultManagerShouldBeFound("managerName.specified=true");

        // Get all the managerList where managerName is null
        defaultManagerShouldNotBeFound("managerName.specified=false");
    }
                @Test
    @Transactional
    public void getAllManagersByManagerNameContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerName contains DEFAULT_MANAGER_NAME
        defaultManagerShouldBeFound("managerName.contains=" + DEFAULT_MANAGER_NAME);

        // Get all the managerList where managerName contains UPDATED_MANAGER_NAME
        defaultManagerShouldNotBeFound("managerName.contains=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    public void getAllManagersByManagerNameNotContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerName does not contain DEFAULT_MANAGER_NAME
        defaultManagerShouldNotBeFound("managerName.doesNotContain=" + DEFAULT_MANAGER_NAME);

        // Get all the managerList where managerName does not contain UPDATED_MANAGER_NAME
        defaultManagerShouldBeFound("managerName.doesNotContain=" + UPDATED_MANAGER_NAME);
    }


    @Test
    @Transactional
    public void getAllManagersByManagerPhoneNumIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerPhoneNum equals to DEFAULT_MANAGER_PHONE_NUM
        defaultManagerShouldBeFound("managerPhoneNum.equals=" + DEFAULT_MANAGER_PHONE_NUM);

        // Get all the managerList where managerPhoneNum equals to UPDATED_MANAGER_PHONE_NUM
        defaultManagerShouldNotBeFound("managerPhoneNum.equals=" + UPDATED_MANAGER_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllManagersByManagerPhoneNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerPhoneNum not equals to DEFAULT_MANAGER_PHONE_NUM
        defaultManagerShouldNotBeFound("managerPhoneNum.notEquals=" + DEFAULT_MANAGER_PHONE_NUM);

        // Get all the managerList where managerPhoneNum not equals to UPDATED_MANAGER_PHONE_NUM
        defaultManagerShouldBeFound("managerPhoneNum.notEquals=" + UPDATED_MANAGER_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllManagersByManagerPhoneNumIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerPhoneNum in DEFAULT_MANAGER_PHONE_NUM or UPDATED_MANAGER_PHONE_NUM
        defaultManagerShouldBeFound("managerPhoneNum.in=" + DEFAULT_MANAGER_PHONE_NUM + "," + UPDATED_MANAGER_PHONE_NUM);

        // Get all the managerList where managerPhoneNum equals to UPDATED_MANAGER_PHONE_NUM
        defaultManagerShouldNotBeFound("managerPhoneNum.in=" + UPDATED_MANAGER_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllManagersByManagerPhoneNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerPhoneNum is not null
        defaultManagerShouldBeFound("managerPhoneNum.specified=true");

        // Get all the managerList where managerPhoneNum is null
        defaultManagerShouldNotBeFound("managerPhoneNum.specified=false");
    }
                @Test
    @Transactional
    public void getAllManagersByManagerPhoneNumContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerPhoneNum contains DEFAULT_MANAGER_PHONE_NUM
        defaultManagerShouldBeFound("managerPhoneNum.contains=" + DEFAULT_MANAGER_PHONE_NUM);

        // Get all the managerList where managerPhoneNum contains UPDATED_MANAGER_PHONE_NUM
        defaultManagerShouldNotBeFound("managerPhoneNum.contains=" + UPDATED_MANAGER_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllManagersByManagerPhoneNumNotContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where managerPhoneNum does not contain DEFAULT_MANAGER_PHONE_NUM
        defaultManagerShouldNotBeFound("managerPhoneNum.doesNotContain=" + DEFAULT_MANAGER_PHONE_NUM);

        // Get all the managerList where managerPhoneNum does not contain UPDATED_MANAGER_PHONE_NUM
        defaultManagerShouldBeFound("managerPhoneNum.doesNotContain=" + UPDATED_MANAGER_PHONE_NUM);
    }


    @Test
    @Transactional
    public void getAllManagersByTeamCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where teamCode equals to DEFAULT_TEAM_CODE
        defaultManagerShouldBeFound("teamCode.equals=" + DEFAULT_TEAM_CODE);

        // Get all the managerList where teamCode equals to UPDATED_TEAM_CODE
        defaultManagerShouldNotBeFound("teamCode.equals=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllManagersByTeamCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where teamCode not equals to DEFAULT_TEAM_CODE
        defaultManagerShouldNotBeFound("teamCode.notEquals=" + DEFAULT_TEAM_CODE);

        // Get all the managerList where teamCode not equals to UPDATED_TEAM_CODE
        defaultManagerShouldBeFound("teamCode.notEquals=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllManagersByTeamCodeIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where teamCode in DEFAULT_TEAM_CODE or UPDATED_TEAM_CODE
        defaultManagerShouldBeFound("teamCode.in=" + DEFAULT_TEAM_CODE + "," + UPDATED_TEAM_CODE);

        // Get all the managerList where teamCode equals to UPDATED_TEAM_CODE
        defaultManagerShouldNotBeFound("teamCode.in=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllManagersByTeamCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where teamCode is not null
        defaultManagerShouldBeFound("teamCode.specified=true");

        // Get all the managerList where teamCode is null
        defaultManagerShouldNotBeFound("teamCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllManagersByTeamCodeContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where teamCode contains DEFAULT_TEAM_CODE
        defaultManagerShouldBeFound("teamCode.contains=" + DEFAULT_TEAM_CODE);

        // Get all the managerList where teamCode contains UPDATED_TEAM_CODE
        defaultManagerShouldNotBeFound("teamCode.contains=" + UPDATED_TEAM_CODE);
    }

    @Test
    @Transactional
    public void getAllManagersByTeamCodeNotContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where teamCode does not contain DEFAULT_TEAM_CODE
        defaultManagerShouldNotBeFound("teamCode.doesNotContain=" + DEFAULT_TEAM_CODE);

        // Get all the managerList where teamCode does not contain UPDATED_TEAM_CODE
        defaultManagerShouldBeFound("teamCode.doesNotContain=" + UPDATED_TEAM_CODE);
    }


    @Test
    @Transactional
    public void getAllManagersByTotalSalesAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where totalSalesAmount equals to DEFAULT_TOTAL_SALES_AMOUNT
        defaultManagerShouldBeFound("totalSalesAmount.equals=" + DEFAULT_TOTAL_SALES_AMOUNT);

        // Get all the managerList where totalSalesAmount equals to UPDATED_TOTAL_SALES_AMOUNT
        defaultManagerShouldNotBeFound("totalSalesAmount.equals=" + UPDATED_TOTAL_SALES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllManagersByTotalSalesAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where totalSalesAmount not equals to DEFAULT_TOTAL_SALES_AMOUNT
        defaultManagerShouldNotBeFound("totalSalesAmount.notEquals=" + DEFAULT_TOTAL_SALES_AMOUNT);

        // Get all the managerList where totalSalesAmount not equals to UPDATED_TOTAL_SALES_AMOUNT
        defaultManagerShouldBeFound("totalSalesAmount.notEquals=" + UPDATED_TOTAL_SALES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllManagersByTotalSalesAmountIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where totalSalesAmount in DEFAULT_TOTAL_SALES_AMOUNT or UPDATED_TOTAL_SALES_AMOUNT
        defaultManagerShouldBeFound("totalSalesAmount.in=" + DEFAULT_TOTAL_SALES_AMOUNT + "," + UPDATED_TOTAL_SALES_AMOUNT);

        // Get all the managerList where totalSalesAmount equals to UPDATED_TOTAL_SALES_AMOUNT
        defaultManagerShouldNotBeFound("totalSalesAmount.in=" + UPDATED_TOTAL_SALES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllManagersByTotalSalesAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where totalSalesAmount is not null
        defaultManagerShouldBeFound("totalSalesAmount.specified=true");

        // Get all the managerList where totalSalesAmount is null
        defaultManagerShouldNotBeFound("totalSalesAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllManagersByTotalSalesAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where totalSalesAmount is greater than or equal to DEFAULT_TOTAL_SALES_AMOUNT
        defaultManagerShouldBeFound("totalSalesAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_SALES_AMOUNT);

        // Get all the managerList where totalSalesAmount is greater than or equal to UPDATED_TOTAL_SALES_AMOUNT
        defaultManagerShouldNotBeFound("totalSalesAmount.greaterThanOrEqual=" + UPDATED_TOTAL_SALES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllManagersByTotalSalesAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where totalSalesAmount is less than or equal to DEFAULT_TOTAL_SALES_AMOUNT
        defaultManagerShouldBeFound("totalSalesAmount.lessThanOrEqual=" + DEFAULT_TOTAL_SALES_AMOUNT);

        // Get all the managerList where totalSalesAmount is less than or equal to SMALLER_TOTAL_SALES_AMOUNT
        defaultManagerShouldNotBeFound("totalSalesAmount.lessThanOrEqual=" + SMALLER_TOTAL_SALES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllManagersByTotalSalesAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where totalSalesAmount is less than DEFAULT_TOTAL_SALES_AMOUNT
        defaultManagerShouldNotBeFound("totalSalesAmount.lessThan=" + DEFAULT_TOTAL_SALES_AMOUNT);

        // Get all the managerList where totalSalesAmount is less than UPDATED_TOTAL_SALES_AMOUNT
        defaultManagerShouldBeFound("totalSalesAmount.lessThan=" + UPDATED_TOTAL_SALES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllManagersByTotalSalesAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where totalSalesAmount is greater than DEFAULT_TOTAL_SALES_AMOUNT
        defaultManagerShouldNotBeFound("totalSalesAmount.greaterThan=" + DEFAULT_TOTAL_SALES_AMOUNT);

        // Get all the managerList where totalSalesAmount is greater than SMALLER_TOTAL_SALES_AMOUNT
        defaultManagerShouldBeFound("totalSalesAmount.greaterThan=" + SMALLER_TOTAL_SALES_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllManagersByEnterDayIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where enterDay equals to DEFAULT_ENTER_DAY
        defaultManagerShouldBeFound("enterDay.equals=" + DEFAULT_ENTER_DAY);

        // Get all the managerList where enterDay equals to UPDATED_ENTER_DAY
        defaultManagerShouldNotBeFound("enterDay.equals=" + UPDATED_ENTER_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByEnterDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where enterDay not equals to DEFAULT_ENTER_DAY
        defaultManagerShouldNotBeFound("enterDay.notEquals=" + DEFAULT_ENTER_DAY);

        // Get all the managerList where enterDay not equals to UPDATED_ENTER_DAY
        defaultManagerShouldBeFound("enterDay.notEquals=" + UPDATED_ENTER_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByEnterDayIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where enterDay in DEFAULT_ENTER_DAY or UPDATED_ENTER_DAY
        defaultManagerShouldBeFound("enterDay.in=" + DEFAULT_ENTER_DAY + "," + UPDATED_ENTER_DAY);

        // Get all the managerList where enterDay equals to UPDATED_ENTER_DAY
        defaultManagerShouldNotBeFound("enterDay.in=" + UPDATED_ENTER_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByEnterDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where enterDay is not null
        defaultManagerShouldBeFound("enterDay.specified=true");

        // Get all the managerList where enterDay is null
        defaultManagerShouldNotBeFound("enterDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllManagersByEnterDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where enterDay is greater than or equal to DEFAULT_ENTER_DAY
        defaultManagerShouldBeFound("enterDay.greaterThanOrEqual=" + DEFAULT_ENTER_DAY);

        // Get all the managerList where enterDay is greater than or equal to UPDATED_ENTER_DAY
        defaultManagerShouldNotBeFound("enterDay.greaterThanOrEqual=" + UPDATED_ENTER_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByEnterDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where enterDay is less than or equal to DEFAULT_ENTER_DAY
        defaultManagerShouldBeFound("enterDay.lessThanOrEqual=" + DEFAULT_ENTER_DAY);

        // Get all the managerList where enterDay is less than or equal to SMALLER_ENTER_DAY
        defaultManagerShouldNotBeFound("enterDay.lessThanOrEqual=" + SMALLER_ENTER_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByEnterDayIsLessThanSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where enterDay is less than DEFAULT_ENTER_DAY
        defaultManagerShouldNotBeFound("enterDay.lessThan=" + DEFAULT_ENTER_DAY);

        // Get all the managerList where enterDay is less than UPDATED_ENTER_DAY
        defaultManagerShouldBeFound("enterDay.lessThan=" + UPDATED_ENTER_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByEnterDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where enterDay is greater than DEFAULT_ENTER_DAY
        defaultManagerShouldNotBeFound("enterDay.greaterThan=" + DEFAULT_ENTER_DAY);

        // Get all the managerList where enterDay is greater than SMALLER_ENTER_DAY
        defaultManagerShouldBeFound("enterDay.greaterThan=" + SMALLER_ENTER_DAY);
    }


    @Test
    @Transactional
    public void getAllManagersByOutDayIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where outDay equals to DEFAULT_OUT_DAY
        defaultManagerShouldBeFound("outDay.equals=" + DEFAULT_OUT_DAY);

        // Get all the managerList where outDay equals to UPDATED_OUT_DAY
        defaultManagerShouldNotBeFound("outDay.equals=" + UPDATED_OUT_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByOutDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where outDay not equals to DEFAULT_OUT_DAY
        defaultManagerShouldNotBeFound("outDay.notEquals=" + DEFAULT_OUT_DAY);

        // Get all the managerList where outDay not equals to UPDATED_OUT_DAY
        defaultManagerShouldBeFound("outDay.notEquals=" + UPDATED_OUT_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByOutDayIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where outDay in DEFAULT_OUT_DAY or UPDATED_OUT_DAY
        defaultManagerShouldBeFound("outDay.in=" + DEFAULT_OUT_DAY + "," + UPDATED_OUT_DAY);

        // Get all the managerList where outDay equals to UPDATED_OUT_DAY
        defaultManagerShouldNotBeFound("outDay.in=" + UPDATED_OUT_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByOutDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where outDay is not null
        defaultManagerShouldBeFound("outDay.specified=true");

        // Get all the managerList where outDay is null
        defaultManagerShouldNotBeFound("outDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllManagersByOutDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where outDay is greater than or equal to DEFAULT_OUT_DAY
        defaultManagerShouldBeFound("outDay.greaterThanOrEqual=" + DEFAULT_OUT_DAY);

        // Get all the managerList where outDay is greater than or equal to UPDATED_OUT_DAY
        defaultManagerShouldNotBeFound("outDay.greaterThanOrEqual=" + UPDATED_OUT_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByOutDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where outDay is less than or equal to DEFAULT_OUT_DAY
        defaultManagerShouldBeFound("outDay.lessThanOrEqual=" + DEFAULT_OUT_DAY);

        // Get all the managerList where outDay is less than or equal to SMALLER_OUT_DAY
        defaultManagerShouldNotBeFound("outDay.lessThanOrEqual=" + SMALLER_OUT_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByOutDayIsLessThanSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where outDay is less than DEFAULT_OUT_DAY
        defaultManagerShouldNotBeFound("outDay.lessThan=" + DEFAULT_OUT_DAY);

        // Get all the managerList where outDay is less than UPDATED_OUT_DAY
        defaultManagerShouldBeFound("outDay.lessThan=" + UPDATED_OUT_DAY);
    }

    @Test
    @Transactional
    public void getAllManagersByOutDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where outDay is greater than DEFAULT_OUT_DAY
        defaultManagerShouldNotBeFound("outDay.greaterThan=" + DEFAULT_OUT_DAY);

        // Get all the managerList where outDay is greater than SMALLER_OUT_DAY
        defaultManagerShouldBeFound("outDay.greaterThan=" + SMALLER_OUT_DAY);
    }


    @Test
    @Transactional
    public void getAllManagersByUseYnIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where useYn equals to DEFAULT_USE_YN
        defaultManagerShouldBeFound("useYn.equals=" + DEFAULT_USE_YN);

        // Get all the managerList where useYn equals to UPDATED_USE_YN
        defaultManagerShouldNotBeFound("useYn.equals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllManagersByUseYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where useYn not equals to DEFAULT_USE_YN
        defaultManagerShouldNotBeFound("useYn.notEquals=" + DEFAULT_USE_YN);

        // Get all the managerList where useYn not equals to UPDATED_USE_YN
        defaultManagerShouldBeFound("useYn.notEquals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllManagersByUseYnIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where useYn in DEFAULT_USE_YN or UPDATED_USE_YN
        defaultManagerShouldBeFound("useYn.in=" + DEFAULT_USE_YN + "," + UPDATED_USE_YN);

        // Get all the managerList where useYn equals to UPDATED_USE_YN
        defaultManagerShouldNotBeFound("useYn.in=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllManagersByUseYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where useYn is not null
        defaultManagerShouldBeFound("useYn.specified=true");

        // Get all the managerList where useYn is null
        defaultManagerShouldNotBeFound("useYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllManagersByCrmCustomIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);
        CrmCustom crmCustom = CrmCustomResourceIT.createEntity(em);
        em.persist(crmCustom);
        em.flush();
        manager.addCrmCustom(crmCustom);
        managerRepository.saveAndFlush(manager);
        Long crmCustomId = crmCustom.getId();

        // Get all the managerList where crmCustom equals to crmCustomId
        defaultManagerShouldBeFound("crmCustomId.equals=" + crmCustomId);

        // Get all the managerList where crmCustom equals to crmCustomId + 1
        defaultManagerShouldNotBeFound("crmCustomId.equals=" + (crmCustomId + 1));
    }


    @Test
    @Transactional
    public void getAllManagersByTeamIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);
        TeamGrp team = TeamGrpResourceIT.createEntity(em);
        em.persist(team);
        em.flush();
        manager.setTeam(team);
        managerRepository.saveAndFlush(manager);
        Long teamId = team.getId();

        // Get all the managerList where team equals to teamId
        defaultManagerShouldBeFound("teamId.equals=" + teamId);

        // Get all the managerList where team equals to teamId + 1
        defaultManagerShouldNotBeFound("teamId.equals=" + (teamId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultManagerShouldBeFound(String filter) throws Exception {
        restManagerMockMvc.perform(get("/api/managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manager.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpCode").value(hasItem(DEFAULT_CORP_CODE)))
            .andExpect(jsonPath("$.[*].managerName").value(hasItem(DEFAULT_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].managerPhoneNum").value(hasItem(DEFAULT_MANAGER_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].teamCode").value(hasItem(DEFAULT_TEAM_CODE)))
            .andExpect(jsonPath("$.[*].totalSalesAmount").value(hasItem(DEFAULT_TOTAL_SALES_AMOUNT)))
            .andExpect(jsonPath("$.[*].enterDay").value(hasItem(DEFAULT_ENTER_DAY.toString())))
            .andExpect(jsonPath("$.[*].outDay").value(hasItem(DEFAULT_OUT_DAY.toString())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));

        // Check, that the count call also returns 1
        restManagerMockMvc.perform(get("/api/managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultManagerShouldNotBeFound(String filter) throws Exception {
        restManagerMockMvc.perform(get("/api/managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restManagerMockMvc.perform(get("/api/managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingManager() throws Exception {
        // Get the manager
        restManagerMockMvc.perform(get("/api/managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManager() throws Exception {
        // Initialize the database
        managerService.save(manager);

        int databaseSizeBeforeUpdate = managerRepository.findAll().size();

        // Update the manager
        Manager updatedManager = managerRepository.findById(manager.getId()).get();
        // Disconnect from session so that the updates on updatedManager are not directly saved in db
        em.detach(updatedManager);
        updatedManager
            .corpCode(UPDATED_CORP_CODE)
            .managerName(UPDATED_MANAGER_NAME)
            .managerPhoneNum(UPDATED_MANAGER_PHONE_NUM)
            .teamCode(UPDATED_TEAM_CODE)
            .totalSalesAmount(UPDATED_TOTAL_SALES_AMOUNT)
            .enterDay(UPDATED_ENTER_DAY)
            .outDay(UPDATED_OUT_DAY)
            .useYn(UPDATED_USE_YN);

        restManagerMockMvc.perform(put("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedManager)))
            .andExpect(status().isOk());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeUpdate);
        Manager testManager = managerList.get(managerList.size() - 1);
        assertThat(testManager.getCorpCode()).isEqualTo(UPDATED_CORP_CODE);
        assertThat(testManager.getManagerName()).isEqualTo(UPDATED_MANAGER_NAME);
        assertThat(testManager.getManagerPhoneNum()).isEqualTo(UPDATED_MANAGER_PHONE_NUM);
        assertThat(testManager.getTeamCode()).isEqualTo(UPDATED_TEAM_CODE);
        assertThat(testManager.getTotalSalesAmount()).isEqualTo(UPDATED_TOTAL_SALES_AMOUNT);
        assertThat(testManager.getEnterDay()).isEqualTo(UPDATED_ENTER_DAY);
        assertThat(testManager.getOutDay()).isEqualTo(UPDATED_OUT_DAY);
        assertThat(testManager.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingManager() throws Exception {
        int databaseSizeBeforeUpdate = managerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagerMockMvc.perform(put("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(manager)))
            .andExpect(status().isBadRequest());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteManager() throws Exception {
        // Initialize the database
        managerService.save(manager);

        int databaseSizeBeforeDelete = managerRepository.findAll().size();

        // Delete the manager
        restManagerMockMvc.perform(delete("/api/managers/{id}", manager.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
