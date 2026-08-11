package com.accountmanagement.validators;

import java.util.regex.Pattern;

import com.accountmanagement.validations.ValidWebsite;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WebsiteValidator implements ConstraintValidator<ValidWebsite, String> {

    private static final Pattern WEBSITE_PATTERN = Pattern.compile(
            "^(https?://)?(www\\.)?([a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}(/.*)?$");

    @Override
    public boolean isValid(String website, ConstraintValidatorContext arg1) {
        if (website == null || website.trim().isEmpty()) {
            return true;
        }
        return WEBSITE_PATTERN.matcher(website.trim()).matches();
    }

}
