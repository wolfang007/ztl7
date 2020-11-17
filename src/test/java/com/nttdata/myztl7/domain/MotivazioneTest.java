package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MotivazioneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Motivazione.class);
        Motivazione motivazione1 = new Motivazione();
        motivazione1.setId(1L);
        Motivazione motivazione2 = new Motivazione();
        motivazione2.setId(motivazione1.getId());
        assertThat(motivazione1).isEqualTo(motivazione2);
        motivazione2.setId(2L);
        assertThat(motivazione1).isNotEqualTo(motivazione2);
        motivazione1.setId(null);
        assertThat(motivazione1).isNotEqualTo(motivazione2);
    }
}
