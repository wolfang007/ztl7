package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FestivitaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Festivita.class);
        Festivita festivita1 = new Festivita();
        festivita1.setId(1L);
        Festivita festivita2 = new Festivita();
        festivita2.setId(festivita1.getId());
        assertThat(festivita1).isEqualTo(festivita2);
        festivita2.setId(2L);
        assertThat(festivita1).isNotEqualTo(festivita2);
        festivita1.setId(null);
        assertThat(festivita1).isNotEqualTo(festivita2);
    }
}
