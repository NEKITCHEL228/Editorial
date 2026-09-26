package domain.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IdValidatorTest {
    private final IdValidator validator = new IdValidator();

    @Test
    void acceptsPositiveId() {
        assertDoesNotThrow(() -> validator.validate(1, "Article ID"));
    }

    @Test
    void rejectsZeroAndNegativeId() {
        IllegalArgumentException zero = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(0, "User ID")
        );
        IllegalArgumentException negative = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(-3, "User ID")
        );

        assertEquals("User ID must be a positive number", zero.getMessage());
        assertEquals("User ID must be a positive number", negative.getMessage());
    }
}
