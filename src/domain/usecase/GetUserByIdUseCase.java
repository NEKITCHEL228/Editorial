package domain.usecase;

import domain.model.User;
import domain.repository.UserRepository;

public class GetUserByIdUseCase {
    private final UserRepository userRepository;

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(int userId) {
        return userRepository.getUserById(userId);
    }
}
