package practice.tdd.chess.game.domain.board;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import practice.tdd.chess.game.domain.piece.Color;
import practice.tdd.chess.game.domain.piece.EmptyPiece;
import practice.tdd.chess.game.domain.piece.Piece;

@Getter @Setter
@Embeddable
public class Board {
    private final int boardSize = 8;
    private final Piece[][] board = new Piece[boardSize][boardSize];

    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = new EmptyPiece(i, j, this);
            }
        }
    }

    public void setPieceOnBoard(int row, int col, Piece piece) {
        this.board[row][col] = piece;
    }

    public Piece getPiece(int row, int col) {
        return this.board[row][col];
    }

    public Color getColorOnLocation(Coordinate coordinate) {
         return this.board[coordinate.getRow()][coordinate.getCol()].getColor();
    }
}
