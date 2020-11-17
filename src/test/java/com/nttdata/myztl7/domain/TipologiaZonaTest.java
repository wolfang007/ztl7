package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class TipologiaZonaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipologiaZona.class);
        TipologiaZona tipologiaZona1 = new TipologiaZona();
        tipologiaZona1.setId(1L);
        TipologiaZona tipologiaZona2 = new TipologiaZona();
        tipologiaZona2.setId(tipologiaZona1.getId());
        assertThat(tipologiaZona1).isEqualTo(tipologiaZona2);
        tipologiaZona2.setId(2L);
        assertThat(tipologiaZona1).isNotEqualTo(tipologiaZona2);
        tipologiaZona1.setId(null);
        assertThat(tipologiaZona1).isNotEqualTo(tipologiaZona2);
    }
}
