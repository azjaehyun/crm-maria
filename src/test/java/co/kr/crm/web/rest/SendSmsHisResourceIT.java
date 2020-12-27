package co.kr.crm.web.rest;

import co.kr.crm.CrmMariaApp;
import co.kr.crm.domain.SendSmsHis;
import co.kr.crm.repository.SendSmsHisRepository;
import co.kr.crm.service.SendSmsHisService;

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
