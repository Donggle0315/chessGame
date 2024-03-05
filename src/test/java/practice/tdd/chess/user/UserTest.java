package practice.tdd.chess.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.tdd.chess.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    @DisplayName("유저가 정상적으로 만들어지는지 테스트")
    public void makeUserNormal() {
        User user = new User(1L, "Kim");

        assertThat(user).isInstanceOf(User.class);
        assertThat(user.getGameId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("Kim");
    }
}
