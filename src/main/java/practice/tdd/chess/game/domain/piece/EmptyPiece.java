package practice.tdd.chess.game.domain.piece;

import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.domain.board.Coordinate;

public class EmptyPiece extends Piece{
    public EmptyPiece(int row, int col, Board board) {
        super(row, col, Color.EMPTY, board);
    }

    @Override
    public boolean canMove(Coordinate destination) {
        return false;
    }
}
