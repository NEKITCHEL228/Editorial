package domain.usecase;

import domain.model.User;
import domain.repository.UserRepository;

public class AddUserUseCase {
    private final UserRepository userRepository;

    public AddUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(User user) {
        if (user == null) throw new IllegalArgumentException("No such user");

        userRepository.addUser(user);
    }
}
