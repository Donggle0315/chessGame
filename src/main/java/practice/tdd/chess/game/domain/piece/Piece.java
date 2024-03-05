package practice.tdd.chess.game.domain.piece;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import practice.tdd.chess.exceptions.OutOfBoardRangeException;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.domain.board.Coordinate;

@Getter @Setter @ToString
public abstract class Piece {
    private Coordinate coordinate;
    private Color color;
    private Color enemyColor;
    Board board;

    public Piece(int row, int col, Color color, Board board) {
        this.coordinate = new Coordinate(row, col);
        this.color = color;
        this.board = board;

        if (color == Color.EMPTY) enemyColor = Color.EMPTY;
        else if (color == Color.WHITE) enemyColor = Color.BLACK;
        else if (color == Color.BLACK) enemyColor = Color.WHITE;
    }

    public abstract boolean canMove(Coordinate destination);

    protected void inBoundary(Coordinate destination) throws OutOfBoardRangeException{
        if (destination.getRow() < 0 || destination.getRow() >= 8 || destination.getCol() < 0 || destination.getCol() >= 8) {
            throw new OutOfBoardRangeException();
        }
    }

    public void changeLocation(Coordinate destination) {
        this.coordinate = destination;
    }

    public boolean isEmpty() {
        return this.color == Color.EMPTY;
    }
}
