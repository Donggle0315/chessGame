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
        if (getCoordinate().equals(destination)) {
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
        int deltaRow = getCoordinate().getRow() < destination.getRow() ? 1 : -1;
        int deltaCol = getCoordinate().getCol() < destination.getCol() ? 1 : -1;
        if (Math.abs(getCoordinate().getRow() - destination.getRow()) != Math.abs(getCoordinate().getCol() - destination.getCol())) {
            return false;
        }
        for (int i = getCoordinate().getRow() + deltaRow, j = getCoordinate().getCol() + deltaCol; i != destination.getRow() && j != destination.getCol(); i += deltaRow, j += deltaCol) {
            if (getBoard().getColorOnLocation(new Coordinate(i, j)) != Color.EMPTY) {
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
