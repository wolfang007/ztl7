package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ProfiloOrarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfiloOrario.class);
        ProfiloOrario profiloOrario1 = new ProfiloOrario();
        profiloOrario1.setId(1L);
        ProfiloOrario profiloOrario2 = new ProfiloOrario();
        profiloOrario2.setId(profiloOrario1.getId());
        assertThat(profiloOrario1).isEqualTo(profiloOrario2);
        profiloOrario2.setId(2L);
        assertThat(profiloOrario1).isNotEqualTo(profiloOrario2);
        profiloOrario1.setId(null);
        assertThat(profiloOrario1).isNotEqualTo(profiloOrario2);
    }
}
