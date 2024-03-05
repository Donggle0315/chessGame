package practice.tdd.chess.game.domain.piece;

import practice.tdd.chess.exceptions.OutOfBoardRangeException;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.domain.board.Coordinate;

public class King extends Piece{
    public King(int row, int col, Color color, Board board) {
        super(row, col, color, board);
    }

    @Override
    public boolean canMove(Coordinate destination) {
        try {
            this.inBoundary(destination);
        } catch (OutOfBoardRangeException e) {
            return false;
        }

        if (board.getColorOnLocation(destination) == getColor()) {
            return false;
        }

        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                if (piece.getColor() == getEnemyColor() &&
                        !(piece instanceof King) &&
                        piece.canMove(destination)) {
                    return false;
                }
            }
        }

        if ((Math.abs((getCoordinate().getRow() - destination.getRow()) + (getCoordinate().getCol() - destination.getCol()))) == 1) {
            return true;
        }

        if ((Math.abs((getCoordinate().getRow() - destination.getRow()) * (getCoordinate().getCol() - destination.getCol()))) == 1) {
            return true;
        }



        return false;
    }
}
