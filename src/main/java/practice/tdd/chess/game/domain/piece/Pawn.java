package practice.tdd.chess.game.domain.piece;

import practice.tdd.chess.exceptions.OutOfBoardRangeException;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.domain.board.Coordinate;

public class Pawn extends Piece{
    private boolean isMove = false;
    private int multiplyNumber;

    public Pawn(int row, int col, Color color, Board board) {
        super(row, col, color, board);
        multiplyNumber = color == Color.BLACK ? 1 : -1;
    }

    @Override
    public boolean canMove(Coordinate destination) {
        Coordinate now = this.getCoordinate();
        if (checkOutOfBoundary(destination)) {
            return false;
        }
        if (verifyMovingToAlliance(destination)) {
            return false;
        }
        if (verifyMovingForwardSingle(destination)) {
            return true;
        }
        if (verifyMovingForwardDoubleAtFirstMove(destination)) {
            return true;
        }
        if (verifyAttackingEnemy(destination)) {
            return true;
        }

        return false;
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

    private boolean verifyAttackingEnemy(Coordinate destination) {
        System.out.println("11" + Math.abs((this.getCoordinate().getRow() - destination.getRow()) * (this.getCoordinate().getCol() - destination.getCol())));
        System.out.println("22" + board.getColorOnLocation(destination) + super.getEnemyColor());

        return (Math.abs((this.getCoordinate().getRow() - destination.getRow()) * (this.getCoordinate().getCol() - destination.getCol())) == 1)
                && board.getColorOnLocation(destination) == super.getEnemyColor();
    }

    private boolean verifyMovingForwardDoubleAtFirstMove(Coordinate destination) {
        return !isMove && (this.getCoordinate().getCol() - destination.getCol()) == 0
                && super.getBoard().getColorOnLocation(new Coordinate(this.getCoordinate().getRow() + multiplyNumber, this.getCoordinate().getCol())) == Color.EMPTY
                && (destination.getRow() - this.getCoordinate().getRow()) == 2 * multiplyNumber;
    }

    private boolean verifyMovingForwardSingle(Coordinate destination) {
        return (this.getCoordinate().getCol() - destination.getCol()) == 0
                && (destination.getRow() - this.getCoordinate().getRow()) == multiplyNumber;
    }

}
