package com.mapfre.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class StringUtils {

    /*public static void main(String[] args) {
        String text = "Cotización #12345 guardada exitosamente.";
        String requestId = extractRequestId(text);
        System.out.println("Extracted Request ID: " + requestId); // Output: 12345

        String mixedText = "User ID: 67890, Order ID: 54321";
        String allDigits = extractAllDigits(mixedText);
        System.out.println("All Digits: " + allDigits); // Output: 6789054321

        String indexedRequestId = extractRequestIdAtIndex(mixedText, 1);
        System.out.println("Second Request ID: " + indexedRequestId); // Output: 54321

        Extracted Request ID: 12345
        All Digits: 6789054321
        Second Request ID: 54321
    }*/

    /**
     * Extracts the first sequence of digits from a string
     * @param text The text to search
     * @return The first number found
     * @throws RuntimeException if no number is found
     */
    public static String extractRequestId(String text) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        throw new RuntimeException("No request ID found in: " + text);
    }

    /**
     * Extracts all digits from a string
     * @param text The text to process
     * @return String containing only digits
     */
    public static String extractAllDigits(String text) {
        return text.replaceAll("\\D", "");
    }

    /**
     * Extracts numbers at specific position
     * @param text The text to search
     * @param index Which number to extract (0 = first, 1 = second, etc)
     * @return The requested number
     */
    public static String extractRequestIdAtIndex(String text, int index) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);

        int count = 0;
        while (matcher.find()) {
            if (count == index) {
                return matcher.group();
            }
            count++;
        }

        throw new RuntimeException("No number at index " + index + " in: " + text);
    }
}
