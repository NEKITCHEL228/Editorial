package domain.usecase;

import domain.model.User;
import domain.repository.UserRepository;
import domain.validator.IdValidator;
import domain.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCasesTest {
    @Mock
    private UserRepository userRepository;

    private final UserValidator userValidator = new UserValidator();
    private final IdValidator idValidator = new IdValidator();

    @Test
    void addUserPersistsValidUser() {
        User user = user();

        new AddUserUseCase(userRepository, userValidator).execute(user);

        verify(userRepository).addUser(user);
    }

    @Test
    void addUserDoesNotPersistInvalidUser() {
        User invalid = new User(0, "ab", "bad", "", null);

        assertThrows(
                IllegalArgumentException.class,
                () -> new AddUserUseCase(userRepository, userValidator).execute(invalid)
        );
        verify(userRepository, never()).addUser(any());
    }

    @Test
    void editUserUpdatesFieldsAndSaves() {
        User existing = user();
        when(userRepository.getUserById(2)).thenReturn(existing);

        new EditUserUseCase(userRepository, userValidator, idValidator)
                .execute(2, "bob", "bob@example.com", "new-hash", User.Role.EDITOR);

        assertEquals("bob", existing.getUsername());
        assertEquals("bob@example.com", existing.getEmail());
        assertEquals("new-hash", existing.getPasswordHash());
        assertEquals(User.Role.EDITOR, existing.getRole());
        verify(userRepository).editUser(existing);
    }

    @Test
    void editUserRejectsInvalidId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new EditUserUseCase(userRepository, userValidator, idValidator)
                        .execute(0, "bob", "bob@example.com", "hash", User.Role.AUTHOR)
        );
        verify(userRepository, never()).getUserById(0);
    }

    @Test
    void editUserDoesNotSaveWhenValidationFails() {
        when(userRepository.getUserById(2)).thenReturn(user());

        assertThrows(
                IllegalArgumentException.class,
                () -> new EditUserUseCase(userRepository, userValidator, idValidator)
                        .execute(2, "bob", "not-an-email", "hash", User.Role.AUTHOR)
        );
        verify(userRepository, never()).editUser(any());
    }

    @Test
    void getUserByIdReturnsRepositoryResult() {
        User existing = user();
        when(userRepository.getUserById(2)).thenReturn(existing);

        User result = new GetUserByIdUseCase(userRepository, idValidator).execute(2);

        assertSame(existing, result);
    }

    @Test
    void deleteUserDeletesAfterIdCheck() {
        new DeleteUserUseCase(userRepository, idValidator).execute(2);

        verify(userRepository).deleteUser(2);
    }

    @Test
    void getAndDeleteRejectInvalidId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new GetUserByIdUseCase(userRepository, idValidator).execute(0)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteUserUseCase(userRepository, idValidator).execute(-5)
        );
        verify(userRepository, never()).getUserById(0);
        verify(userRepository, never()).deleteUser(-5);
    }

    private static User user() {
        return new User(2, "alice", "alice@example.com", "hash", User.Role.AUTHOR);
    }
}
