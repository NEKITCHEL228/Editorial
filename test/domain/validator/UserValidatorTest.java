package domain.validator;

import domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserValidatorTest {
    private final UserValidator validator = new UserValidator();

    @Test
    void acceptsValidUser() {
        assertDoesNotThrow(() -> validator.validate(validUser()));
    }

    @Test
    void rejectsNullUser() {
        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(null)
        );

        assertEquals("User is required", error.getMessage());
    }

    @Test
    void rejectsBlankAndShortUsername() {
        assertEquals(
                "Username cannot be empty",
                exceptionMessage(() -> validator.validateUsername("  "))
        );
        assertEquals(
                "Username must be at least 3 characters",
                exceptionMessage(() -> validator.validateUsername("ab"))
        );
    }

    @Test
    void rejectsTooLongUsername() {
        assertEquals(
                "Username must be at most 100 characters",
                exceptionMessage(() -> validator.validateUsername("a".repeat(101)))
        );
    }

    @Test
    void rejectsInvalidEmail() {
        assertEquals("Email cannot be empty", exceptionMessage(() -> validator.validateEmail("")));
        assertEquals("Email is invalid", exceptionMessage(() -> validator.validateEmail("not-an-email")));
        assertEquals(
                "Email must be at most 255 characters",
                exceptionMessage(() -> validator.validateEmail("a".repeat(252) + "@b.c"))
        );
    }

    @Test
    void rejectsBlankPasswordHash() {
        assertEquals(
                "Password hash cannot be empty",
                exceptionMessage(() -> validator.validatePasswordHash(" "))
        );
    }

    @Test
    void rejectsMissingRole() {
        User user = new User(1, "alice", "alice@example.com", "hash", null);

        assertEquals("Role is required", exceptionMessage(() -> validator.validate(user)));
    }

    private static User validUser() {
        return new User(1, "alice", "alice@example.com", "hash", User.Role.AUTHOR);
    }

    private static String exceptionMessage(Runnable action) {
        return assertThrows(IllegalArgumentException.class, action::run).getMessage();
    }
}
