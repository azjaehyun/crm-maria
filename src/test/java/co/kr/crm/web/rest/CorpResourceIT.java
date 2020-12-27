package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.Corp;
import co.kr.crm.repository.CorpRepository;
import co.kr.crm.service.CorpService;

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
 * Integration tests for the {@link CorpResource} REST controller.
 */
@SpringBootTest(classes = CrmMariaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CorpResourceIT {

    private static final String DEFAULT_CORP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CORP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CORP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORP_NAME = "BBBBBBBBBB";

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private CorpRepository corpRepository;

    @Autowired
    private CorpService corpService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCorpMockMvc;

    private Corp corp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corp createEntity(EntityManager em) {
        Corp corp = new Corp()
            .corpCode(DEFAULT_CORP_CODE)
            .corpName(DEFAULT_CORP_NAME)
            .useYn(DEFAULT_USE_YN);
        return corp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corp createUpdatedEntity(EntityManager em) {
        Corp corp = new Corp()
            .corpCode(UPDATED_CORP_CODE)
            .corpName(UPDATED_CORP_NAME)
            .useYn(UPDATED_USE_YN);
        return corp;
    }

    @BeforeEach
    public void initTest() {
        corp = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorp() throws Exception {
        int databaseSizeBeforeCreate = corpRepository.findAll().size();
        // Create the Corp
        restCorpMockMvc.perform(post("/api/corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corp)))
            .andExpect(status().isCreated());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeCreate + 1);
        Corp testCorp = corpList.get(corpList.size() - 1);
        assertThat(testCorp.getCorpCode()).isEqualTo(DEFAULT_CORP_CODE);
        assertThat(testCorp.getCorpName()).isEqualTo(DEFAULT_CORP_NAME);
        assertThat(testCorp.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    public void createCorpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corpRepository.findAll().size();

        // Create the Corp with an existing ID
        corp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorpMockMvc.perform(post("/api/corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corp)))
            .andExpect(status().isBadRequest());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCorpCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = corpRepository.findAll().size();
        // set the field null
        corp.setCorpCode(null);

        // Create the Corp, which fails.


        restCorpMockMvc.perform(post("/api/corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corp)))
            .andExpect(status().isBadRequest());

        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorpNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corpRepository.findAll().size();
        // set the field null
        corp.setCorpName(null);

        // Create the Corp, which fails.


        restCorpMockMvc.perform(post("/api/corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corp)))
            .andExpect(status().isBadRequest());

        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorps() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList
        restCorpMockMvc.perform(get("/api/corps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corp.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpCode").value(hasItem(DEFAULT_CORP_CODE)))
            .andExpect(jsonPath("$.[*].corpName").value(hasItem(DEFAULT_CORP_NAME)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));
    }
    
    @Test
    @Transactional
    public void getCorp() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get the corp
        restCorpMockMvc.perform(get("/api/corps/{id}", corp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(corp.getId().intValue()))
            .andExpect(jsonPath("$.corpCode").value(DEFAULT_CORP_CODE))
            .andExpect(jsonPath("$.corpName").value(DEFAULT_CORP_NAME))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCorp() throws Exception {
        // Get the corp
        restCorpMockMvc.perform(get("/api/corps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorp() throws Exception {
        // Initialize the database
        corpService.save(corp);

        int databaseSizeBeforeUpdate = corpRepository.findAll().size();

        // Update the corp
        Corp updatedCorp = corpRepository.findById(corp.getId()).get();
        // Disconnect from session so that the updates on updatedCorp are not directly saved in db
        em.detach(updatedCorp);
        updatedCorp
            .corpCode(UPDATED_CORP_CODE)
            .corpName(UPDATED_CORP_NAME)
            .useYn(UPDATED_USE_YN);

        restCorpMockMvc.perform(put("/api/corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCorp)))
            .andExpect(status().isOk());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
        Corp testCorp = corpList.get(corpList.size() - 1);
        assertThat(testCorp.getCorpCode()).isEqualTo(UPDATED_CORP_CODE);
        assertThat(testCorp.getCorpName()).isEqualTo(UPDATED_CORP_NAME);
        assertThat(testCorp.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingCorp() throws Exception {
        int databaseSizeBeforeUpdate = corpRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorpMockMvc.perform(put("/api/corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corp)))
            .andExpect(status().isBadRequest());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorp() throws Exception {
        // Initialize the database
        corpService.save(corp);

        int databaseSizeBeforeDelete = corpRepository.findAll().size();

        // Delete the corp
        restCorpMockMvc.perform(delete("/api/corps/{id}", corp.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
