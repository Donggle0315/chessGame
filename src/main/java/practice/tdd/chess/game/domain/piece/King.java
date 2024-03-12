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
        if (checkOutOfBoundary(destination)) {
            return false;
        }

        if (verifyMovingToAlliance(destination)) {
            return false;
        }

        if (verifyKingChecked(destination)) {
            return false;
        }

        if (verifyMovingToNormalPosition(destination)) {
            return true;
        }

        return false;
    }

    private boolean verifyMovingToNormalPosition(Coordinate destination) {
        if ((Math.abs(getCoordinate().getRow() - destination.getRow()) + Math.abs((getCoordinate().getCol() - destination.getCol()))) == 1) {
            return true;
        }

        if ((Math.abs((getCoordinate().getRow() - destination.getRow()) * (getCoordinate().getCol() - destination.getCol()))) == 1) {
            return true;
        }
        return false;
    }

    private boolean verifyKingChecked(Coordinate destination) {
        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                if (piece.getColor() == getEnemyColor() &&
                        !(piece instanceof King) &&
                        piece.canMove(destination)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verifyMovingToAlliance(Coordinate destination) {
        return board.getColorOnLocation(destination) == getColor();
    }

    private boolean checkOutOfBoundary(Coordinate destination) {
        try {
            this.inBoundary(destination);
        } catch (OutOfBoardRangeException e) {
            return true;
        }
        return false;
    }
}
