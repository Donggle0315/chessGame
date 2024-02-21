package practice.tdd.chess.game.piece;

import practice.tdd.chess.exceptions.OutOfBoardRangeException;
import practice.tdd.chess.game.board.Board;
import practice.tdd.chess.game.board.Coordinate;

public class Queen extends Piece{
    public Queen(int row, int col, Color color, Board board) {
        super(row, col, color, board);
    }

    @Override
    public boolean canMove(Coordinate destination) {
        if (checkOutOfBoundary(destination)) {
            return false;
        }
        if (verifyAnyPiecesOnVerticalHorizontalWay(destination)) {
            return false;
        }
        if (verifyAnyPieceOnDiagonalWay(destination)) {
            return false;
        }
        if (verifyMovingToAlliance(destination)) {
            return false;
        }
        if (verifyMovingVerticalOrHorizontal(destination)) {
            return true;
        }
        if (verifyMovingDiagonal(destination)) {
            return true;
        }

        return false;
    }

    private boolean verifyMovingDiagonal(Coordinate destination) {
        return ((getCoordinate().getRow() - getCoordinate().getCol()) == (destination.getRow()) - destination.getCol()) ||
                ((getCoordinate().getRow() + getCoordinate().getCol()) == (destination.getRow() + destination.getCol()));
    }

    private boolean verifyMovingVerticalOrHorizontal(Coordinate destination) {
        return ((getCoordinate().getRow() - destination.getRow()) * (getCoordinate().getCol() - destination.getCol())) == 0;
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

    private boolean verifyAnyPieceOnDiagonalWay(Coordinate destination) {
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

    private boolean verifyAnyPiecesOnVerticalHorizontalWay(Coordinate destination) {
        if (otherPieceOnVerticalWay(destination)) return true;
        if (otherPieceOnHorizontalWay(destination)) return true;
        return false;
    }

    private boolean otherPieceOnHorizontalWay(Coordinate destination) {
        if (getCoordinate().getCol() == destination.getCol()) {
            int startRow = Math.min(getCoordinate().getRow(), destination.getRow());
            int finishRow = Math.max(getCoordinate().getRow(), destination.getRow());
            for (int i = startRow + 1; i < finishRow; i++) {
                if (board.getColorOnLocation(new Coordinate(i, getCoordinate().getCol())) != Color.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean otherPieceOnVerticalWay(Coordinate destination) {
        if (getCoordinate().getRow() == destination.getRow()) {
            int startCol = Math.min(getCoordinate().getCol(), destination.getCol());
            int finishCol = Math.max(getCoordinate().getCol(), destination.getCol());
            for (int i = startCol + 1; i < finishCol; i++) {
                if (board.getColorOnLocation(new Coordinate(getCoordinate().getRow(), i)) != Color.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }
}
