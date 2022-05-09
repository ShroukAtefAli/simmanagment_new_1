package com.hypercell.orange.simmanagement.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.orange.simmanagement.IntegrationTest;
import com.hypercell.orange.simmanagement.domain.Alert;
import com.hypercell.orange.simmanagement.domain.enumeration.AlertPeriod;
import com.hypercell.orange.simmanagement.repository.AlertRepository;
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
 * Integration tests for the {@link AlertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlertResourceIT {

    private static final UUID DEFAULT_ALERT_ID = UUID.randomUUID();
    private static final UUID UPDATED_ALERT_ID = UUID.randomUUID();

    private static final String DEFAULT_ALERT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ALERT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ALERT_CAPACITY = 1L;
    private static final Long UPDATED_ALERT_CAPACITY = 2L;

    private static final String DEFAULT_ALERT_SMS_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ALERT_SMS_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ALERT_EMAIL_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ALERT_EMAIL_MESSAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALERT_SMS_MESSAGE_ACTIVE = false;
    private static final Boolean UPDATED_ALERT_SMS_MESSAGE_ACTIVE = true;

    private static final Boolean DEFAULT_ALERT_EMAIL_MESSAGE_ACTIVE = false;
    private static final Boolean UPDATED_ALERT_EMAIL_MESSAGE_ACTIVE = true;

    private static final AlertPeriod DEFAULT_ALERT_PERIOD = AlertPeriod.Daily;
    private static final AlertPeriod UPDATED_ALERT_PERIOD = AlertPeriod.Weekly;

    private static final String ENTITY_API_URL = "/api/alerts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertMockMvc;

    private Alert alert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alert createEntity(EntityManager em) {
        Alert alert = new Alert()
            .alertId(DEFAULT_ALERT_ID)
            .alertName(DEFAULT_ALERT_NAME)
            .alertCapacity(DEFAULT_ALERT_CAPACITY)
            .alertSmsMessage(DEFAULT_ALERT_SMS_MESSAGE)
            .alertEmailMessage(DEFAULT_ALERT_EMAIL_MESSAGE)
            .alertSmsMessageActive(DEFAULT_ALERT_SMS_MESSAGE_ACTIVE)
            .alertEmailMessageActive(DEFAULT_ALERT_EMAIL_MESSAGE_ACTIVE)
            .alertPeriod(DEFAULT_ALERT_PERIOD);
        return alert;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alert createUpdatedEntity(EntityManager em) {
        Alert alert = new Alert()
            .alertId(UPDATED_ALERT_ID)
            .alertName(UPDATED_ALERT_NAME)
            .alertCapacity(UPDATED_ALERT_CAPACITY)
            .alertSmsMessage(UPDATED_ALERT_SMS_MESSAGE)
            .alertEmailMessage(UPDATED_ALERT_EMAIL_MESSAGE)
            .alertSmsMessageActive(UPDATED_ALERT_SMS_MESSAGE_ACTIVE)
            .alertEmailMessageActive(UPDATED_ALERT_EMAIL_MESSAGE_ACTIVE)
            .alertPeriod(UPDATED_ALERT_PERIOD);
        return alert;
    }

    @BeforeEach
    public void initTest() {
        alert = createEntity(em);
    }

    @Test
    @Transactional
    void createAlert() throws Exception {
        int databaseSizeBeforeCreate = alertRepository.findAll().size();
        // Create the Alert
        restAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isCreated());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeCreate + 1);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getAlertId()).isEqualTo(DEFAULT_ALERT_ID);
        assertThat(testAlert.getAlertName()).isEqualTo(DEFAULT_ALERT_NAME);
        assertThat(testAlert.getAlertCapacity()).isEqualTo(DEFAULT_ALERT_CAPACITY);
        assertThat(testAlert.getAlertSmsMessage()).isEqualTo(DEFAULT_ALERT_SMS_MESSAGE);
        assertThat(testAlert.getAlertEmailMessage()).isEqualTo(DEFAULT_ALERT_EMAIL_MESSAGE);
        assertThat(testAlert.getAlertSmsMessageActive()).isEqualTo(DEFAULT_ALERT_SMS_MESSAGE_ACTIVE);
        assertThat(testAlert.getAlertEmailMessageActive()).isEqualTo(DEFAULT_ALERT_EMAIL_MESSAGE_ACTIVE);
        assertThat(testAlert.getAlertPeriod()).isEqualTo(DEFAULT_ALERT_PERIOD);
    }

    @Test
    @Transactional
    void createAlertWithExistingId() throws Exception {
        // Create the Alert with an existing ID
        alert.setId(1L);

        int databaseSizeBeforeCreate = alertRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAlertIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertRepository.findAll().size();
        // set the field null
        alert.setAlertId(null);

        // Create the Alert, which fails.

        restAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isBadRequest());

        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAlertNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertRepository.findAll().size();
        // set the field null
        alert.setAlertName(null);

        // Create the Alert, which fails.

        restAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isBadRequest());

        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAlertCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertRepository.findAll().size();
        // set the field null
        alert.setAlertCapacity(null);

        // Create the Alert, which fails.

        restAlertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isBadRequest());

        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlerts() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        // Get all the alertList
        restAlertMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alert.getId().intValue())))
            .andExpect(jsonPath("$.[*].alertId").value(hasItem(DEFAULT_ALERT_ID.toString())))
            .andExpect(jsonPath("$.[*].alertName").value(hasItem(DEFAULT_ALERT_NAME)))
            .andExpect(jsonPath("$.[*].alertCapacity").value(hasItem(DEFAULT_ALERT_CAPACITY.intValue())))
            .andExpect(jsonPath("$.[*].alertSmsMessage").value(hasItem(DEFAULT_ALERT_SMS_MESSAGE)))
            .andExpect(jsonPath("$.[*].alertEmailMessage").value(hasItem(DEFAULT_ALERT_EMAIL_MESSAGE)))
            .andExpect(jsonPath("$.[*].alertSmsMessageActive").value(hasItem(DEFAULT_ALERT_SMS_MESSAGE_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].alertEmailMessageActive").value(hasItem(DEFAULT_ALERT_EMAIL_MESSAGE_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].alertPeriod").value(hasItem(DEFAULT_ALERT_PERIOD.toString())));
    }

    @Test
    @Transactional
    void getAlert() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        // Get the alert
        restAlertMockMvc
            .perform(get(ENTITY_API_URL_ID, alert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alert.getId().intValue()))
            .andExpect(jsonPath("$.alertId").value(DEFAULT_ALERT_ID.toString()))
            .andExpect(jsonPath("$.alertName").value(DEFAULT_ALERT_NAME))
            .andExpect(jsonPath("$.alertCapacity").value(DEFAULT_ALERT_CAPACITY.intValue()))
            .andExpect(jsonPath("$.alertSmsMessage").value(DEFAULT_ALERT_SMS_MESSAGE))
            .andExpect(jsonPath("$.alertEmailMessage").value(DEFAULT_ALERT_EMAIL_MESSAGE))
            .andExpect(jsonPath("$.alertSmsMessageActive").value(DEFAULT_ALERT_SMS_MESSAGE_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.alertEmailMessageActive").value(DEFAULT_ALERT_EMAIL_MESSAGE_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.alertPeriod").value(DEFAULT_ALERT_PERIOD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAlert() throws Exception {
        // Get the alert
        restAlertMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAlert() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Update the alert
        Alert updatedAlert = alertRepository.findById(alert.getId()).get();
        // Disconnect from session so that the updates on updatedAlert are not directly saved in db
        em.detach(updatedAlert);
        updatedAlert
            .alertId(UPDATED_ALERT_ID)
            .alertName(UPDATED_ALERT_NAME)
            .alertCapacity(UPDATED_ALERT_CAPACITY)
            .alertSmsMessage(UPDATED_ALERT_SMS_MESSAGE)
            .alertEmailMessage(UPDATED_ALERT_EMAIL_MESSAGE)
            .alertSmsMessageActive(UPDATED_ALERT_SMS_MESSAGE_ACTIVE)
            .alertEmailMessageActive(UPDATED_ALERT_EMAIL_MESSAGE_ACTIVE)
            .alertPeriod(UPDATED_ALERT_PERIOD);

        restAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAlert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAlert))
            )
            .andExpect(status().isOk());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getAlertId()).isEqualTo(UPDATED_ALERT_ID);
        assertThat(testAlert.getAlertName()).isEqualTo(UPDATED_ALERT_NAME);
        assertThat(testAlert.getAlertCapacity()).isEqualTo(UPDATED_ALERT_CAPACITY);
        assertThat(testAlert.getAlertSmsMessage()).isEqualTo(UPDATED_ALERT_SMS_MESSAGE);
        assertThat(testAlert.getAlertEmailMessage()).isEqualTo(UPDATED_ALERT_EMAIL_MESSAGE);
        assertThat(testAlert.getAlertSmsMessageActive()).isEqualTo(UPDATED_ALERT_SMS_MESSAGE_ACTIVE);
        assertThat(testAlert.getAlertEmailMessageActive()).isEqualTo(UPDATED_ALERT_EMAIL_MESSAGE_ACTIVE);
        assertThat(testAlert.getAlertPeriod()).isEqualTo(UPDATED_ALERT_PERIOD);
    }

    @Test
    @Transactional
    void putNonExistingAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlertWithPatch() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Update the alert using partial update
        Alert partialUpdatedAlert = new Alert();
        partialUpdatedAlert.setId(alert.getId());

        partialUpdatedAlert
            .alertId(UPDATED_ALERT_ID)
            .alertName(UPDATED_ALERT_NAME)
            .alertEmailMessage(UPDATED_ALERT_EMAIL_MESSAGE)
            .alertSmsMessageActive(UPDATED_ALERT_SMS_MESSAGE_ACTIVE)
            .alertEmailMessageActive(UPDATED_ALERT_EMAIL_MESSAGE_ACTIVE)
            .alertPeriod(UPDATED_ALERT_PERIOD);

        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlert))
            )
            .andExpect(status().isOk());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getAlertId()).isEqualTo(UPDATED_ALERT_ID);
        assertThat(testAlert.getAlertName()).isEqualTo(UPDATED_ALERT_NAME);
        assertThat(testAlert.getAlertCapacity()).isEqualTo(DEFAULT_ALERT_CAPACITY);
        assertThat(testAlert.getAlertSmsMessage()).isEqualTo(DEFAULT_ALERT_SMS_MESSAGE);
        assertThat(testAlert.getAlertEmailMessage()).isEqualTo(UPDATED_ALERT_EMAIL_MESSAGE);
        assertThat(testAlert.getAlertSmsMessageActive()).isEqualTo(UPDATED_ALERT_SMS_MESSAGE_ACTIVE);
        assertThat(testAlert.getAlertEmailMessageActive()).isEqualTo(UPDATED_ALERT_EMAIL_MESSAGE_ACTIVE);
        assertThat(testAlert.getAlertPeriod()).isEqualTo(UPDATED_ALERT_PERIOD);
    }

    @Test
    @Transactional
    void fullUpdateAlertWithPatch() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Update the alert using partial update
        Alert partialUpdatedAlert = new Alert();
        partialUpdatedAlert.setId(alert.getId());

        partialUpdatedAlert
            .alertId(UPDATED_ALERT_ID)
            .alertName(UPDATED_ALERT_NAME)
            .alertCapacity(UPDATED_ALERT_CAPACITY)
            .alertSmsMessage(UPDATED_ALERT_SMS_MESSAGE)
            .alertEmailMessage(UPDATED_ALERT_EMAIL_MESSAGE)
            .alertSmsMessageActive(UPDATED_ALERT_SMS_MESSAGE_ACTIVE)
            .alertEmailMessageActive(UPDATED_ALERT_EMAIL_MESSAGE_ACTIVE)
            .alertPeriod(UPDATED_ALERT_PERIOD);

        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlert))
            )
            .andExpect(status().isOk());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getAlertId()).isEqualTo(UPDATED_ALERT_ID);
        assertThat(testAlert.getAlertName()).isEqualTo(UPDATED_ALERT_NAME);
        assertThat(testAlert.getAlertCapacity()).isEqualTo(UPDATED_ALERT_CAPACITY);
        assertThat(testAlert.getAlertSmsMessage()).isEqualTo(UPDATED_ALERT_SMS_MESSAGE);
        assertThat(testAlert.getAlertEmailMessage()).isEqualTo(UPDATED_ALERT_EMAIL_MESSAGE);
        assertThat(testAlert.getAlertSmsMessageActive()).isEqualTo(UPDATED_ALERT_SMS_MESSAGE_ACTIVE);
        assertThat(testAlert.getAlertEmailMessageActive()).isEqualTo(UPDATED_ALERT_EMAIL_MESSAGE_ACTIVE);
        assertThat(testAlert.getAlertPeriod()).isEqualTo(UPDATED_ALERT_PERIOD);
    }

    @Test
    @Transactional
    void patchNonExistingAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();
        alert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(alert)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlert() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeDelete = alertRepository.findAll().size();

        // Delete the alert
        restAlertMockMvc
            .perform(delete(ENTITY_API_URL_ID, alert.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
