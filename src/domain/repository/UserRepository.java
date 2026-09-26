package domain.repository;

import domain.model.User;

// Интерфейс для пользователей
public interface UserRepository {
    void addUser(User user);

    void editUser(User user);

    void deleteUser(int userId);

    User getUserById(int userId);

    boolean existsById(int userId);
}
