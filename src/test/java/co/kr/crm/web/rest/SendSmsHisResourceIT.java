package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.SendSmsHis;
import co.kr.crm.domain.CrmCustom;
import co.kr.crm.repository.SendSmsHisRepository;
import co.kr.crm.service.SendSmsHisService;
import co.kr.crm.service.dto.SendSmsHisCriteria;
import co.kr.crm.service.SendSmsHisQueryService;

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
 * Integration tests for the {@link SendSmsHisResource} REST controller.
 */
@SpringBootTest(classes = CrmMariaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SendSmsHisResourceIT {

    private static final LocalDate DEFAULT_SEND_DTM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SEND_DTM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SEND_DTM = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_FROM_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_FROM_PHONE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_TO_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_TO_PHONE_NUM = "BBBBBBBBBB";

    private static final Yn DEFAULT_USE_YN = Yn.Y;
    private static final Yn UPDATED_USE_YN = Yn.N;

    @Autowired
    private SendSmsHisRepository sendSmsHisRepository;

    @Autowired
    private SendSmsHisService sendSmsHisService;

    @Autowired
    private SendSmsHisQueryService sendSmsHisQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSendSmsHisMockMvc;

    private SendSmsHis sendSmsHis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SendSmsHis createEntity(EntityManager em) {
        SendSmsHis sendSmsHis = new SendSmsHis()
            .sendDtm(DEFAULT_SEND_DTM)
            .fromPhoneNum(DEFAULT_FROM_PHONE_NUM)
            .toPhoneNum(DEFAULT_TO_PHONE_NUM)
            .useYn(DEFAULT_USE_YN);
        return sendSmsHis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SendSmsHis createUpdatedEntity(EntityManager em) {
        SendSmsHis sendSmsHis = new SendSmsHis()
            .sendDtm(UPDATED_SEND_DTM)
            .fromPhoneNum(UPDATED_FROM_PHONE_NUM)
            .toPhoneNum(UPDATED_TO_PHONE_NUM)
            .useYn(UPDATED_USE_YN);
        return sendSmsHis;
    }

    @BeforeEach
    public void initTest() {
        sendSmsHis = createEntity(em);
    }

    @Test
    @Transactional
    public void createSendSmsHis() throws Exception {
        int databaseSizeBeforeCreate = sendSmsHisRepository.findAll().size();
        // Create the SendSmsHis
        restSendSmsHisMockMvc.perform(post("/api/send-sms-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sendSmsHis)))
            .andExpect(status().isCreated());

        // Validate the SendSmsHis in the database
        List<SendSmsHis> sendSmsHisList = sendSmsHisRepository.findAll();
        assertThat(sendSmsHisList).hasSize(databaseSizeBeforeCreate + 1);
        SendSmsHis testSendSmsHis = sendSmsHisList.get(sendSmsHisList.size() - 1);
        assertThat(testSendSmsHis.getSendDtm()).isEqualTo(DEFAULT_SEND_DTM);
        assertThat(testSendSmsHis.getFromPhoneNum()).isEqualTo(DEFAULT_FROM_PHONE_NUM);
        assertThat(testSendSmsHis.getToPhoneNum()).isEqualTo(DEFAULT_TO_PHONE_NUM);
        assertThat(testSendSmsHis.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    public void createSendSmsHisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sendSmsHisRepository.findAll().size();

        // Create the SendSmsHis with an existing ID
        sendSmsHis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSendSmsHisMockMvc.perform(post("/api/send-sms-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sendSmsHis)))
            .andExpect(status().isBadRequest());

        // Validate the SendSmsHis in the database
        List<SendSmsHis> sendSmsHisList = sendSmsHisRepository.findAll();
        assertThat(sendSmsHisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSendSmsHis() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList
        restSendSmsHisMockMvc.perform(get("/api/send-sms-his?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sendSmsHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].sendDtm").value(hasItem(DEFAULT_SEND_DTM.toString())))
            .andExpect(jsonPath("$.[*].fromPhoneNum").value(hasItem(DEFAULT_FROM_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].toPhoneNum").value(hasItem(DEFAULT_TO_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));
    }
    
    @Test
    @Transactional
    public void getSendSmsHis() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get the sendSmsHis
        restSendSmsHisMockMvc.perform(get("/api/send-sms-his/{id}", sendSmsHis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sendSmsHis.getId().intValue()))
            .andExpect(jsonPath("$.sendDtm").value(DEFAULT_SEND_DTM.toString()))
            .andExpect(jsonPath("$.fromPhoneNum").value(DEFAULT_FROM_PHONE_NUM))
            .andExpect(jsonPath("$.toPhoneNum").value(DEFAULT_TO_PHONE_NUM))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.toString()));
    }


    @Test
    @Transactional
    public void getSendSmsHisByIdFiltering() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        Long id = sendSmsHis.getId();

        defaultSendSmsHisShouldBeFound("id.equals=" + id);
        defaultSendSmsHisShouldNotBeFound("id.notEquals=" + id);

        defaultSendSmsHisShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSendSmsHisShouldNotBeFound("id.greaterThan=" + id);

        defaultSendSmsHisShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSendSmsHisShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSendSmsHisBySendDtmIsEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where sendDtm equals to DEFAULT_SEND_DTM
        defaultSendSmsHisShouldBeFound("sendDtm.equals=" + DEFAULT_SEND_DTM);

        // Get all the sendSmsHisList where sendDtm equals to UPDATED_SEND_DTM
        defaultSendSmsHisShouldNotBeFound("sendDtm.equals=" + UPDATED_SEND_DTM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisBySendDtmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where sendDtm not equals to DEFAULT_SEND_DTM
        defaultSendSmsHisShouldNotBeFound("sendDtm.notEquals=" + DEFAULT_SEND_DTM);

        // Get all the sendSmsHisList where sendDtm not equals to UPDATED_SEND_DTM
        defaultSendSmsHisShouldBeFound("sendDtm.notEquals=" + UPDATED_SEND_DTM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisBySendDtmIsInShouldWork() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where sendDtm in DEFAULT_SEND_DTM or UPDATED_SEND_DTM
        defaultSendSmsHisShouldBeFound("sendDtm.in=" + DEFAULT_SEND_DTM + "," + UPDATED_SEND_DTM);

        // Get all the sendSmsHisList where sendDtm equals to UPDATED_SEND_DTM
        defaultSendSmsHisShouldNotBeFound("sendDtm.in=" + UPDATED_SEND_DTM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisBySendDtmIsNullOrNotNull() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where sendDtm is not null
        defaultSendSmsHisShouldBeFound("sendDtm.specified=true");

        // Get all the sendSmsHisList where sendDtm is null
        defaultSendSmsHisShouldNotBeFound("sendDtm.specified=false");
    }

    @Test
    @Transactional
    public void getAllSendSmsHisBySendDtmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where sendDtm is greater than or equal to DEFAULT_SEND_DTM
        defaultSendSmsHisShouldBeFound("sendDtm.greaterThanOrEqual=" + DEFAULT_SEND_DTM);

        // Get all the sendSmsHisList where sendDtm is greater than or equal to UPDATED_SEND_DTM
        defaultSendSmsHisShouldNotBeFound("sendDtm.greaterThanOrEqual=" + UPDATED_SEND_DTM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisBySendDtmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where sendDtm is less than or equal to DEFAULT_SEND_DTM
        defaultSendSmsHisShouldBeFound("sendDtm.lessThanOrEqual=" + DEFAULT_SEND_DTM);

        // Get all the sendSmsHisList where sendDtm is less than or equal to SMALLER_SEND_DTM
        defaultSendSmsHisShouldNotBeFound("sendDtm.lessThanOrEqual=" + SMALLER_SEND_DTM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisBySendDtmIsLessThanSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where sendDtm is less than DEFAULT_SEND_DTM
        defaultSendSmsHisShouldNotBeFound("sendDtm.lessThan=" + DEFAULT_SEND_DTM);

        // Get all the sendSmsHisList where sendDtm is less than UPDATED_SEND_DTM
        defaultSendSmsHisShouldBeFound("sendDtm.lessThan=" + UPDATED_SEND_DTM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisBySendDtmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where sendDtm is greater than DEFAULT_SEND_DTM
        defaultSendSmsHisShouldNotBeFound("sendDtm.greaterThan=" + DEFAULT_SEND_DTM);

        // Get all the sendSmsHisList where sendDtm is greater than SMALLER_SEND_DTM
        defaultSendSmsHisShouldBeFound("sendDtm.greaterThan=" + SMALLER_SEND_DTM);
    }


    @Test
    @Transactional
    public void getAllSendSmsHisByFromPhoneNumIsEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where fromPhoneNum equals to DEFAULT_FROM_PHONE_NUM
        defaultSendSmsHisShouldBeFound("fromPhoneNum.equals=" + DEFAULT_FROM_PHONE_NUM);

        // Get all the sendSmsHisList where fromPhoneNum equals to UPDATED_FROM_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("fromPhoneNum.equals=" + UPDATED_FROM_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByFromPhoneNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where fromPhoneNum not equals to DEFAULT_FROM_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("fromPhoneNum.notEquals=" + DEFAULT_FROM_PHONE_NUM);

        // Get all the sendSmsHisList where fromPhoneNum not equals to UPDATED_FROM_PHONE_NUM
        defaultSendSmsHisShouldBeFound("fromPhoneNum.notEquals=" + UPDATED_FROM_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByFromPhoneNumIsInShouldWork() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where fromPhoneNum in DEFAULT_FROM_PHONE_NUM or UPDATED_FROM_PHONE_NUM
        defaultSendSmsHisShouldBeFound("fromPhoneNum.in=" + DEFAULT_FROM_PHONE_NUM + "," + UPDATED_FROM_PHONE_NUM);

        // Get all the sendSmsHisList where fromPhoneNum equals to UPDATED_FROM_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("fromPhoneNum.in=" + UPDATED_FROM_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByFromPhoneNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where fromPhoneNum is not null
        defaultSendSmsHisShouldBeFound("fromPhoneNum.specified=true");

        // Get all the sendSmsHisList where fromPhoneNum is null
        defaultSendSmsHisShouldNotBeFound("fromPhoneNum.specified=false");
    }
                @Test
    @Transactional
    public void getAllSendSmsHisByFromPhoneNumContainsSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where fromPhoneNum contains DEFAULT_FROM_PHONE_NUM
        defaultSendSmsHisShouldBeFound("fromPhoneNum.contains=" + DEFAULT_FROM_PHONE_NUM);

        // Get all the sendSmsHisList where fromPhoneNum contains UPDATED_FROM_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("fromPhoneNum.contains=" + UPDATED_FROM_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByFromPhoneNumNotContainsSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where fromPhoneNum does not contain DEFAULT_FROM_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("fromPhoneNum.doesNotContain=" + DEFAULT_FROM_PHONE_NUM);

        // Get all the sendSmsHisList where fromPhoneNum does not contain UPDATED_FROM_PHONE_NUM
        defaultSendSmsHisShouldBeFound("fromPhoneNum.doesNotContain=" + UPDATED_FROM_PHONE_NUM);
    }


    @Test
    @Transactional
    public void getAllSendSmsHisByToPhoneNumIsEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where toPhoneNum equals to DEFAULT_TO_PHONE_NUM
        defaultSendSmsHisShouldBeFound("toPhoneNum.equals=" + DEFAULT_TO_PHONE_NUM);

        // Get all the sendSmsHisList where toPhoneNum equals to UPDATED_TO_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("toPhoneNum.equals=" + UPDATED_TO_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByToPhoneNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where toPhoneNum not equals to DEFAULT_TO_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("toPhoneNum.notEquals=" + DEFAULT_TO_PHONE_NUM);

        // Get all the sendSmsHisList where toPhoneNum not equals to UPDATED_TO_PHONE_NUM
        defaultSendSmsHisShouldBeFound("toPhoneNum.notEquals=" + UPDATED_TO_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByToPhoneNumIsInShouldWork() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where toPhoneNum in DEFAULT_TO_PHONE_NUM or UPDATED_TO_PHONE_NUM
        defaultSendSmsHisShouldBeFound("toPhoneNum.in=" + DEFAULT_TO_PHONE_NUM + "," + UPDATED_TO_PHONE_NUM);

        // Get all the sendSmsHisList where toPhoneNum equals to UPDATED_TO_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("toPhoneNum.in=" + UPDATED_TO_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByToPhoneNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where toPhoneNum is not null
        defaultSendSmsHisShouldBeFound("toPhoneNum.specified=true");

        // Get all the sendSmsHisList where toPhoneNum is null
        defaultSendSmsHisShouldNotBeFound("toPhoneNum.specified=false");
    }
                @Test
    @Transactional
    public void getAllSendSmsHisByToPhoneNumContainsSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where toPhoneNum contains DEFAULT_TO_PHONE_NUM
        defaultSendSmsHisShouldBeFound("toPhoneNum.contains=" + DEFAULT_TO_PHONE_NUM);

        // Get all the sendSmsHisList where toPhoneNum contains UPDATED_TO_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("toPhoneNum.contains=" + UPDATED_TO_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByToPhoneNumNotContainsSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where toPhoneNum does not contain DEFAULT_TO_PHONE_NUM
        defaultSendSmsHisShouldNotBeFound("toPhoneNum.doesNotContain=" + DEFAULT_TO_PHONE_NUM);

        // Get all the sendSmsHisList where toPhoneNum does not contain UPDATED_TO_PHONE_NUM
        defaultSendSmsHisShouldBeFound("toPhoneNum.doesNotContain=" + UPDATED_TO_PHONE_NUM);
    }


    @Test
    @Transactional
    public void getAllSendSmsHisByUseYnIsEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where useYn equals to DEFAULT_USE_YN
        defaultSendSmsHisShouldBeFound("useYn.equals=" + DEFAULT_USE_YN);

        // Get all the sendSmsHisList where useYn equals to UPDATED_USE_YN
        defaultSendSmsHisShouldNotBeFound("useYn.equals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByUseYnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where useYn not equals to DEFAULT_USE_YN
        defaultSendSmsHisShouldNotBeFound("useYn.notEquals=" + DEFAULT_USE_YN);

        // Get all the sendSmsHisList where useYn not equals to UPDATED_USE_YN
        defaultSendSmsHisShouldBeFound("useYn.notEquals=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByUseYnIsInShouldWork() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where useYn in DEFAULT_USE_YN or UPDATED_USE_YN
        defaultSendSmsHisShouldBeFound("useYn.in=" + DEFAULT_USE_YN + "," + UPDATED_USE_YN);

        // Get all the sendSmsHisList where useYn equals to UPDATED_USE_YN
        defaultSendSmsHisShouldNotBeFound("useYn.in=" + UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByUseYnIsNullOrNotNull() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);

        // Get all the sendSmsHisList where useYn is not null
        defaultSendSmsHisShouldBeFound("useYn.specified=true");

        // Get all the sendSmsHisList where useYn is null
        defaultSendSmsHisShouldNotBeFound("useYn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSendSmsHisByCrmCustomIsEqualToSomething() throws Exception {
        // Initialize the database
        sendSmsHisRepository.saveAndFlush(sendSmsHis);
        CrmCustom crmCustom = CrmCustomResourceIT.createEntity(em);
        em.persist(crmCustom);
        em.flush();
        sendSmsHis.setCrmCustom(crmCustom);
        sendSmsHisRepository.saveAndFlush(sendSmsHis);
        Long crmCustomId = crmCustom.getId();

        // Get all the sendSmsHisList where crmCustom equals to crmCustomId
        defaultSendSmsHisShouldBeFound("crmCustomId.equals=" + crmCustomId);

        // Get all the sendSmsHisList where crmCustom equals to crmCustomId + 1
        defaultSendSmsHisShouldNotBeFound("crmCustomId.equals=" + (crmCustomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSendSmsHisShouldBeFound(String filter) throws Exception {
        restSendSmsHisMockMvc.perform(get("/api/send-sms-his?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sendSmsHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].sendDtm").value(hasItem(DEFAULT_SEND_DTM.toString())))
            .andExpect(jsonPath("$.[*].fromPhoneNum").value(hasItem(DEFAULT_FROM_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].toPhoneNum").value(hasItem(DEFAULT_TO_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.toString())));

        // Check, that the count call also returns 1
        restSendSmsHisMockMvc.perform(get("/api/send-sms-his/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSendSmsHisShouldNotBeFound(String filter) throws Exception {
        restSendSmsHisMockMvc.perform(get("/api/send-sms-his?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSendSmsHisMockMvc.perform(get("/api/send-sms-his/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSendSmsHis() throws Exception {
        // Get the sendSmsHis
        restSendSmsHisMockMvc.perform(get("/api/send-sms-his/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSendSmsHis() throws Exception {
        // Initialize the database
        sendSmsHisService.save(sendSmsHis);

        int databaseSizeBeforeUpdate = sendSmsHisRepository.findAll().size();

        // Update the sendSmsHis
        SendSmsHis updatedSendSmsHis = sendSmsHisRepository.findById(sendSmsHis.getId()).get();
        // Disconnect from session so that the updates on updatedSendSmsHis are not directly saved in db
        em.detach(updatedSendSmsHis);
        updatedSendSmsHis
            .sendDtm(UPDATED_SEND_DTM)
            .fromPhoneNum(UPDATED_FROM_PHONE_NUM)
            .toPhoneNum(UPDATED_TO_PHONE_NUM)
            .useYn(UPDATED_USE_YN);

        restSendSmsHisMockMvc.perform(put("/api/send-sms-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSendSmsHis)))
            .andExpect(status().isOk());

        // Validate the SendSmsHis in the database
        List<SendSmsHis> sendSmsHisList = sendSmsHisRepository.findAll();
        assertThat(sendSmsHisList).hasSize(databaseSizeBeforeUpdate);
        SendSmsHis testSendSmsHis = sendSmsHisList.get(sendSmsHisList.size() - 1);
        assertThat(testSendSmsHis.getSendDtm()).isEqualTo(UPDATED_SEND_DTM);
        assertThat(testSendSmsHis.getFromPhoneNum()).isEqualTo(UPDATED_FROM_PHONE_NUM);
        assertThat(testSendSmsHis.getToPhoneNum()).isEqualTo(UPDATED_TO_PHONE_NUM);
        assertThat(testSendSmsHis.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingSendSmsHis() throws Exception {
        int databaseSizeBeforeUpdate = sendSmsHisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSendSmsHisMockMvc.perform(put("/api/send-sms-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sendSmsHis)))
            .andExpect(status().isBadRequest());

        // Validate the SendSmsHis in the database
        List<SendSmsHis> sendSmsHisList = sendSmsHisRepository.findAll();
        assertThat(sendSmsHisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSendSmsHis() throws Exception {
        // Initialize the database
        sendSmsHisService.save(sendSmsHis);

        int databaseSizeBeforeDelete = sendSmsHisRepository.findAll().size();

        // Delete the sendSmsHis
        restSendSmsHisMockMvc.perform(delete("/api/send-sms-his/{id}", sendSmsHis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SendSmsHis> sendSmsHisList = sendSmsHisRepository.findAll();
        assertThat(sendSmsHisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
