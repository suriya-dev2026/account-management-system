package com.accountmanagement.utility;

import java.util.UUID;
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
        if (value == null) {
            return false;
        }
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&]).{8,16}$";
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

}
