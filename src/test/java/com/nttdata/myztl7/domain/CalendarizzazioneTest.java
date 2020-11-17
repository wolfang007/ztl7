package com.nttdata.myztl7.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nttdata.myztl7.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CalendarizzazioneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calendarizzazione.class);
        Calendarizzazione calendarizzazione1 = new Calendarizzazione();
        calendarizzazione1.setId(1L);
        Calendarizzazione calendarizzazione2 = new Calendarizzazione();
        calendarizzazione2.setId(calendarizzazione1.getId());
        assertThat(calendarizzazione1).isEqualTo(calendarizzazione2);
        calendarizzazione2.setId(2L);
        assertThat(calendarizzazione1).isNotEqualTo(calendarizzazione2);
        calendarizzazione1.setId(null);
        assertThat(calendarizzazione1).isNotEqualTo(calendarizzazione2);
    }
}
