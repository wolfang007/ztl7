package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class TipologiaPermessoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipologiaPermesso.class);
        TipologiaPermesso tipologiaPermesso1 = new TipologiaPermesso();
        tipologiaPermesso1.setId(1L);
        TipologiaPermesso tipologiaPermesso2 = new TipologiaPermesso();
        tipologiaPermesso2.setId(tipologiaPermesso1.getId());
        assertThat(tipologiaPermesso1).isEqualTo(tipologiaPermesso2);
        tipologiaPermesso2.setId(2L);
        assertThat(tipologiaPermesso1).isNotEqualTo(tipologiaPermesso2);
        tipologiaPermesso1.setId(null);
        assertThat(tipologiaPermesso1).isNotEqualTo(tipologiaPermesso2);
    }
}
