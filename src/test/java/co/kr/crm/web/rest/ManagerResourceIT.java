package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.Manager;
import co.kr.crm.repository.ManagerRepository;
import co.kr.crm.service.ManagerService;

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

    private static final LocalDate DEFAULT_ENTER_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENTER_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_OUT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OUT_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerService managerService;

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
