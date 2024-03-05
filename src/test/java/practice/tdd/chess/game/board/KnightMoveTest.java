package practice.tdd.chess.game.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.service.BoardBuilder;
import practice.tdd.chess.game.domain.board.Coordinate;
import practice.tdd.chess.game.domain.piece.Color;
import practice.tdd.chess.game.domain.piece.Knight;
import practice.tdd.chess.game.domain.piece.Piece;

import static org.assertj.core.api.Assertions.assertThat;

public class KnightMoveTest {
    private BoardBuilder builder;
    private Board board;

    private Piece blackKnight;
    private Piece whiteKnight;

    @BeforeEach
    public void init() {
        builder = new BoardBuilder();
        board = builder.buildChessBoard();
        blackKnight = new Knight(2, 2, Color.BLACK, board);
        whiteKnight = new Knight(5, 5, Color.WHITE, board);
        board.setPieceOnBoard(2, 2, blackKnight);
        board.setPieceOnBoard(5, 5, whiteKnight);
    }

    @Test
    @DisplayName("범위 밖으로 나가면 예외 발생")
    public void checkOutOfRange() {
        Coordinate coordinate = new Coordinate(-1, -1);
        assertThat(blackKnight.canMove(coordinate)).isEqualTo(false);
        assertThat(whiteKnight.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 나이트가 정상적으로 빈 칸으로 이동")
    public void moveBlackKnightNormal() {
        int[] rows = {0, 0, 1, 1, 3, 3, 4, 4};
        int[] cols = {1, 3, 0, 4, 0, 4, 1, 3};

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(blackKnight.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("검은색 나이트가 정상적인 곳으로 이동할 수 없음")
    public void moveBlackKnightAbnormal() {
        int[] rows = {0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        int[] cols = {0, 2, 4, 1, 2, 3, 0, 1, 3, 4, 1, 2, 3, 0, 2, 4};

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(blackKnight.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("검은색 나이트가 같은팀 말이 있는 곳으로 이동할 수 없음")
    public void moveBlackKnightToAlliance() {
        int[] rows = {0, 0, 1, 1, 3, 3, 4, 4};
        int[] cols = {1, 3, 0, 4, 0, 4, 1, 3};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new Knight(rows[i], cols[i], Color.BLACK, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);

            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(blackKnight.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("검은색 나이트가 상대방 말이 있는 곳으로 이동할 수 있음")
    public void moveBlackKnightToEnemy() {
        int[] rows = {0, 0, 1, 1, 3, 3, 4, 4};
        int[] cols = {1, 3, 0, 4, 0, 4, 1, 3};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new Knight(rows[i], cols[i], Color.WHITE, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);

            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(blackKnight.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 나이트가 정상적으로 빈 칸으로 이동")
    public void moveWhiteKnightNormal() {
        int[] rows = {3, 3, 4, 4, 6, 6, 7, 7};
        int[] cols = {4, 6, 3, 7, 3, 7, 4, 6};

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(whiteKnight.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 나이트가 정상적인 곳으로 이동할 수 없음")
    public void moveWhiteKnightAbnormal() {
        int[] rows = {3, 3, 3, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7};
        int[] cols = {3, 5, 7, 4, 5, 6, 3, 4, 6, 7, 4, 5, 6, 3, 5, 7};

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(whiteKnight.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("흰색 나이트가 같은팀 말이 있는 곳으로 이동할 수 없음")
    public void moveWhiteKnightToAlliance() {
        int[] rows = {3, 3, 4, 4, 6, 6, 7, 7};
        int[] cols = {4, 6, 3, 7, 3, 7, 4, 6};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new Knight(rows[i], cols[i], Color.WHITE, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);

            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(whiteKnight.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("흰색 나이트가 상대방 말이 있는 곳으로 이동할 수 있음")
    public void moveWhiteKnightToEnemy() {
        int[] rows = {3, 3, 4, 4, 6, 6, 7, 7};
        int[] cols = {4, 6, 3, 7, 3, 7, 4, 6};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new Knight(rows[i], cols[i], Color.BLACK, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);

            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(whiteKnight.canMove(coordinate)).isEqualTo(true);
        }
    }
}
