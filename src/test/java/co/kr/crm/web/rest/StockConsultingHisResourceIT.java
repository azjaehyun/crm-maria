package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.StockConsultingHis;
import co.kr.crm.domain.CrmCustom;
import co.kr.crm.repository.StockConsultingHisRepository;
import co.kr.crm.service.StockConsultingHisService;
import co.kr.crm.service.dto.StockConsultingHisCriteria;
import co.kr.crm.service.StockConsultingHisQueryService;

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
 * Integration tests for the {@link StockConsultingHisResource} REST controller.
 */
@SpringBootTest(classes = CrmMariaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StockConsultingHisResourceIT {

    private static final String DEFAULT_CONSULTING_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTING_MEMO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REG_DTM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REG_DTM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REG_DTM = LocalDate.ofEpochDay(-1L);

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private StockConsultingHisRepository stockConsultingHisRepository;

    @Autowired
    private StockConsultingHisService stockConsultingHisService;

    @Autowired
    private StockConsultingHisQueryService stockConsultingHisQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockConsultingHisMockMvc;

    private StockConsultingHis stockConsultingHis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockConsultingHis createEntity(EntityManager em) {
        StockConsultingHis stockConsultingHis = new StockConsultingHis()
            .consultingMemo(DEFAULT_CONSULTING_MEMO)
            .regDtm(DEFAULT_REG_DTM)
            .useYn(DEFAULT_USE_YN);
        return stockConsultingHis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockConsultingHis createUpdatedEntity(EntityManager em) {
        StockConsultingHis stockConsultingHis = new StockConsultingHis()
            .consultingMemo(UPDATED_CONSULTING_MEMO)
            .regDtm(UPDATED_REG_DTM)
            .useYn(UPDATED_USE_YN);
        return stockConsultingHis;
    }

    @BeforeEach
    public void initTest() {
        stockConsultingHis = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockConsultingHis() throws Exception {
        int databaseSizeBeforeCreate = stockConsultingHisRepository.findAll().size();
        // Create the StockConsultingHis
        restStockConsultingHisMockMvc.perform(post("/api/stock-consulting-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockConsultingHis)))
            .andExpect(status().isCreated());

        // Validate the StockConsultingHis in the database
        List<StockConsultingHis> stockConsultingHisList = stockConsultingHisRepository.findAll();
        assertThat(stockConsultingHisList).hasSize(databaseSizeBeforeCreate + 1);
        StockConsultingHis testStockConsultingHis = stockConsultingHisList.get(stockConsultingHisList.size() - 1);
        assertThat(testStockConsultingHis.getConsultingMemo()).isEqualTo(DEFAULT_CONSULTING_MEMO);
        assertThat(testStockConsultingHis.getRegDtm()).isEqualTo(DEFAULT_REG_DTM);
        assertThat(testStockConsultingHis.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    public void createStockConsultingHisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockConsultingHisRepository.findAll().size();

        // Create the StockConsultingHis with an existing ID
        stockConsultingHis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockConsultingHisMockMvc.perform(post("/api/stock-consulting-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockConsultingHis)))
            .andExpect(status().isBadRequest());

        // Validate the StockConsultingHis in the database
        List<StockConsultingHis> stockConsultingHisList = stockConsultingHisRepository.findAll();
        assertThat(stockConsultingHisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStockConsultingHis() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList
        restStockConsultingHisMockMvc.perform(get("/api/stock-consulting-his?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockConsultingHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].consultingMemo").value(hasItem(DEFAULT_CONSULTING_MEMO)))
            .andExpect(jsonPath("$.[*].regDtm").value(hasItem(DEFAULT_REG_DTM.toString())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));
    }
    
    @Test
    @Transactional
    public void getStockConsultingHis() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get the stockConsultingHis
        restStockConsultingHisMockMvc.perform(get("/api/stock-consulting-his/{id}", stockConsultingHis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockConsultingHis.getId().intValue()))
            .andExpect(jsonPath("$.consultingMemo").value(DEFAULT_CONSULTING_MEMO))
            .andExpect(jsonPath("$.regDtm").value(DEFAULT_REG_DTM.toString()))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.toString()));
    }


    @Test
    @Transactional
    public void getStockConsultingHisByIdFiltering() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        Long id = stockConsultingHis.getId();

        defaultStockConsultingHisShouldBeFound("id.equals=" + id);
        defaultStockConsultingHisShouldNotBeFound("id.notEquals=" + id);

        defaultStockConsultingHisShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockConsultingHisShouldNotBeFound("id.greaterThan=" + id);

        defaultStockConsultingHisShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockConsultingHisShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockConsultingHisByConsultingMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where consultingMemo equals to DEFAULT_CONSULTING_MEMO
        defaultStockConsultingHisShouldBeFound("consultingMemo.equals=" + DEFAULT_CONSULTING_MEMO);

        // Get all the stockConsultingHisList where consultingMemo equals to UPDATED_CONSULTING_MEMO
        defaultStockConsultingHisShouldNotBeFound("consultingMemo.equals=" + UPDATED_CONSULTING_MEMO);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByConsultingMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where consultingMemo not equals to DEFAULT_CONSULTING_MEMO
        defaultStockConsultingHisShouldNotBeFound("consultingMemo.notEquals=" + DEFAULT_CONSULTING_MEMO);

        // Get all the stockConsultingHisList where consultingMemo not equals to UPDATED_CONSULTING_MEMO
        defaultStockConsultingHisShouldBeFound("consultingMemo.notEquals=" + UPDATED_CONSULTING_MEMO);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByConsultingMemoIsInShouldWork() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where consultingMemo in DEFAULT_CONSULTING_MEMO or UPDATED_CONSULTING_MEMO
        defaultStockConsultingHisShouldBeFound("consultingMemo.in=" + DEFAULT_CONSULTING_MEMO + "," + UPDATED_CONSULTING_MEMO);

        // Get all the stockConsultingHisList where consultingMemo equals to UPDATED_CONSULTING_MEMO
        defaultStockConsultingHisShouldNotBeFound("consultingMemo.in=" + UPDATED_CONSULTING_MEMO);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByConsultingMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where consultingMemo is not null
        defaultStockConsultingHisShouldBeFound("consultingMemo.specified=true");

        // Get all the stockConsultingHisList where consultingMemo is null
        defaultStockConsultingHisShouldNotBeFound("consultingMemo.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockConsultingHisByConsultingMemoContainsSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where consultingMemo contains DEFAULT_CONSULTING_MEMO
        defaultStockConsultingHisShouldBeFound("consultingMemo.contains=" + DEFAULT_CONSULTING_MEMO);

        // Get all the stockConsultingHisList where consultingMemo contains UPDATED_CONSULTING_MEMO
        defaultStockConsultingHisShouldNotBeFound("consultingMemo.contains=" + UPDATED_CONSULTING_MEMO);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByConsultingMemoNotContainsSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where consultingMemo does not contain DEFAULT_CONSULTING_MEMO
        defaultStockConsultingHisShouldNotBeFound("consultingMemo.doesNotContain=" + DEFAULT_CONSULTING_MEMO);

        // Get all the stockConsultingHisList where consultingMemo does not contain UPDATED_CONSULTING_MEMO
        defaultStockConsultingHisShouldBeFound("consultingMemo.doesNotContain=" + UPDATED_CONSULTING_MEMO);
    }


    @Test
    @Transactional
    public void getAllStockConsultingHisByRegDtmIsEqualToSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where regDtm equals to DEFAULT_REG_DTM
        defaultStockConsultingHisShouldBeFound("regDtm.equals=" + DEFAULT_REG_DTM);

        // Get all the stockConsultingHisList where regDtm equals to UPDATED_REG_DTM
        defaultStockConsultingHisShouldNotBeFound("regDtm.equals=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByRegDtmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where regDtm not equals to DEFAULT_REG_DTM
        defaultStockConsultingHisShouldNotBeFound("regDtm.notEquals=" + DEFAULT_REG_DTM);

        // Get all the stockConsultingHisList where regDtm not equals to UPDATED_REG_DTM
        defaultStockConsultingHisShouldBeFound("regDtm.notEquals=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByRegDtmIsInShouldWork() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where regDtm in DEFAULT_REG_DTM or UPDATED_REG_DTM
        defaultStockConsultingHisShouldBeFound("regDtm.in=" + DEFAULT_REG_DTM + "," + UPDATED_REG_DTM);

        // Get all the stockConsultingHisList where regDtm equals to UPDATED_REG_DTM
        defaultStockConsultingHisShouldNotBeFound("regDtm.in=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByRegDtmIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where regDtm is not null
        defaultStockConsultingHisShouldBeFound("regDtm.specified=true");

        // Get all the stockConsultingHisList where regDtm is null
        defaultStockConsultingHisShouldNotBeFound("regDtm.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByRegDtmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where regDtm is greater than or equal to DEFAULT_REG_DTM
        defaultStockConsultingHisShouldBeFound("regDtm.greaterThanOrEqual=" + DEFAULT_REG_DTM);

        // Get all the stockConsultingHisList where regDtm is greater than or equal to UPDATED_REG_DTM
        defaultStockConsultingHisShouldNotBeFound("regDtm.greaterThanOrEqual=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByRegDtmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where regDtm is less than or equal to DEFAULT_REG_DTM
        defaultStockConsultingHisShouldBeFound("regDtm.lessThanOrEqual=" + DEFAULT_REG_DTM);

        // Get all the stockConsultingHisList where regDtm is less than or equal to SMALLER_REG_DTM
        defaultStockConsultingHisShouldNotBeFound("regDtm.lessThanOrEqual=" + SMALLER_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByRegDtmIsLessThanSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where regDtm is less than DEFAULT_REG_DTM
        defaultStockConsultingHisShouldNotBeFound("regDtm.lessThan=" + DEFAULT_REG_DTM);

        // Get all the stockConsultingHisList where regDtm is less than UPDATED_REG_DTM
        defaultStockConsultingHisShouldBeFound("regDtm.lessThan=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByRegDtmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where regDtm is greater than DEFAULT_REG_DTM
        defaultStockConsultingHisShouldNotBeFound("regDtm.greaterThan=" + DEFAULT_REG_DTM);

        // Get all the stockConsultingHisList where regDtm is greater than SMALLER_REG_DTM
        defaultStockConsultingHisShouldBeFound("regDtm.greaterThan=" + SMALLER_REG_DTM);
    }


    @Test
    @Transactional
    public void getAllStockConsultingHisByUseYnIsEqualToSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where useYn equals to DEFAULT_USE_YN
        defaultStockConsultingHisShouldBeFound("useYn.equals=" + DEFAULT_USE_YN);

        // Get all the stockConsultingHisList where useYn equals to UPDATED_USE_YN
        defaultStockConsultingHisShouldNotBeFound("useYn.equals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByUseYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where useYn not equals to DEFAULT_USE_YN
        defaultStockConsultingHisShouldNotBeFound("useYn.notEquals=" + DEFAULT_USE_YN);

        // Get all the stockConsultingHisList where useYn not equals to UPDATED_USE_YN
        defaultStockConsultingHisShouldBeFound("useYn.notEquals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByUseYnIsInShouldWork() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where useYn in DEFAULT_USE_YN or UPDATED_USE_YN
        defaultStockConsultingHisShouldBeFound("useYn.in=" + DEFAULT_USE_YN + "," + UPDATED_USE_YN);

        // Get all the stockConsultingHisList where useYn equals to UPDATED_USE_YN
        defaultStockConsultingHisShouldNotBeFound("useYn.in=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByUseYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);

        // Get all the stockConsultingHisList where useYn is not null
        defaultStockConsultingHisShouldBeFound("useYn.specified=true");

        // Get all the stockConsultingHisList where useYn is null
        defaultStockConsultingHisShouldNotBeFound("useYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockConsultingHisByCrmCustomIsEqualToSomething() throws Exception {
        // Initialize the database
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);
        CrmCustom crmCustom = CrmCustomResourceIT.createEntity(em);
        em.persist(crmCustom);
        em.flush();
        stockConsultingHis.setCrmCustom(crmCustom);
        stockConsultingHisRepository.saveAndFlush(stockConsultingHis);
        Long crmCustomId = crmCustom.getId();

        // Get all the stockConsultingHisList where crmCustom equals to crmCustomId
        defaultStockConsultingHisShouldBeFound("crmCustomId.equals=" + crmCustomId);

        // Get all the stockConsultingHisList where crmCustom equals to crmCustomId + 1
        defaultStockConsultingHisShouldNotBeFound("crmCustomId.equals=" + (crmCustomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockConsultingHisShouldBeFound(String filter) throws Exception {
        restStockConsultingHisMockMvc.perform(get("/api/stock-consulting-his?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockConsultingHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].consultingMemo").value(hasItem(DEFAULT_CONSULTING_MEMO)))
            .andExpect(jsonPath("$.[*].regDtm").value(hasItem(DEFAULT_REG_DTM.toString())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));

        // Check, that the count call also returns 1
        restStockConsultingHisMockMvc.perform(get("/api/stock-consulting-his/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockConsultingHisShouldNotBeFound(String filter) throws Exception {
        restStockConsultingHisMockMvc.perform(get("/api/stock-consulting-his?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockConsultingHisMockMvc.perform(get("/api/stock-consulting-his/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStockConsultingHis() throws Exception {
        // Get the stockConsultingHis
        restStockConsultingHisMockMvc.perform(get("/api/stock-consulting-his/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockConsultingHis() throws Exception {
        // Initialize the database
        stockConsultingHisService.save(stockConsultingHis);

        int databaseSizeBeforeUpdate = stockConsultingHisRepository.findAll().size();

        // Update the stockConsultingHis
        StockConsultingHis updatedStockConsultingHis = stockConsultingHisRepository.findById(stockConsultingHis.getId()).get();
        // Disconnect from session so that the updates on updatedStockConsultingHis are not directly saved in db
        em.detach(updatedStockConsultingHis);
        updatedStockConsultingHis
            .consultingMemo(UPDATED_CONSULTING_MEMO)
            .regDtm(UPDATED_REG_DTM)
            .useYn(UPDATED_USE_YN);

        restStockConsultingHisMockMvc.perform(put("/api/stock-consulting-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockConsultingHis)))
            .andExpect(status().isOk());

        // Validate the StockConsultingHis in the database
        List<StockConsultingHis> stockConsultingHisList = stockConsultingHisRepository.findAll();
        assertThat(stockConsultingHisList).hasSize(databaseSizeBeforeUpdate);
        StockConsultingHis testStockConsultingHis = stockConsultingHisList.get(stockConsultingHisList.size() - 1);
        assertThat(testStockConsultingHis.getConsultingMemo()).isEqualTo(UPDATED_CONSULTING_MEMO);
        assertThat(testStockConsultingHis.getRegDtm()).isEqualTo(UPDATED_REG_DTM);
        assertThat(testStockConsultingHis.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingStockConsultingHis() throws Exception {
        int databaseSizeBeforeUpdate = stockConsultingHisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockConsultingHisMockMvc.perform(put("/api/stock-consulting-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockConsultingHis)))
            .andExpect(status().isBadRequest());

        // Validate the StockConsultingHis in the database
        List<StockConsultingHis> stockConsultingHisList = stockConsultingHisRepository.findAll();
        assertThat(stockConsultingHisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockConsultingHis() throws Exception {
        // Initialize the database
        stockConsultingHisService.save(stockConsultingHis);

        int databaseSizeBeforeDelete = stockConsultingHisRepository.findAll().size();

        // Delete the stockConsultingHis
        restStockConsultingHisMockMvc.perform(delete("/api/stock-consulting-his/{id}", stockConsultingHis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockConsultingHis> stockConsultingHisList = stockConsultingHisRepository.findAll();
        assertThat(stockConsultingHisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
