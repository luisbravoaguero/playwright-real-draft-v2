package com.mapfre.utils.testdata;

import java.util.concurrent.ThreadLocalRandom;

public final class SeedManager {
    private static final ThreadLocal<Long> TL_SEED = new ThreadLocal<>();

    private SeedManager() {}

    public static long seed() {
        Long s = TL_SEED.get();
        if (s != null) return s;

        String prop = System.getProperty("data.seed");
        long seed = (prop != null && !prop.isBlank())
                ? Long.parseLong(prop)
                : ThreadLocalRandom.current().nextLong();

        TL_SEED.set(seed);
        return seed;
    }

    public static void clear() {
        TL_SEED.remove();
    }
}