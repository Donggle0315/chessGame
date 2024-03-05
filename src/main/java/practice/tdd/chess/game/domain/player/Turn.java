package practice.tdd.chess.game.domain.player;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import practice.tdd.chess.game.domain.piece.Color;

@Getter @Setter
@Embeddable
public class Turn {
    @Column(insertable=false, updatable=false)
    private int turn;
    @Column(insertable=false, updatable=false)
    private Color color;

    public Turn() {
        this.turn = 0;
        this.color = Color.BLACK;
    }

    public Turn(int turn, Color color) {
        this.turn = turn;
        this.color = color;
    }

    public void nextTurn() {
        if (this.color == Color.BLACK) {
            this.color = Color.WHITE;
        } else {
            this.turn++;
            this.color = Color.BLACK;
        }
    }
}
