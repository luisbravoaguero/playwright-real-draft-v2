package com.mapfre.utils;

import java.util.concurrent.ThreadLocalRandom;

public final class VehicleIdGenerator {

    // VIN base: 17 chars, sin I, O, Q (comúnmente inválidos en VIN)
    private static final char[] VIN_CHARS =
            "ABCDEFGHJKLMNPRSTUVWXYZ123456789".toCharArray();

    // Motor: suele aceptarse alfanumérico, aquí lo dejamos uppercase + números
    private static final char[] ENGINE_CHARS =
            "ABCDEFGHJKLMNPRSTUVWXYZ123456789".toCharArray();

    private VehicleIdGenerator() {}

    public static String generateVin() {
        return generateFromCharset(VIN_CHARS, 17);
    }

    public static String generateEngineNumber(int length) {
        if (length < 6 || length > 20) {
            throw new IllegalArgumentException("Engine number length should be between 6 and 20.");
        }
        return generateFromCharset(ENGINE_CHARS, length);
    }

    private static String generateFromCharset(char[] charset, int length) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(charset[rnd.nextInt(charset.length)]);
        }
        return sb.toString();
    }
}