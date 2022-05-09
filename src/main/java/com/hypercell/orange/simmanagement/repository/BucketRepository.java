package com.hypercell.orange.simmanagement.repository;

import com.hypercell.orange.simmanagement.domain.Bucket;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bucket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {}
