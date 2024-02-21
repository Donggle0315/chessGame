package practice.tdd.chess.game.board;

import practice.tdd.chess.game.piece.*;

public class BoardBuilder {
    public Board buildChessBoard() {
        return new Board();
    }

    public void initialPieces(Board board) {
        setPiecesOnLine(board,6, PieceName.PAWN, Color.WHITE);
        setPiecesOnLine(board, 1, PieceName.PAWN, Color.BLACK);

        setPieceOnPoint(board, 7, 0, PieceName.ROOK, Color.WHITE);
        setPieceOnPoint(board, 7, 7, PieceName.ROOK, Color.WHITE);
        setPieceOnPoint(board, 0, 0, PieceName.ROOK, Color.BLACK);
        setPieceOnPoint(board, 0, 7, PieceName.ROOK, Color.BLACK);

        setPieceOnPoint(board, 7, 1, PieceName.KNIGHT, Color.WHITE);
        setPieceOnPoint(board, 7, 6, PieceName.KNIGHT, Color.WHITE);
        setPieceOnPoint(board, 0, 1, PieceName.KNIGHT, Color.BLACK);
        setPieceOnPoint(board, 0, 6, PieceName.KNIGHT, Color.BLACK);

        setPieceOnPoint(board, 7, 2, PieceName.BISHOP, Color.WHITE);
        setPieceOnPoint(board, 7, 5, PieceName.BISHOP, Color.WHITE);
        setPieceOnPoint(board, 0, 2, PieceName.BISHOP, Color.BLACK);
        setPieceOnPoint(board, 0, 5, PieceName.BISHOP, Color.BLACK);

        setPieceOnPoint(board, 7, 3, PieceName.KING, Color.WHITE);
        setPieceOnPoint(board, 0, 3, PieceName.KING, Color.BLACK);

        setPieceOnPoint(board, 7, 4, PieceName.QUEEN, Color.WHITE);
        setPieceOnPoint(board, 0, 4, PieceName.QUEEN, Color.BLACK);

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
        switch (pieceName) {
            case PAWN :
                return new Pawn(row, col, color, board);
            case ROOK:
                return new Rook(row, col, color, board);
            case KNIGHT:
                return new Knight(row, col, color, board);
            case BISHOP:
                return new Bishop(row, col, color, board);
            case KING:
                return new King(row, col, color, board);
            case QUEEN:
                return new Queen(row, col, color, board);
        }

        return new EmptyPiece(row, col, board);
    }
}
