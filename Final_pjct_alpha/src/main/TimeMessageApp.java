package main;

import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A text-based adventure application that outputs atmospheric messages based on the time of day.
 * This class demonstrates the use of ResourceBundles for localization (English/Spanish).
 * * @author Miguel Angel Barajas Zea
 * @version 1.0
 */
public class TimeMessageApp {

    /**
     * The main entry point of the application.
     * Demonstrates the functionality by calling the test method with various times and locales.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Test Case 1: Early Morning in English
        testMessage(LocalTime.of(3, 0), Locale.US);

        // Test Case 2: Noon in Spanish
        testMessage(LocalTime.of(12, 30), new Locale("es", "ES"));

        // Test Case 3: Evening in English
        testMessage(LocalTime.of(22, 15), Locale.US);
        
        // Test Case 4: Morning in Spanish
        testMessage(LocalTime.of(9, 0), new Locale("es", "ES"));
        
        // Test Case 5: Afternoon in English
        testMessage(LocalTime.of(15, 45), Locale.US);
    }

    /**
     * Retrieves and prints a localized message based on the provided time and locale.
     * * @param time   The time to evaluate for the message logic.
     * @param locale The locale (English or Spanish) to use for translation.
     */
    public static void testMessage(LocalTime time, Locale locale) {
        // Load the resource bundle based on the locale
        ResourceBundle messages = ResourceBundle.getBundle("main.Messages", locale);
        String messageKey = "";

        // Logic to determine the time period based on assignment ranges
        if (isBetween(time, LocalTime.of(0, 0), LocalTime.of(6, 0))) {
            messageKey = "early_morning";
        } else if (isBetween(time, LocalTime.of(6, 1), LocalTime.of(11, 59))) {
            messageKey = "morning";
        } else if (isBetween(time, LocalTime.of(12, 0), LocalTime.of(12, 59))) {
            messageKey = "noon";
        } else if (isBetween(time, LocalTime.of(13, 0), LocalTime.of(20, 59))) {
            messageKey = "afternoon";
        } else {
            // Evening: 21:00 - 23:59
            messageKey = "evening";
        }

        // Output the result
        System.out.println("[" + time + " | " + locale.getLanguage() + "] " + messages.getString(messageKey));
    }

    /**
     * Helper method to check if a time falls within a specific range (inclusive).
     * * @param target The time to check.
     * @param start  The start of the range.
     * @param end    The end of the range.
     * @return True if the time is within the range, false otherwise.
     */
    private static boolean isBetween(LocalTime target, LocalTime start, LocalTime end) {
        return !target.isBefore(start) && !target.isAfter(end);
    }
}