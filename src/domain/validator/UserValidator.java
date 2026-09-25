package domain.validator;

import domain.model.User;

public class UserValidator implements Validator<User> {
    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int USERNAME_MAX_LENGTH = 100;
    private static final int EMAIL_MAX_LENGTH = 255;
    private static final String EMAIL_PATTERN = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

    @Override
    public void validate(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is required");
        }
        if (isBlank(user.getUsername())) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getUsername().length() < USERNAME_MIN_LENGTH){
            throw new IllegalArgumentException("Username must be at least " + USERNAME_MIN_LENGTH + " characters");
        }
        if (user.getUsername().length() > USERNAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Username must be at most " + USERNAME_MAX_LENGTH + " characters");
        }
        if (isBlank(user.getEmail())) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (user.getEmail().length() > EMAIL_MAX_LENGTH) {
            throw new IllegalArgumentException("Email must be at most " + EMAIL_MAX_LENGTH + " characters");
        }
        if (!user.getEmail().matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Email is invalid");
        }
        if (isBlank(user.getPasswordHash())) {
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
