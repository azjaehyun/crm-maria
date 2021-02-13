package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.Corp;
import co.kr.crm.domain.TeamGrp;
import co.kr.crm.repository.CorpRepository;
import co.kr.crm.service.CorpService;
import co.kr.crm.service.dto.CorpCriteria;
import co.kr.crm.service.CorpQueryService;

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
    private CorpQueryService corpQueryService;

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
    public void getCorpsByIdFiltering() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        Long id = corp.getId();

        defaultCorpShouldBeFound("id.equals=" + id);
        defaultCorpShouldNotBeFound("id.notEquals=" + id);

        defaultCorpShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCorpShouldNotBeFound("id.greaterThan=" + id);

        defaultCorpShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCorpShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCorpsByCorpCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpCode equals to DEFAULT_CORP_CODE
        defaultCorpShouldBeFound("corpCode.equals=" + DEFAULT_CORP_CODE);

        // Get all the corpList where corpCode equals to UPDATED_CORP_CODE
        defaultCorpShouldNotBeFound("corpCode.equals=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllCorpsByCorpCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpCode not equals to DEFAULT_CORP_CODE
        defaultCorpShouldNotBeFound("corpCode.notEquals=" + DEFAULT_CORP_CODE);

        // Get all the corpList where corpCode not equals to UPDATED_CORP_CODE
        defaultCorpShouldBeFound("corpCode.notEquals=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllCorpsByCorpCodeIsInShouldWork() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpCode in DEFAULT_CORP_CODE or UPDATED_CORP_CODE
        defaultCorpShouldBeFound("corpCode.in=" + DEFAULT_CORP_CODE + "," + UPDATED_CORP_CODE);

        // Get all the corpList where corpCode equals to UPDATED_CORP_CODE
        defaultCorpShouldNotBeFound("corpCode.in=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllCorpsByCorpCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpCode is not null
        defaultCorpShouldBeFound("corpCode.specified=true");

        // Get all the corpList where corpCode is null
        defaultCorpShouldNotBeFound("corpCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCorpsByCorpCodeContainsSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpCode contains DEFAULT_CORP_CODE
        defaultCorpShouldBeFound("corpCode.contains=" + DEFAULT_CORP_CODE);

        // Get all the corpList where corpCode contains UPDATED_CORP_CODE
        defaultCorpShouldNotBeFound("corpCode.contains=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllCorpsByCorpCodeNotContainsSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpCode does not contain DEFAULT_CORP_CODE
        defaultCorpShouldNotBeFound("corpCode.doesNotContain=" + DEFAULT_CORP_CODE);

        // Get all the corpList where corpCode does not contain UPDATED_CORP_CODE
        defaultCorpShouldBeFound("corpCode.doesNotContain=" + UPDATED_CORP_CODE);
    }


    @Test
    @Transactional
    public void getAllCorpsByCorpNameIsEqualToSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpName equals to DEFAULT_CORP_NAME
        defaultCorpShouldBeFound("corpName.equals=" + DEFAULT_CORP_NAME);

        // Get all the corpList where corpName equals to UPDATED_CORP_NAME
        defaultCorpShouldNotBeFound("corpName.equals=" + UPDATED_CORP_NAME);
    }

    @Test
    @Transactional
    public void getAllCorpsByCorpNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpName not equals to DEFAULT_CORP_NAME
        defaultCorpShouldNotBeFound("corpName.notEquals=" + DEFAULT_CORP_NAME);

        // Get all the corpList where corpName not equals to UPDATED_CORP_NAME
        defaultCorpShouldBeFound("corpName.notEquals=" + UPDATED_CORP_NAME);
    }

    @Test
    @Transactional
    public void getAllCorpsByCorpNameIsInShouldWork() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpName in DEFAULT_CORP_NAME or UPDATED_CORP_NAME
        defaultCorpShouldBeFound("corpName.in=" + DEFAULT_CORP_NAME + "," + UPDATED_CORP_NAME);

        // Get all the corpList where corpName equals to UPDATED_CORP_NAME
        defaultCorpShouldNotBeFound("corpName.in=" + UPDATED_CORP_NAME);
    }

    @Test
    @Transactional
    public void getAllCorpsByCorpNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpName is not null
        defaultCorpShouldBeFound("corpName.specified=true");

        // Get all the corpList where corpName is null
        defaultCorpShouldNotBeFound("corpName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCorpsByCorpNameContainsSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpName contains DEFAULT_CORP_NAME
        defaultCorpShouldBeFound("corpName.contains=" + DEFAULT_CORP_NAME);

        // Get all the corpList where corpName contains UPDATED_CORP_NAME
        defaultCorpShouldNotBeFound("corpName.contains=" + UPDATED_CORP_NAME);
    }

    @Test
    @Transactional
    public void getAllCorpsByCorpNameNotContainsSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where corpName does not contain DEFAULT_CORP_NAME
        defaultCorpShouldNotBeFound("corpName.doesNotContain=" + DEFAULT_CORP_NAME);

        // Get all the corpList where corpName does not contain UPDATED_CORP_NAME
        defaultCorpShouldBeFound("corpName.doesNotContain=" + UPDATED_CORP_NAME);
    }


    @Test
    @Transactional
    public void getAllCorpsByUseYnIsEqualToSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where useYn equals to DEFAULT_USE_YN
        defaultCorpShouldBeFound("useYn.equals=" + DEFAULT_USE_YN);

        // Get all the corpList where useYn equals to UPDATED_USE_YN
        defaultCorpShouldNotBeFound("useYn.equals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllCorpsByUseYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where useYn not equals to DEFAULT_USE_YN
        defaultCorpShouldNotBeFound("useYn.notEquals=" + DEFAULT_USE_YN);

        // Get all the corpList where useYn not equals to UPDATED_USE_YN
        defaultCorpShouldBeFound("useYn.notEquals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllCorpsByUseYnIsInShouldWork() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where useYn in DEFAULT_USE_YN or UPDATED_USE_YN
        defaultCorpShouldBeFound("useYn.in=" + DEFAULT_USE_YN + "," + UPDATED_USE_YN);

        // Get all the corpList where useYn equals to UPDATED_USE_YN
        defaultCorpShouldNotBeFound("useYn.in=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllCorpsByUseYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList where useYn is not null
        defaultCorpShouldBeFound("useYn.specified=true");

        // Get all the corpList where useYn is null
        defaultCorpShouldNotBeFound("useYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorpsByTeamGrpIsEqualToSomething() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);
        TeamGrp teamGrp = TeamGrpResourceIT.createEntity(em);
        em.persist(teamGrp);
        em.flush();
        corp.addTeamGrp(teamGrp);
        corpRepository.saveAndFlush(corp);
        Long teamGrpId = teamGrp.getId();

        // Get all the corpList where teamGrp equals to teamGrpId
        defaultCorpShouldBeFound("teamGrpId.equals=" + teamGrpId);

        // Get all the corpList where teamGrp equals to teamGrpId + 1
        defaultCorpShouldNotBeFound("teamGrpId.equals=" + (teamGrpId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCorpShouldBeFound(String filter) throws Exception {
        restCorpMockMvc.perform(get("/api/corps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corp.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpCode").value(hasItem(DEFAULT_CORP_CODE)))
            .andExpect(jsonPath("$.[*].corpName").value(hasItem(DEFAULT_CORP_NAME)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));

        // Check, that the count call also returns 1
        restCorpMockMvc.perform(get("/api/corps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCorpShouldNotBeFound(String filter) throws Exception {
        restCorpMockMvc.perform(get("/api/corps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCorpMockMvc.perform(get("/api/corps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
