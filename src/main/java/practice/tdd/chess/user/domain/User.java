package practice.tdd.chess.user.domain;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private Long gameId;
    private String name;

    public User(Long gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }
}
