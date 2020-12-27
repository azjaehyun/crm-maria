package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.MemoHis;
import co.kr.crm.repository.MemoHisRepository;
import co.kr.crm.service.MemoHisService;

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

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private MemoHisRepository memoHisRepository;

    @Autowired
    private MemoHisService memoHisService;

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
