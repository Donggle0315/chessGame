package practice.tdd.chess.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.tdd.chess.user.domain.LoginUserDTO;
import practice.tdd.chess.user.exception.UserLoginException;
import practice.tdd.chess.user.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void login(LoginUserDTO loginUserDTO) throws UserLoginException {
        userRepository.findByNameAndPassword(loginUserDTO.getName(), loginUserDTO.getPassword()).orElseThrow(
                () -> new UserLoginException("Not Joined User")
        );
    }
}
