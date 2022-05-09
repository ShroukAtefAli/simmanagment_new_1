package com.hypercell.orange.simmanagement.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.orange.simmanagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActiveAlertTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActiveAlert.class);
        ActiveAlert activeAlert1 = new ActiveAlert();
        activeAlert1.setId(1L);
        ActiveAlert activeAlert2 = new ActiveAlert();
        activeAlert2.setId(activeAlert1.getId());
        assertThat(activeAlert1).isEqualTo(activeAlert2);
        activeAlert2.setId(2L);
        assertThat(activeAlert1).isNotEqualTo(activeAlert2);
        activeAlert1.setId(null);
        assertThat(activeAlert1).isNotEqualTo(activeAlert2);
    }
}
