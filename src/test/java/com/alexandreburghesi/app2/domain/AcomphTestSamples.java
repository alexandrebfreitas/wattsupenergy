package com.alexandreburghesi.app2.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AcomphTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Acomph getAcomphSample1() {
        return new Acomph().id(1L);
    }

    public static Acomph getAcomphSample2() {
        return new Acomph().id(2L);
    }

    public static Acomph getAcomphRandomSampleGenerator() {
        return new Acomph().id(longCount.incrementAndGet());
    }
}
