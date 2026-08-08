package com.mapfre.utils.testdata;

import java.time.Instant;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public final class RandomData {
    private RandomData() {}

    private static Random rnd() {
        // reproducible per scenario if data.seed provided
        return new Random(SeedManager.seed());
    }

    public static String uniqueSuffix() {
        // good for parallel uniqueness
        long t = Instant.now().toEpochMilli();
        long r = Math.abs(rnd().nextInt(1_000_000));
        return t + "-" + r;
    }

    public static String email(String prefix) {
        return (prefix + "+" + uniqueSuffix() + "@example.com").toLowerCase(Locale.ROOT);
    }

    public static String numeric(int digits) {
        if (digits <= 0) throw new IllegalArgumentException("digits must be > 0");
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        int val = min + rnd().nextInt(max - min + 1);
        return String.valueOf(val);
    }

    /*public static String idDocumentPeru(int startDigit, int digits) {
        if (digits <= 1) throw new IllegalArgumentException("Digits debe ser mayor a 1");

        long min = (long) Math.pow(10, digits - 2);
        long max = (long) Math.pow(10, digits - 1) - 1;
        long randomPart = java.util.concurrent.ThreadLocalRandom.current().nextLong(min, max + 1);

        return startDigit + String.valueOf(randomPart);
    }*/

    public static String idDocumentPeru(Integer startDigit, int digits) {
        if (digits <= 0) {
            throw new IllegalArgumentException("Digits must be greater than 0");
        }

        // How many random digits we need
        int randomDigits = (startDigit == null) ? digits : digits - 1;

        if (randomDigits <= 0) {
            throw new IllegalArgumentException("Invalid digit configuration");
        }

        long min = (long) Math.pow(10, randomDigits - 1);
        long max = (long) Math.pow(10, randomDigits) - 1;

        long randomPart = java.util.concurrent.ThreadLocalRandom.current()
                .nextLong(min, max + 1);

        return (startDigit == null)
                ? String.valueOf(randomPart)
                : startDigit + String.valueOf(randomPart);
    }

    public static String uuidShort() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    public static String fullName() {
        String[] first = {"Luis","Ana","Marco","Sofia","Diego","Maria","Jorge","Lucia"};
        String[] last  = {"Rojas","Perez","Gomez","Vargas","Torres","Diaz","Castillo","Silva"};
        return first[rnd().nextInt(first.length)] + " " + last[rnd().nextInt(last.length)];
    }

    public static String randomPlate() {
        // Formato peruano: 1 letra + 2 mixtos + 3 dígitos
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 26 caracteres
        String mixedChars = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ"; // 35 caracteres
        String digits = "123456789"; // 9 caracteres
        StringBuilder sb = new StringBuilder();
        Random random = rnd();

        // Posición 0: 1 letra (nextInt(26))
        sb.append(letters.charAt(random.nextInt(letters.length())));

        // Posiciones 1-2: 2 caracteres mixtos (nextInt(35) cada uno)
        for (int i = 0; i < 2; i++) {
            sb.append(mixedChars.charAt(random.nextInt(mixedChars.length())));
        }

        // Posiciones 3-5: 3 dígitos (nextInt(9) cada uno)
        for (int i = 0; i < 3; i++) {
            sb.append(digits.charAt(random.nextInt(digits.length())));
        }

        return sb.toString();
    }

    public static String randomVehicleIdentificationNumber() {
        // Arrays con tamaños específicos para sincronización
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 26 caracteres
        String mixedChars = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ"; // 35 caracteres
        String digits = "123456789"; // 9 caracteres
        String yearChars = "ABCDEFGHJKLMNPRSTUVWXYZ"; // 24 caracteres (año)
        String visChars = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ"; // 35 caracteres

        Random random = rnd();
        StringBuilder vin = new StringBuilder();

        // WMI: 3 caracteres
        // Posición 0: 1 letra (nextInt(26)) - similar a randomPlate posición 0
        vin.append(letters.charAt(random.nextInt(letters.length())));

        // Posiciones 1-2: 2 caracteres mixtos (nextInt(35) cada uno) - similar a randomPlate posiciones 1-2
        for (int i = 0; i < 2; i++) {
            vin.append(mixedChars.charAt(random.nextInt(mixedChars.length())));
        }

        // VDS: 6 caracteres
        // Posiciones 3-5: 3 dígitos (nextInt(9) cada uno) - similar a randomPlate posiciones 3-5
        for (int i = 0; i < 3; i++) {
            vin.append(digits.charAt(random.nextInt(digits.length())));
        }

        // Posiciones 6-8: 3 caracteres mixtos (nextInt(35) cada uno)
        for (int i = 0; i < 3; i++) {
            vin.append(mixedChars.charAt(random.nextInt(mixedChars.length())));
        }

        // VIS: 8 caracteres (posiciones 9-16)
        // Posición 9: Año modelo (nextInt(24))
        vin.append(yearChars.charAt(random.nextInt(yearChars.length())));

        // Posiciones 10-16: 7 caracteres aleatorios (nextInt(35) cada uno)
        for (int i = 0; i < 7; i++) {
            vin.append(visChars.charAt(random.nextInt(visChars.length())));
        }

        return vin.toString();
    }

    public static String randomEngineNumber() {
        // Arrays con tamaños específicos para sincronización
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 26 caracteres
        String mixedChars = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ"; // 35 caracteres
        String digits = "123456789"; // 9 caracteres
        String engineChars = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ"; // 35 caracteres

        Random random = rnd();
        StringBuilder engineNumber = new StringBuilder();

        // Primeros 6 caracteres: sincronizados con randomPlate()
        // Posición 0: 1 letra (nextInt(26))
        engineNumber.append(letters.charAt(random.nextInt(letters.length())));

        // Posiciones 1-2: 2 caracteres mixtos (nextInt(35) cada uno)
        for (int i = 0; i < 2; i++) {
            engineNumber.append(mixedChars.charAt(random.nextInt(mixedChars.length())));
        }

        // Posiciones 3-5: 3 dígitos (nextInt(9) cada uno)
        for (int i = 0; i < 3; i++) {
            engineNumber.append(digits.charAt(random.nextInt(digits.length())));
        }

        // Caracteres 6-11: 6 caracteres adicionales (nextInt(35) cada uno)
        for (int i = 0; i < 6; i++) {
            engineNumber.append(engineChars.charAt(random.nextInt(engineChars.length())));
        }

        return engineNumber.toString();
    }

}