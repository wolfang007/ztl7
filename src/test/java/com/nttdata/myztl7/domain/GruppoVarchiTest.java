package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class GruppoVarchiTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GruppoVarchi.class);
        GruppoVarchi gruppoVarchi1 = new GruppoVarchi();
        gruppoVarchi1.setId(1L);
        GruppoVarchi gruppoVarchi2 = new GruppoVarchi();
        gruppoVarchi2.setId(gruppoVarchi1.getId());
        assertThat(gruppoVarchi1).isEqualTo(gruppoVarchi2);
        gruppoVarchi2.setId(2L);
        assertThat(gruppoVarchi1).isNotEqualTo(gruppoVarchi2);
        gruppoVarchi1.setId(null);
        assertThat(gruppoVarchi1).isNotEqualTo(gruppoVarchi2);
    }
}
