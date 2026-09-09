package com.accountmanagement.constants.message;

public class UserMessage {

    public static final String USERS_RETRIEVED = "users retrieved successfully";

    public static final String USER_UPDATED = "user updated successfully";

    public static final String USER_DELETED = "user deleted successfully";

    public static final String USER_NOT_FOUND = "user does not exists";

    public static final String USER_REGISTER = "User Registered Successfully.";

    public static final String USER_LOGIN = "user logged in successfully";

    public static final String OTP_VERIFY = "Otp verified successfully";

    public static final String INVALID_OTP = "invalid otp";

    public static final String USER_REGISTERED_OTP = "User registered successfully. A verification OTP has been sent to your registered email address";

    public static final String OTP = "Otp send successfully";

    public static final String TEMPORARY_PASSWORD_CHANGED = "Temporary password changed successfully. Please login with your new one.";

    public static final String OTP_RESEND = "Otp resend successfully. For email verification";

    public static final String USER_EMAIL_VERIFY = "user email verified successfully";

    public static final String USER_EMAIL_NOT_FOUND = "user email not found";

    public static final String ACCESS_TOKEN = "access token not found";

    public static final String NEW_ACCESS_TOKEN = "new access token generated successfully";

    public static final String CHANGE_PASSWORD = "password changed successfully";

    public static final String LOGOUT = "user logout successfully";

    public static final String USER_LOGOUT = "User loggedout. Please login again";

    public static final String SESSION_NOT_FOUND = "User session not found";

    public static final String ACCOUNT_LOCKED = "Account locked. Try again after 30 mins";

    public static final String LOCKED = "Account locked";

    public static final String INVALID_TOKEN = "Invalid Token";

    public static final String TOKEN_EXPIRED = "Token Expired";

    public static final String INVALID_REQUEST = "Authentication Token Is Required";

    public static final String RESEND_EMAIL_VERIFICATION_LINK = "Email Verification Link Resend Successfully";

    public static final String SEND_EMAIL_VERIFICATION_LINK = "Email Verification Link Sent Successfully";

    public static final String TEMPORARY_CREDENTIAL = "Temporary Credential Send To User's Email Successfully. Please Change Your Password By Using The Link Below";

    public static final String EMAIL_VERIFICATION_LINK = "http://localhost:8080/api/auth/v1/verify/email?token=";

    public static final String TEMPORARY_PASSWORD_CHANGE_LINK = "http://localhost:3000/reset-password?resetToken=";

    public static final String USER_INACTIVE = "User is inactive and cannot be updated.";

     public static final String PASSWORD_MISMATCH = "New password and confirm password do not match";
     
}
