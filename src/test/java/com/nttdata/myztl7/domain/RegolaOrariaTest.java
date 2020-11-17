package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class RegolaOrariaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegolaOraria.class);
        RegolaOraria regolaOraria1 = new RegolaOraria();
        regolaOraria1.setId(1L);
        RegolaOraria regolaOraria2 = new RegolaOraria();
        regolaOraria2.setId(regolaOraria1.getId());
        assertThat(regolaOraria1).isEqualTo(regolaOraria2);
        regolaOraria2.setId(2L);
        assertThat(regolaOraria1).isNotEqualTo(regolaOraria2);
        regolaOraria1.setId(null);
        assertThat(regolaOraria1).isNotEqualTo(regolaOraria2);
    }
}
