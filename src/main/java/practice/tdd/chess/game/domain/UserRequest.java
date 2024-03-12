package practice.tdd.chess.game.domain;

import lombok.Getter;
import lombok.Setter;
import practice.tdd.chess.game.domain.board.Coordinate;
import practice.tdd.chess.game.domain.player.Turn;

@Getter @Setter
public class UserRequest {
    private Turn turn;
    private Coordinate startCoordinate;
    private Coordinate finishCoordinate;
}
