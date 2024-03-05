package practice.tdd.chess.game.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.service.BoardBuilder;
import practice.tdd.chess.game.domain.board.Coordinate;
import practice.tdd.chess.game.domain.piece.Bishop;
import practice.tdd.chess.game.domain.piece.Color;
import practice.tdd.chess.game.domain.piece.Piece;

import static org.assertj.core.api.Assertions.assertThat;

public class BishopMoveTest {
    private BoardBuilder builder;
    private Board board;

    private Piece blackBishop;
    private Piece whiteBishop;

    @BeforeEach
    public void init() {
        builder = new BoardBuilder();
        board = builder.buildChessBoard();
        blackBishop = new Bishop(2, 2, Color.BLACK, board);
        whiteBishop = new Bishop(5, 4, Color.WHITE, board);
        board.setPieceOnBoard(2, 2, blackBishop);
        board.setPieceOnBoard(5, 4, whiteBishop);
    }

    @Test
    @DisplayName("범위 밖으로 나가면 예외 발생")
    public void checkOutOfRange() {
        Coordinate coordinate = new Coordinate(-1, -1);
        assertThat(blackBishop.canMove(coordinate)).isEqualTo(false);
        assertThat(whiteBishop.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 비숍이 정상적으로 빈 칸 이동")
    public void moveBlackBishopNormal() {
        int[] rows = {0, 1, 3, 4, 5, 6, 7, 4, 3, 1, 0};
        int[] cols = {0, 1, 3, 4, 5, 6, 7, 0, 1, 3, 4};

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(blackBishop.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("검은색 비숍이 비정상적으로 빈 칸 이동")
    public void moveBlackBishopAbnormal() {
        int[][] cols = {{1, 2, 3, 5, 6, 7},
                {0, 2, 4, 5, 6, 7},
                {0, 1, 3, 4, 5, 6, 7},
                {0, 2, 4, 5, 6, 7},
                {1, 2, 3, 5, 6, 7},
                {0, 1, 2, 3, 4, 6, 7},
                {0, 1, 2, 3, 4, 5, 7},
                {0, 1, 2, 3, 4, 5, 6}};

        for (int i = 0; i < 8; i++) {
            for (int j : cols[i]) {
                Coordinate coordinate = new Coordinate(i, j);
                assertThat(blackBishop.canMove(coordinate)).isEqualTo(false);
            }
        }
    }

    @Test
    @DisplayName("검은색 비숍이 중간에 말이 있다면 이동하지 못함")
    public void moveBlackBishopBetweenWay() {
        Piece piece = new Bishop(4, 4, Color.BLACK, board);
        board.setPieceOnBoard(4, 4, piece);

        Coordinate coordinate = new Coordinate(5, 5);
        assertThat(blackBishop.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 비숍이 같은팀 말이 있다면 이동하지 못함")
    public void moveBlackBishopOnAlliance() {
        Piece piece = new Bishop(4, 4, Color.BLACK, board);
        board.setPieceOnBoard(4, 4, piece);

        Coordinate coordinate = new Coordinate(4, 4);
        assertThat(blackBishop.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 비숍이 상대팀 말이 있다면 이동 가능")
    public void moveBlackBishopOnEnemy() {
        Piece piece = new Bishop(4, 4, Color.WHITE, board);
        board.setPieceOnBoard(4, 4, piece);

        Coordinate coordinate = new Coordinate(4, 4);
        assertThat(blackBishop.canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 비숍이 정상적으로 빈 칸 이동")
    public void moveWhiteBishopNormal() {
        int[] rows = {1, 2, 3, 4, 6, 7, 7, 6, 4, 3, 2};
        int[] cols = {0, 1, 2, 3, 5, 6, 2, 3, 5, 6, 7};

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(whiteBishop.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 비숍이 비정상적으로 빈 칸 이동")
    public void moveWhiteBishopAbnormal() {
        int[][] cols = {{0, 1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {0, 2, 3, 4, 5, 6},
                {0, 1, 3, 4, 5, 7},
                {0, 1, 2, 4, 6, 7},
                {0, 1, 2, 3, 5, 6, 7},
                {0, 1, 2, 4, 6, 7},
                {0, 1, 3, 4, 5, 7}};

        for (int i = 0; i < 8; i++) {
            for (int j : cols[i]) {
                Coordinate coordinate = new Coordinate(i, j);
                assertThat(whiteBishop.canMove(coordinate)).isEqualTo(false);
            }
        }
    }

    @Test
    @DisplayName("흰색 비숍이 중간에 말이 있다면 이동하지 못함")
    public void moveWhiteBishopBetweenWay() {
        Piece piece = new Bishop(3, 2, Color.WHITE, board);
        board.setPieceOnBoard(3, 2, piece);

        Coordinate coordinate = new Coordinate(2, 1);
        assertThat(whiteBishop.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 비숍이 같은팀 말이 있다면 이동하지 못함")
    public void moveWhiteBishopOnAlliance() {
        Piece piece = new Bishop(2, 1, Color.WHITE, board);
        board.setPieceOnBoard(2, 1, piece);

        Coordinate coordinate = new Coordinate(2, 1);
        assertThat(whiteBishop.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 비숍이 상대팀 말이 있다면 이동 가능")
    public void moveWhiteBishopOnEnemy() {
        Piece piece = new Bishop(2, 1, Color.BLACK, board);
        board.setPieceOnBoard(2, 1, piece);

        Coordinate coordinate = new Coordinate(2, 1);
        assertThat(whiteBishop.canMove(coordinate)).isEqualTo(true);
    }
}
