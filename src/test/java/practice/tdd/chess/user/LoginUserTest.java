package practice.tdd.chess.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.tdd.chess.user.domain.JoinUser;
import practice.tdd.chess.user.domain.LoginUserDTO;
import practice.tdd.chess.user.exception.UserJoinException;
import practice.tdd.chess.user.exception.UserLoginException;
import practice.tdd.chess.user.repository.UserRepository;
import practice.tdd.chess.user.service.LoginService;
import practice.tdd.chess.user.service.UserJoinService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
public class LoginUserTest {
    private final LoginDataGiver loginDataGiver = new LoginDataGiver();
    @Autowired private LoginService loginService;
    @Autowired private UserJoinService userJoinService;
    @Autowired private UserRepository userRepository;


    @Test
    @DisplayName("로그인 폼에 정상적으로 이름과 비밀번호를 입력해서 전달 받음")
    public void loginNameAndPasswordNormal() {
        LoginUserDTO loginUserDTO = loginDataGiver.getLoginDataNormal();
        JoinUser joinUser = new JoinUser();
        joinUser.setName(loginUserDTO.getName());
        joinUser.setPassword(loginUserDTO.getPassword());

        try {
            userJoinService.join(joinUser);
            loginService.login(loginUserDTO);
        } catch (UserJoinException | UserLoginException e) {
            fail();
        }
    }

    @Test
    @DisplayName("로그인 폼에 정상적으로 이름과 비밀번호를 입력받았지만 회원등록되어 있지 않으면 예외 발생")
    public void loginNameAndPasswordButNotJoin() {
        LoginUserDTO userDTO = loginDataGiver.getLoginDataNormal();

        if (userRepository.findByName(userDTO.getName()).isPresent()) {
            fail();
        }

        try {
            loginService.login(userDTO);
            fail();
        } catch (UserLoginException e) {
        }
    }

    @Test
    @DisplayName("로그인 폼에 이름이 누락되어 있으면 예외 발생")
    public void loginWithoutName() {
        LoginUserDTO user = loginDataGiver.getLoginDataWithoutName();

        try {
            loginService.login(user);
            fail();
        } catch (UserLoginException e) {
        }
    }

    @Test
    @DisplayName("로그인 폼에 비밀번호가 누락되어 있으면 예외 발생")
    public void loginWithoutPassword() {
        LoginUserDTO user = loginDataGiver.getLoginDataWithoutPassword();

        try {
            loginService.login(user);
            fail();
        } catch (UserLoginException e) {
        }
    }

    @Test
    @DisplayName("로그인 폼에 모든 정보가 누락되어 있으면 예외 발생")
    public void loginWithNothing() {
        LoginUserDTO user = loginDataGiver.getLoginDataNothing();

        try {
            loginService.login(user);
            fail();
        } catch (UserLoginException e) {
        }
    }
}
