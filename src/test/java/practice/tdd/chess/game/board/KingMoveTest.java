package practice.tdd.chess.game.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.tdd.chess.game.piece.Color;
import practice.tdd.chess.game.piece.King;
import practice.tdd.chess.game.piece.Piece;
import practice.tdd.chess.game.piece.Rook;

import static org.assertj.core.api.Assertions.assertThat;

public class KingMoveTest {
    private BoardBuilder builder;
    private Board board;

    private Piece blackKing;
    private Piece whiteKing;

    @BeforeEach
    public void init() {
        builder = new BoardBuilder();
        board = builder.buildChessBoard();
        blackKing = new King(2, 2, Color.BLACK, board);
        whiteKing = new King(5, 5, Color.WHITE, board);
        board.setPieceOnBoard(2, 2, blackKing);
        board.setPieceOnBoard(5, 5, whiteKing);
    }

    @Test
    @DisplayName("범위 밖으로 나가면 예외 발생")
    public void checkOutOfRange() {
        Coordinate coordinate = new Coordinate(-1, -1);
        assertThat(blackKing.canMove(coordinate)).isEqualTo(false);
        assertThat(whiteKing.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 왕이 수직으로 한 칸 정상적으로 이동 가능")
    public void moveBlackKingVertical() {
        Coordinate coordinate1 = new Coordinate(1, 2);
        Coordinate coordinate2 = new Coordinate(3, 2);

        assertThat(blackKing.canMove(coordinate1)).isEqualTo(true);
        assertThat(blackKing.canMove(coordinate2)).isEqualTo(true);
    }

    @Test
    @DisplayName("검은색 왕이 수평으로 한 칸 정상적으로 이동 가능")
    public void moveBlackKingHorizontal() {
        Coordinate coordinate1 = new Coordinate(2, 1);
        Coordinate coordinate2 = new Coordinate(2, 3);

        assertThat(blackKing.canMove(coordinate1)).isEqualTo(true);
        assertThat(blackKing.canMove(coordinate2)).isEqualTo(true);
    }

    @Test
    @DisplayName("검은색 왕이 대각선으로 한 칸 정상적으로 이동 가능")
    public void moveBlackKingDiagonal() {
        Coordinate coordinate1 = new Coordinate(1, 1);
        Coordinate coordinate2 = new Coordinate(3, 3);
        Coordinate coordinate3 = new Coordinate(3, 1);
        Coordinate coordinate4 = new Coordinate(1, 3);

        assertThat(blackKing.canMove(coordinate1)).isEqualTo(true);
        assertThat(blackKing.canMove(coordinate2)).isEqualTo(true);
        assertThat(blackKing.canMove(coordinate3)).isEqualTo(true);
        assertThat(blackKing.canMove(coordinate4)).isEqualTo(true);
    }

    @Test
    @DisplayName("검은색 왕이 정상적으로 이동하는 곳에 같은 팀 말이 있으면 이동 불가")
    public void moveBlackKingToAlliance() {
        int[] rows = {1, 1, 1, 2, 2, 3, 3, 3};
        int[] cols = {1, 2, 3, 1, 3, 1, 2, 3};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new King(rows[i], cols[i], Color.BLACK, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);
        }

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(blackKing.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("상대방 말이 움직이는 곳으로 올 수 있다면 검은색 왕은 이동 불가")
    public void moveBlackKingEnemyCanAttack() {
        Piece piece = new Rook(3, 5, Color.WHITE, board);
        board.setPieceOnBoard(3, 5, piece);

        Coordinate coordinate = new Coordinate(3, 2);
        assertThat(blackKing.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 왕이 수직으로 한 칸 정상적으로 이동 가능")
    public void moveWhiteKingVertical() {
        Coordinate coordinate1 = new Coordinate(4, 5);
        Coordinate coordinate2 = new Coordinate(6, 5);

        assertThat(whiteKing.canMove(coordinate1)).isEqualTo(true);
        assertThat(whiteKing.canMove(coordinate2)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 왕이 수평으로 한 칸 정상적으로 이동 가능")
    public void moveWhiteKingHorizontal() {
        Coordinate coordinate1 = new Coordinate(5, 6);
        Coordinate coordinate2 = new Coordinate(5, 4);

        assertThat(whiteKing.canMove(coordinate1)).isEqualTo(true);
        assertThat(whiteKing.canMove(coordinate2)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 왕이 대각선으로 한 칸 정상적으로 이동 가능")
    public void moveWhiteKingDiagonal() {
        Coordinate coordinate1 = new Coordinate(4, 4);
        Coordinate coordinate2 = new Coordinate(4, 6);
        Coordinate coordinate3 = new Coordinate(6, 4);
        Coordinate coordinate4 = new Coordinate(6, 6);

        assertThat(whiteKing.canMove(coordinate1)).isEqualTo(true);
        assertThat(whiteKing.canMove(coordinate2)).isEqualTo(true);
        assertThat(whiteKing.canMove(coordinate3)).isEqualTo(true);
        assertThat(whiteKing.canMove(coordinate4)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 왕이 정상적으로 이동하는 곳에 같은 팀 말이 있으면 이동 불가")
    public void moveWhiteKingToAlliance() {
        int[] rows = {4, 4, 4, 5, 5, 6, 6, 6};
        int[] cols = {4, 5, 6, 4, 6, 4, 5, 6};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new King(rows[i], cols[i], Color.WHITE, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);
        }

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(whiteKing.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("상대방 말이 움직이는 곳으로 올 수 있다면 흰색 왕은 이동 불가")
    public void moveWhiteKingEnemyCanAttack() {
        Piece piece = new Rook(3, 4, Color.BLACK, board);
        board.setPieceOnBoard(3, 4, piece);

        Coordinate coordinate = new Coordinate(5, 4);
        assertThat(whiteKing.canMove(coordinate)).isEqualTo(false);
    }
}
