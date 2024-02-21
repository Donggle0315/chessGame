package practice.tdd.chess.game.piece;

import practice.tdd.chess.game.board.Board;
import practice.tdd.chess.game.board.Coordinate;

public class Queen extends Piece{
    public Queen(int row, int col, Color color, Board board) {
        super(row, col, color, board);
    }

    @Override
    public boolean canMove(Coordinate destination) {
        return false;
    }
}
