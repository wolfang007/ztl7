package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AutorizzazioneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autorizzazione.class);
        Autorizzazione autorizzazione1 = new Autorizzazione();
        autorizzazione1.setId(1L);
        Autorizzazione autorizzazione2 = new Autorizzazione();
        autorizzazione2.setId(autorizzazione1.getId());
        assertThat(autorizzazione1).isEqualTo(autorizzazione2);
        autorizzazione2.setId(2L);
        assertThat(autorizzazione1).isNotEqualTo(autorizzazione2);
        autorizzazione1.setId(null);
        assertThat(autorizzazione1).isNotEqualTo(autorizzazione2);
    }
}
