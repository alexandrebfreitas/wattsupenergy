package com.alexandreburghesi.app2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PostoMedicaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PostoMedicao getPostoMedicaoSample1() {
        return new PostoMedicao()
            .id(1L)
            .nome("nome1")
            .numUsinaHidreletrica(1)
            .bacia("bacia1")
            .subbacia("subbacia1")
            .submercado("submercado1");
    }

    public static PostoMedicao getPostoMedicaoSample2() {
        return new PostoMedicao()
            .id(2L)
            .nome("nome2")
            .numUsinaHidreletrica(2)
            .bacia("bacia2")
            .subbacia("subbacia2")
            .submercado("submercado2");
    }

    public static PostoMedicao getPostoMedicaoRandomSampleGenerator() {
        return new PostoMedicao()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .numUsinaHidreletrica(intCount.incrementAndGet())
            .bacia(UUID.randomUUID().toString())
            .subbacia(UUID.randomUUID().toString())
            .submercado(UUID.randomUUID().toString());
    }
}
