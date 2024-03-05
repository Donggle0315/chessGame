package practice.tdd.chess.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.tdd.chess.user.domain.JoinUser;
import practice.tdd.chess.user.exception.UserJoinException;
import practice.tdd.chess.user.repository.UserRepository;
import practice.tdd.chess.user.service.UserJoinService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class JoinUserRepositoryTest {
    private final JoinDataGiver joinDataGiver = new JoinDataGiver();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserJoinService joinService;

    @Test
    @DisplayName("중복되지 않은 유저가 회원가입을 하면 DB에 저장됨")
    public void joinUser() {
        JoinUser joinUser = joinDataGiver.getJoinUserNormal();

        JoinUser user = userRepository.save(joinUser);

        assertThat(joinUser).isEqualTo(user);
    }

    @Test
    @DisplayName("중복되지 않은 유저가 회원가입을 요청하면 회원가입 서비스 실행")
    public void joinUserService() {
        JoinUser joinUser = joinDataGiver.getJoinUserNormal();

        try {
            joinService.join(joinUser);
        } catch (UserJoinException e) {
            throw new RuntimeException(e);
        }

        Long userId = joinUser.getId();
        JoinUser user = userRepository.findById(userId).get();

        assertThat(user).isEqualTo(joinUser);
    }

    @Test
    @DisplayName("중복된 유저가 회원가입을 요청하면 예외 발생")
    public void joinDuplicatedUserException() {
        JoinUser user1 = joinDataGiver.getJoinUserNormal();
        JoinUser user2 = joinDataGiver.getJoinUserNormal();

        try {
            joinService.join(user1);
        } catch (UserJoinException e) {
            throw new RuntimeException(e);
        }

        assertThrows(
                UserJoinException.class,
                () -> joinService.join(user2)
        );
    }
}
