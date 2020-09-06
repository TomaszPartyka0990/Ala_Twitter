package pl.sda.partyka.validation;

public class ValidationError {
    private String errorHeader;
    private String errorMessage;

    public ValidationError(String errorHeader, String errorMessage) {
        this.errorHeader = errorHeader;
        this.errorMessage = errorMessage;
    }

    public String getErrorHeader() {
        return errorHeader;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
