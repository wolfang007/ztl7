package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class TipologiaVeicoloTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipologiaVeicolo.class);
        TipologiaVeicolo tipologiaVeicolo1 = new TipologiaVeicolo();
        tipologiaVeicolo1.setId(1L);
        TipologiaVeicolo tipologiaVeicolo2 = new TipologiaVeicolo();
        tipologiaVeicolo2.setId(tipologiaVeicolo1.getId());
        assertThat(tipologiaVeicolo1).isEqualTo(tipologiaVeicolo2);
        tipologiaVeicolo2.setId(2L);
        assertThat(tipologiaVeicolo1).isNotEqualTo(tipologiaVeicolo2);
        tipologiaVeicolo1.setId(null);
        assertThat(tipologiaVeicolo1).isNotEqualTo(tipologiaVeicolo2);
    }
}
