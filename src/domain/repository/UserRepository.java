package domain.repository;

import domain.model.User;

public interface UserRepository {
    void addUser(User user);

    void editUser(User user);

    void deleteUser(int userId);

    User getUserById(int userId);
}
