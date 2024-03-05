package practice.tdd.chess.game.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.domain.piece.*;
import practice.tdd.chess.game.service.BoardBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardInitTest {
    private Board board;
    private BoardBuilder builder;

    @BeforeEach
    public void init() {
        builder = new BoardBuilder();
        board = builder.buildChessBoard();
    }

    @Test
    @DisplayName("8*8 체스 보드 생성")
    public void boardSizeInitialize(){
        assertThat(board.getBoard().length).isEqualTo(8);

        for (Piece[] pieces : board.getBoard()) {
            assertThat(pieces.length).isEqualTo(8);
        }
    }

    @Test
    @DisplayName("builder를 통해서 chess 보드 판을 생성함")
    public void chessBoardBuilder() {
        assertThat(board).isInstanceOf(Board.class);
    }

    @Test
    @DisplayName("보드에 말들을 세팅")
    public void setPieceOnBoard() {
        builder.initialPieces(board);

        int pieceCount = 64;

        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                if (piece.isEmpty()) {
                    pieceCount--;
                }
            }
        }

        assertThat(pieceCount).isEqualTo(32);
    }

    @Test
    @DisplayName("빈 칸은 EmptyPiece로 보드에 세팅")
    public void setEmptyPieceOnBoard() {
        builder.initialPieces(board);
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertThat(board.getBoard()[i][j]).isInstanceOf(EmptyPiece.class);
                assertThat(board.getBoard()[i][j].getColor()).isEqualTo(Color.EMPTY);
            }
        }
    }

    @Test
    @DisplayName("흰색 PAWN을 보드에 세팅")
    public void setWhitePawnOnBoard() {
        builder.initialPieces(board);
        for (int i = 0; i < 8; i++) {
            assertThat(board.getBoard()[6][i]).isInstanceOf(Pawn.class);
            assertThat(board.getBoard()[6][i].getColor()).isEqualTo(Color.WHITE);
        }
    }

    @Test
    @DisplayName("검은색 PAWN을 보드에 세팅")
    public void setBlackPawnOnBoard() {
        builder.initialPieces(board);
        for (int i = 0; i < 8; i++) {
            assertThat(board.getBoard()[1][i]).isInstanceOf(Pawn.class);
            assertThat(board.getBoard()[1][i].getColor()).isEqualTo(Color.BLACK);
        }
    }

    @Test
    @DisplayName("흰색 ROOK을 보드에 세팅")
    public void setWhiteRookOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[7][0]).isInstanceOf(Rook.class);
        assertThat(board.getBoard()[7][0].getColor()).isEqualTo(Color.WHITE);
        assertThat(board.getBoard()[7][7]).isInstanceOf(Rook.class);
        assertThat(board.getBoard()[7][7].getColor()).isEqualTo(Color.WHITE);
    }

    @Test
    @DisplayName("검은색 ROOK을 보드에 세팅")
    public void setBlackRookOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[0][0]).isInstanceOf(Rook.class);
        assertThat(board.getBoard()[0][0].getColor()).isEqualTo(Color.BLACK);
        assertThat(board.getBoard()[0][7]).isInstanceOf(Rook.class);
        assertThat(board.getBoard()[0][7].getColor()).isEqualTo(Color.BLACK);
    }

    @Test
    @DisplayName("흰색 KNIGHT을 보드에 세팅")
    public void setWhiteKnightOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[7][1]).isInstanceOf(Knight.class);
        assertThat(board.getBoard()[7][1].getColor()).isEqualTo(Color.WHITE);
        assertThat(board.getBoard()[7][6]).isInstanceOf(Knight.class);
        assertThat(board.getBoard()[7][6].getColor()).isEqualTo(Color.WHITE);
    }

    @Test
    @DisplayName("검은색 KNIGHT을 보드에 세팅")
    public void setBlackKnightOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[0][1]).isInstanceOf(Knight.class);
        assertThat(board.getBoard()[0][1].getColor()).isEqualTo(Color.BLACK);
        assertThat(board.getBoard()[0][6]).isInstanceOf(Knight.class);
        assertThat(board.getBoard()[0][6].getColor()).isEqualTo(Color.BLACK);
    }

    @Test
    @DisplayName("흰색 BISHOP 보드에 세팅")
    public void setWhiteBishopOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[7][2]).isInstanceOf(Bishop.class);
        assertThat(board.getBoard()[7][2].getColor()).isEqualTo(Color.WHITE);
        assertThat(board.getBoard()[7][5]).isInstanceOf(Bishop.class);
        assertThat(board.getBoard()[7][5].getColor()).isEqualTo(Color.WHITE);
    }

    @Test
    @DisplayName("검은색 BISHOP을 보드에 세팅")
    public void setBlackBishopOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[0][2]).isInstanceOf(Bishop.class);
        assertThat(board.getBoard()[0][2].getColor()).isEqualTo(Color.BLACK);
        assertThat(board.getBoard()[0][5]).isInstanceOf(Bishop.class);
        assertThat(board.getBoard()[0][5].getColor()).isEqualTo(Color.BLACK);
    }

    @Test
    @DisplayName("흰색 KING을 보드에 세팅")
    public void setWhiteKingOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[7][3]).isInstanceOf(King.class);
        assertThat(board.getBoard()[7][3].getColor()).isEqualTo(Color.WHITE);
    }

    @Test
    @DisplayName("검은색 KING을 보드에 세팅")
    public void setBlackKingOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[0][3]).isInstanceOf(King.class);
        assertThat(board.getBoard()[0][3].getColor()).isEqualTo(Color.BLACK);
    }

    @Test
    @DisplayName("흰색 QUEEN을 보드에 세팅")
    public void setWhiteQueenOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[7][4]).isInstanceOf(Queen.class);
        assertThat(board.getBoard()[7][4].getColor()).isEqualTo(Color.WHITE);
    }

    @Test
    @DisplayName("검은색 QUEEN을 보드에 세팅")
    public void setBlackQueenOnBoard() {
        builder.initialPieces(board);
        assertThat(board.getBoard()[0][4]).isInstanceOf(Queen.class);
        assertThat(board.getBoard()[0][4].getColor()).isEqualTo(Color.BLACK);
    }
}
