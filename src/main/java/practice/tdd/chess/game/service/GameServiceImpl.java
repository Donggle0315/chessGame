package practice.tdd.chess.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.tdd.chess.exceptions.InvalidUserRequestException;
import practice.tdd.chess.game.domain.game.Game;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.domain.board.Coordinate;
import practice.tdd.chess.game.domain.piece.Color;
import practice.tdd.chess.game.domain.piece.EmptyPiece;
import practice.tdd.chess.game.domain.piece.Piece;
import practice.tdd.chess.game.domain.player.Player;
import practice.tdd.chess.game.domain.player.Turn;
import practice.tdd.chess.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService{
    private final BoardBuilder builder;

    @Autowired
    public GameServiceImpl(BoardBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Game initializeGame(User player1, User player2) {
        Board board = builder.buildChessBoard();
        builder.initialPieces(board);

        Player p1 = new Player(player1.getGameId(), player1.getName(),0, Color.BLACK);
        Player p2 = new Player(player2.getGameId(), player2.getName(), 0, Color.WHITE);

        return new Game(p1, p2, board);
    }

    @Override
    public void matchTurn(Turn requestTurn, Turn gameTurn) throws InvalidUserRequestException {
        if (requestTurn.getTurn() != gameTurn.getTurn() ||
                requestTurn.getColor() != gameTurn.getColor()) {
            throw new InvalidUserRequestException();
        }
    }

    @Override
    public void movePiece(Game game, Coordinate start, Coordinate finish) throws InvalidUserRequestException {
        if (game.getBoard().getColorOnLocation(start) != game.getTurn().getColor()) {
            throw new InvalidUserRequestException("Can't control enemy's piece");
        }

        if (game.getBoard().getPiece(start.getRow(), start.getCol()).canMove(finish) == false) {
            throw new InvalidUserRequestException("Can't move that Coordinate");
        }

        Piece movedPiece = game.getBoard().getPiece(start.getRow(), start.getCol());
        Piece removedPiece = game.getBoard().getPiece(finish.getRow(), finish.getCol());

        movedPiece.setCoordinate(new Coordinate(finish.getRow(), finish.getCol()));
        game.getBoard().setPieceOnBoard(finish.getRow(), finish.getCol(), movedPiece);
        game.getBoard().setPieceOnBoard(start.getRow(), start.getCol(), new EmptyPiece(start.getRow(), start.getCol(), game.getBoard()));
    }

    @Override
    public List<Coordinate> getMovableCoordinates(Game game, Coordinate start) {
        Piece piece = game.getBoard().getPiece(start.getRow(), start.getCol());

        List<Coordinate> movable = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                if (piece.canMove(coordinate)) {
                    movable.add(coordinate);
                }
            }
        }

        return movable;
    }
}
