package com.example.CVE.Utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class AppUtils {

    // Private constructor to prevent instantiation
    private AppUtils() {
        // This class should not be instantiated
    }


//      Formats a LocalDate object to a readable string (e.g., "dd-MM-yyyy").
//      @param date The LocalDate object to format.
//      @return Formatted date string.

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

//
//      Checks if a string is null or empty.
//      @param s The string to check.
//      @return true if the string is not null and not empty, otherwise false.
//
    public static boolean isNotNullOrEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }
}