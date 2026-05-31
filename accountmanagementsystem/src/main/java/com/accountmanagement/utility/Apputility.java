package com.accountmanagement.utility;

import org.springframework.security.core.context.SecurityContextHolder;

import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.messages.UserMessage;
import com.accountmanagement.model.User;

public class Apputility {

    public static boolean isValidPhone(String phone) {
        String regex = "^\\+91[6-9][0-9]{9}$";
        return phone != null && phone.matches(regex);
    }

    public static User  getLoggedUser() throws Exception, NullPointerException {
        User user = new User();
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user == null) {
                throw new RecordNotFoundException(UserMessage.USER_NOT_FOUND);
            }
            return user;
        } catch (NullPointerException e) {

            throw new NullPointerException(UserMessage.USER_NOT_FOUND);
        }
    }

}
