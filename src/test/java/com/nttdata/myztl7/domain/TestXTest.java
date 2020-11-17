package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class TestXTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestX.class);
        TestX testX1 = new TestX();
        testX1.setId(1L);
        TestX testX2 = new TestX();
        testX2.setId(testX1.getId());
        assertThat(testX1).isEqualTo(testX2);
        testX2.setId(2L);
        assertThat(testX1).isNotEqualTo(testX2);
        testX1.setId(null);
        assertThat(testX1).isNotEqualTo(testX2);
    }
}
