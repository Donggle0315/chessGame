package practice.tdd.chess.user.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class LoginUserDTO {
    private String name;
    private String password;

    public LoginUserDTO() {
    }
}
