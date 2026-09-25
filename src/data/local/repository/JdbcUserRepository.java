package data.local.repository;

import domain.model.User;
import domain.repository.UserRepository;
import data.local.database.DatabaseConnectionFactory;
import data.local.database.DatabaseConfig;

public class JdbcUserRepository implements UserRepository {


    public void addUser(User user) {}

    public void editUser(User user) {}

    public void deleteUser(int userId) {}

    public void getUserById(int userId) {}
}
