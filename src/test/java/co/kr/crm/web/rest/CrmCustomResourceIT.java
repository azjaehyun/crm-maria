package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.CrmCustom;
import co.kr.crm.domain.MemoHis;
import co.kr.crm.domain.SendSmsHis;
import co.kr.crm.domain.StockContractHis;
import co.kr.crm.domain.StockConsultingHis;
import co.kr.crm.domain.Manager;
import co.kr.crm.domain.TmManager;
import co.kr.crm.repository.CrmCustomRepository;
import co.kr.crm.service.CrmCustomService;
import co.kr.crm.service.dto.CrmCustomCriteria;
import co.kr.crm.service.CrmCustomQueryService;

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

import co.kr.crm.domain.enumeration.SalesStatus;
import co.kr.crm.domain.enumeration.SmsReceptionYn;
import co.kr.crm.domain.enumeration.CallStatus;
import co.kr.crm.domain.enumeration.CustomStatus;
import co.kr.crm.domain.enumeration.Yn;
/**
 * Integration tests for the {@link CrmCustomResource} REST controller.
 */
@SpringBootTest(classes = CrmMariaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CrmCustomResourceIT {

    private static final String DEFAULT_CORP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CORP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CRM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CRM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_FIVE_DAYFREE_YN = "AAAAAAAAAA";
    private static final String UPDATED_FIVE_DAYFREE_YN = "BBBBBBBBBB";

    private static final SalesStatus DEFAULT_SALES_STATUS = SalesStatus.PAY;
    private static final SalesStatus UPDATED_SALES_STATUS = SalesStatus.FIVEDAYFREE;

    private static final SmsReceptionYn DEFAULT_SMS_RECEPTION_YN = SmsReceptionYn.Y;
    private static final SmsReceptionYn UPDATED_SMS_RECEPTION_YN = SmsReceptionYn.N;

    private static final CallStatus DEFAULT_CALL_STATUS = CallStatus.HOPE;
    private static final CallStatus UPDATED_CALL_STATUS = CallStatus.REJECT;

    private static final CustomStatus DEFAULT_CUSTOM_STATUS = CustomStatus.BEST;
    private static final CustomStatus UPDATED_CUSTOM_STATUS = CustomStatus.NOMAl;

    private static final String DEFAULT_TEMP_ONE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TEMP_ONE_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TEMP_TWO_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TEMP_TWO_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DB_INSERT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DB_INSERT_TYPE = "BBBBBBBBBB";

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private CrmCustomRepository crmCustomRepository;

    @Autowired
    private CrmCustomService crmCustomService;

    @Autowired
    private CrmCustomQueryService crmCustomQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCrmCustomMockMvc;

    private CrmCustom crmCustom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmCustom createEntity(EntityManager em) {
        CrmCustom crmCustom = new CrmCustom()
            .corpCode(DEFAULT_CORP_CODE)
            .crmName(DEFAULT_CRM_NAME)
            .phoneNum(DEFAULT_PHONE_NUM)
            .fiveDayfreeYn(DEFAULT_FIVE_DAYFREE_YN)
            .salesStatus(DEFAULT_SALES_STATUS)
            .smsReceptionYn(DEFAULT_SMS_RECEPTION_YN)
            .callStatus(DEFAULT_CALL_STATUS)
            .customStatus(DEFAULT_CUSTOM_STATUS)
            .tempOneStatus(DEFAULT_TEMP_ONE_STATUS)
            .tempTwoStatus(DEFAULT_TEMP_TWO_STATUS)
            .dbInsertType(DEFAULT_DB_INSERT_TYPE)
            .useYn(DEFAULT_USE_YN);
        return crmCustom;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmCustom createUpdatedEntity(EntityManager em) {
        CrmCustom crmCustom = new CrmCustom()
            .corpCode(UPDATED_CORP_CODE)
            .crmName(UPDATED_CRM_NAME)
            .phoneNum(UPDATED_PHONE_NUM)
            .fiveDayfreeYn(UPDATED_FIVE_DAYFREE_YN)
            .salesStatus(UPDATED_SALES_STATUS)
            .smsReceptionYn(UPDATED_SMS_RECEPTION_YN)
            .callStatus(UPDATED_CALL_STATUS)
            .customStatus(UPDATED_CUSTOM_STATUS)
            .tempOneStatus(UPDATED_TEMP_ONE_STATUS)
            .tempTwoStatus(UPDATED_TEMP_TWO_STATUS)
            .dbInsertType(UPDATED_DB_INSERT_TYPE)
            .useYn(UPDATED_USE_YN);
        return crmCustom;
    }

    @BeforeEach
    public void initTest() {
        crmCustom = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmCustom() throws Exception {
        int databaseSizeBeforeCreate = crmCustomRepository.findAll().size();
        // Create the CrmCustom
        restCrmCustomMockMvc.perform(post("/api/crm-customs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crmCustom)))
            .andExpect(status().isCreated());

        // Validate the CrmCustom in the database
        List<CrmCustom> crmCustomList = crmCustomRepository.findAll();
        assertThat(crmCustomList).hasSize(databaseSizeBeforeCreate + 1);
        CrmCustom testCrmCustom = crmCustomList.get(crmCustomList.size() - 1);
        assertThat(testCrmCustom.getCorpCode()).isEqualTo(DEFAULT_CORP_CODE);
        assertThat(testCrmCustom.getCrmName()).isEqualTo(DEFAULT_CRM_NAME);
        assertThat(testCrmCustom.getPhoneNum()).isEqualTo(DEFAULT_PHONE_NUM);
        assertThat(testCrmCustom.getFiveDayfreeYn()).isEqualTo(DEFAULT_FIVE_DAYFREE_YN);
        assertThat(testCrmCustom.getSalesStatus()).isEqualTo(DEFAULT_SALES_STATUS);
        assertThat(testCrmCustom.getSmsReceptionYn()).isEqualTo(DEFAULT_SMS_RECEPTION_YN);
        assertThat(testCrmCustom.getCallStatus()).isEqualTo(DEFAULT_CALL_STATUS);
        assertThat(testCrmCustom.getCustomStatus()).isEqualTo(DEFAULT_CUSTOM_STATUS);
        assertThat(testCrmCustom.getTempOneStatus()).isEqualTo(DEFAULT_TEMP_ONE_STATUS);
        assertThat(testCrmCustom.getTempTwoStatus()).isEqualTo(DEFAULT_TEMP_TWO_STATUS);
        assertThat(testCrmCustom.getDbInsertType()).isEqualTo(DEFAULT_DB_INSERT_TYPE);
        assertThat(testCrmCustom.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    public void createCrmCustomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmCustomRepository.findAll().size();

        // Create the CrmCustom with an existing ID
        crmCustom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmCustomMockMvc.perform(post("/api/crm-customs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crmCustom)))
            .andExpect(status().isBadRequest());

        // Validate the CrmCustom in the database
        List<CrmCustom> crmCustomList = crmCustomRepository.findAll();
        assertThat(crmCustomList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCorpCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmCustomRepository.findAll().size();
        // set the field null
        crmCustom.setCorpCode(null);

        // Create the CrmCustom, which fails.


        restCrmCustomMockMvc.perform(post("/api/crm-customs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crmCustom)))
            .andExpect(status().isBadRequest());

        List<CrmCustom> crmCustomList = crmCustomRepository.findAll();
        assertThat(crmCustomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCrmNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmCustomRepository.findAll().size();
        // set the field null
        crmCustom.setCrmName(null);

        // Create the CrmCustom, which fails.


        restCrmCustomMockMvc.perform(post("/api/crm-customs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crmCustom)))
            .andExpect(status().isBadRequest());

        List<CrmCustom> crmCustomList = crmCustomRepository.findAll();
        assertThat(crmCustomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFiveDayfreeYnIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmCustomRepository.findAll().size();
        // set the field null
        crmCustom.setFiveDayfreeYn(null);

        // Create the CrmCustom, which fails.


        restCrmCustomMockMvc.perform(post("/api/crm-customs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crmCustom)))
            .andExpect(status().isBadRequest());

        List<CrmCustom> crmCustomList = crmCustomRepository.findAll();
        assertThat(crmCustomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCrmCustoms() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList
        restCrmCustomMockMvc.perform(get("/api/crm-customs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmCustom.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpCode").value(hasItem(DEFAULT_CORP_CODE)))
            .andExpect(jsonPath("$.[*].crmName").value(hasItem(DEFAULT_CRM_NAME)))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].fiveDayfreeYn").value(hasItem(DEFAULT_FIVE_DAYFREE_YN)))
            .andExpect(jsonPath("$.[*].salesStatus").value(hasItem(DEFAULT_SALES_STATUS.toString())))
            .andExpect(jsonPath("$.[*].smsReceptionYn").value(hasItem(DEFAULT_SMS_RECEPTION_YN.toString())))
            .andExpect(jsonPath("$.[*].callStatus").value(hasItem(DEFAULT_CALL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].customStatus").value(hasItem(DEFAULT_CUSTOM_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tempOneStatus").value(hasItem(DEFAULT_TEMP_ONE_STATUS)))
            .andExpect(jsonPath("$.[*].tempTwoStatus").value(hasItem(DEFAULT_TEMP_TWO_STATUS)))
            .andExpect(jsonPath("$.[*].dbInsertType").value(hasItem(DEFAULT_DB_INSERT_TYPE)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));
    }
    
    @Test
    @Transactional
    public void getCrmCustom() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get the crmCustom
        restCrmCustomMockMvc.perform(get("/api/crm-customs/{id}", crmCustom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crmCustom.getId().intValue()))
            .andExpect(jsonPath("$.corpCode").value(DEFAULT_CORP_CODE))
            .andExpect(jsonPath("$.crmName").value(DEFAULT_CRM_NAME))
            .andExpect(jsonPath("$.phoneNum").value(DEFAULT_PHONE_NUM))
            .andExpect(jsonPath("$.fiveDayfreeYn").value(DEFAULT_FIVE_DAYFREE_YN))
            .andExpect(jsonPath("$.salesStatus").value(DEFAULT_SALES_STATUS.toString()))
            .andExpect(jsonPath("$.smsReceptionYn").value(DEFAULT_SMS_RECEPTION_YN.toString()))
            .andExpect(jsonPath("$.callStatus").value(DEFAULT_CALL_STATUS.toString()))
            .andExpect(jsonPath("$.customStatus").value(DEFAULT_CUSTOM_STATUS.toString()))
            .andExpect(jsonPath("$.tempOneStatus").value(DEFAULT_TEMP_ONE_STATUS))
            .andExpect(jsonPath("$.tempTwoStatus").value(DEFAULT_TEMP_TWO_STATUS))
            .andExpect(jsonPath("$.dbInsertType").value(DEFAULT_DB_INSERT_TYPE))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.toString()));
    }


    @Test
    @Transactional
    public void getCrmCustomsByIdFiltering() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        Long id = crmCustom.getId();

        defaultCrmCustomShouldBeFound("id.equals=" + id);
        defaultCrmCustomShouldNotBeFound("id.notEquals=" + id);

        defaultCrmCustomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCrmCustomShouldNotBeFound("id.greaterThan=" + id);

        defaultCrmCustomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCrmCustomShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByCorpCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where corpCode equals to DEFAULT_CORP_CODE
        defaultCrmCustomShouldBeFound("corpCode.equals=" + DEFAULT_CORP_CODE);

        // Get all the crmCustomList where corpCode equals to UPDATED_CORP_CODE
        defaultCrmCustomShouldNotBeFound("corpCode.equals=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCorpCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where corpCode not equals to DEFAULT_CORP_CODE
        defaultCrmCustomShouldNotBeFound("corpCode.notEquals=" + DEFAULT_CORP_CODE);

        // Get all the crmCustomList where corpCode not equals to UPDATED_CORP_CODE
        defaultCrmCustomShouldBeFound("corpCode.notEquals=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCorpCodeIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where corpCode in DEFAULT_CORP_CODE or UPDATED_CORP_CODE
        defaultCrmCustomShouldBeFound("corpCode.in=" + DEFAULT_CORP_CODE + "," + UPDATED_CORP_CODE);

        // Get all the crmCustomList where corpCode equals to UPDATED_CORP_CODE
        defaultCrmCustomShouldNotBeFound("corpCode.in=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCorpCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where corpCode is not null
        defaultCrmCustomShouldBeFound("corpCode.specified=true");

        // Get all the crmCustomList where corpCode is null
        defaultCrmCustomShouldNotBeFound("corpCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCrmCustomsByCorpCodeContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where corpCode contains DEFAULT_CORP_CODE
        defaultCrmCustomShouldBeFound("corpCode.contains=" + DEFAULT_CORP_CODE);

        // Get all the crmCustomList where corpCode contains UPDATED_CORP_CODE
        defaultCrmCustomShouldNotBeFound("corpCode.contains=" + UPDATED_CORP_CODE);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCorpCodeNotContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where corpCode does not contain DEFAULT_CORP_CODE
        defaultCrmCustomShouldNotBeFound("corpCode.doesNotContain=" + DEFAULT_CORP_CODE);

        // Get all the crmCustomList where corpCode does not contain UPDATED_CORP_CODE
        defaultCrmCustomShouldBeFound("corpCode.doesNotContain=" + UPDATED_CORP_CODE);
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByCrmNameIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where crmName equals to DEFAULT_CRM_NAME
        defaultCrmCustomShouldBeFound("crmName.equals=" + DEFAULT_CRM_NAME);

        // Get all the crmCustomList where crmName equals to UPDATED_CRM_NAME
        defaultCrmCustomShouldNotBeFound("crmName.equals=" + UPDATED_CRM_NAME);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCrmNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where crmName not equals to DEFAULT_CRM_NAME
        defaultCrmCustomShouldNotBeFound("crmName.notEquals=" + DEFAULT_CRM_NAME);

        // Get all the crmCustomList where crmName not equals to UPDATED_CRM_NAME
        defaultCrmCustomShouldBeFound("crmName.notEquals=" + UPDATED_CRM_NAME);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCrmNameIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where crmName in DEFAULT_CRM_NAME or UPDATED_CRM_NAME
        defaultCrmCustomShouldBeFound("crmName.in=" + DEFAULT_CRM_NAME + "," + UPDATED_CRM_NAME);

        // Get all the crmCustomList where crmName equals to UPDATED_CRM_NAME
        defaultCrmCustomShouldNotBeFound("crmName.in=" + UPDATED_CRM_NAME);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCrmNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where crmName is not null
        defaultCrmCustomShouldBeFound("crmName.specified=true");

        // Get all the crmCustomList where crmName is null
        defaultCrmCustomShouldNotBeFound("crmName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCrmCustomsByCrmNameContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where crmName contains DEFAULT_CRM_NAME
        defaultCrmCustomShouldBeFound("crmName.contains=" + DEFAULT_CRM_NAME);

        // Get all the crmCustomList where crmName contains UPDATED_CRM_NAME
        defaultCrmCustomShouldNotBeFound("crmName.contains=" + UPDATED_CRM_NAME);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCrmNameNotContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where crmName does not contain DEFAULT_CRM_NAME
        defaultCrmCustomShouldNotBeFound("crmName.doesNotContain=" + DEFAULT_CRM_NAME);

        // Get all the crmCustomList where crmName does not contain UPDATED_CRM_NAME
        defaultCrmCustomShouldBeFound("crmName.doesNotContain=" + UPDATED_CRM_NAME);
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByPhoneNumIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where phoneNum equals to DEFAULT_PHONE_NUM
        defaultCrmCustomShouldBeFound("phoneNum.equals=" + DEFAULT_PHONE_NUM);

        // Get all the crmCustomList where phoneNum equals to UPDATED_PHONE_NUM
        defaultCrmCustomShouldNotBeFound("phoneNum.equals=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByPhoneNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where phoneNum not equals to DEFAULT_PHONE_NUM
        defaultCrmCustomShouldNotBeFound("phoneNum.notEquals=" + DEFAULT_PHONE_NUM);

        // Get all the crmCustomList where phoneNum not equals to UPDATED_PHONE_NUM
        defaultCrmCustomShouldBeFound("phoneNum.notEquals=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByPhoneNumIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where phoneNum in DEFAULT_PHONE_NUM or UPDATED_PHONE_NUM
        defaultCrmCustomShouldBeFound("phoneNum.in=" + DEFAULT_PHONE_NUM + "," + UPDATED_PHONE_NUM);

        // Get all the crmCustomList where phoneNum equals to UPDATED_PHONE_NUM
        defaultCrmCustomShouldNotBeFound("phoneNum.in=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByPhoneNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where phoneNum is not null
        defaultCrmCustomShouldBeFound("phoneNum.specified=true");

        // Get all the crmCustomList where phoneNum is null
        defaultCrmCustomShouldNotBeFound("phoneNum.specified=false");
    }
                @Test
    @Transactional
    public void getAllCrmCustomsByPhoneNumContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where phoneNum contains DEFAULT_PHONE_NUM
        defaultCrmCustomShouldBeFound("phoneNum.contains=" + DEFAULT_PHONE_NUM);

        // Get all the crmCustomList where phoneNum contains UPDATED_PHONE_NUM
        defaultCrmCustomShouldNotBeFound("phoneNum.contains=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByPhoneNumNotContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where phoneNum does not contain DEFAULT_PHONE_NUM
        defaultCrmCustomShouldNotBeFound("phoneNum.doesNotContain=" + DEFAULT_PHONE_NUM);

        // Get all the crmCustomList where phoneNum does not contain UPDATED_PHONE_NUM
        defaultCrmCustomShouldBeFound("phoneNum.doesNotContain=" + UPDATED_PHONE_NUM);
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByFiveDayfreeYnIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where fiveDayfreeYn equals to DEFAULT_FIVE_DAYFREE_YN
        defaultCrmCustomShouldBeFound("fiveDayfreeYn.equals=" + DEFAULT_FIVE_DAYFREE_YN);

        // Get all the crmCustomList where fiveDayfreeYn equals to UPDATED_FIVE_DAYFREE_YN
        defaultCrmCustomShouldNotBeFound("fiveDayfreeYn.equals=" + UPDATED_FIVE_DAYFREE_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByFiveDayfreeYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where fiveDayfreeYn not equals to DEFAULT_FIVE_DAYFREE_YN
        defaultCrmCustomShouldNotBeFound("fiveDayfreeYn.notEquals=" + DEFAULT_FIVE_DAYFREE_YN);

        // Get all the crmCustomList where fiveDayfreeYn not equals to UPDATED_FIVE_DAYFREE_YN
        defaultCrmCustomShouldBeFound("fiveDayfreeYn.notEquals=" + UPDATED_FIVE_DAYFREE_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByFiveDayfreeYnIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where fiveDayfreeYn in DEFAULT_FIVE_DAYFREE_YN or UPDATED_FIVE_DAYFREE_YN
        defaultCrmCustomShouldBeFound("fiveDayfreeYn.in=" + DEFAULT_FIVE_DAYFREE_YN + "," + UPDATED_FIVE_DAYFREE_YN);

        // Get all the crmCustomList where fiveDayfreeYn equals to UPDATED_FIVE_DAYFREE_YN
        defaultCrmCustomShouldNotBeFound("fiveDayfreeYn.in=" + UPDATED_FIVE_DAYFREE_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByFiveDayfreeYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where fiveDayfreeYn is not null
        defaultCrmCustomShouldBeFound("fiveDayfreeYn.specified=true");

        // Get all the crmCustomList where fiveDayfreeYn is null
        defaultCrmCustomShouldNotBeFound("fiveDayfreeYn.specified=false");
    }
                @Test
    @Transactional
    public void getAllCrmCustomsByFiveDayfreeYnContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where fiveDayfreeYn contains DEFAULT_FIVE_DAYFREE_YN
        defaultCrmCustomShouldBeFound("fiveDayfreeYn.contains=" + DEFAULT_FIVE_DAYFREE_YN);

        // Get all the crmCustomList where fiveDayfreeYn contains UPDATED_FIVE_DAYFREE_YN
        defaultCrmCustomShouldNotBeFound("fiveDayfreeYn.contains=" + UPDATED_FIVE_DAYFREE_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByFiveDayfreeYnNotContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where fiveDayfreeYn does not contain DEFAULT_FIVE_DAYFREE_YN
        defaultCrmCustomShouldNotBeFound("fiveDayfreeYn.doesNotContain=" + DEFAULT_FIVE_DAYFREE_YN);

        // Get all the crmCustomList where fiveDayfreeYn does not contain UPDATED_FIVE_DAYFREE_YN
        defaultCrmCustomShouldBeFound("fiveDayfreeYn.doesNotContain=" + UPDATED_FIVE_DAYFREE_YN);
    }


    @Test
    @Transactional
    public void getAllCrmCustomsBySalesStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where salesStatus equals to DEFAULT_SALES_STATUS
        defaultCrmCustomShouldBeFound("salesStatus.equals=" + DEFAULT_SALES_STATUS);

        // Get all the crmCustomList where salesStatus equals to UPDATED_SALES_STATUS
        defaultCrmCustomShouldNotBeFound("salesStatus.equals=" + UPDATED_SALES_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsBySalesStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where salesStatus not equals to DEFAULT_SALES_STATUS
        defaultCrmCustomShouldNotBeFound("salesStatus.notEquals=" + DEFAULT_SALES_STATUS);

        // Get all the crmCustomList where salesStatus not equals to UPDATED_SALES_STATUS
        defaultCrmCustomShouldBeFound("salesStatus.notEquals=" + UPDATED_SALES_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsBySalesStatusIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where salesStatus in DEFAULT_SALES_STATUS or UPDATED_SALES_STATUS
        defaultCrmCustomShouldBeFound("salesStatus.in=" + DEFAULT_SALES_STATUS + "," + UPDATED_SALES_STATUS);

        // Get all the crmCustomList where salesStatus equals to UPDATED_SALES_STATUS
        defaultCrmCustomShouldNotBeFound("salesStatus.in=" + UPDATED_SALES_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsBySalesStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where salesStatus is not null
        defaultCrmCustomShouldBeFound("salesStatus.specified=true");

        // Get all the crmCustomList where salesStatus is null
        defaultCrmCustomShouldNotBeFound("salesStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllCrmCustomsBySmsReceptionYnIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where smsReceptionYn equals to DEFAULT_SMS_RECEPTION_YN
        defaultCrmCustomShouldBeFound("smsReceptionYn.equals=" + DEFAULT_SMS_RECEPTION_YN);

        // Get all the crmCustomList where smsReceptionYn equals to UPDATED_SMS_RECEPTION_YN
        defaultCrmCustomShouldNotBeFound("smsReceptionYn.equals=" + UPDATED_SMS_RECEPTION_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsBySmsReceptionYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where smsReceptionYn not equals to DEFAULT_SMS_RECEPTION_YN
        defaultCrmCustomShouldNotBeFound("smsReceptionYn.notEquals=" + DEFAULT_SMS_RECEPTION_YN);

        // Get all the crmCustomList where smsReceptionYn not equals to UPDATED_SMS_RECEPTION_YN
        defaultCrmCustomShouldBeFound("smsReceptionYn.notEquals=" + UPDATED_SMS_RECEPTION_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsBySmsReceptionYnIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where smsReceptionYn in DEFAULT_SMS_RECEPTION_YN or UPDATED_SMS_RECEPTION_YN
        defaultCrmCustomShouldBeFound("smsReceptionYn.in=" + DEFAULT_SMS_RECEPTION_YN + "," + UPDATED_SMS_RECEPTION_YN);

        // Get all the crmCustomList where smsReceptionYn equals to UPDATED_SMS_RECEPTION_YN
        defaultCrmCustomShouldNotBeFound("smsReceptionYn.in=" + UPDATED_SMS_RECEPTION_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsBySmsReceptionYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where smsReceptionYn is not null
        defaultCrmCustomShouldBeFound("smsReceptionYn.specified=true");

        // Get all the crmCustomList where smsReceptionYn is null
        defaultCrmCustomShouldNotBeFound("smsReceptionYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCallStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where callStatus equals to DEFAULT_CALL_STATUS
        defaultCrmCustomShouldBeFound("callStatus.equals=" + DEFAULT_CALL_STATUS);

        // Get all the crmCustomList where callStatus equals to UPDATED_CALL_STATUS
        defaultCrmCustomShouldNotBeFound("callStatus.equals=" + UPDATED_CALL_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCallStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where callStatus not equals to DEFAULT_CALL_STATUS
        defaultCrmCustomShouldNotBeFound("callStatus.notEquals=" + DEFAULT_CALL_STATUS);

        // Get all the crmCustomList where callStatus not equals to UPDATED_CALL_STATUS
        defaultCrmCustomShouldBeFound("callStatus.notEquals=" + UPDATED_CALL_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCallStatusIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where callStatus in DEFAULT_CALL_STATUS or UPDATED_CALL_STATUS
        defaultCrmCustomShouldBeFound("callStatus.in=" + DEFAULT_CALL_STATUS + "," + UPDATED_CALL_STATUS);

        // Get all the crmCustomList where callStatus equals to UPDATED_CALL_STATUS
        defaultCrmCustomShouldNotBeFound("callStatus.in=" + UPDATED_CALL_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCallStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where callStatus is not null
        defaultCrmCustomShouldBeFound("callStatus.specified=true");

        // Get all the crmCustomList where callStatus is null
        defaultCrmCustomShouldNotBeFound("callStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCustomStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where customStatus equals to DEFAULT_CUSTOM_STATUS
        defaultCrmCustomShouldBeFound("customStatus.equals=" + DEFAULT_CUSTOM_STATUS);

        // Get all the crmCustomList where customStatus equals to UPDATED_CUSTOM_STATUS
        defaultCrmCustomShouldNotBeFound("customStatus.equals=" + UPDATED_CUSTOM_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCustomStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where customStatus not equals to DEFAULT_CUSTOM_STATUS
        defaultCrmCustomShouldNotBeFound("customStatus.notEquals=" + DEFAULT_CUSTOM_STATUS);

        // Get all the crmCustomList where customStatus not equals to UPDATED_CUSTOM_STATUS
        defaultCrmCustomShouldBeFound("customStatus.notEquals=" + UPDATED_CUSTOM_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCustomStatusIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where customStatus in DEFAULT_CUSTOM_STATUS or UPDATED_CUSTOM_STATUS
        defaultCrmCustomShouldBeFound("customStatus.in=" + DEFAULT_CUSTOM_STATUS + "," + UPDATED_CUSTOM_STATUS);

        // Get all the crmCustomList where customStatus equals to UPDATED_CUSTOM_STATUS
        defaultCrmCustomShouldNotBeFound("customStatus.in=" + UPDATED_CUSTOM_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByCustomStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where customStatus is not null
        defaultCrmCustomShouldBeFound("customStatus.specified=true");

        // Get all the crmCustomList where customStatus is null
        defaultCrmCustomShouldNotBeFound("customStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByTempOneStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempOneStatus equals to DEFAULT_TEMP_ONE_STATUS
        defaultCrmCustomShouldBeFound("tempOneStatus.equals=" + DEFAULT_TEMP_ONE_STATUS);

        // Get all the crmCustomList where tempOneStatus equals to UPDATED_TEMP_ONE_STATUS
        defaultCrmCustomShouldNotBeFound("tempOneStatus.equals=" + UPDATED_TEMP_ONE_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByTempOneStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempOneStatus not equals to DEFAULT_TEMP_ONE_STATUS
        defaultCrmCustomShouldNotBeFound("tempOneStatus.notEquals=" + DEFAULT_TEMP_ONE_STATUS);

        // Get all the crmCustomList where tempOneStatus not equals to UPDATED_TEMP_ONE_STATUS
        defaultCrmCustomShouldBeFound("tempOneStatus.notEquals=" + UPDATED_TEMP_ONE_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByTempOneStatusIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempOneStatus in DEFAULT_TEMP_ONE_STATUS or UPDATED_TEMP_ONE_STATUS
        defaultCrmCustomShouldBeFound("tempOneStatus.in=" + DEFAULT_TEMP_ONE_STATUS + "," + UPDATED_TEMP_ONE_STATUS);

        // Get all the crmCustomList where tempOneStatus equals to UPDATED_TEMP_ONE_STATUS
        defaultCrmCustomShouldNotBeFound("tempOneStatus.in=" + UPDATED_TEMP_ONE_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByTempOneStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempOneStatus is not null
        defaultCrmCustomShouldBeFound("tempOneStatus.specified=true");

        // Get all the crmCustomList where tempOneStatus is null
        defaultCrmCustomShouldNotBeFound("tempOneStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllCrmCustomsByTempOneStatusContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempOneStatus contains DEFAULT_TEMP_ONE_STATUS
        defaultCrmCustomShouldBeFound("tempOneStatus.contains=" + DEFAULT_TEMP_ONE_STATUS);

        // Get all the crmCustomList where tempOneStatus contains UPDATED_TEMP_ONE_STATUS
        defaultCrmCustomShouldNotBeFound("tempOneStatus.contains=" + UPDATED_TEMP_ONE_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByTempOneStatusNotContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempOneStatus does not contain DEFAULT_TEMP_ONE_STATUS
        defaultCrmCustomShouldNotBeFound("tempOneStatus.doesNotContain=" + DEFAULT_TEMP_ONE_STATUS);

        // Get all the crmCustomList where tempOneStatus does not contain UPDATED_TEMP_ONE_STATUS
        defaultCrmCustomShouldBeFound("tempOneStatus.doesNotContain=" + UPDATED_TEMP_ONE_STATUS);
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByTempTwoStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempTwoStatus equals to DEFAULT_TEMP_TWO_STATUS
        defaultCrmCustomShouldBeFound("tempTwoStatus.equals=" + DEFAULT_TEMP_TWO_STATUS);

        // Get all the crmCustomList where tempTwoStatus equals to UPDATED_TEMP_TWO_STATUS
        defaultCrmCustomShouldNotBeFound("tempTwoStatus.equals=" + UPDATED_TEMP_TWO_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByTempTwoStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempTwoStatus not equals to DEFAULT_TEMP_TWO_STATUS
        defaultCrmCustomShouldNotBeFound("tempTwoStatus.notEquals=" + DEFAULT_TEMP_TWO_STATUS);

        // Get all the crmCustomList where tempTwoStatus not equals to UPDATED_TEMP_TWO_STATUS
        defaultCrmCustomShouldBeFound("tempTwoStatus.notEquals=" + UPDATED_TEMP_TWO_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByTempTwoStatusIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempTwoStatus in DEFAULT_TEMP_TWO_STATUS or UPDATED_TEMP_TWO_STATUS
        defaultCrmCustomShouldBeFound("tempTwoStatus.in=" + DEFAULT_TEMP_TWO_STATUS + "," + UPDATED_TEMP_TWO_STATUS);

        // Get all the crmCustomList where tempTwoStatus equals to UPDATED_TEMP_TWO_STATUS
        defaultCrmCustomShouldNotBeFound("tempTwoStatus.in=" + UPDATED_TEMP_TWO_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByTempTwoStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempTwoStatus is not null
        defaultCrmCustomShouldBeFound("tempTwoStatus.specified=true");

        // Get all the crmCustomList where tempTwoStatus is null
        defaultCrmCustomShouldNotBeFound("tempTwoStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllCrmCustomsByTempTwoStatusContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempTwoStatus contains DEFAULT_TEMP_TWO_STATUS
        defaultCrmCustomShouldBeFound("tempTwoStatus.contains=" + DEFAULT_TEMP_TWO_STATUS);

        // Get all the crmCustomList where tempTwoStatus contains UPDATED_TEMP_TWO_STATUS
        defaultCrmCustomShouldNotBeFound("tempTwoStatus.contains=" + UPDATED_TEMP_TWO_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByTempTwoStatusNotContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where tempTwoStatus does not contain DEFAULT_TEMP_TWO_STATUS
        defaultCrmCustomShouldNotBeFound("tempTwoStatus.doesNotContain=" + DEFAULT_TEMP_TWO_STATUS);

        // Get all the crmCustomList where tempTwoStatus does not contain UPDATED_TEMP_TWO_STATUS
        defaultCrmCustomShouldBeFound("tempTwoStatus.doesNotContain=" + UPDATED_TEMP_TWO_STATUS);
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByDbInsertTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where dbInsertType equals to DEFAULT_DB_INSERT_TYPE
        defaultCrmCustomShouldBeFound("dbInsertType.equals=" + DEFAULT_DB_INSERT_TYPE);

        // Get all the crmCustomList where dbInsertType equals to UPDATED_DB_INSERT_TYPE
        defaultCrmCustomShouldNotBeFound("dbInsertType.equals=" + UPDATED_DB_INSERT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByDbInsertTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where dbInsertType not equals to DEFAULT_DB_INSERT_TYPE
        defaultCrmCustomShouldNotBeFound("dbInsertType.notEquals=" + DEFAULT_DB_INSERT_TYPE);

        // Get all the crmCustomList where dbInsertType not equals to UPDATED_DB_INSERT_TYPE
        defaultCrmCustomShouldBeFound("dbInsertType.notEquals=" + UPDATED_DB_INSERT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByDbInsertTypeIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where dbInsertType in DEFAULT_DB_INSERT_TYPE or UPDATED_DB_INSERT_TYPE
        defaultCrmCustomShouldBeFound("dbInsertType.in=" + DEFAULT_DB_INSERT_TYPE + "," + UPDATED_DB_INSERT_TYPE);

        // Get all the crmCustomList where dbInsertType equals to UPDATED_DB_INSERT_TYPE
        defaultCrmCustomShouldNotBeFound("dbInsertType.in=" + UPDATED_DB_INSERT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByDbInsertTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where dbInsertType is not null
        defaultCrmCustomShouldBeFound("dbInsertType.specified=true");

        // Get all the crmCustomList where dbInsertType is null
        defaultCrmCustomShouldNotBeFound("dbInsertType.specified=false");
    }
                @Test
    @Transactional
    public void getAllCrmCustomsByDbInsertTypeContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where dbInsertType contains DEFAULT_DB_INSERT_TYPE
        defaultCrmCustomShouldBeFound("dbInsertType.contains=" + DEFAULT_DB_INSERT_TYPE);

        // Get all the crmCustomList where dbInsertType contains UPDATED_DB_INSERT_TYPE
        defaultCrmCustomShouldNotBeFound("dbInsertType.contains=" + UPDATED_DB_INSERT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByDbInsertTypeNotContainsSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where dbInsertType does not contain DEFAULT_DB_INSERT_TYPE
        defaultCrmCustomShouldNotBeFound("dbInsertType.doesNotContain=" + DEFAULT_DB_INSERT_TYPE);

        // Get all the crmCustomList where dbInsertType does not contain UPDATED_DB_INSERT_TYPE
        defaultCrmCustomShouldBeFound("dbInsertType.doesNotContain=" + UPDATED_DB_INSERT_TYPE);
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByUseYnIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where useYn equals to DEFAULT_USE_YN
        defaultCrmCustomShouldBeFound("useYn.equals=" + DEFAULT_USE_YN);

        // Get all the crmCustomList where useYn equals to UPDATED_USE_YN
        defaultCrmCustomShouldNotBeFound("useYn.equals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByUseYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where useYn not equals to DEFAULT_USE_YN
        defaultCrmCustomShouldNotBeFound("useYn.notEquals=" + DEFAULT_USE_YN);

        // Get all the crmCustomList where useYn not equals to UPDATED_USE_YN
        defaultCrmCustomShouldBeFound("useYn.notEquals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByUseYnIsInShouldWork() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where useYn in DEFAULT_USE_YN or UPDATED_USE_YN
        defaultCrmCustomShouldBeFound("useYn.in=" + DEFAULT_USE_YN + "," + UPDATED_USE_YN);

        // Get all the crmCustomList where useYn equals to UPDATED_USE_YN
        defaultCrmCustomShouldNotBeFound("useYn.in=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByUseYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);

        // Get all the crmCustomList where useYn is not null
        defaultCrmCustomShouldBeFound("useYn.specified=true");

        // Get all the crmCustomList where useYn is null
        defaultCrmCustomShouldNotBeFound("useYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCrmCustomsByMemoHisIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);
        MemoHis memoHis = MemoHisResourceIT.createEntity(em);
        em.persist(memoHis);
        em.flush();
        crmCustom.addMemoHis(memoHis);
        crmCustomRepository.saveAndFlush(crmCustom);
        Long memoHisId = memoHis.getId();

        // Get all the crmCustomList where memoHis equals to memoHisId
        defaultCrmCustomShouldBeFound("memoHisId.equals=" + memoHisId);

        // Get all the crmCustomList where memoHis equals to memoHisId + 1
        defaultCrmCustomShouldNotBeFound("memoHisId.equals=" + (memoHisId + 1));
    }


    @Test
    @Transactional
    public void getAllCrmCustomsBySendSmsHisIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);
        SendSmsHis sendSmsHis = SendSmsHisResourceIT.createEntity(em);
        em.persist(sendSmsHis);
        em.flush();
        crmCustom.addSendSmsHis(sendSmsHis);
        crmCustomRepository.saveAndFlush(crmCustom);
        Long sendSmsHisId = sendSmsHis.getId();

        // Get all the crmCustomList where sendSmsHis equals to sendSmsHisId
        defaultCrmCustomShouldBeFound("sendSmsHisId.equals=" + sendSmsHisId);

        // Get all the crmCustomList where sendSmsHis equals to sendSmsHisId + 1
        defaultCrmCustomShouldNotBeFound("sendSmsHisId.equals=" + (sendSmsHisId + 1));
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByStockContractHisIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);
        StockContractHis stockContractHis = StockContractHisResourceIT.createEntity(em);
        em.persist(stockContractHis);
        em.flush();
        crmCustom.addStockContractHis(stockContractHis);
        crmCustomRepository.saveAndFlush(crmCustom);
        Long stockContractHisId = stockContractHis.getId();

        // Get all the crmCustomList where stockContractHis equals to stockContractHisId
        defaultCrmCustomShouldBeFound("stockContractHisId.equals=" + stockContractHisId);

        // Get all the crmCustomList where stockContractHis equals to stockContractHisId + 1
        defaultCrmCustomShouldNotBeFound("stockContractHisId.equals=" + (stockContractHisId + 1));
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByStockConsultingHisIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);
        StockConsultingHis stockConsultingHis = StockConsultingHisResourceIT.createEntity(em);
        em.persist(stockConsultingHis);
        em.flush();
        crmCustom.addStockConsultingHis(stockConsultingHis);
        crmCustomRepository.saveAndFlush(crmCustom);
        Long stockConsultingHisId = stockConsultingHis.getId();

        // Get all the crmCustomList where stockConsultingHis equals to stockConsultingHisId
        defaultCrmCustomShouldBeFound("stockConsultingHisId.equals=" + stockConsultingHisId);

        // Get all the crmCustomList where stockConsultingHis equals to stockConsultingHisId + 1
        defaultCrmCustomShouldNotBeFound("stockConsultingHisId.equals=" + (stockConsultingHisId + 1));
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);
        Manager manager = ManagerResourceIT.createEntity(em);
        em.persist(manager);
        em.flush();
        crmCustom.setManager(manager);
        crmCustomRepository.saveAndFlush(crmCustom);
        Long managerId = manager.getId();

        // Get all the crmCustomList where manager equals to managerId
        defaultCrmCustomShouldBeFound("managerId.equals=" + managerId);

        // Get all the crmCustomList where manager equals to managerId + 1
        defaultCrmCustomShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }


    @Test
    @Transactional
    public void getAllCrmCustomsByTmManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        crmCustomRepository.saveAndFlush(crmCustom);
        TmManager tmManager = TmManagerResourceIT.createEntity(em);
        em.persist(tmManager);
        em.flush();
        crmCustom.setTmManager(tmManager);
        crmCustomRepository.saveAndFlush(crmCustom);
        Long tmManagerId = tmManager.getId();

        // Get all the crmCustomList where tmManager equals to tmManagerId
        defaultCrmCustomShouldBeFound("tmManagerId.equals=" + tmManagerId);

        // Get all the crmCustomList where tmManager equals to tmManagerId + 1
        defaultCrmCustomShouldNotBeFound("tmManagerId.equals=" + (tmManagerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCrmCustomShouldBeFound(String filter) throws Exception {
        restCrmCustomMockMvc.perform(get("/api/crm-customs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmCustom.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpCode").value(hasItem(DEFAULT_CORP_CODE)))
            .andExpect(jsonPath("$.[*].crmName").value(hasItem(DEFAULT_CRM_NAME)))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].fiveDayfreeYn").value(hasItem(DEFAULT_FIVE_DAYFREE_YN)))
            .andExpect(jsonPath("$.[*].salesStatus").value(hasItem(DEFAULT_SALES_STATUS.toString())))
            .andExpect(jsonPath("$.[*].smsReceptionYn").value(hasItem(DEFAULT_SMS_RECEPTION_YN.toString())))
            .andExpect(jsonPath("$.[*].callStatus").value(hasItem(DEFAULT_CALL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].customStatus").value(hasItem(DEFAULT_CUSTOM_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tempOneStatus").value(hasItem(DEFAULT_TEMP_ONE_STATUS)))
            .andExpect(jsonPath("$.[*].tempTwoStatus").value(hasItem(DEFAULT_TEMP_TWO_STATUS)))
            .andExpect(jsonPath("$.[*].dbInsertType").value(hasItem(DEFAULT_DB_INSERT_TYPE)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));

        // Check, that the count call also returns 1
        restCrmCustomMockMvc.perform(get("/api/crm-customs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCrmCustomShouldNotBeFound(String filter) throws Exception {
        restCrmCustomMockMvc.perform(get("/api/crm-customs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCrmCustomMockMvc.perform(get("/api/crm-customs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCrmCustom() throws Exception {
        // Get the crmCustom
        restCrmCustomMockMvc.perform(get("/api/crm-customs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmCustom() throws Exception {
        // Initialize the database
        crmCustomService.save(crmCustom);

        int databaseSizeBeforeUpdate = crmCustomRepository.findAll().size();

        // Update the crmCustom
        CrmCustom updatedCrmCustom = crmCustomRepository.findById(crmCustom.getId()).get();
        // Disconnect from session so that the updates on updatedCrmCustom are not directly saved in db
        em.detach(updatedCrmCustom);
        updatedCrmCustom
            .corpCode(UPDATED_CORP_CODE)
            .crmName(UPDATED_CRM_NAME)
            .phoneNum(UPDATED_PHONE_NUM)
            .fiveDayfreeYn(UPDATED_FIVE_DAYFREE_YN)
            .salesStatus(UPDATED_SALES_STATUS)
            .smsReceptionYn(UPDATED_SMS_RECEPTION_YN)
            .callStatus(UPDATED_CALL_STATUS)
            .customStatus(UPDATED_CUSTOM_STATUS)
            .tempOneStatus(UPDATED_TEMP_ONE_STATUS)
            .tempTwoStatus(UPDATED_TEMP_TWO_STATUS)
            .dbInsertType(UPDATED_DB_INSERT_TYPE)
            .useYn(UPDATED_USE_YN);

        restCrmCustomMockMvc.perform(put("/api/crm-customs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCrmCustom)))
            .andExpect(status().isOk());

        // Validate the CrmCustom in the database
        List<CrmCustom> crmCustomList = crmCustomRepository.findAll();
        assertThat(crmCustomList).hasSize(databaseSizeBeforeUpdate);
        CrmCustom testCrmCustom = crmCustomList.get(crmCustomList.size() - 1);
        assertThat(testCrmCustom.getCorpCode()).isEqualTo(UPDATED_CORP_CODE);
        assertThat(testCrmCustom.getCrmName()).isEqualTo(UPDATED_CRM_NAME);
        assertThat(testCrmCustom.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
        assertThat(testCrmCustom.getFiveDayfreeYn()).isEqualTo(UPDATED_FIVE_DAYFREE_YN);
        assertThat(testCrmCustom.getSalesStatus()).isEqualTo(UPDATED_SALES_STATUS);
        assertThat(testCrmCustom.getSmsReceptionYn()).isEqualTo(UPDATED_SMS_RECEPTION_YN);
        assertThat(testCrmCustom.getCallStatus()).isEqualTo(UPDATED_CALL_STATUS);
        assertThat(testCrmCustom.getCustomStatus()).isEqualTo(UPDATED_CUSTOM_STATUS);
        assertThat(testCrmCustom.getTempOneStatus()).isEqualTo(UPDATED_TEMP_ONE_STATUS);
        assertThat(testCrmCustom.getTempTwoStatus()).isEqualTo(UPDATED_TEMP_TWO_STATUS);
        assertThat(testCrmCustom.getDbInsertType()).isEqualTo(UPDATED_DB_INSERT_TYPE);
        assertThat(testCrmCustom.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmCustom() throws Exception {
        int databaseSizeBeforeUpdate = crmCustomRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrmCustomMockMvc.perform(put("/api/crm-customs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crmCustom)))
            .andExpect(status().isBadRequest());

        // Validate the CrmCustom in the database
        List<CrmCustom> crmCustomList = crmCustomRepository.findAll();
        assertThat(crmCustomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCrmCustom() throws Exception {
        // Initialize the database
        crmCustomService.save(crmCustom);

        int databaseSizeBeforeDelete = crmCustomRepository.findAll().size();

        // Delete the crmCustom
        restCrmCustomMockMvc.perform(delete("/api/crm-customs/{id}", crmCustom.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CrmCustom> crmCustomList = crmCustomRepository.findAll();
        assertThat(crmCustomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
