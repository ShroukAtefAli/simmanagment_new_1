package com.hypercell.orange.simmanagement.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.orange.simmanagement.IntegrationTest;
import com.hypercell.orange.simmanagement.domain.ActiveAlert;
import com.hypercell.orange.simmanagement.domain.enumeration.AlertPeriod;
import com.hypercell.orange.simmanagement.repository.ActiveAlertRepository;
import java.util.List;
import java.util.Random;
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
 * Integration tests for the {@link ActiveAlertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActiveAlertResourceIT {

    private static final Long DEFAULT_ACTIVE_ALERT_ID = 1L;
    private static final Long UPDATED_ACTIVE_ALERT_ID = 2L;

    private static final String DEFAULT_ACTIVE_ALERT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE_ALERT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ACTIVE_ALERT_CAPACITY = 1L;
    private static final Long UPDATED_ACTIVE_ALERT_CAPACITY = 2L;

    private static final Long DEFAULT_ACTIVE_ALERT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_ACTIVE_ALERT_CUSTOMER_ID = 2L;

    private static final Long DEFAULT_ACTIVE_ALERT_DIAL_NUMBER = 1L;
    private static final Long UPDATED_ACTIVE_ALERT_DIAL_NUMBER = 2L;

    private static final String DEFAULT_ACTIVE_ALERT_SMS_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE_ALERT_SMS_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE = true;

    private static final Boolean DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE = true;

    private static final AlertPeriod DEFAULT_ACTIVE_ALERT_PERIOD = AlertPeriod.Daily;
    private static final AlertPeriod UPDATED_ACTIVE_ALERT_PERIOD = AlertPeriod.Weekly;

    private static final String ENTITY_API_URL = "/api/active-alerts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActiveAlertRepository activeAlertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActiveAlertMockMvc;

    private ActiveAlert activeAlert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActiveAlert createEntity(EntityManager em) {
        ActiveAlert activeAlert = new ActiveAlert()
            .activeAlertId(DEFAULT_ACTIVE_ALERT_ID)
            .activeAlertName(DEFAULT_ACTIVE_ALERT_NAME)
            .activeAlertCapacity(DEFAULT_ACTIVE_ALERT_CAPACITY)
            .activeAlertCustomerId(DEFAULT_ACTIVE_ALERT_CUSTOMER_ID)
            .activeAlertDialNumber(DEFAULT_ACTIVE_ALERT_DIAL_NUMBER)
            .activeAlertSmsMessage(DEFAULT_ACTIVE_ALERT_SMS_MESSAGE)
            .activeAlertEmailMessage(DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE)
            .activeAlertSmsMessageActive(DEFAULT_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE)
            .activeAlertEmailMessageActive(DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE)
            .activeAlertPeriod(DEFAULT_ACTIVE_ALERT_PERIOD);
        return activeAlert;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActiveAlert createUpdatedEntity(EntityManager em) {
        ActiveAlert activeAlert = new ActiveAlert()
            .activeAlertId(UPDATED_ACTIVE_ALERT_ID)
            .activeAlertName(UPDATED_ACTIVE_ALERT_NAME)
            .activeAlertCapacity(UPDATED_ACTIVE_ALERT_CAPACITY)
            .activeAlertCustomerId(UPDATED_ACTIVE_ALERT_CUSTOMER_ID)
            .activeAlertDialNumber(UPDATED_ACTIVE_ALERT_DIAL_NUMBER)
            .activeAlertSmsMessage(UPDATED_ACTIVE_ALERT_SMS_MESSAGE)
            .activeAlertEmailMessage(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE)
            .activeAlertSmsMessageActive(UPDATED_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE)
            .activeAlertEmailMessageActive(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE)
            .activeAlertPeriod(UPDATED_ACTIVE_ALERT_PERIOD);
        return activeAlert;
    }

    @BeforeEach
    public void initTest() {
        activeAlert = createEntity(em);
    }

    @Test
    @Transactional
    void createActiveAlert() throws Exception {
        int databaseSizeBeforeCreate = activeAlertRepository.findAll().size();
        // Create the ActiveAlert
        restActiveAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeAlert)))
            .andExpect(status().isCreated());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeCreate + 1);
        ActiveAlert testActiveAlert = activeAlertList.get(activeAlertList.size() - 1);
        assertThat(testActiveAlert.getActiveAlertId()).isEqualTo(DEFAULT_ACTIVE_ALERT_ID);
        assertThat(testActiveAlert.getActiveAlertName()).isEqualTo(DEFAULT_ACTIVE_ALERT_NAME);
        assertThat(testActiveAlert.getActiveAlertCapacity()).isEqualTo(DEFAULT_ACTIVE_ALERT_CAPACITY);
        assertThat(testActiveAlert.getActiveAlertCustomerId()).isEqualTo(DEFAULT_ACTIVE_ALERT_CUSTOMER_ID);
        assertThat(testActiveAlert.getActiveAlertDialNumber()).isEqualTo(DEFAULT_ACTIVE_ALERT_DIAL_NUMBER);
        assertThat(testActiveAlert.getActiveAlertSmsMessage()).isEqualTo(DEFAULT_ACTIVE_ALERT_SMS_MESSAGE);
        assertThat(testActiveAlert.getActiveAlertEmailMessage()).isEqualTo(DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE);
        assertThat(testActiveAlert.getActiveAlertSmsMessageActive()).isEqualTo(DEFAULT_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE);
        assertThat(testActiveAlert.getActiveAlertEmailMessageActive()).isEqualTo(DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE);
        assertThat(testActiveAlert.getActiveAlertPeriod()).isEqualTo(DEFAULT_ACTIVE_ALERT_PERIOD);
    }

    @Test
    @Transactional
    void createActiveAlertWithExistingId() throws Exception {
        // Create the ActiveAlert with an existing ID
        activeAlert.setId(1L);

        int databaseSizeBeforeCreate = activeAlertRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiveAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeAlert)))
            .andExpect(status().isBadRequest());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActiveAlertIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = activeAlertRepository.findAll().size();
        // set the field null
        activeAlert.setActiveAlertId(null);

        // Create the ActiveAlert, which fails.

        restActiveAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeAlert)))
            .andExpect(status().isBadRequest());

        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveAlertNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = activeAlertRepository.findAll().size();
        // set the field null
        activeAlert.setActiveAlertName(null);

        // Create the ActiveAlert, which fails.

        restActiveAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeAlert)))
            .andExpect(status().isBadRequest());

        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveAlertCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = activeAlertRepository.findAll().size();
        // set the field null
        activeAlert.setActiveAlertCapacity(null);

        // Create the ActiveAlert, which fails.

        restActiveAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeAlert)))
            .andExpect(status().isBadRequest());

        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveAlertCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = activeAlertRepository.findAll().size();
        // set the field null
        activeAlert.setActiveAlertCustomerId(null);

        // Create the ActiveAlert, which fails.

        restActiveAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeAlert)))
            .andExpect(status().isBadRequest());

        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveAlertDialNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = activeAlertRepository.findAll().size();
        // set the field null
        activeAlert.setActiveAlertDialNumber(null);

        // Create the ActiveAlert, which fails.

        restActiveAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeAlert)))
            .andExpect(status().isBadRequest());

        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllActiveAlerts() throws Exception {
        // Initialize the database
        activeAlertRepository.saveAndFlush(activeAlert);

        // Get all the activeAlertList
        restActiveAlertMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activeAlert.getId().intValue())))
            .andExpect(jsonPath("$.[*].activeAlertId").value(hasItem(DEFAULT_ACTIVE_ALERT_ID.intValue())))
            .andExpect(jsonPath("$.[*].activeAlertName").value(hasItem(DEFAULT_ACTIVE_ALERT_NAME)))
            .andExpect(jsonPath("$.[*].activeAlertCapacity").value(hasItem(DEFAULT_ACTIVE_ALERT_CAPACITY.intValue())))
            .andExpect(jsonPath("$.[*].activeAlertCustomerId").value(hasItem(DEFAULT_ACTIVE_ALERT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].activeAlertDialNumber").value(hasItem(DEFAULT_ACTIVE_ALERT_DIAL_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].activeAlertSmsMessage").value(hasItem(DEFAULT_ACTIVE_ALERT_SMS_MESSAGE)))
            .andExpect(jsonPath("$.[*].activeAlertEmailMessage").value(hasItem(DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE)))
            .andExpect(jsonPath("$.[*].activeAlertSmsMessageActive").value(hasItem(DEFAULT_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE.booleanValue())))
            .andExpect(
                jsonPath("$.[*].activeAlertEmailMessageActive").value(hasItem(DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].activeAlertPeriod").value(hasItem(DEFAULT_ACTIVE_ALERT_PERIOD.toString())));
    }

    @Test
    @Transactional
    void getActiveAlert() throws Exception {
        // Initialize the database
        activeAlertRepository.saveAndFlush(activeAlert);

        // Get the activeAlert
        restActiveAlertMockMvc
            .perform(get(ENTITY_API_URL_ID, activeAlert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activeAlert.getId().intValue()))
            .andExpect(jsonPath("$.activeAlertId").value(DEFAULT_ACTIVE_ALERT_ID.intValue()))
            .andExpect(jsonPath("$.activeAlertName").value(DEFAULT_ACTIVE_ALERT_NAME))
            .andExpect(jsonPath("$.activeAlertCapacity").value(DEFAULT_ACTIVE_ALERT_CAPACITY.intValue()))
            .andExpect(jsonPath("$.activeAlertCustomerId").value(DEFAULT_ACTIVE_ALERT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.activeAlertDialNumber").value(DEFAULT_ACTIVE_ALERT_DIAL_NUMBER.intValue()))
            .andExpect(jsonPath("$.activeAlertSmsMessage").value(DEFAULT_ACTIVE_ALERT_SMS_MESSAGE))
            .andExpect(jsonPath("$.activeAlertEmailMessage").value(DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE))
            .andExpect(jsonPath("$.activeAlertSmsMessageActive").value(DEFAULT_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.activeAlertEmailMessageActive").value(DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.activeAlertPeriod").value(DEFAULT_ACTIVE_ALERT_PERIOD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingActiveAlert() throws Exception {
        // Get the activeAlert
        restActiveAlertMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewActiveAlert() throws Exception {
        // Initialize the database
        activeAlertRepository.saveAndFlush(activeAlert);

        int databaseSizeBeforeUpdate = activeAlertRepository.findAll().size();

        // Update the activeAlert
        ActiveAlert updatedActiveAlert = activeAlertRepository.findById(activeAlert.getId()).get();
        // Disconnect from session so that the updates on updatedActiveAlert are not directly saved in db
        em.detach(updatedActiveAlert);
        updatedActiveAlert
            .activeAlertId(UPDATED_ACTIVE_ALERT_ID)
            .activeAlertName(UPDATED_ACTIVE_ALERT_NAME)
            .activeAlertCapacity(UPDATED_ACTIVE_ALERT_CAPACITY)
            .activeAlertCustomerId(UPDATED_ACTIVE_ALERT_CUSTOMER_ID)
            .activeAlertDialNumber(UPDATED_ACTIVE_ALERT_DIAL_NUMBER)
            .activeAlertSmsMessage(UPDATED_ACTIVE_ALERT_SMS_MESSAGE)
            .activeAlertEmailMessage(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE)
            .activeAlertSmsMessageActive(UPDATED_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE)
            .activeAlertEmailMessageActive(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE)
            .activeAlertPeriod(UPDATED_ACTIVE_ALERT_PERIOD);

        restActiveAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActiveAlert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActiveAlert))
            )
            .andExpect(status().isOk());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeUpdate);
        ActiveAlert testActiveAlert = activeAlertList.get(activeAlertList.size() - 1);
        assertThat(testActiveAlert.getActiveAlertId()).isEqualTo(UPDATED_ACTIVE_ALERT_ID);
        assertThat(testActiveAlert.getActiveAlertName()).isEqualTo(UPDATED_ACTIVE_ALERT_NAME);
        assertThat(testActiveAlert.getActiveAlertCapacity()).isEqualTo(UPDATED_ACTIVE_ALERT_CAPACITY);
        assertThat(testActiveAlert.getActiveAlertCustomerId()).isEqualTo(UPDATED_ACTIVE_ALERT_CUSTOMER_ID);
        assertThat(testActiveAlert.getActiveAlertDialNumber()).isEqualTo(UPDATED_ACTIVE_ALERT_DIAL_NUMBER);
        assertThat(testActiveAlert.getActiveAlertSmsMessage()).isEqualTo(UPDATED_ACTIVE_ALERT_SMS_MESSAGE);
        assertThat(testActiveAlert.getActiveAlertEmailMessage()).isEqualTo(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE);
        assertThat(testActiveAlert.getActiveAlertSmsMessageActive()).isEqualTo(UPDATED_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE);
        assertThat(testActiveAlert.getActiveAlertEmailMessageActive()).isEqualTo(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE);
        assertThat(testActiveAlert.getActiveAlertPeriod()).isEqualTo(UPDATED_ACTIVE_ALERT_PERIOD);
    }

    @Test
    @Transactional
    void putNonExistingActiveAlert() throws Exception {
        int databaseSizeBeforeUpdate = activeAlertRepository.findAll().size();
        activeAlert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiveAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activeAlert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeAlert))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActiveAlert() throws Exception {
        int databaseSizeBeforeUpdate = activeAlertRepository.findAll().size();
        activeAlert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiveAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeAlert))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActiveAlert() throws Exception {
        int databaseSizeBeforeUpdate = activeAlertRepository.findAll().size();
        activeAlert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiveAlertMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeAlert)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActiveAlertWithPatch() throws Exception {
        // Initialize the database
        activeAlertRepository.saveAndFlush(activeAlert);

        int databaseSizeBeforeUpdate = activeAlertRepository.findAll().size();

        // Update the activeAlert using partial update
        ActiveAlert partialUpdatedActiveAlert = new ActiveAlert();
        partialUpdatedActiveAlert.setId(activeAlert.getId());

        partialUpdatedActiveAlert
            .activeAlertId(UPDATED_ACTIVE_ALERT_ID)
            .activeAlertName(UPDATED_ACTIVE_ALERT_NAME)
            .activeAlertCustomerId(UPDATED_ACTIVE_ALERT_CUSTOMER_ID)
            .activeAlertSmsMessage(UPDATED_ACTIVE_ALERT_SMS_MESSAGE)
            .activeAlertEmailMessage(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE);

        restActiveAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiveAlert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActiveAlert))
            )
            .andExpect(status().isOk());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeUpdate);
        ActiveAlert testActiveAlert = activeAlertList.get(activeAlertList.size() - 1);
        assertThat(testActiveAlert.getActiveAlertId()).isEqualTo(UPDATED_ACTIVE_ALERT_ID);
        assertThat(testActiveAlert.getActiveAlertName()).isEqualTo(UPDATED_ACTIVE_ALERT_NAME);
        assertThat(testActiveAlert.getActiveAlertCapacity()).isEqualTo(DEFAULT_ACTIVE_ALERT_CAPACITY);
        assertThat(testActiveAlert.getActiveAlertCustomerId()).isEqualTo(UPDATED_ACTIVE_ALERT_CUSTOMER_ID);
        assertThat(testActiveAlert.getActiveAlertDialNumber()).isEqualTo(DEFAULT_ACTIVE_ALERT_DIAL_NUMBER);
        assertThat(testActiveAlert.getActiveAlertSmsMessage()).isEqualTo(UPDATED_ACTIVE_ALERT_SMS_MESSAGE);
        assertThat(testActiveAlert.getActiveAlertEmailMessage()).isEqualTo(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE);
        assertThat(testActiveAlert.getActiveAlertSmsMessageActive()).isEqualTo(DEFAULT_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE);
        assertThat(testActiveAlert.getActiveAlertEmailMessageActive()).isEqualTo(DEFAULT_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE);
        assertThat(testActiveAlert.getActiveAlertPeriod()).isEqualTo(DEFAULT_ACTIVE_ALERT_PERIOD);
    }

    @Test
    @Transactional
    void fullUpdateActiveAlertWithPatch() throws Exception {
        // Initialize the database
        activeAlertRepository.saveAndFlush(activeAlert);

        int databaseSizeBeforeUpdate = activeAlertRepository.findAll().size();

        // Update the activeAlert using partial update
        ActiveAlert partialUpdatedActiveAlert = new ActiveAlert();
        partialUpdatedActiveAlert.setId(activeAlert.getId());

        partialUpdatedActiveAlert
            .activeAlertId(UPDATED_ACTIVE_ALERT_ID)
            .activeAlertName(UPDATED_ACTIVE_ALERT_NAME)
            .activeAlertCapacity(UPDATED_ACTIVE_ALERT_CAPACITY)
            .activeAlertCustomerId(UPDATED_ACTIVE_ALERT_CUSTOMER_ID)
            .activeAlertDialNumber(UPDATED_ACTIVE_ALERT_DIAL_NUMBER)
            .activeAlertSmsMessage(UPDATED_ACTIVE_ALERT_SMS_MESSAGE)
            .activeAlertEmailMessage(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE)
            .activeAlertSmsMessageActive(UPDATED_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE)
            .activeAlertEmailMessageActive(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE)
            .activeAlertPeriod(UPDATED_ACTIVE_ALERT_PERIOD);

        restActiveAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiveAlert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActiveAlert))
            )
            .andExpect(status().isOk());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeUpdate);
        ActiveAlert testActiveAlert = activeAlertList.get(activeAlertList.size() - 1);
        assertThat(testActiveAlert.getActiveAlertId()).isEqualTo(UPDATED_ACTIVE_ALERT_ID);
        assertThat(testActiveAlert.getActiveAlertName()).isEqualTo(UPDATED_ACTIVE_ALERT_NAME);
        assertThat(testActiveAlert.getActiveAlertCapacity()).isEqualTo(UPDATED_ACTIVE_ALERT_CAPACITY);
        assertThat(testActiveAlert.getActiveAlertCustomerId()).isEqualTo(UPDATED_ACTIVE_ALERT_CUSTOMER_ID);
        assertThat(testActiveAlert.getActiveAlertDialNumber()).isEqualTo(UPDATED_ACTIVE_ALERT_DIAL_NUMBER);
        assertThat(testActiveAlert.getActiveAlertSmsMessage()).isEqualTo(UPDATED_ACTIVE_ALERT_SMS_MESSAGE);
        assertThat(testActiveAlert.getActiveAlertEmailMessage()).isEqualTo(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE);
        assertThat(testActiveAlert.getActiveAlertSmsMessageActive()).isEqualTo(UPDATED_ACTIVE_ALERT_SMS_MESSAGE_ACTIVE);
        assertThat(testActiveAlert.getActiveAlertEmailMessageActive()).isEqualTo(UPDATED_ACTIVE_ALERT_EMAIL_MESSAGE_ACTIVE);
        assertThat(testActiveAlert.getActiveAlertPeriod()).isEqualTo(UPDATED_ACTIVE_ALERT_PERIOD);
    }

    @Test
    @Transactional
    void patchNonExistingActiveAlert() throws Exception {
        int databaseSizeBeforeUpdate = activeAlertRepository.findAll().size();
        activeAlert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiveAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activeAlert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activeAlert))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActiveAlert() throws Exception {
        int databaseSizeBeforeUpdate = activeAlertRepository.findAll().size();
        activeAlert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiveAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activeAlert))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActiveAlert() throws Exception {
        int databaseSizeBeforeUpdate = activeAlertRepository.findAll().size();
        activeAlert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiveAlertMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activeAlert))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActiveAlert in the database
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActiveAlert() throws Exception {
        // Initialize the database
        activeAlertRepository.saveAndFlush(activeAlert);

        int databaseSizeBeforeDelete = activeAlertRepository.findAll().size();

        // Delete the activeAlert
        restActiveAlertMockMvc
            .perform(delete(ENTITY_API_URL_ID, activeAlert.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActiveAlert> activeAlertList = activeAlertRepository.findAll();
        assertThat(activeAlertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
