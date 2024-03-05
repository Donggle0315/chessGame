package practice.tdd.chess.game.domain.player;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;
import practice.tdd.chess.game.domain.piece.Color;
import practice.tdd.chess.user.domain.User;

@Getter @Setter
@Embeddable
public class Player extends User {
    @Embedded
    @Column(insertable=false, updatable=false)
    private Turn turn;

    public Player(Long id, String name, int turn, Color color) {
        super(id, name);
        this.turn = new Turn(turn, color);
    }

    public int getTurn() {
        return this.turn.getTurn();
    }

    public Color getColor() {
        return this.turn.getColor();
    }
}
