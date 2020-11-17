package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ZonaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zona.class);
        Zona zona1 = new Zona();
        zona1.setId(1L);
        Zona zona2 = new Zona();
        zona2.setId(zona1.getId());
        assertThat(zona1).isEqualTo(zona2);
        zona2.setId(2L);
        assertThat(zona1).isNotEqualTo(zona2);
        zona1.setId(null);
        assertThat(zona1).isNotEqualTo(zona2);
    }
}
