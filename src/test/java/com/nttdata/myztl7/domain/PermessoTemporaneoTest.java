package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PermessoTemporaneoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermessoTemporaneo.class);
        PermessoTemporaneo permessoTemporaneo1 = new PermessoTemporaneo();
        permessoTemporaneo1.setId(1L);
        PermessoTemporaneo permessoTemporaneo2 = new PermessoTemporaneo();
        permessoTemporaneo2.setId(permessoTemporaneo1.getId());
        assertThat(permessoTemporaneo1).isEqualTo(permessoTemporaneo2);
        permessoTemporaneo2.setId(2L);
        assertThat(permessoTemporaneo1).isNotEqualTo(permessoTemporaneo2);
        permessoTemporaneo1.setId(null);
        assertThat(permessoTemporaneo1).isNotEqualTo(permessoTemporaneo2);
    }
}
