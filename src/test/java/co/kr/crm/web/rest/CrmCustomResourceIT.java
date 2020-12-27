package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.CrmCustom;
import co.kr.crm.repository.CrmCustomRepository;
import co.kr.crm.service.CrmCustomService;

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
