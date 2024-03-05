package practice.tdd.chess.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.tdd.chess.exceptions.NullValueException;
import practice.tdd.chess.user.domain.JoinUser;
import practice.tdd.chess.user.domain.JoinUserDTO;
import practice.tdd.chess.user.exception.UserJoinException;
import practice.tdd.chess.user.repository.UserRepository;

@Service
public class UserJoinServiceImpl implements UserJoinService {
    private final UserRepository userRepository;

    @Autowired
    public UserJoinServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public JoinUser convertDTOToJoinUser(JoinUserDTO joinUserDTO) throws NullValueException {
        JoinUser joinUser = new JoinUser();
        joinUser.setName(joinUserDTO.getName());
        joinUser.setPassword(joinUserDTO.getPassword());

        if (joinUser.hasNull() || joinUser.hasEmpty()) {
            throw new NullValueException("이름 혹은 비밀번호를 입력하지 않음.");
        }

        return joinUser;
    }

    @Override
    public void join(JoinUser joinUser) throws UserJoinException {
        checkDuplicatedUser(joinUser);

        userRepository.save(joinUser);
    }

    private void checkDuplicatedUser(JoinUser joinUser) throws UserJoinException {
        if (userRepository.findByName(joinUser.getName()).isPresent()) {
            throw new UserJoinException("이미 존재하는 회원");
        }
    }
}
