package domain.usecase;

import domain.model.User;
import domain.repository.UserRepository;

public class EditUserUseCase {
    private final UserRepository userRepository;

    public EditUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(
            int userId,
            String username,
            String email,
            String passwordHash,
            User.Role role
    ) {
        User user = userRepository.getUserById(userId);

        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRole(role);

        userRepository.editUser(user);
    }
}
