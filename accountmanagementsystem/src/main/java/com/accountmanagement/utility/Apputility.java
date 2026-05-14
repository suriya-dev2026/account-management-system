package com.accountmanagement.utility;

public class Apputility {

    public static boolean isValidPhone(String phone) {
        String regex = "^(\\+91)?[6-9][0-9]{9}$";
        return phone != null && phone.matches(regex);
    }
}
