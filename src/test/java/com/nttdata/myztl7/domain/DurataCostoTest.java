package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class DurataCostoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DurataCosto.class);
        DurataCosto durataCosto1 = new DurataCosto();
        durataCosto1.setId(1L);
        DurataCosto durataCosto2 = new DurataCosto();
        durataCosto2.setId(durataCosto1.getId());
        assertThat(durataCosto1).isEqualTo(durataCosto2);
        durataCosto2.setId(2L);
        assertThat(durataCosto1).isNotEqualTo(durataCosto2);
        durataCosto1.setId(null);
        assertThat(durataCosto1).isNotEqualTo(durataCosto2);
    }
}
