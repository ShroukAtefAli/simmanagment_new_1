package com.hypercell.orange.simmanagement.web.rest;

import com.hypercell.orange.simmanagement.domain.Bucket;
import com.hypercell.orange.simmanagement.repository.BucketRepository;
import com.hypercell.orange.simmanagement.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hypercell.orange.simmanagement.domain.Bucket}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BucketResource {

    private final Logger log = LoggerFactory.getLogger(BucketResource.class);

    private static final String ENTITY_NAME = "simManagementBucket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BucketRepository bucketRepository;

    public BucketResource(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    /**
     * {@code POST  /buckets} : Create a new bucket.
     *
     * @param bucket the bucket to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bucket, or with status {@code 400 (Bad Request)} if the bucket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buckets")
    public ResponseEntity<Bucket> createBucket(@Valid @RequestBody Bucket bucket) throws URISyntaxException {
        log.debug("REST request to save Bucket : {}", bucket);
        if (bucket.getId() != null) {
            throw new BadRequestAlertException("A new bucket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bucket result = bucketRepository.save(bucket);
        return ResponseEntity
            .created(new URI("/api/buckets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buckets/:id} : Updates an existing bucket.
     *
     * @param id the id of the bucket to save.
     * @param bucket the bucket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bucket,
     * or with status {@code 400 (Bad Request)} if the bucket is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bucket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buckets/{id}")
    public ResponseEntity<Bucket> updateBucket(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bucket bucket
    ) throws URISyntaxException {
        log.debug("REST request to update Bucket : {}, {}", id, bucket);
        if (bucket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bucket.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bucketRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bucket result = bucketRepository.save(bucket);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bucket.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buckets/:id} : Partial updates given fields of an existing bucket, field will ignore if it is null
     *
     * @param id the id of the bucket to save.
     * @param bucket the bucket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bucket,
     * or with status {@code 400 (Bad Request)} if the bucket is not valid,
     * or with status {@code 404 (Not Found)} if the bucket is not found,
     * or with status {@code 500 (Internal Server Error)} if the bucket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/buckets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bucket> partialUpdateBucket(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bucket bucket
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bucket partially : {}, {}", id, bucket);
        if (bucket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bucket.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bucketRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bucket> result = bucketRepository
            .findById(bucket.getId())
            .map(existingBucket -> {
                if (bucket.getBucketId() != null) {
                    existingBucket.setBucketId(bucket.getBucketId());
                }
                if (bucket.getBucketName() != null) {
                    existingBucket.setBucketName(bucket.getBucketName());
                }
                if (bucket.getBucketCapacity() != null) {
                    existingBucket.setBucketCapacity(bucket.getBucketCapacity());
                }
                if (bucket.getBucketType() != null) {
                    existingBucket.setBucketType(bucket.getBucketType());
                }
                if (bucket.getBucketDescription() != null) {
                    existingBucket.setBucketDescription(bucket.getBucketDescription());
                }
                if (bucket.getRatePlan() != null) {
                    existingBucket.setRatePlan(bucket.getRatePlan());
                }

                return existingBucket;
            })
            .map(bucketRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bucket.getId().toString())
        );
    }

    /**
     * {@code GET  /buckets} : get all the buckets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buckets in body.
     */
    @GetMapping("/buckets")
    public List<Bucket> getAllBuckets() {
        log.debug("REST request to get all Buckets");
        return bucketRepository.findAll();
    }

    /**
     * {@code GET  /buckets/:id} : get the "id" bucket.
     *
     * @param id the id of the bucket to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bucket, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buckets/{id}")
    public ResponseEntity<Bucket> getBucket(@PathVariable Long id) {
        log.debug("REST request to get Bucket : {}", id);
        Optional<Bucket> bucket = bucketRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bucket);
    }

    /**
     * {@code DELETE  /buckets/:id} : delete the "id" bucket.
     *
     * @param id the id of the bucket to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buckets/{id}")
    public ResponseEntity<Void> deleteBucket(@PathVariable Long id) {
        log.debug("REST request to delete Bucket : {}", id);
        bucketRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
