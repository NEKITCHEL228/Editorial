package data.local.repository;

import data.local.JdbcTestBase;
import domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcUserRepositoryTest extends JdbcTestBase {
    @Test
    void addUserAssignsIdAndGetUserByIdReadsFields() {
        User user = new User(0, "alice", "alice@example.com", "hash", User.Role.AUTHOR);

        userRepository.addUser(user);

        assertTrue(user.getId() > 0);
        User stored = userRepository.getUserById(user.getId());
        assertEquals("alice", stored.getUsername());
        assertEquals("alice@example.com", stored.getEmail());
        assertEquals("hash", stored.getPasswordHash());
        assertEquals(User.Role.AUTHOR, stored.getRole());
    }

    @Test
    void existsByIdReflectsInsertedUser() {
        User user = new User(0, "bob", "bob@example.com", "hash", User.Role.EDITOR);
        userRepository.addUser(user);

        assertTrue(userRepository.existsById(user.getId()));
        assertFalse(userRepository.existsById(user.getId() + 100));
    }

    @Test
    void editUserUpdatesPersistedFields() {
        User user = new User(0, "carol", "carol@example.com", "old-hash", User.Role.AUTHOR);
        userRepository.addUser(user);

        user.setUsername("carol-edited");
        user.setEmail("carol.edited@example.com");
        user.setPasswordHash("new-hash");
        user.setRole(User.Role.ADMIN);
        userRepository.editUser(user);

        User stored = userRepository.getUserById(user.getId());
        assertEquals("carol-edited", stored.getUsername());
        assertEquals("carol.edited@example.com", stored.getEmail());
        assertEquals("new-hash", stored.getPasswordHash());
        assertEquals(User.Role.ADMIN, stored.getRole());
    }

    @Test
    void editUserThrowsWhenUserDoesNotExist() {
        User missing = new User(99, "ghost", "ghost@example.com", "hash", User.Role.AUTHOR);

        assertThrows(IllegalArgumentException.class, () -> userRepository.editUser(missing));
    }

    @Test
    void deleteUserRemovesRow() {
        User user = new User(0, "dave", "dave@example.com", "hash", User.Role.AUTHOR);
        userRepository.addUser(user);

        userRepository.deleteUser(user.getId());

        assertFalse(userRepository.existsById(user.getId()));
        assertThrows(IllegalStateException.class, () -> userRepository.getUserById(user.getId()));
    }

    @Test
    void deleteUserThrowsWhenUserDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.deleteUser(42));
    }

    @Test
    void addUserWrapsUniqueConstraintAsIllegalState() {
        userRepository.addUser(new User(0, "erin", "erin@example.com", "hash", User.Role.AUTHOR));

        assertThrows(
                IllegalStateException.class,
                () -> userRepository.addUser(new User(0, "erin", "other@example.com", "hash", User.Role.AUTHOR))
        );
    }
}
