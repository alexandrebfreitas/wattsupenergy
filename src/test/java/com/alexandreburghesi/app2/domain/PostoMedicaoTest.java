package com.alexandreburghesi.app2.domain;

import static com.alexandreburghesi.app2.domain.AcomphTestSamples.*;
import static com.alexandreburghesi.app2.domain.PostoMedicaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.alexandreburghesi.app2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PostoMedicaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostoMedicao.class);
        PostoMedicao postoMedicao1 = getPostoMedicaoSample1();
        PostoMedicao postoMedicao2 = new PostoMedicao();
        assertThat(postoMedicao1).isNotEqualTo(postoMedicao2);

        postoMedicao2.setId(postoMedicao1.getId());
        assertThat(postoMedicao1).isEqualTo(postoMedicao2);

        postoMedicao2 = getPostoMedicaoSample2();
        assertThat(postoMedicao1).isNotEqualTo(postoMedicao2);
    }

    @Test
    void numPostoMedicaoTest() {
        PostoMedicao postoMedicao = getPostoMedicaoRandomSampleGenerator();
        Acomph acomphBack = getAcomphRandomSampleGenerator();

        postoMedicao.addNumPostoMedicao(acomphBack);
        assertThat(postoMedicao.getNumPostoMedicaos()).containsOnly(acomphBack);
        assertThat(acomphBack.getNumPostoMedicao()).isEqualTo(postoMedicao);

        postoMedicao.removeNumPostoMedicao(acomphBack);
        assertThat(postoMedicao.getNumPostoMedicaos()).doesNotContain(acomphBack);
        assertThat(acomphBack.getNumPostoMedicao()).isNull();

        postoMedicao.numPostoMedicaos(new HashSet<>(Set.of(acomphBack)));
        assertThat(postoMedicao.getNumPostoMedicaos()).containsOnly(acomphBack);
        assertThat(acomphBack.getNumPostoMedicao()).isEqualTo(postoMedicao);

        postoMedicao.setNumPostoMedicaos(new HashSet<>());
        assertThat(postoMedicao.getNumPostoMedicaos()).doesNotContain(acomphBack);
        assertThat(acomphBack.getNumPostoMedicao()).isNull();
    }
}
