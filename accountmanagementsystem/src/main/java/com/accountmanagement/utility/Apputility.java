package com.accountmanagement.utility;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.core.context.SecurityContextHolder;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.User;

public class Apputility {

    public static boolean isValidContactNumber(String phoneNo) {
        if (phoneNo == null)
            return false;
        if (phoneNo.matches("[6-9]\\d{9}"))
            return true;
        else if (phoneNo.matches("(\\+91|91)[6-9]\\d{9}"))
            return true;
        else if (phoneNo.matches("[6-9]\\d{2}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
            return true;
        else if (phoneNo.matches("\\([6-9]\\d{2}\\)-\\d{3}-\\d{4}"))
            return true;
        else
            return false;
    }

    public static User getLoggedUser() {
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new RecordNotFoundException(UserMessage.USER_NOT_FOUND);
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof User)) {
            throw new RecordNotFoundException(UserMessage.USER_NOT_FOUND);
        }
        return (User) principal;
    }

    public static Boolean isValidEmail(String value) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(value);
        if (mat.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isValidPassword(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&#]).{8,20}$";
        return value.matches(regex);
    }

    public static String sanitizeInput(String input) {
        if (input == null) {
            return input;
        }
        input = input.trim();
        input = input.replaceAll("<[^>]*>", "");
        input = input.replaceAll("[^a-zA-Z0-9@._\\-\\s]", "");

        return input;
    }

    public static Boolean isValidOtp(String value) {
        if (value == null) {
            return false;
        }
        String regex = "^\\d{6}$";
        return value.matches(regex);
    }

    public static String generateTemporaryPassword() {

        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String special = "@#$%";
        String allCharacters = uppercase + lowercase + numbers + special;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        password.append(uppercase.charAt(random.nextInt(uppercase.length())));
        password.append(lowercase.charAt(random.nextInt(lowercase.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(special.charAt(random.nextInt(special.length())));
        for (int i = 4; i < 10; i++) {
            password.append(
                    allCharacters.charAt(random.nextInt(allCharacters.length())));
        }
        for (int i = password.length() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(j));
            password.setCharAt(j, temp);
        }
        return password.toString();
    }

}
