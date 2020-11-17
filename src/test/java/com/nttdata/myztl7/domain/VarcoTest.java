package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class VarcoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Varco.class);
        Varco varco1 = new Varco();
        varco1.setId(1L);
        Varco varco2 = new Varco();
        varco2.setId(varco1.getId());
        assertThat(varco1).isEqualTo(varco2);
        varco2.setId(2L);
        assertThat(varco1).isNotEqualTo(varco2);
        varco1.setId(null);
        assertThat(varco1).isNotEqualTo(varco2);
    }
}
