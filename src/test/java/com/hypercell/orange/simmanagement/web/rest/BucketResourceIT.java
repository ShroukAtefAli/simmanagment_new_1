package com.hypercell.orange.simmanagement.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.orange.simmanagement.IntegrationTest;
import com.hypercell.orange.simmanagement.domain.Bucket;
import com.hypercell.orange.simmanagement.repository.BucketRepository;
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
 * Integration tests for the {@link BucketResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BucketResourceIT {

    private static final UUID DEFAULT_BUCKET_ID = UUID.randomUUID();
    private static final UUID UPDATED_BUCKET_ID = UUID.randomUUID();

    private static final String DEFAULT_BUCKET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUCKET_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_BUCKET_CAPACITY = 1L;
    private static final Long UPDATED_BUCKET_CAPACITY = 2L;

    private static final String DEFAULT_BUCKET_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BUCKET_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BUCKET_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BUCKET_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RATE_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_RATE_PLAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/buckets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBucketMockMvc;

    private Bucket bucket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bucket createEntity(EntityManager em) {
        Bucket bucket = new Bucket()
            .bucketId(DEFAULT_BUCKET_ID)
            .bucketName(DEFAULT_BUCKET_NAME)
            .bucketCapacity(DEFAULT_BUCKET_CAPACITY)
            .bucketType(DEFAULT_BUCKET_TYPE)
            .bucketDescription(DEFAULT_BUCKET_DESCRIPTION)
            .ratePlan(DEFAULT_RATE_PLAN);
        return bucket;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bucket createUpdatedEntity(EntityManager em) {
        Bucket bucket = new Bucket()
            .bucketId(UPDATED_BUCKET_ID)
            .bucketName(UPDATED_BUCKET_NAME)
            .bucketCapacity(UPDATED_BUCKET_CAPACITY)
            .bucketType(UPDATED_BUCKET_TYPE)
            .bucketDescription(UPDATED_BUCKET_DESCRIPTION)
            .ratePlan(UPDATED_RATE_PLAN);
        return bucket;
    }

    @BeforeEach
    public void initTest() {
        bucket = createEntity(em);
    }

    @Test
    @Transactional
    void createBucket() throws Exception {
        int databaseSizeBeforeCreate = bucketRepository.findAll().size();
        // Create the Bucket
        restBucketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bucket)))
            .andExpect(status().isCreated());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeCreate + 1);
        Bucket testBucket = bucketList.get(bucketList.size() - 1);
        assertThat(testBucket.getBucketId()).isEqualTo(DEFAULT_BUCKET_ID);
        assertThat(testBucket.getBucketName()).isEqualTo(DEFAULT_BUCKET_NAME);
        assertThat(testBucket.getBucketCapacity()).isEqualTo(DEFAULT_BUCKET_CAPACITY);
        assertThat(testBucket.getBucketType()).isEqualTo(DEFAULT_BUCKET_TYPE);
        assertThat(testBucket.getBucketDescription()).isEqualTo(DEFAULT_BUCKET_DESCRIPTION);
        assertThat(testBucket.getRatePlan()).isEqualTo(DEFAULT_RATE_PLAN);
    }

    @Test
    @Transactional
    void createBucketWithExistingId() throws Exception {
        // Create the Bucket with an existing ID
        bucket.setId(1L);

        int databaseSizeBeforeCreate = bucketRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBucketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bucket)))
            .andExpect(status().isBadRequest());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBucketIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bucketRepository.findAll().size();
        // set the field null
        bucket.setBucketId(null);

        // Create the Bucket, which fails.

        restBucketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bucket)))
            .andExpect(status().isBadRequest());

        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBucketNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bucketRepository.findAll().size();
        // set the field null
        bucket.setBucketName(null);

        // Create the Bucket, which fails.

        restBucketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bucket)))
            .andExpect(status().isBadRequest());

        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBucketCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = bucketRepository.findAll().size();
        // set the field null
        bucket.setBucketCapacity(null);

        // Create the Bucket, which fails.

        restBucketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bucket)))
            .andExpect(status().isBadRequest());

        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBucketTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bucketRepository.findAll().size();
        // set the field null
        bucket.setBucketType(null);

        // Create the Bucket, which fails.

        restBucketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bucket)))
            .andExpect(status().isBadRequest());

        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuckets() throws Exception {
        // Initialize the database
        bucketRepository.saveAndFlush(bucket);

        // Get all the bucketList
        restBucketMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bucket.getId().intValue())))
            .andExpect(jsonPath("$.[*].bucketId").value(hasItem(DEFAULT_BUCKET_ID.toString())))
            .andExpect(jsonPath("$.[*].bucketName").value(hasItem(DEFAULT_BUCKET_NAME)))
            .andExpect(jsonPath("$.[*].bucketCapacity").value(hasItem(DEFAULT_BUCKET_CAPACITY.intValue())))
            .andExpect(jsonPath("$.[*].bucketType").value(hasItem(DEFAULT_BUCKET_TYPE)))
            .andExpect(jsonPath("$.[*].bucketDescription").value(hasItem(DEFAULT_BUCKET_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].ratePlan").value(hasItem(DEFAULT_RATE_PLAN)));
    }

    @Test
    @Transactional
    void getBucket() throws Exception {
        // Initialize the database
        bucketRepository.saveAndFlush(bucket);

        // Get the bucket
        restBucketMockMvc
            .perform(get(ENTITY_API_URL_ID, bucket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bucket.getId().intValue()))
            .andExpect(jsonPath("$.bucketId").value(DEFAULT_BUCKET_ID.toString()))
            .andExpect(jsonPath("$.bucketName").value(DEFAULT_BUCKET_NAME))
            .andExpect(jsonPath("$.bucketCapacity").value(DEFAULT_BUCKET_CAPACITY.intValue()))
            .andExpect(jsonPath("$.bucketType").value(DEFAULT_BUCKET_TYPE))
            .andExpect(jsonPath("$.bucketDescription").value(DEFAULT_BUCKET_DESCRIPTION))
            .andExpect(jsonPath("$.ratePlan").value(DEFAULT_RATE_PLAN));
    }

    @Test
    @Transactional
    void getNonExistingBucket() throws Exception {
        // Get the bucket
        restBucketMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBucket() throws Exception {
        // Initialize the database
        bucketRepository.saveAndFlush(bucket);

        int databaseSizeBeforeUpdate = bucketRepository.findAll().size();

        // Update the bucket
        Bucket updatedBucket = bucketRepository.findById(bucket.getId()).get();
        // Disconnect from session so that the updates on updatedBucket are not directly saved in db
        em.detach(updatedBucket);
        updatedBucket
            .bucketId(UPDATED_BUCKET_ID)
            .bucketName(UPDATED_BUCKET_NAME)
            .bucketCapacity(UPDATED_BUCKET_CAPACITY)
            .bucketType(UPDATED_BUCKET_TYPE)
            .bucketDescription(UPDATED_BUCKET_DESCRIPTION)
            .ratePlan(UPDATED_RATE_PLAN);

        restBucketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBucket.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBucket))
            )
            .andExpect(status().isOk());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeUpdate);
        Bucket testBucket = bucketList.get(bucketList.size() - 1);
        assertThat(testBucket.getBucketId()).isEqualTo(UPDATED_BUCKET_ID);
        assertThat(testBucket.getBucketName()).isEqualTo(UPDATED_BUCKET_NAME);
        assertThat(testBucket.getBucketCapacity()).isEqualTo(UPDATED_BUCKET_CAPACITY);
        assertThat(testBucket.getBucketType()).isEqualTo(UPDATED_BUCKET_TYPE);
        assertThat(testBucket.getBucketDescription()).isEqualTo(UPDATED_BUCKET_DESCRIPTION);
        assertThat(testBucket.getRatePlan()).isEqualTo(UPDATED_RATE_PLAN);
    }

    @Test
    @Transactional
    void putNonExistingBucket() throws Exception {
        int databaseSizeBeforeUpdate = bucketRepository.findAll().size();
        bucket.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBucketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bucket.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bucket))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBucket() throws Exception {
        int databaseSizeBeforeUpdate = bucketRepository.findAll().size();
        bucket.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBucketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bucket))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBucket() throws Exception {
        int databaseSizeBeforeUpdate = bucketRepository.findAll().size();
        bucket.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBucketMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bucket)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBucketWithPatch() throws Exception {
        // Initialize the database
        bucketRepository.saveAndFlush(bucket);

        int databaseSizeBeforeUpdate = bucketRepository.findAll().size();

        // Update the bucket using partial update
        Bucket partialUpdatedBucket = new Bucket();
        partialUpdatedBucket.setId(bucket.getId());

        partialUpdatedBucket.bucketId(UPDATED_BUCKET_ID).bucketCapacity(UPDATED_BUCKET_CAPACITY);

        restBucketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBucket.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBucket))
            )
            .andExpect(status().isOk());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeUpdate);
        Bucket testBucket = bucketList.get(bucketList.size() - 1);
        assertThat(testBucket.getBucketId()).isEqualTo(UPDATED_BUCKET_ID);
        assertThat(testBucket.getBucketName()).isEqualTo(DEFAULT_BUCKET_NAME);
        assertThat(testBucket.getBucketCapacity()).isEqualTo(UPDATED_BUCKET_CAPACITY);
        assertThat(testBucket.getBucketType()).isEqualTo(DEFAULT_BUCKET_TYPE);
        assertThat(testBucket.getBucketDescription()).isEqualTo(DEFAULT_BUCKET_DESCRIPTION);
        assertThat(testBucket.getRatePlan()).isEqualTo(DEFAULT_RATE_PLAN);
    }

    @Test
    @Transactional
    void fullUpdateBucketWithPatch() throws Exception {
        // Initialize the database
        bucketRepository.saveAndFlush(bucket);

        int databaseSizeBeforeUpdate = bucketRepository.findAll().size();

        // Update the bucket using partial update
        Bucket partialUpdatedBucket = new Bucket();
        partialUpdatedBucket.setId(bucket.getId());

        partialUpdatedBucket
            .bucketId(UPDATED_BUCKET_ID)
            .bucketName(UPDATED_BUCKET_NAME)
            .bucketCapacity(UPDATED_BUCKET_CAPACITY)
            .bucketType(UPDATED_BUCKET_TYPE)
            .bucketDescription(UPDATED_BUCKET_DESCRIPTION)
            .ratePlan(UPDATED_RATE_PLAN);

        restBucketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBucket.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBucket))
            )
            .andExpect(status().isOk());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeUpdate);
        Bucket testBucket = bucketList.get(bucketList.size() - 1);
        assertThat(testBucket.getBucketId()).isEqualTo(UPDATED_BUCKET_ID);
        assertThat(testBucket.getBucketName()).isEqualTo(UPDATED_BUCKET_NAME);
        assertThat(testBucket.getBucketCapacity()).isEqualTo(UPDATED_BUCKET_CAPACITY);
        assertThat(testBucket.getBucketType()).isEqualTo(UPDATED_BUCKET_TYPE);
        assertThat(testBucket.getBucketDescription()).isEqualTo(UPDATED_BUCKET_DESCRIPTION);
        assertThat(testBucket.getRatePlan()).isEqualTo(UPDATED_RATE_PLAN);
    }

    @Test
    @Transactional
    void patchNonExistingBucket() throws Exception {
        int databaseSizeBeforeUpdate = bucketRepository.findAll().size();
        bucket.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBucketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bucket.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bucket))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBucket() throws Exception {
        int databaseSizeBeforeUpdate = bucketRepository.findAll().size();
        bucket.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBucketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bucket))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBucket() throws Exception {
        int databaseSizeBeforeUpdate = bucketRepository.findAll().size();
        bucket.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBucketMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bucket)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bucket in the database
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBucket() throws Exception {
        // Initialize the database
        bucketRepository.saveAndFlush(bucket);

        int databaseSizeBeforeDelete = bucketRepository.findAll().size();

        // Delete the bucket
        restBucketMockMvc
            .perform(delete(ENTITY_API_URL_ID, bucket.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bucket> bucketList = bucketRepository.findAll();
        assertThat(bucketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
