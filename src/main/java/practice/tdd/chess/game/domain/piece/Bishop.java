package practice.tdd.chess.game.domain.piece;

import practice.tdd.chess.exceptions.OutOfBoardRangeException;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.domain.board.Coordinate;

public class Bishop extends Piece{
    public Bishop(int row, int col, Color color, Board board) {
        super(row, col, color, board);
    }

    @Override
    public boolean canMove(Coordinate destination) {
        if (checkOutOfBoundary(destination)) {
            return false;
        }
        if (verifyMovingNotDiagonal(destination)) {
            return false;
        }
        if (verifyAnyPieceOnWay(destination)) {
            return false;
        }
        if (verifyAllianceOnDestination(destination)) {
            return false;
        }

        return true;
    }

    private boolean verifyAllianceOnDestination(Coordinate destination) {
        return board.getColorOnLocation(destination) == getColor();
    }

    private boolean verifyAnyPieceOnWay(Coordinate destination) {
        int startRow = Math.min(getCoordinate().getRow(), destination.getRow());
        int finishRow = Math.max(getCoordinate().getRow(), destination.getRow());
        int startCol = Math.min(getCoordinate().getCol(), destination.getCol());
        int finishCol = Math.min(getCoordinate().getCol(), destination.getCol());
        for (int i = startRow + 1, j = startCol + 1; i < finishRow && i != 8 && j != 8; i++, j++) {
            if (board.getColorOnLocation(new Coordinate(i, j)) != Color.EMPTY) {
                return true;
            }
        }
        return false;
    }

    private boolean verifyMovingNotDiagonal(Coordinate destination) {
        return ((getCoordinate().getRow() - getCoordinate().getCol()) != (destination.getRow() - destination.getCol()))
                && ((getCoordinate().getRow() + getCoordinate().getCol()) != (destination.getRow() + destination.getCol()));
    }

    private boolean checkOutOfBoundary(Coordinate destination) {
        try {
            super.inBoundary(destination);
        } catch (OutOfBoardRangeException e) {
            return true;
        }
        return false;
    }
}
