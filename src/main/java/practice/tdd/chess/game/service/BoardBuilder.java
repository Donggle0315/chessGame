package practice.tdd.chess.game.service;

import org.springframework.stereotype.Service;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.domain.piece.*;

@Service
public class BoardBuilder {
    public Board buildChessBoard() {
        return new Board();
    }

    public void initialPieces(Board board) {
        setPawnsOnBoard(board);
        setRooksOnBoard(board);
        setKnightsOnBoard(board);
        setBishopsOnBoard(board);
        setKingsOnBoard(board);
        setQueensOnBoard(board);
    }

    private void setQueensOnBoard(Board board) {
        setPieceOnPoint(board, 7, 4, PieceName.QUEEN, Color.WHITE);
        setPieceOnPoint(board, 0, 4, PieceName.QUEEN, Color.BLACK);
    }

    private void setKingsOnBoard(Board board) {
        setPieceOnPoint(board, 7, 3, PieceName.KING, Color.WHITE);
        setPieceOnPoint(board, 0, 3, PieceName.KING, Color.BLACK);
    }

    private void setBishopsOnBoard(Board board) {
        setPieceOnPoint(board, 7, 2, PieceName.BISHOP, Color.WHITE);
        setPieceOnPoint(board, 7, 5, PieceName.BISHOP, Color.WHITE);
        setPieceOnPoint(board, 0, 2, PieceName.BISHOP, Color.BLACK);
        setPieceOnPoint(board, 0, 5, PieceName.BISHOP, Color.BLACK);
    }

    private void setKnightsOnBoard(Board board) {
        setPieceOnPoint(board, 7, 1, PieceName.KNIGHT, Color.WHITE);
        setPieceOnPoint(board, 7, 6, PieceName.KNIGHT, Color.WHITE);
        setPieceOnPoint(board, 0, 1, PieceName.KNIGHT, Color.BLACK);
        setPieceOnPoint(board, 0, 6, PieceName.KNIGHT, Color.BLACK);
    }

    private void setRooksOnBoard(Board board) {
        setPieceOnPoint(board, 7, 0, PieceName.ROOK, Color.WHITE);
        setPieceOnPoint(board, 7, 7, PieceName.ROOK, Color.WHITE);
        setPieceOnPoint(board, 0, 0, PieceName.ROOK, Color.BLACK);
        setPieceOnPoint(board, 0, 7, PieceName.ROOK, Color.BLACK);
    }

    private void setPawnsOnBoard(Board board) {
        setPiecesOnLine(board,6, PieceName.PAWN, Color.WHITE);
        setPiecesOnLine(board, 1, PieceName.PAWN, Color.BLACK);
    }

    private void setPiecesOnLine(Board board, int row, PieceName pieceName, Color color) {
        for (int i = 0; i < 8; i++) {
            setPieceOnPoint(board, row, i, pieceName, color);
        }
    }

    private void setPieceOnPoint(Board board, int row, int col, PieceName pieceName, Color color) {
        Piece piece = makePiece(pieceName, row, col, color, board);
        board.setPieceOnBoard(row, col, piece);
    }

    private Piece makePiece(PieceName pieceName, int row, int col, Color color, Board board) {
        return switch (pieceName) {
            case PAWN -> new Pawn(row, col, color, board);
            case ROOK -> new Rook(row, col, color, board);
            case KNIGHT -> new Knight(row, col, color, board);
            case BISHOP -> new Bishop(row, col, color, board);
            case KING -> new King(row, col, color, board);
            case QUEEN -> new Queen(row, col, color, board);
        };
    }
}
