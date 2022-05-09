package com.hypercell.orange.simmanagement.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.orange.simmanagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BucketTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bucket.class);
        Bucket bucket1 = new Bucket();
        bucket1.setId(1L);
        Bucket bucket2 = new Bucket();
        bucket2.setId(bucket1.getId());
        assertThat(bucket1).isEqualTo(bucket2);
        bucket2.setId(2L);
        assertThat(bucket1).isNotEqualTo(bucket2);
        bucket1.setId(null);
        assertThat(bucket1).isNotEqualTo(bucket2);
    }
}
