package practice.tdd.chess.game.service;

import practice.tdd.chess.exceptions.InvalidUserRequestException;
import practice.tdd.chess.game.domain.game.Game;
import practice.tdd.chess.game.domain.board.Coordinate;
import practice.tdd.chess.game.domain.player.Turn;
import practice.tdd.chess.user.domain.User;

public interface GameService {
    Game initializeGame(User player1, User player2);

    void matchTurn(Turn requestTurn, Turn gameTurn) throws InvalidUserRequestException;

    void movePiece(Game game, Coordinate start, Coordinate finish) throws InvalidUserRequestException;
}
