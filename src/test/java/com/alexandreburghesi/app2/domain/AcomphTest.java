package com.alexandreburghesi.app2.domain;

import static com.alexandreburghesi.app2.domain.AcomphTestSamples.*;
import static com.alexandreburghesi.app2.domain.PostoMedicaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.alexandreburghesi.app2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcomphTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acomph.class);
        Acomph acomph1 = getAcomphSample1();
        Acomph acomph2 = new Acomph();
        assertThat(acomph1).isNotEqualTo(acomph2);

        acomph2.setId(acomph1.getId());
        assertThat(acomph1).isEqualTo(acomph2);

        acomph2 = getAcomphSample2();
        assertThat(acomph1).isNotEqualTo(acomph2);
    }

    @Test
    void numPostoMedicaoTest() {
        Acomph acomph = getAcomphRandomSampleGenerator();
        PostoMedicao postoMedicaoBack = getPostoMedicaoRandomSampleGenerator();

        acomph.setNumPostoMedicao(postoMedicaoBack);
        assertThat(acomph.getNumPostoMedicao()).isEqualTo(postoMedicaoBack);

        acomph.numPostoMedicao(null);
        assertThat(acomph.getNumPostoMedicao()).isNull();
    }
}
