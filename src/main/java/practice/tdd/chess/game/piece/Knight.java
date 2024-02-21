package practice.tdd.chess.game.piece;

import practice.tdd.chess.exceptions.OutOfBoardRangeException;
import practice.tdd.chess.game.board.Board;
import practice.tdd.chess.game.board.Coordinate;

public class Knight extends Piece{
    public Knight(int row, int col, Color color, Board board) {
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
        if (verifyMovingToAbnormalPoint(destination)) {
            return false;
        }
        return true;
    }

    private boolean verifyMovingToAbnormalPoint(Coordinate destination) {
        return Math.abs((getCoordinate().getRow() - destination.getRow()) * (getCoordinate().getCol() - destination.getCol())) != 2;
    }

    private boolean verifyMovingToAlliance(Coordinate destination) {
        return board.getColorOnLocation(destination) == this.getColor();
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
