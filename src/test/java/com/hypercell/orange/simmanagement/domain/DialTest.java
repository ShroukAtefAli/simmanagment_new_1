package com.hypercell.orange.simmanagement.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.orange.simmanagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dial.class);
        Dial dial1 = new Dial();
        dial1.setId(1L);
        Dial dial2 = new Dial();
        dial2.setId(dial1.getId());
        assertThat(dial1).isEqualTo(dial2);
        dial2.setId(2L);
        assertThat(dial1).isNotEqualTo(dial2);
        dial1.setId(null);
        assertThat(dial1).isNotEqualTo(dial2);
    }
}
