package practice.tdd.chess.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.tdd.chess.user.domain.JoinUser;
import practice.tdd.chess.user.domain.JoinUserDTO;
import practice.tdd.chess.user.service.UserJoinService;
import practice.tdd.chess.exceptions.NullValueException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JoinUserDomainTest {
    @Autowired private UserJoinService userJoinService;
    private final JoinDataGiver joinDataGiver = new JoinDataGiver();
    @Test
    @DisplayName("회원가입 요청이 DTO 클래스에 올바르게 입력이 들어오는 경우 JoinUser에 매핑")
    public void joinDataNormal() {
        JoinUserDTO joinUserDTO = joinDataGiver.getJoinUserDTONormal();

        JoinUser joinUser = null;
        try {
            joinUser = userJoinService.convertDTOToJoinUser(joinUserDTO);
        } catch (NullValueException e) {
            throw new RuntimeException(e);
        }

        assertThat(joinUser.getName()).isEqualTo(joinUserDTO.getName());
        assertThat(joinUser.getPassword()).isEqualTo(joinUserDTO.getPassword());
    }

    @Test
    @DisplayName("회원가입 요청 DTO의 이름에 null 값이 있으면 예외 발생")
    public void joinDataHasNullNameException() {
        JoinUserDTO joinUserDTO = joinDataGiver.getJoinUserDTOWithNullNameValue();

        assertThrows(
                NullValueException.class,
                () -> userJoinService.convertDTOToJoinUser(joinUserDTO)
        );
    }

    @Test
    @DisplayName("회원가입 요청 DTO의 비밀번호에 null 값이 있으면 예외 발생")
    public void joinDataHasNullPasswordException() {
        JoinUserDTO joinUserDTO = joinDataGiver.getJoinUserDTOWithNullPasswordValue();

        assertThrows(
                NullValueException.class,
                () -> userJoinService.convertDTOToJoinUser(joinUserDTO)
        );
    }

    @Test
    @DisplayName("회원가입 요청 DTO의 이름이 빈칸이면 예외 발생")
    public void joinDataHasEmptyNameException() {
        JoinUserDTO joinUserDTO = joinDataGiver.getJoinUserDTOWithEmptyNameValue();

        assertThrows(
                NullValueException.class,
                () -> userJoinService.convertDTOToJoinUser(joinUserDTO)
        );
    }

    @Test
    @DisplayName("회원가입 요청 DTO의 비밀번호에 null 값이 있으면 예외 발생")
    public void joinDataHasEmptyPasswordException() {
        JoinUserDTO joinUserDTO = joinDataGiver.getJoinUserDTOWithEmptyPasswordValue();

        assertThrows(
                NullValueException.class,
                () -> userJoinService.convertDTOToJoinUser(joinUserDTO)
        );
    }




}
