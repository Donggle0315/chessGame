package practice.tdd.chess.game.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.tdd.chess.game.piece.Color;
import practice.tdd.chess.game.piece.Piece;
import practice.tdd.chess.game.piece.Queen;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueenMoveTest {
    private BoardBuilder builder;
    private Board board;

    private Piece blackQueen;
    private Piece whiteQueen;

    @BeforeEach
    public void init() {
        builder = new BoardBuilder();
        board = builder.buildChessBoard();
        blackQueen = new Queen(2, 2, Color.BLACK, board);
        whiteQueen = new Queen(5, 4, Color.WHITE, board);
        board.setPieceOnBoard(2, 2, blackQueen);
        board.setPieceOnBoard(5, 4, whiteQueen);
    }

    @Test
    @DisplayName("보드 범위 밖으로 나가면 예외 발생")
    public void moveOutBoardMakeException() {
        Coordinate coordinate = new Coordinate(-1, -1);

        assertThat(blackQueen.canMove(coordinate)).isEqualTo(false);
        assertThat(whiteQueen.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검정색 퀸이 상하로 정상적으로 이동 가능")
    public void moveBlackQueenVertical() {
        int[] rows = {0, 1, 3, 4, 5, 6, 7};

        for (int row : rows) {
            Coordinate coordinate = new Coordinate(row, 2);
            assertThat(blackQueen.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("검정색 퀸이 좌우로 정상적으로 이동 가능")
    public void moveBlackQueenHorizontal() {
        int[] cols = {0, 1, 3, 4, 5, 6, 7};

        for (int col : cols) {
            Coordinate coordinate = new Coordinate(2, col);
            assertThat(blackQueen.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("검정색 퀸이 대각선으로 정상적으로 이동 가능")
    public void moveBlackQueenDiagonal() {
        int[] rows = {0, 1, 3, 4, 5, 6, 7, 4, 3, 1, 0};
        int[] cols = {0, 1, 3, 4, 5, 6, 7, 0, 1, 3, 4};

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(blackQueen.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("검정색 퀸이 상하좌우 길 중간에 말이 있을 경우 이동 불가")
    public void moveBlackQueenVerticalHorizontalBetweenWay() {
        Piece piece1 = new Queen(2, 1, Color.BLACK, board);
        board.setPieceOnBoard(2, 1, piece1);

        Piece piece2 = new Queen(1, 2, Color.BLACK, board);
        board.setPieceOnBoard(1, 2, piece2);

        Piece piece3 = new Queen(2, 3, Color.BLACK, board);
        board.setPieceOnBoard(2, 3, piece3);

        Piece piece4 = new Queen(3, 2, Color.BLACK, board);
        board.setPieceOnBoard(3, 2, piece4);

        List<Coordinate> coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(2, 0));
        coordinateList.add(new Coordinate(0, 2));
        coordinateList.add(new Coordinate(2, 4));
        coordinateList.add(new Coordinate(4, 2));

        for (Coordinate coordinate : coordinateList) {
            assertThat(blackQueen.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("검정색 퀸이 대각선 길 중간에 말이 있을 경우 이동 불가")
    public void moveBlackQueenDiagonalBetweenWay() {
        Piece piece1 = new Queen(1, 1, Color.BLACK, board);
        board.setPieceOnBoard(1, 1, piece1);

        Piece piece2 = new Queen(3, 3, Color.BLACK, board);
        board.setPieceOnBoard(3, 3, piece2);

        Piece piece3 = new Queen(1, 3, Color.BLACK, board);
        board.setPieceOnBoard(1, 3, piece3);

        Piece piece4 = new Queen(3, 1, Color.BLACK, board);
        board.setPieceOnBoard(3, 1, piece4);

        List<Coordinate> coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(0, 0));
        coordinateList.add(new Coordinate(4, 4));
        coordinateList.add(new Coordinate(0, 4));
        coordinateList.add(new Coordinate(4, 0));

        for (Coordinate coordinate : coordinateList) {
            assertThat(blackQueen.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("검정색 퀸이 같은 팀 말이 있는 곳으로 이동 불가")
    public void moveBlackQueenToAlliance() {
        int[] rows = {1, 1, 1, 2, 2, 3, 3, 3};
        int[] cols = {1, 2, 3, 1, 3, 1, 2, 3};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new Queen(rows[i], cols[i], Color.BLACK, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);
        }

        for (int i = 0; i < rows.length; i++) {
            assertThat(blackQueen.canMove(new Coordinate(rows[i], cols[i]))).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("검정색 퀸이 상대 팀 말이 있는 곳으로 이동 가능")
    public void moveBlackQueenToEnemy() {
        int[] rows = {1, 1, 1, 2, 2, 3, 3, 3};
        int[] cols = {1, 2, 3, 1, 3, 1, 2, 3};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new Queen(rows[i], cols[i], Color.WHITE, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);
        }

        for (int i = 0; i < rows.length; i++) {
            assertThat(blackQueen.canMove(new Coordinate(rows[i], cols[i]))).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 퀸이 상하로 정상적으로 이동 가능")
    public void moveWhiteQueenVertical() {
        int[] rows = {0, 1, 2, 3, 4, 6, 7};

        for (int row : rows) {
            Coordinate coordinate = new Coordinate(row, 4);
            assertThat(whiteQueen.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 퀸이 좌우로 정상적으로 이동 가능")
    public void moveWhiteQueenHorizontal() {
        int[] cols = {0, 1, 2, 3, 5, 6, 7};

        for (int col : cols) {
            Coordinate coordinate = new Coordinate(5, col);
            assertThat(whiteQueen.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 퀸이 대각선으로 정상적으로 이동 가능")
    public void moveWhiteQueenDiagonal() {
        int[] rows = {1, 2, 3, 4, 6, 7, 7, 6, 4, 3, 2};
        int[] cols = {0, 1, 2, 3, 5, 6, 2, 3, 5, 6, 7};

        for (int i = 0; i < rows.length; i++) {
            Coordinate coordinate = new Coordinate(rows[i], cols[i]);
            assertThat(whiteQueen.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 퀸이 상하좌우 길 중간에 말이 있을 경우 이동 불가")
    public void moveWhiteQueenVerticalHorizontalBetweenWay() {
        Piece piece1 = new Queen(4, 4, Color.WHITE, board);
        board.setPieceOnBoard(4, 4, piece1);

        Piece piece2 = new Queen(5, 3, Color.WHITE, board);
        board.setPieceOnBoard(5, 3, piece2);

        Piece piece3 = new Queen(5, 5, Color.WHITE, board);
        board.setPieceOnBoard(5, 5, piece3);

        Piece piece4 = new Queen(6, 4, Color.WHITE, board);
        board.setPieceOnBoard(6, 4, piece4);

        List<Coordinate> coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(3, 4));
        coordinateList.add(new Coordinate(5, 2));
        coordinateList.add(new Coordinate(5, 6));
        coordinateList.add(new Coordinate(7, 4));

        for (Coordinate coordinate : coordinateList) {
            assertThat(whiteQueen.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("흰색 퀸이 대각선 길 중간에 말이 있을 경우 이동 불가")
    public void moveWhiteQueenDiagonalBetweenWay() {
        Piece piece1 = new Queen(4, 3, Color.WHITE, board);
        board.setPieceOnBoard(4, 3, piece1);

        Piece piece2 = new Queen(4, 5, Color.WHITE, board);
        board.setPieceOnBoard(4, 5, piece2);

        Piece piece3 = new Queen(6, 3, Color.WHITE, board);
        board.setPieceOnBoard(6, 3, piece3);

        Piece piece4 = new Queen(6, 5, Color.WHITE, board);
        board.setPieceOnBoard(6, 5, piece4);

        List<Coordinate> coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(3, 2));
        coordinateList.add(new Coordinate(3, 6));
        coordinateList.add(new Coordinate(7, 2));
        coordinateList.add(new Coordinate(7, 6));

        for (Coordinate coordinate : coordinateList) {
            assertThat(whiteQueen.canMove(coordinate)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("흰색 퀸이 같은 팀 말이 있는 곳으로 이동 불가")
    public void moveWhiteQueenToAlliance() {
        int[] rows = {4, 4, 4, 5, 5, 6, 6, 6};
        int[] cols = {3, 4, 5, 3, 5, 3, 4, 5};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new Queen(rows[i], cols[i], Color.WHITE, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);
        }

        for (int i = 0; i < rows.length; i++) {
            assertThat(whiteQueen.canMove(new Coordinate(rows[i], cols[i]))).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("흰색 퀸이 상대 팀 말이 있는 곳으로 이동 가능")
    public void moveWhiteQueenToEnemy() {
        int[] rows = {4, 4, 4, 5, 5, 6, 6, 6};
        int[] cols = {3, 4, 5, 3, 5, 3, 4, 5};

        for (int i = 0; i < rows.length; i++) {
            Piece piece = new Queen(rows[i], cols[i], Color.BLACK, board);
            board.setPieceOnBoard(rows[i], cols[i], piece);
        }

        for (int i = 0; i < rows.length; i++) {
            assertThat(whiteQueen.canMove(new Coordinate(rows[i], cols[i]))).isEqualTo(true);
        }
    }
}
