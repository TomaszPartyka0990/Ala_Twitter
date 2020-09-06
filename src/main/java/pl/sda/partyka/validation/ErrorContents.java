package pl.sda.partyka.validation;

public class ErrorContents {
    public static final String LOGIN_ERROR_HEADER = "INVALID LOGIN";
    public static final String LOGIN_REGISTARTION_ERROR_MESSAGE
            = "Provided login is already in use";
    public static final String EMAIL_ERROR_HEADER = "INVALID EMAIL";
    public static final String EMAIL_REGISTRATION_ERROR_MESSAGE
            = "Provided email is already in use";
    public static final String LOGIN_LOGGING_ERROR_MESSAGE
            = "Provided login doesn't exists in database";
    public static final String PASSWORD_ERROR_HEADER = "INVALID PASSWORD";
    public static final String PASSWORD_LOGGING_ERROR_MESSAGE = "Provided password is incorrect";
}
