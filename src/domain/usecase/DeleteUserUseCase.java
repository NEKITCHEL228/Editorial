package domain.usecase;

import domain.repository.UserRepository;

public class DeleteUserUseCase {
    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(int userId) {
        userRepository.deleteUser(userId);
    }
}
