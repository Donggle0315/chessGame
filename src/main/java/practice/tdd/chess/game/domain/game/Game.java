package practice.tdd.chess.game.domain.game;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.domain.player.Player;
import practice.tdd.chess.game.domain.player.Turn;

@Getter @Setter
@Entity
public class Game {
    @Id
    private Long Id;
    @Embedded
    private Player p1;
    @Embedded
    private Player p2;
    @Embedded
    private Board board;
    @Embedded
    @Column(insertable=false, updatable=false)
    private Turn turn;

    public Game(Player p1, Player p2, Board board) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = board;
        this.turn = new Turn();
    }
}
