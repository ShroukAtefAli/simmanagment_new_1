package com.hypercell.orange.simmanagement.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.orange.simmanagement.IntegrationTest;
import com.hypercell.orange.simmanagement.domain.Dial;
import com.hypercell.orange.simmanagement.repository.DialRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DialResourceIT {

    private static final UUID DEFAULT_DIAL_ID = UUID.randomUUID();
    private static final UUID UPDATED_DIAL_ID = UUID.randomUUID();

    private static final String DEFAULT_DIAL = "AAAAAAAAAA";
    private static final String UPDATED_DIAL = "BBBBBBBBBB";

    private static final Long DEFAULT_ACTIVE_ALERT_ID = 1L;
    private static final Long UPDATED_ACTIVE_ALERT_ID = 2L;

    private static final Long DEFAULT_DIAL_CONSUMPTION = 1L;
    private static final Long UPDATED_DIAL_CONSUMPTION = 2L;

    private static final Long DEFAULT_BUCKET_ID = 1L;
    private static final Long UPDATED_BUCKET_ID = 2L;

    private static final LocalDate DEFAULT_BUCKETDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUCKETDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTRACT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CONTRACT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTRACT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SIM_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SIM_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_VOL_STATUS_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_VOL_STATUS_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_APN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SOFT_DISCONNECT = "AAAAAAAAAA";
    private static final String UPDATED_SOFT_DISCONNECT = "BBBBBBBBBB";

    private static final Integer DEFAULT_BILL_CYCLE = 1;
    private static final Integer UPDATED_BILL_CYCLE = 2;

    private static final String DEFAULT_M_2_M_MONITORING_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_M_2_M_MONITORING_SERVICE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DialRepository dialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDialMockMvc;

    private Dial dial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dial createEntity(EntityManager em) {
        Dial dial = new Dial()
            .dialId(DEFAULT_DIAL_ID)
            .dial(DEFAULT_DIAL)
            .activeAlertId(DEFAULT_ACTIVE_ALERT_ID)
            .dialConsumption(DEFAULT_DIAL_CONSUMPTION)
            .bucketId(DEFAULT_BUCKET_ID)
            .bucketdate(DEFAULT_BUCKETDATE)
            .contractStatus(DEFAULT_CONTRACT_STATUS)
            .contractDate(DEFAULT_CONTRACT_DATE)
            .simNum(DEFAULT_SIM_NUM)
            .volStatusFlag(DEFAULT_VOL_STATUS_FLAG)
            .apnName(DEFAULT_APN_NAME)
            .softDisconnect(DEFAULT_SOFT_DISCONNECT)
            .billCycle(DEFAULT_BILL_CYCLE)
            .m2mMonitoringService(DEFAULT_M_2_M_MONITORING_SERVICE);
        return dial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dial createUpdatedEntity(EntityManager em) {
        Dial dial = new Dial()
            .dialId(UPDATED_DIAL_ID)
            .dial(UPDATED_DIAL)
            .activeAlertId(UPDATED_ACTIVE_ALERT_ID)
            .dialConsumption(UPDATED_DIAL_CONSUMPTION)
            .bucketId(UPDATED_BUCKET_ID)
            .bucketdate(UPDATED_BUCKETDATE)
            .contractStatus(UPDATED_CONTRACT_STATUS)
            .contractDate(UPDATED_CONTRACT_DATE)
            .simNum(UPDATED_SIM_NUM)
            .volStatusFlag(UPDATED_VOL_STATUS_FLAG)
            .apnName(UPDATED_APN_NAME)
            .softDisconnect(UPDATED_SOFT_DISCONNECT)
            .billCycle(UPDATED_BILL_CYCLE)
            .m2mMonitoringService(UPDATED_M_2_M_MONITORING_SERVICE);
        return dial;
    }

    @BeforeEach
    public void initTest() {
        dial = createEntity(em);
    }

    @Test
    @Transactional
    void createDial() throws Exception {
        int databaseSizeBeforeCreate = dialRepository.findAll().size();
        // Create the Dial
        restDialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dial)))
            .andExpect(status().isCreated());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeCreate + 1);
        Dial testDial = dialList.get(dialList.size() - 1);
        assertThat(testDial.getDialId()).isEqualTo(DEFAULT_DIAL_ID);
        assertThat(testDial.getDial()).isEqualTo(DEFAULT_DIAL);
        assertThat(testDial.getActiveAlertId()).isEqualTo(DEFAULT_ACTIVE_ALERT_ID);
        assertThat(testDial.getDialConsumption()).isEqualTo(DEFAULT_DIAL_CONSUMPTION);
        assertThat(testDial.getBucketId()).isEqualTo(DEFAULT_BUCKET_ID);
        assertThat(testDial.getBucketdate()).isEqualTo(DEFAULT_BUCKETDATE);
        assertThat(testDial.getContractStatus()).isEqualTo(DEFAULT_CONTRACT_STATUS);
        assertThat(testDial.getContractDate()).isEqualTo(DEFAULT_CONTRACT_DATE);
        assertThat(testDial.getSimNum()).isEqualTo(DEFAULT_SIM_NUM);
        assertThat(testDial.getVolStatusFlag()).isEqualTo(DEFAULT_VOL_STATUS_FLAG);
        assertThat(testDial.getApnName()).isEqualTo(DEFAULT_APN_NAME);
        assertThat(testDial.getSoftDisconnect()).isEqualTo(DEFAULT_SOFT_DISCONNECT);
        assertThat(testDial.getBillCycle()).isEqualTo(DEFAULT_BILL_CYCLE);
        assertThat(testDial.getm2mMonitoringService()).isEqualTo(DEFAULT_M_2_M_MONITORING_SERVICE);
    }

    @Test
    @Transactional
    void createDialWithExistingId() throws Exception {
        // Create the Dial with an existing ID
        dial.setId(1L);

        int databaseSizeBeforeCreate = dialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dial)))
            .andExpect(status().isBadRequest());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDialIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialRepository.findAll().size();
        // set the field null
        dial.setDialId(null);

        // Create the Dial, which fails.

        restDialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dial)))
            .andExpect(status().isBadRequest());

        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDialIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialRepository.findAll().size();
        // set the field null
        dial.setDial(null);

        // Create the Dial, which fails.

        restDialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dial)))
            .andExpect(status().isBadRequest());

        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDials() throws Exception {
        // Initialize the database
        dialRepository.saveAndFlush(dial);

        // Get all the dialList
        restDialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dial.getId().intValue())))
            .andExpect(jsonPath("$.[*].dialId").value(hasItem(DEFAULT_DIAL_ID.toString())))
            .andExpect(jsonPath("$.[*].dial").value(hasItem(DEFAULT_DIAL)))
            .andExpect(jsonPath("$.[*].activeAlertId").value(hasItem(DEFAULT_ACTIVE_ALERT_ID.intValue())))
            .andExpect(jsonPath("$.[*].dialConsumption").value(hasItem(DEFAULT_DIAL_CONSUMPTION.intValue())))
            .andExpect(jsonPath("$.[*].bucketId").value(hasItem(DEFAULT_BUCKET_ID.intValue())))
            .andExpect(jsonPath("$.[*].bucketdate").value(hasItem(DEFAULT_BUCKETDATE.toString())))
            .andExpect(jsonPath("$.[*].contractStatus").value(hasItem(DEFAULT_CONTRACT_STATUS)))
            .andExpect(jsonPath("$.[*].contractDate").value(hasItem(DEFAULT_CONTRACT_DATE.toString())))
            .andExpect(jsonPath("$.[*].simNum").value(hasItem(DEFAULT_SIM_NUM)))
            .andExpect(jsonPath("$.[*].volStatusFlag").value(hasItem(DEFAULT_VOL_STATUS_FLAG)))
            .andExpect(jsonPath("$.[*].apnName").value(hasItem(DEFAULT_APN_NAME)))
            .andExpect(jsonPath("$.[*].softDisconnect").value(hasItem(DEFAULT_SOFT_DISCONNECT)))
            .andExpect(jsonPath("$.[*].billCycle").value(hasItem(DEFAULT_BILL_CYCLE)))
            .andExpect(jsonPath("$.[*].m2mMonitoringService").value(hasItem(DEFAULT_M_2_M_MONITORING_SERVICE)));
    }

    @Test
    @Transactional
    void getDial() throws Exception {
        // Initialize the database
        dialRepository.saveAndFlush(dial);

        // Get the dial
        restDialMockMvc
            .perform(get(ENTITY_API_URL_ID, dial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dial.getId().intValue()))
            .andExpect(jsonPath("$.dialId").value(DEFAULT_DIAL_ID.toString()))
            .andExpect(jsonPath("$.dial").value(DEFAULT_DIAL))
            .andExpect(jsonPath("$.activeAlertId").value(DEFAULT_ACTIVE_ALERT_ID.intValue()))
            .andExpect(jsonPath("$.dialConsumption").value(DEFAULT_DIAL_CONSUMPTION.intValue()))
            .andExpect(jsonPath("$.bucketId").value(DEFAULT_BUCKET_ID.intValue()))
            .andExpect(jsonPath("$.bucketdate").value(DEFAULT_BUCKETDATE.toString()))
            .andExpect(jsonPath("$.contractStatus").value(DEFAULT_CONTRACT_STATUS))
            .andExpect(jsonPath("$.contractDate").value(DEFAULT_CONTRACT_DATE.toString()))
            .andExpect(jsonPath("$.simNum").value(DEFAULT_SIM_NUM))
            .andExpect(jsonPath("$.volStatusFlag").value(DEFAULT_VOL_STATUS_FLAG))
            .andExpect(jsonPath("$.apnName").value(DEFAULT_APN_NAME))
            .andExpect(jsonPath("$.softDisconnect").value(DEFAULT_SOFT_DISCONNECT))
            .andExpect(jsonPath("$.billCycle").value(DEFAULT_BILL_CYCLE))
            .andExpect(jsonPath("$.m2mMonitoringService").value(DEFAULT_M_2_M_MONITORING_SERVICE));
    }

    @Test
    @Transactional
    void getNonExistingDial() throws Exception {
        // Get the dial
        restDialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDial() throws Exception {
        // Initialize the database
        dialRepository.saveAndFlush(dial);

        int databaseSizeBeforeUpdate = dialRepository.findAll().size();

        // Update the dial
        Dial updatedDial = dialRepository.findById(dial.getId()).get();
        // Disconnect from session so that the updates on updatedDial are not directly saved in db
        em.detach(updatedDial);
        updatedDial
            .dialId(UPDATED_DIAL_ID)
            .dial(UPDATED_DIAL)
            .activeAlertId(UPDATED_ACTIVE_ALERT_ID)
            .dialConsumption(UPDATED_DIAL_CONSUMPTION)
            .bucketId(UPDATED_BUCKET_ID)
            .bucketdate(UPDATED_BUCKETDATE)
            .contractStatus(UPDATED_CONTRACT_STATUS)
            .contractDate(UPDATED_CONTRACT_DATE)
            .simNum(UPDATED_SIM_NUM)
            .volStatusFlag(UPDATED_VOL_STATUS_FLAG)
            .apnName(UPDATED_APN_NAME)
            .softDisconnect(UPDATED_SOFT_DISCONNECT)
            .billCycle(UPDATED_BILL_CYCLE)
            .m2mMonitoringService(UPDATED_M_2_M_MONITORING_SERVICE);

        restDialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDial))
            )
            .andExpect(status().isOk());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeUpdate);
        Dial testDial = dialList.get(dialList.size() - 1);
        assertThat(testDial.getDialId()).isEqualTo(UPDATED_DIAL_ID);
        assertThat(testDial.getDial()).isEqualTo(UPDATED_DIAL);
        assertThat(testDial.getActiveAlertId()).isEqualTo(UPDATED_ACTIVE_ALERT_ID);
        assertThat(testDial.getDialConsumption()).isEqualTo(UPDATED_DIAL_CONSUMPTION);
        assertThat(testDial.getBucketId()).isEqualTo(UPDATED_BUCKET_ID);
        assertThat(testDial.getBucketdate()).isEqualTo(UPDATED_BUCKETDATE);
        assertThat(testDial.getContractStatus()).isEqualTo(UPDATED_CONTRACT_STATUS);
        assertThat(testDial.getContractDate()).isEqualTo(UPDATED_CONTRACT_DATE);
        assertThat(testDial.getSimNum()).isEqualTo(UPDATED_SIM_NUM);
        assertThat(testDial.getVolStatusFlag()).isEqualTo(UPDATED_VOL_STATUS_FLAG);
        assertThat(testDial.getApnName()).isEqualTo(UPDATED_APN_NAME);
        assertThat(testDial.getSoftDisconnect()).isEqualTo(UPDATED_SOFT_DISCONNECT);
        assertThat(testDial.getBillCycle()).isEqualTo(UPDATED_BILL_CYCLE);
        assertThat(testDial.getm2mMonitoringService()).isEqualTo(UPDATED_M_2_M_MONITORING_SERVICE);
    }

    @Test
    @Transactional
    void putNonExistingDial() throws Exception {
        int databaseSizeBeforeUpdate = dialRepository.findAll().size();
        dial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dial))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDial() throws Exception {
        int databaseSizeBeforeUpdate = dialRepository.findAll().size();
        dial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dial))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDial() throws Exception {
        int databaseSizeBeforeUpdate = dialRepository.findAll().size();
        dial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDialWithPatch() throws Exception {
        // Initialize the database
        dialRepository.saveAndFlush(dial);

        int databaseSizeBeforeUpdate = dialRepository.findAll().size();

        // Update the dial using partial update
        Dial partialUpdatedDial = new Dial();
        partialUpdatedDial.setId(dial.getId());

        partialUpdatedDial
            .dialId(UPDATED_DIAL_ID)
            .dial(UPDATED_DIAL)
            .activeAlertId(UPDATED_ACTIVE_ALERT_ID)
            .contractStatus(UPDATED_CONTRACT_STATUS)
            .contractDate(UPDATED_CONTRACT_DATE)
            .softDisconnect(UPDATED_SOFT_DISCONNECT)
            .billCycle(UPDATED_BILL_CYCLE);

        restDialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDial))
            )
            .andExpect(status().isOk());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeUpdate);
        Dial testDial = dialList.get(dialList.size() - 1);
        assertThat(testDial.getDialId()).isEqualTo(UPDATED_DIAL_ID);
        assertThat(testDial.getDial()).isEqualTo(UPDATED_DIAL);
        assertThat(testDial.getActiveAlertId()).isEqualTo(UPDATED_ACTIVE_ALERT_ID);
        assertThat(testDial.getDialConsumption()).isEqualTo(DEFAULT_DIAL_CONSUMPTION);
        assertThat(testDial.getBucketId()).isEqualTo(DEFAULT_BUCKET_ID);
        assertThat(testDial.getBucketdate()).isEqualTo(DEFAULT_BUCKETDATE);
        assertThat(testDial.getContractStatus()).isEqualTo(UPDATED_CONTRACT_STATUS);
        assertThat(testDial.getContractDate()).isEqualTo(UPDATED_CONTRACT_DATE);
        assertThat(testDial.getSimNum()).isEqualTo(DEFAULT_SIM_NUM);
        assertThat(testDial.getVolStatusFlag()).isEqualTo(DEFAULT_VOL_STATUS_FLAG);
        assertThat(testDial.getApnName()).isEqualTo(DEFAULT_APN_NAME);
        assertThat(testDial.getSoftDisconnect()).isEqualTo(UPDATED_SOFT_DISCONNECT);
        assertThat(testDial.getBillCycle()).isEqualTo(UPDATED_BILL_CYCLE);
        assertThat(testDial.getm2mMonitoringService()).isEqualTo(DEFAULT_M_2_M_MONITORING_SERVICE);
    }

    @Test
    @Transactional
    void fullUpdateDialWithPatch() throws Exception {
        // Initialize the database
        dialRepository.saveAndFlush(dial);

        int databaseSizeBeforeUpdate = dialRepository.findAll().size();

        // Update the dial using partial update
        Dial partialUpdatedDial = new Dial();
        partialUpdatedDial.setId(dial.getId());

        partialUpdatedDial
            .dialId(UPDATED_DIAL_ID)
            .dial(UPDATED_DIAL)
            .activeAlertId(UPDATED_ACTIVE_ALERT_ID)
            .dialConsumption(UPDATED_DIAL_CONSUMPTION)
            .bucketId(UPDATED_BUCKET_ID)
            .bucketdate(UPDATED_BUCKETDATE)
            .contractStatus(UPDATED_CONTRACT_STATUS)
            .contractDate(UPDATED_CONTRACT_DATE)
            .simNum(UPDATED_SIM_NUM)
            .volStatusFlag(UPDATED_VOL_STATUS_FLAG)
            .apnName(UPDATED_APN_NAME)
            .softDisconnect(UPDATED_SOFT_DISCONNECT)
            .billCycle(UPDATED_BILL_CYCLE)
            .m2mMonitoringService(UPDATED_M_2_M_MONITORING_SERVICE);

        restDialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDial))
            )
            .andExpect(status().isOk());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeUpdate);
        Dial testDial = dialList.get(dialList.size() - 1);
        assertThat(testDial.getDialId()).isEqualTo(UPDATED_DIAL_ID);
        assertThat(testDial.getDial()).isEqualTo(UPDATED_DIAL);
        assertThat(testDial.getActiveAlertId()).isEqualTo(UPDATED_ACTIVE_ALERT_ID);
        assertThat(testDial.getDialConsumption()).isEqualTo(UPDATED_DIAL_CONSUMPTION);
        assertThat(testDial.getBucketId()).isEqualTo(UPDATED_BUCKET_ID);
        assertThat(testDial.getBucketdate()).isEqualTo(UPDATED_BUCKETDATE);
        assertThat(testDial.getContractStatus()).isEqualTo(UPDATED_CONTRACT_STATUS);
        assertThat(testDial.getContractDate()).isEqualTo(UPDATED_CONTRACT_DATE);
        assertThat(testDial.getSimNum()).isEqualTo(UPDATED_SIM_NUM);
        assertThat(testDial.getVolStatusFlag()).isEqualTo(UPDATED_VOL_STATUS_FLAG);
        assertThat(testDial.getApnName()).isEqualTo(UPDATED_APN_NAME);
        assertThat(testDial.getSoftDisconnect()).isEqualTo(UPDATED_SOFT_DISCONNECT);
        assertThat(testDial.getBillCycle()).isEqualTo(UPDATED_BILL_CYCLE);
        assertThat(testDial.getm2mMonitoringService()).isEqualTo(UPDATED_M_2_M_MONITORING_SERVICE);
    }

    @Test
    @Transactional
    void patchNonExistingDial() throws Exception {
        int databaseSizeBeforeUpdate = dialRepository.findAll().size();
        dial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dial))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDial() throws Exception {
        int databaseSizeBeforeUpdate = dialRepository.findAll().size();
        dial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dial))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDial() throws Exception {
        int databaseSizeBeforeUpdate = dialRepository.findAll().size();
        dial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDialMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dial)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dial in the database
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDial() throws Exception {
        // Initialize the database
        dialRepository.saveAndFlush(dial);

        int databaseSizeBeforeDelete = dialRepository.findAll().size();

        // Delete the dial
        restDialMockMvc
            .perform(delete(ENTITY_API_URL_ID, dial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dial> dialList = dialRepository.findAll();
        assertThat(dialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
