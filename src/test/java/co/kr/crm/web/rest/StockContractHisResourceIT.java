package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.StockContractHis;
import co.kr.crm.repository.StockContractHisRepository;
import co.kr.crm.service.StockContractHisService;

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

    private static final LocalDate DEFAULT_TO_CONTRACT_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_CONTRACT_DT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CONTRACT_AMOUNT = 1;
    private static final Integer UPDATED_CONTRACT_AMOUNT = 2;

    private static final LocalDate DEFAULT_REG_DTM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REG_DTM = LocalDate.now(ZoneId.systemDefault());

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private StockContractHisRepository stockContractHisRepository;

    @Autowired
    private StockContractHisService stockContractHisService;

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
