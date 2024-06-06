package com.email.api.emailapi.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EmailUtils {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static String extractFirstName(String email) {
        // Split the email at '@' and take the part before it
        String localPart = email.split("@")[0];

        // Split the local part at '.' or digits to handle different formats
        String[] parts = localPart.split("[\\.\\d]");

        // If the first part is a valid word, return it as the first name
        for (String part : parts) {
            if (part.matches("[a-zA-Z]+")) {
                return capitalize(part);
            }
        }

        return "";
    }

    // Helper method to capitalize the first letter of the name
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
