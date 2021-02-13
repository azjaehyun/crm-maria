package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.MemoHis;
import co.kr.crm.domain.CrmCustom;
import co.kr.crm.repository.MemoHisRepository;
import co.kr.crm.service.MemoHisService;
import co.kr.crm.service.dto.MemoHisCriteria;
import co.kr.crm.service.MemoHisQueryService;

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
 * Integration tests for the {@link MemoHisResource} REST controller.
 */
@SpringBootTest(classes = CrmMariaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MemoHisResourceIT {

    private static final String DEFAULT_MEMO_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_MEMO_CONTENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REG_DTM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REG_DTM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REG_DTM = LocalDate.ofEpochDay(-1L);

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private MemoHisRepository memoHisRepository;

    @Autowired
    private MemoHisService memoHisService;

    @Autowired
    private MemoHisQueryService memoHisQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemoHisMockMvc;

    private MemoHis memoHis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemoHis createEntity(EntityManager em) {
        MemoHis memoHis = new MemoHis()
            .memoContent(DEFAULT_MEMO_CONTENT)
            .regDtm(DEFAULT_REG_DTM)
            .useYn(DEFAULT_USE_YN);
        return memoHis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemoHis createUpdatedEntity(EntityManager em) {
        MemoHis memoHis = new MemoHis()
            .memoContent(UPDATED_MEMO_CONTENT)
            .regDtm(UPDATED_REG_DTM)
            .useYn(UPDATED_USE_YN);
        return memoHis;
    }

    @BeforeEach
    public void initTest() {
        memoHis = createEntity(em);
    }

    @Test
    @Transactional
    public void createMemoHis() throws Exception {
        int databaseSizeBeforeCreate = memoHisRepository.findAll().size();
        // Create the MemoHis
        restMemoHisMockMvc.perform(post("/api/memo-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memoHis)))
            .andExpect(status().isCreated());

        // Validate the MemoHis in the database
        List<MemoHis> memoHisList = memoHisRepository.findAll();
        assertThat(memoHisList).hasSize(databaseSizeBeforeCreate + 1);
        MemoHis testMemoHis = memoHisList.get(memoHisList.size() - 1);
        assertThat(testMemoHis.getMemoContent()).isEqualTo(DEFAULT_MEMO_CONTENT);
        assertThat(testMemoHis.getRegDtm()).isEqualTo(DEFAULT_REG_DTM);
        assertThat(testMemoHis.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    public void createMemoHisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memoHisRepository.findAll().size();

        // Create the MemoHis with an existing ID
        memoHis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemoHisMockMvc.perform(post("/api/memo-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memoHis)))
            .andExpect(status().isBadRequest());

        // Validate the MemoHis in the database
        List<MemoHis> memoHisList = memoHisRepository.findAll();
        assertThat(memoHisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMemoHis() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList
        restMemoHisMockMvc.perform(get("/api/memo-his?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memoHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].memoContent").value(hasItem(DEFAULT_MEMO_CONTENT)))
            .andExpect(jsonPath("$.[*].regDtm").value(hasItem(DEFAULT_REG_DTM.toString())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));
    }
    
    @Test
    @Transactional
    public void getMemoHis() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get the memoHis
        restMemoHisMockMvc.perform(get("/api/memo-his/{id}", memoHis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memoHis.getId().intValue()))
            .andExpect(jsonPath("$.memoContent").value(DEFAULT_MEMO_CONTENT))
            .andExpect(jsonPath("$.regDtm").value(DEFAULT_REG_DTM.toString()))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.toString()));
    }


    @Test
    @Transactional
    public void getMemoHisByIdFiltering() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        Long id = memoHis.getId();

        defaultMemoHisShouldBeFound("id.equals=" + id);
        defaultMemoHisShouldNotBeFound("id.notEquals=" + id);

        defaultMemoHisShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemoHisShouldNotBeFound("id.greaterThan=" + id);

        defaultMemoHisShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemoHisShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMemoHisByMemoContentIsEqualToSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where memoContent equals to DEFAULT_MEMO_CONTENT
        defaultMemoHisShouldBeFound("memoContent.equals=" + DEFAULT_MEMO_CONTENT);

        // Get all the memoHisList where memoContent equals to UPDATED_MEMO_CONTENT
        defaultMemoHisShouldNotBeFound("memoContent.equals=" + UPDATED_MEMO_CONTENT);
    }

    @Test
    @Transactional
    public void getAllMemoHisByMemoContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where memoContent not equals to DEFAULT_MEMO_CONTENT
        defaultMemoHisShouldNotBeFound("memoContent.notEquals=" + DEFAULT_MEMO_CONTENT);

        // Get all the memoHisList where memoContent not equals to UPDATED_MEMO_CONTENT
        defaultMemoHisShouldBeFound("memoContent.notEquals=" + UPDATED_MEMO_CONTENT);
    }

    @Test
    @Transactional
    public void getAllMemoHisByMemoContentIsInShouldWork() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where memoContent in DEFAULT_MEMO_CONTENT or UPDATED_MEMO_CONTENT
        defaultMemoHisShouldBeFound("memoContent.in=" + DEFAULT_MEMO_CONTENT + "," + UPDATED_MEMO_CONTENT);

        // Get all the memoHisList where memoContent equals to UPDATED_MEMO_CONTENT
        defaultMemoHisShouldNotBeFound("memoContent.in=" + UPDATED_MEMO_CONTENT);
    }

    @Test
    @Transactional
    public void getAllMemoHisByMemoContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where memoContent is not null
        defaultMemoHisShouldBeFound("memoContent.specified=true");

        // Get all the memoHisList where memoContent is null
        defaultMemoHisShouldNotBeFound("memoContent.specified=false");
    }
                @Test
    @Transactional
    public void getAllMemoHisByMemoContentContainsSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where memoContent contains DEFAULT_MEMO_CONTENT
        defaultMemoHisShouldBeFound("memoContent.contains=" + DEFAULT_MEMO_CONTENT);

        // Get all the memoHisList where memoContent contains UPDATED_MEMO_CONTENT
        defaultMemoHisShouldNotBeFound("memoContent.contains=" + UPDATED_MEMO_CONTENT);
    }

    @Test
    @Transactional
    public void getAllMemoHisByMemoContentNotContainsSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where memoContent does not contain DEFAULT_MEMO_CONTENT
        defaultMemoHisShouldNotBeFound("memoContent.doesNotContain=" + DEFAULT_MEMO_CONTENT);

        // Get all the memoHisList where memoContent does not contain UPDATED_MEMO_CONTENT
        defaultMemoHisShouldBeFound("memoContent.doesNotContain=" + UPDATED_MEMO_CONTENT);
    }


    @Test
    @Transactional
    public void getAllMemoHisByRegDtmIsEqualToSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where regDtm equals to DEFAULT_REG_DTM
        defaultMemoHisShouldBeFound("regDtm.equals=" + DEFAULT_REG_DTM);

        // Get all the memoHisList where regDtm equals to UPDATED_REG_DTM
        defaultMemoHisShouldNotBeFound("regDtm.equals=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllMemoHisByRegDtmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where regDtm not equals to DEFAULT_REG_DTM
        defaultMemoHisShouldNotBeFound("regDtm.notEquals=" + DEFAULT_REG_DTM);

        // Get all the memoHisList where regDtm not equals to UPDATED_REG_DTM
        defaultMemoHisShouldBeFound("regDtm.notEquals=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllMemoHisByRegDtmIsInShouldWork() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where regDtm in DEFAULT_REG_DTM or UPDATED_REG_DTM
        defaultMemoHisShouldBeFound("regDtm.in=" + DEFAULT_REG_DTM + "," + UPDATED_REG_DTM);

        // Get all the memoHisList where regDtm equals to UPDATED_REG_DTM
        defaultMemoHisShouldNotBeFound("regDtm.in=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllMemoHisByRegDtmIsNullOrNotNull() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where regDtm is not null
        defaultMemoHisShouldBeFound("regDtm.specified=true");

        // Get all the memoHisList where regDtm is null
        defaultMemoHisShouldNotBeFound("regDtm.specified=false");
    }

    @Test
    @Transactional
    public void getAllMemoHisByRegDtmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where regDtm is greater than or equal to DEFAULT_REG_DTM
        defaultMemoHisShouldBeFound("regDtm.greaterThanOrEqual=" + DEFAULT_REG_DTM);

        // Get all the memoHisList where regDtm is greater than or equal to UPDATED_REG_DTM
        defaultMemoHisShouldNotBeFound("regDtm.greaterThanOrEqual=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllMemoHisByRegDtmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where regDtm is less than or equal to DEFAULT_REG_DTM
        defaultMemoHisShouldBeFound("regDtm.lessThanOrEqual=" + DEFAULT_REG_DTM);

        // Get all the memoHisList where regDtm is less than or equal to SMALLER_REG_DTM
        defaultMemoHisShouldNotBeFound("regDtm.lessThanOrEqual=" + SMALLER_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllMemoHisByRegDtmIsLessThanSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where regDtm is less than DEFAULT_REG_DTM
        defaultMemoHisShouldNotBeFound("regDtm.lessThan=" + DEFAULT_REG_DTM);

        // Get all the memoHisList where regDtm is less than UPDATED_REG_DTM
        defaultMemoHisShouldBeFound("regDtm.lessThan=" + UPDATED_REG_DTM);
    }

    @Test
    @Transactional
    public void getAllMemoHisByRegDtmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where regDtm is greater than DEFAULT_REG_DTM
        defaultMemoHisShouldNotBeFound("regDtm.greaterThan=" + DEFAULT_REG_DTM);

        // Get all the memoHisList where regDtm is greater than SMALLER_REG_DTM
        defaultMemoHisShouldBeFound("regDtm.greaterThan=" + SMALLER_REG_DTM);
    }


    @Test
    @Transactional
    public void getAllMemoHisByUseYnIsEqualToSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where useYn equals to DEFAULT_USE_YN
        defaultMemoHisShouldBeFound("useYn.equals=" + DEFAULT_USE_YN);

        // Get all the memoHisList where useYn equals to UPDATED_USE_YN
        defaultMemoHisShouldNotBeFound("useYn.equals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllMemoHisByUseYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where useYn not equals to DEFAULT_USE_YN
        defaultMemoHisShouldNotBeFound("useYn.notEquals=" + DEFAULT_USE_YN);

        // Get all the memoHisList where useYn not equals to UPDATED_USE_YN
        defaultMemoHisShouldBeFound("useYn.notEquals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllMemoHisByUseYnIsInShouldWork() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where useYn in DEFAULT_USE_YN or UPDATED_USE_YN
        defaultMemoHisShouldBeFound("useYn.in=" + DEFAULT_USE_YN + "," + UPDATED_USE_YN);

        // Get all the memoHisList where useYn equals to UPDATED_USE_YN
        defaultMemoHisShouldNotBeFound("useYn.in=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllMemoHisByUseYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);

        // Get all the memoHisList where useYn is not null
        defaultMemoHisShouldBeFound("useYn.specified=true");

        // Get all the memoHisList where useYn is null
        defaultMemoHisShouldNotBeFound("useYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllMemoHisByCrmCustomIsEqualToSomething() throws Exception {
        // Initialize the database
        memoHisRepository.saveAndFlush(memoHis);
        CrmCustom crmCustom = CrmCustomResourceIT.createEntity(em);
        em.persist(crmCustom);
        em.flush();
        memoHis.setCrmCustom(crmCustom);
        memoHisRepository.saveAndFlush(memoHis);
        Long crmCustomId = crmCustom.getId();

        // Get all the memoHisList where crmCustom equals to crmCustomId
        defaultMemoHisShouldBeFound("crmCustomId.equals=" + crmCustomId);

        // Get all the memoHisList where crmCustom equals to crmCustomId + 1
        defaultMemoHisShouldNotBeFound("crmCustomId.equals=" + (crmCustomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemoHisShouldBeFound(String filter) throws Exception {
        restMemoHisMockMvc.perform(get("/api/memo-his?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memoHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].memoContent").value(hasItem(DEFAULT_MEMO_CONTENT)))
            .andExpect(jsonPath("$.[*].regDtm").value(hasItem(DEFAULT_REG_DTM.toString())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));

        // Check, that the count call also returns 1
        restMemoHisMockMvc.perform(get("/api/memo-his/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemoHisShouldNotBeFound(String filter) throws Exception {
        restMemoHisMockMvc.perform(get("/api/memo-his?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemoHisMockMvc.perform(get("/api/memo-his/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMemoHis() throws Exception {
        // Get the memoHis
        restMemoHisMockMvc.perform(get("/api/memo-his/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMemoHis() throws Exception {
        // Initialize the database
        memoHisService.save(memoHis);

        int databaseSizeBeforeUpdate = memoHisRepository.findAll().size();

        // Update the memoHis
        MemoHis updatedMemoHis = memoHisRepository.findById(memoHis.getId()).get();
        // Disconnect from session so that the updates on updatedMemoHis are not directly saved in db
        em.detach(updatedMemoHis);
        updatedMemoHis
            .memoContent(UPDATED_MEMO_CONTENT)
            .regDtm(UPDATED_REG_DTM)
            .useYn(UPDATED_USE_YN);

        restMemoHisMockMvc.perform(put("/api/memo-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMemoHis)))
            .andExpect(status().isOk());

        // Validate the MemoHis in the database
        List<MemoHis> memoHisList = memoHisRepository.findAll();
        assertThat(memoHisList).hasSize(databaseSizeBeforeUpdate);
        MemoHis testMemoHis = memoHisList.get(memoHisList.size() - 1);
        assertThat(testMemoHis.getMemoContent()).isEqualTo(UPDATED_MEMO_CONTENT);
        assertThat(testMemoHis.getRegDtm()).isEqualTo(UPDATED_REG_DTM);
        assertThat(testMemoHis.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingMemoHis() throws Exception {
        int databaseSizeBeforeUpdate = memoHisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemoHisMockMvc.perform(put("/api/memo-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(memoHis)))
            .andExpect(status().isBadRequest());

        // Validate the MemoHis in the database
        List<MemoHis> memoHisList = memoHisRepository.findAll();
        assertThat(memoHisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMemoHis() throws Exception {
        // Initialize the database
        memoHisService.save(memoHis);

        int databaseSizeBeforeDelete = memoHisRepository.findAll().size();

        // Delete the memoHis
        restMemoHisMockMvc.perform(delete("/api/memo-his/{id}", memoHis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemoHis> memoHisList = memoHisRepository.findAll();
        assertThat(memoHisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
