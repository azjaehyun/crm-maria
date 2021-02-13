package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.StockContractHis;
import co.kr.crm.domain.CrmCustom;
import co.kr.crm.repository.StockContractHisRepository;
import co.kr.crm.service.StockContractHisService;
import co.kr.crm.service.dto.StockContractHisCriteria;
import co.kr.crm.service.StockContractHisQueryService;

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
 * Integration tests for the {@link StockContractHisResource} REST controller.
 */
@SpringBootTest(classes = CrmMariaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StockContractHisResourceIT {

    private static final LocalDate DEFAULT_FROM_CONTRACT_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_CONTRACT_DT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM_CONTRACT_DT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO_CONTRACT_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_CONTRACT_DT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO_CONTRACT_DT = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_CONTRACT_AMOUNT = 1;
    private static final Integer UPDATED_CONTRACT_AMOUNT = 2;
    private static final Integer SMALLER_CONTRACT_AMOUNT = 1 - 1;

    private static final LocalDate DEFAULT_REG_DTM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REG_DTM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REG_DTM = LocalDate.ofEpochDay(-1L);

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private StockContractHisRepository stockContractHisRepository;

    @Autowired
    private StockContractHisService stockContractHisService;

    @Autowired
    private StockContractHisQueryService stockContractHisQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockContractHisMockMvc;

    private StockContractHis stockContractHis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockContractHis createEntity(EntityManager em) {
        StockContractHis stockContractHis = new StockContractHis()
            .fromContractDt(DEFAULT_FROM_CONTRACT_DT)
            .toContractDt(DEFAULT_TO_CONTRACT_DT)
            .contractAmount(DEFAULT_CONTRACT_AMOUNT)
            .regDtm(DEFAULT_REG_DTM)
            .useYn(DEFAULT_USE_YN);
        return stockContractHis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockContractHis createUpdatedEntity(EntityManager em) {
        StockContractHis stockContractHis = new StockContractHis()
            .fromContractDt(UPDATED_FROM_CONTRACT_DT)
            .toContractDt(UPDATED_TO_CONTRACT_DT)
            .contractAmount(UPDATED_CONTRACT_AMOUNT)
            .regDtm(UPDATED_REG_DTM)
            .useYn(UPDATED_USE_YN);
        return stockContractHis;
    }

    @BeforeEach
    public void initTest() {
        stockContractHis = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockContractHis() throws Exception {
        int databaseSizeBeforeCreate = stockContractHisRepository.findAll().size();
        // Create the StockContractHis
        restStockContractHisMockMvc.perform(post("/api/stock-contract-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockContractHis)))
            .andExpect(status().isCreated());

        // Validate the StockContractHis in the database
        List<StockContractHis> stockContractHisList = stockContractHisRepository.findAll();
        assertThat(stockContractHisList).hasSize(databaseSizeBeforeCreate + 1);
        StockContractHis testStockContractHis = stockContractHisList.get(stockContractHisList.size() - 1);
        assertThat(testStockContractHis.getFromContractDt()).isEqualTo(DEFAULT_FROM_CONTRACT_DT);
        assertThat(testStockContractHis.getToContractDt()).isEqualTo(DEFAULT_TO_CONTRACT_DT);
        assertThat(testStockContractHis.getContractAmount()).isEqualTo(DEFAULT_CONTRACT_AMOUNT);
        assertThat(testStockContractHis.getRegDtm()).isEqualTo(DEFAULT_REG_DTM);
        assertThat(testStockContractHis.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    public void createStockContractHisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockContractHisRepository.findAll().size();

        // Create the StockContractHis with an existing ID
        stockContractHis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockContractHisMockMvc.perform(post("/api/stock-contract-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockContractHis)))
            .andExpect(status().isBadRequest());

        // Validate the StockContractHis in the database
        List<StockContractHis> stockContractHisList = stockContractHisRepository.findAll();
        assertThat(stockContractHisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStockContractHis() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList
        restStockContractHisMockMvc.perform(get("/api/stock-contract-his?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockContractHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromContractDt").value(hasItem(DEFAULT_FROM_CONTRACT_DT.toString())))
            .andExpect(jsonPath("$.[*].toContractDt").value(hasItem(DEFAULT_TO_CONTRACT_DT.toString())))
            .andExpect(jsonPath("$.[*].contractAmount").value(hasItem(DEFAULT_CONTRACT_AMOUNT)))
            .andExpect(jsonPath("$.[*].regDtm").value(hasItem(DEFAULT_REG_DTM.toString())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));
    }
    
    @Test
    @Transactional
    public void getStockContractHis() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get the stockContractHis
        restStockContractHisMockMvc.perform(get("/api/stock-contract-his/{id}", stockContractHis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockContractHis.getId().intValue()))
            .andExpect(jsonPath("$.fromContractDt").value(DEFAULT_FROM_CONTRACT_DT.toString()))
            .andExpect(jsonPath("$.toContractDt").value(DEFAULT_TO_CONTRACT_DT.toString()))
            .andExpect(jsonPath("$.contractAmount").value(DEFAULT_CONTRACT_AMOUNT))
            .andExpect(jsonPath("$.regDtm").value(DEFAULT_REG_DTM.toString()))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.toString()));
    }


    @Test
    @Transactional
    public void getStockContractHisByIdFiltering() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        Long id = stockContractHis.getId();

        defaultStockContractHisShouldBeFound("id.equals=" + id);
        defaultStockContractHisShouldNotBeFound("id.notEquals=" + id);

        defaultStockContractHisShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockContractHisShouldNotBeFound("id.greaterThan=" + id);

        defaultStockContractHisShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockContractHisShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockContractHisByFromContractDtIsEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where fromContractDt equals to DEFAULT_FROM_CONTRACT_DT
        defaultStockContractHisShouldBeFound("fromContractDt.equals=" + DEFAULT_FROM_CONTRACT_DT);

        // Get all the stockContractHisList where fromContractDt equals to UPDATED_FROM_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("fromContractDt.equals=" + UPDATED_FROM_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByFromContractDtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where fromContractDt not equals to DEFAULT_FROM_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("fromContractDt.notEquals=" + DEFAULT_FROM_CONTRACT_DT);

        // Get all the stockContractHisList where fromContractDt not equals to UPDATED_FROM_CONTRACT_DT
        defaultStockContractHisShouldBeFound("fromContractDt.notEquals=" + UPDATED_FROM_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByFromContractDtIsInShouldWork() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where fromContractDt in DEFAULT_FROM_CONTRACT_DT or UPDATED_FROM_CONTRACT_DT
        defaultStockContractHisShouldBeFound("fromContractDt.in=" + DEFAULT_FROM_CONTRACT_DT + "," + UPDATED_FROM_CONTRACT_DT);

        // Get all the stockContractHisList where fromContractDt equals to UPDATED_FROM_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("fromContractDt.in=" + UPDATED_FROM_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByFromContractDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where fromContractDt is not null
        defaultStockContractHisShouldBeFound("fromContractDt.specified=true");

        // Get all the stockContractHisList where fromContractDt is null
        defaultStockContractHisShouldNotBeFound("fromContractDt.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockContractHisByFromContractDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where fromContractDt is greater than or equal to DEFAULT_FROM_CONTRACT_DT
        defaultStockContractHisShouldBeFound("fromContractDt.greaterThanOrEqual=" + DEFAULT_FROM_CONTRACT_DT);

        // Get all the stockContractHisList where fromContractDt is greater than or equal to UPDATED_FROM_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("fromContractDt.greaterThanOrEqual=" + UPDATED_FROM_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByFromContractDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where fromContractDt is less than or equal to DEFAULT_FROM_CONTRACT_DT
        defaultStockContractHisShouldBeFound("fromContractDt.lessThanOrEqual=" + DEFAULT_FROM_CONTRACT_DT);

        // Get all the stockContractHisList where fromContractDt is less than or equal to SMALLER_FROM_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("fromContractDt.lessThanOrEqual=" + SMALLER_FROM_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByFromContractDtIsLessThanSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where fromContractDt is less than DEFAULT_FROM_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("fromContractDt.lessThan=" + DEFAULT_FROM_CONTRACT_DT);

        // Get all the stockContractHisList where fromContractDt is less than UPDATED_FROM_CONTRACT_DT
        defaultStockContractHisShouldBeFound("fromContractDt.lessThan=" + UPDATED_FROM_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByFromContractDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where fromContractDt is greater than DEFAULT_FROM_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("fromContractDt.greaterThan=" + DEFAULT_FROM_CONTRACT_DT);

        // Get all the stockContractHisList where fromContractDt is greater than SMALLER_FROM_CONTRACT_DT
        defaultStockContractHisShouldBeFound("fromContractDt.greaterThan=" + SMALLER_FROM_CONTRACT_DT);
    }


    @Test
    @Transactional
    public void getAllStockContractHisByToContractDtIsEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where toContractDt equals to DEFAULT_TO_CONTRACT_DT
        defaultStockContractHisShouldBeFound("toContractDt.equals=" + DEFAULT_TO_CONTRACT_DT);

        // Get all the stockContractHisList where toContractDt equals to UPDATED_TO_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("toContractDt.equals=" + UPDATED_TO_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByToContractDtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where toContractDt not equals to DEFAULT_TO_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("toContractDt.notEquals=" + DEFAULT_TO_CONTRACT_DT);

        // Get all the stockContractHisList where toContractDt not equals to UPDATED_TO_CONTRACT_DT
        defaultStockContractHisShouldBeFound("toContractDt.notEquals=" + UPDATED_TO_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByToContractDtIsInShouldWork() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where toContractDt in DEFAULT_TO_CONTRACT_DT or UPDATED_TO_CONTRACT_DT
        defaultStockContractHisShouldBeFound("toContractDt.in=" + DEFAULT_TO_CONTRACT_DT + "," + UPDATED_TO_CONTRACT_DT);

        // Get all the stockContractHisList where toContractDt equals to UPDATED_TO_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("toContractDt.in=" + UPDATED_TO_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByToContractDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where toContractDt is not null
        defaultStockContractHisShouldBeFound("toContractDt.specified=true");

        // Get all the stockContractHisList where toContractDt is null
        defaultStockContractHisShouldNotBeFound("toContractDt.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockContractHisByToContractDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where toContractDt is greater than or equal to DEFAULT_TO_CONTRACT_DT
        defaultStockContractHisShouldBeFound("toContractDt.greaterThanOrEqual=" + DEFAULT_TO_CONTRACT_DT);

        // Get all the stockContractHisList where toContractDt is greater than or equal to UPDATED_TO_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("toContractDt.greaterThanOrEqual=" + UPDATED_TO_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByToContractDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where toContractDt is less than or equal to DEFAULT_TO_CONTRACT_DT
        defaultStockContractHisShouldBeFound("toContractDt.lessThanOrEqual=" + DEFAULT_TO_CONTRACT_DT);

        // Get all the stockContractHisList where toContractDt is less than or equal to SMALLER_TO_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("toContractDt.lessThanOrEqual=" + SMALLER_TO_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByToContractDtIsLessThanSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where toContractDt is less than DEFAULT_TO_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("toContractDt.lessThan=" + DEFAULT_TO_CONTRACT_DT);

        // Get all the stockContractHisList where toContractDt is less than UPDATED_TO_CONTRACT_DT
        defaultStockContractHisShouldBeFound("toContractDt.lessThan=" + UPDATED_TO_CONTRACT_DT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByToContractDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where toContractDt is greater than DEFAULT_TO_CONTRACT_DT
        defaultStockContractHisShouldNotBeFound("toContractDt.greaterThan=" + DEFAULT_TO_CONTRACT_DT);

        // Get all the stockContractHisList where toContractDt is greater than SMALLER_TO_CONTRACT_DT
        defaultStockContractHisShouldBeFound("toContractDt.greaterThan=" + SMALLER_TO_CONTRACT_DT);
    }


    @Test
    @Transactional
    public void getAllStockContractHisByContractAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where contractAmount equals to DEFAULT_CONTRACT_AMOUNT
        defaultStockContractHisShouldBeFound("contractAmount.equals=" + DEFAULT_CONTRACT_AMOUNT);

        // Get all the stockContractHisList where contractAmount equals to UPDATED_CONTRACT_AMOUNT
        defaultStockContractHisShouldNotBeFound("contractAmount.equals=" + UPDATED_CONTRACT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByContractAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where contractAmount not equals to DEFAULT_CONTRACT_AMOUNT
        defaultStockContractHisShouldNotBeFound("contractAmount.notEquals=" + DEFAULT_CONTRACT_AMOUNT);

        // Get all the stockContractHisList where contractAmount not equals to UPDATED_CONTRACT_AMOUNT
        defaultStockContractHisShouldBeFound("contractAmount.notEquals=" + UPDATED_CONTRACT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByContractAmountIsInShouldWork() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where contractAmount in DEFAULT_CONTRACT_AMOUNT or UPDATED_CONTRACT_AMOUNT
        defaultStockContractHisShouldBeFound("contractAmount.in=" + DEFAULT_CONTRACT_AMOUNT + "," + UPDATED_CONTRACT_AMOUNT);

        // Get all the stockContractHisList where contractAmount equals to UPDATED_CONTRACT_AMOUNT
        defaultStockContractHisShouldNotBeFound("contractAmount.in=" + UPDATED_CONTRACT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByContractAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where contractAmount is not null
        defaultStockContractHisShouldBeFound("contractAmount.specified=true");

        // Get all the stockContractHisList where contractAmount is null
        defaultStockContractHisShouldNotBeFound("contractAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockContractHisByContractAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where contractAmount is greater than or equal to DEFAULT_CONTRACT_AMOUNT
        defaultStockContractHisShouldBeFound("contractAmount.greaterThanOrEqual=" + DEFAULT_CONTRACT_AMOUNT);

        // Get all the stockContractHisList where contractAmount is greater than or equal to UPDATED_CONTRACT_AMOUNT
        defaultStockContractHisShouldNotBeFound("contractAmount.greaterThanOrEqual=" + UPDATED_CONTRACT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByContractAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where contractAmount is less than or equal to DEFAULT_CONTRACT_AMOUNT
        defaultStockContractHisShouldBeFound("contractAmount.lessThanOrEqual=" + DEFAULT_CONTRACT_AMOUNT);

        // Get all the stockContractHisList where contractAmount is less than or equal to SMALLER_CONTRACT_AMOUNT
        defaultStockContractHisShouldNotBeFound("contractAmount.lessThanOrEqual=" + SMALLER_CONTRACT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByContractAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where contractAmount is less than DEFAULT_CONTRACT_AMOUNT
        defaultStockContractHisShouldNotBeFound("contractAmount.lessThan=" + DEFAULT_CONTRACT_AMOUNT);

        // Get all the stockContractHisList where contractAmount is less than UPDATED_CONTRACT_AMOUNT
        defaultStockContractHisShouldBeFound("contractAmount.lessThan=" + UPDATED_CONTRACT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByContractAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where contractAmount is greater than DEFAULT_CONTRACT_AMOUNT
        defaultStockContractHisShouldNotBeFound("contractAmount.greaterThan=" + DEFAULT_CONTRACT_AMOUNT);

        // Get all the stockContractHisList where contractAmount is greater than SMALLER_CONTRACT_AMOUNT
        defaultStockContractHisShouldBeFound("contractAmount.greaterThan=" + SMALLER_CONTRACT_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllStockContractHisByRegDtmIsEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where regDtm equals to DEFAULT_REG_DTM
        defaultStockContractHisShouldBeFound("regDtm.equals=" + DEFAULT_REG_DTM);

        // Get all the stockContractHisList where regDtm equals to UPDATED_REG_DTM
        defaultStockContractHisShouldNotBeFound("regDtm.equals=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByRegDtmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where regDtm not equals to DEFAULT_REG_DTM
        defaultStockContractHisShouldNotBeFound("regDtm.notEquals=" + DEFAULT_REG_DTM);

        // Get all the stockContractHisList where regDtm not equals to UPDATED_REG_DTM
        defaultStockContractHisShouldBeFound("regDtm.notEquals=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByRegDtmIsInShouldWork() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where regDtm in DEFAULT_REG_DTM or UPDATED_REG_DTM
        defaultStockContractHisShouldBeFound("regDtm.in=" + DEFAULT_REG_DTM + "," + UPDATED_REG_DTM);

        // Get all the stockContractHisList where regDtm equals to UPDATED_REG_DTM
        defaultStockContractHisShouldNotBeFound("regDtm.in=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByRegDtmIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where regDtm is not null
        defaultStockContractHisShouldBeFound("regDtm.specified=true");

        // Get all the stockContractHisList where regDtm is null
        defaultStockContractHisShouldNotBeFound("regDtm.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockContractHisByRegDtmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where regDtm is greater than or equal to DEFAULT_REG_DTM
        defaultStockContractHisShouldBeFound("regDtm.greaterThanOrEqual=" + DEFAULT_REG_DTM);

        // Get all the stockContractHisList where regDtm is greater than or equal to UPDATED_REG_DTM
        defaultStockContractHisShouldNotBeFound("regDtm.greaterThanOrEqual=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByRegDtmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where regDtm is less than or equal to DEFAULT_REG_DTM
        defaultStockContractHisShouldBeFound("regDtm.lessThanOrEqual=" + DEFAULT_REG_DTM);

        // Get all the stockContractHisList where regDtm is less than or equal to SMALLER_REG_DTM
        defaultStockContractHisShouldNotBeFound("regDtm.lessThanOrEqual=" + SMALLER_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByRegDtmIsLessThanSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where regDtm is less than DEFAULT_REG_DTM
        defaultStockContractHisShouldNotBeFound("regDtm.lessThan=" + DEFAULT_REG_DTM);

        // Get all the stockContractHisList where regDtm is less than UPDATED_REG_DTM
        defaultStockContractHisShouldBeFound("regDtm.lessThan=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByRegDtmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where regDtm is greater than DEFAULT_REG_DTM
        defaultStockContractHisShouldNotBeFound("regDtm.greaterThan=" + DEFAULT_REG_DTM);

        // Get all the stockContractHisList where regDtm is greater than SMALLER_REG_DTM
        defaultStockContractHisShouldBeFound("regDtm.greaterThan=" + SMALLER_REG_DTM);
    }


    @Test
    @Transactional
    public void getAllStockContractHisByUseYnIsEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where useYn equals to DEFAULT_USE_YN
        defaultStockContractHisShouldBeFound("useYn.equals=" + DEFAULT_USE_YN);

        // Get all the stockContractHisList where useYn equals to UPDATED_USE_YN
        defaultStockContractHisShouldNotBeFound("useYn.equals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByUseYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where useYn not equals to DEFAULT_USE_YN
        defaultStockContractHisShouldNotBeFound("useYn.notEquals=" + DEFAULT_USE_YN);

        // Get all the stockContractHisList where useYn not equals to UPDATED_USE_YN
        defaultStockContractHisShouldBeFound("useYn.notEquals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByUseYnIsInShouldWork() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where useYn in DEFAULT_USE_YN or UPDATED_USE_YN
        defaultStockContractHisShouldBeFound("useYn.in=" + DEFAULT_USE_YN + "," + UPDATED_USE_YN);

        // Get all the stockContractHisList where useYn equals to UPDATED_USE_YN
        defaultStockContractHisShouldNotBeFound("useYn.in=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllStockContractHisByUseYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);

        // Get all the stockContractHisList where useYn is not null
        defaultStockContractHisShouldBeFound("useYn.specified=true");

        // Get all the stockContractHisList where useYn is null
        defaultStockContractHisShouldNotBeFound("useYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockContractHisByCrmCustomIsEqualToSomething() throws Exception {
        // Initialize the database
        stockContractHisRepository.saveAndFlush(stockContractHis);
        CrmCustom crmCustom = CrmCustomResourceIT.createEntity(em);
        em.persist(crmCustom);
        em.flush();
        stockContractHis.setCrmCustom(crmCustom);
        stockContractHisRepository.saveAndFlush(stockContractHis);
        Long crmCustomId = crmCustom.getId();

        // Get all the stockContractHisList where crmCustom equals to crmCustomId
        defaultStockContractHisShouldBeFound("crmCustomId.equals=" + crmCustomId);

        // Get all the stockContractHisList where crmCustom equals to crmCustomId + 1
        defaultStockContractHisShouldNotBeFound("crmCustomId.equals=" + (crmCustomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockContractHisShouldBeFound(String filter) throws Exception {
        restStockContractHisMockMvc.perform(get("/api/stock-contract-his?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockContractHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromContractDt").value(hasItem(DEFAULT_FROM_CONTRACT_DT.toString())))
            .andExpect(jsonPath("$.[*].toContractDt").value(hasItem(DEFAULT_TO_CONTRACT_DT.toString())))
            .andExpect(jsonPath("$.[*].contractAmount").value(hasItem(DEFAULT_CONTRACT_AMOUNT)))
            .andExpect(jsonPath("$.[*].regDtm").value(hasItem(DEFAULT_REG_DTM.toString())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));

        // Check, that the count call also returns 1
        restStockContractHisMockMvc.perform(get("/api/stock-contract-his/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockContractHisShouldNotBeFound(String filter) throws Exception {
        restStockContractHisMockMvc.perform(get("/api/stock-contract-his?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockContractHisMockMvc.perform(get("/api/stock-contract-his/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStockContractHis() throws Exception {
        // Get the stockContractHis
        restStockContractHisMockMvc.perform(get("/api/stock-contract-his/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockContractHis() throws Exception {
        // Initialize the database
        stockContractHisService.save(stockContractHis);

        int databaseSizeBeforeUpdate = stockContractHisRepository.findAll().size();

        // Update the stockContractHis
        StockContractHis updatedStockContractHis = stockContractHisRepository.findById(stockContractHis.getId()).get();
        // Disconnect from session so that the updates on updatedStockContractHis are not directly saved in db
        em.detach(updatedStockContractHis);
        updatedStockContractHis
            .fromContractDt(UPDATED_FROM_CONTRACT_DT)
            .toContractDt(UPDATED_TO_CONTRACT_DT)
            .contractAmount(UPDATED_CONTRACT_AMOUNT)
            .regDtm(UPDATED_REG_DTM)
            .useYn(UPDATED_USE_YN);

        restStockContractHisMockMvc.perform(put("/api/stock-contract-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockContractHis)))
            .andExpect(status().isOk());

        // Validate the StockContractHis in the database
        List<StockContractHis> stockContractHisList = stockContractHisRepository.findAll();
        assertThat(stockContractHisList).hasSize(databaseSizeBeforeUpdate);
        StockContractHis testStockContractHis = stockContractHisList.get(stockContractHisList.size() - 1);
        assertThat(testStockContractHis.getFromContractDt()).isEqualTo(UPDATED_FROM_CONTRACT_DT);
        assertThat(testStockContractHis.getToContractDt()).isEqualTo(UPDATED_TO_CONTRACT_DT);
        assertThat(testStockContractHis.getContractAmount()).isEqualTo(UPDATED_CONTRACT_AMOUNT);
        assertThat(testStockContractHis.getRegDtm()).isEqualTo(UPDATED_REG_DTM);
        assertThat(testStockContractHis.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingStockContractHis() throws Exception {
        int databaseSizeBeforeUpdate = stockContractHisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockContractHisMockMvc.perform(put("/api/stock-contract-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockContractHis)))
            .andExpect(status().isBadRequest());

        // Validate the StockContractHis in the database
        List<StockContractHis> stockContractHisList = stockContractHisRepository.findAll();
        assertThat(stockContractHisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockContractHis() throws Exception {
        // Initialize the database
        stockContractHisService.save(stockContractHis);

        int databaseSizeBeforeDelete = stockContractHisRepository.findAll().size();

        // Delete the stockContractHis
        restStockContractHisMockMvc.perform(delete("/api/stock-contract-his/{id}", stockContractHis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockContractHis> stockContractHisList = stockContractHisRepository.findAll();
        assertThat(stockContractHisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
