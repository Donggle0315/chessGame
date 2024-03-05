package practice.tdd.chess.game.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.service.BoardBuilder;
import practice.tdd.chess.game.domain.board.Coordinate;
import practice.tdd.chess.game.domain.piece.Color;
import practice.tdd.chess.game.domain.piece.Piece;
import practice.tdd.chess.game.domain.piece.Rook;

import static org.assertj.core.api.Assertions.assertThat;

public class RookMoveTest {
    private BoardBuilder builder;
    private Board board;

    private Piece blackRook;
    private Piece whiteRook;

    @BeforeEach
    public void init() {
        builder = new BoardBuilder();
        board = builder.buildChessBoard();
        blackRook = new Rook(2, 2, Color.BLACK, board);
        whiteRook = new Rook(5, 5, Color.WHITE, board);
        board.setPieceOnBoard(2, 2, blackRook);
        board.setPieceOnBoard(5, 5, whiteRook);
    }

    @Test
    @DisplayName("검은색 룩이 앞으로 이동할 수 있음")
    public void moveBlackRookForward() {
        for (int i = 3; i < 8; i++) {
            Coordinate coordinate = new Coordinate(i, 2);
            assertThat(blackRook.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("검은색 룩이 뒤로 이동할 수 있음")
    public void moveBlackRookBackward() {
        for (int i = 1; i >= 0; i--) {
            Coordinate coordinate = new Coordinate(i, 2);
            assertThat(blackRook.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("검은색 룩이 오른쪽으로 이동할 수 있음")
    public void moveBlackRookRightSide() {
        for (int i = 3; i < 8; i++) {
            Coordinate coordinate = new Coordinate(2, i);
            assertThat(blackRook.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("검은색 룩이 왼쪽으로 이동할 수 있음")
    public void moveBlackRookLeftSide() {
        for (int i = 1; i >= 0; i--) {
            Coordinate coordinate = new Coordinate(2, i);
            assertThat(blackRook.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("검은색 룩이 대각선으로 이동할 수 없음")
    public void moveBlackRookDiagonal() {
        for (int i = 3; i < 5; i++) {
            Coordinate coordinate = new Coordinate(i, i);
            assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
        }

        for (int i = 1; i >= 0; i--) {
            Coordinate coordinate = new Coordinate(i, i);
            assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
        }

        for (int i = 3, j = 1; i < 5; i++, j--) {
            Coordinate coordinate1 = new Coordinate(i, j);
            assertThat(blackRook.canMove(coordinate1)).isEqualTo(false);
            Coordinate coordinate2 = new Coordinate(j, i);
            assertThat(blackRook.canMove(coordinate2)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("검은색 룩이 앞으로 가는 동안 다른 말이 존재하면 안됨")
    public void moveBlackRookForwardWithoutPieceOnWay() {
        Piece piece = new Rook(4, 2, Color.BLACK, board);
        board.setPieceOnBoard(4, 2, piece);
        Coordinate coordinate = new Coordinate(7, 2);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 룩이 뒤로 가는 동안 다른 말이 존재하면 안됨")
    public void moveBlackRookBackwardWithoutPieceOnWay() {
        Piece piece = new Rook(1, 2, Color.BLACK, board);
        board.setPieceOnBoard(1, 2, piece);
        Coordinate coordinate = new Coordinate(0, 2);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 룩이 오른쪽으로 가는 동안 다른 말이 존재하면 안됨")
    public void moveBlackRookRightSideWithoutPieceOnWay() {
        Piece piece = new Rook(2, 4, Color.BLACK, board);
        board.setPieceOnBoard(2, 4, piece);
        Coordinate coordinate = new Coordinate(2, 7);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 룩이 왼쪽으로 가는 동안 다른 말이 존재하면 안됨")
    public void moveBlackRookLeftSideWithoutPieceOnWay() {
        Piece piece = new Rook(2, 1, Color.BLACK, board);
        board.setPieceOnBoard(2, 1, piece);
        Coordinate coordinate = new Coordinate(2, 0);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
    }


    @Test
    @DisplayName("검은색 룩이 앞에 있는 상대방 말까지 갈 수 있음")
    public void moveBlackRookForwardToEnemy() {
        Piece piece = new Rook(4, 2, Color.WHITE, board);
        board.setPieceOnBoard(4, 2, piece);

        Coordinate coordinate = new Coordinate(4, 2);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("검은색 룩이 뒤에 있는 상대방 말까지 갈 수 있음")
    public void moveBlackRookBackwardToEnemy() {
        Piece piece = new Rook(0, 2, Color.WHITE, board);
        board.setPieceOnBoard(0, 2, piece);

        Coordinate coordinate = new Coordinate(0, 2);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("검은색 룩이 오른쪽에 있는 상대방 말까지 갈 수 있음")
    public void moveBlackRookRightSideToEnemy() {
        Piece piece = new Rook(2, 4, Color.WHITE, board);
        board.setPieceOnBoard(2, 4, piece);

        Coordinate coordinate = new Coordinate(2, 4);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("검은색 룩이 왼쪽에 있는 상대방 말까지 갈 수 있음")
    public void moveBlackRookLeftSideToEnemy() {
        Piece piece = new Rook(2, 0, Color.WHITE, board);
        board.setPieceOnBoard(2, 0, piece);

        Coordinate coordinate = new Coordinate(2, 0);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("검은색 룩이 앞에 있는 같은 팀 말까지 갈 수 없음")
    public void moveBlackRookForwardToAlliance() {
        Piece piece = new Rook(4, 2, Color.BLACK, board);
        board.setPieceOnBoard(4, 2, piece);

        Coordinate coordinate = new Coordinate(4, 2);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 룩이 뒤에 있는 같은 팀 말까지 갈 수 없음")
    public void moveBlackRookBackwardToAlliance() {
        Piece piece = new Rook(0, 2, Color.BLACK, board);
        board.setPieceOnBoard(0, 2, piece);

        Coordinate coordinate = new Coordinate(0, 2);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 룩이 오른쪽에 있는 같은 팀 말까지 갈 수 없음")
    public void moveBlackRookRightSideToAlliance() {
        Piece piece = new Rook(2, 4, Color.BLACK, board);
        board.setPieceOnBoard(2, 4, piece);

        Coordinate coordinate = new Coordinate(2, 4);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 룩이 왼쪽에 있는 같은 팀 말까지 갈 수 없음")
    public void moveBlackRookLeftSideToAlliance() {
        Piece piece = new Rook(2, 0, Color.BLACK, board);
        board.setPieceOnBoard(2, 0, piece);

        Coordinate coordinate = new Coordinate(2, 0);
        assertThat(blackRook.canMove(coordinate)).isEqualTo(false);
    }

    /* 흰색 말 테스트 */
    @Test
    @DisplayName("흰색 룩이 앞으로 이동할 수 있음")
    public void moveWhiteRookForward() {
        for (int i = 4; i >= 0; i--) {
            Coordinate coordinate = new Coordinate(i, 5);
            assertThat(whiteRook.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 룩이 뒤로 이동할 수 있음")
    public void moveWhiteRookBackward() {
        for (int i = 6; i < 8; i++) {
            Coordinate coordinate = new Coordinate(i, 5);
            assertThat(whiteRook.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 룩이 오른쪽으로 이동할 수 있음")
    public void moveWhiteRookRightSide() {
        for (int i = 6; i < 8; i++) {
            Coordinate coordinate = new Coordinate(5, i);
            assertThat(whiteRook.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 룩이 왼쪽으로 이동할 수 있음")
    public void moveWhiteRookLeftSide() {
        for (int i = 4; i >= 0; i--) {
            Coordinate coordinate = new Coordinate(5, i);
            assertThat(whiteRook.canMove(coordinate)).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("흰색 룩이 대각선으로 이동할 수 없음")
    public void moveWhiteRookDiagonal() {
        for (int i = 6; i < 8; i++) {
            Coordinate coordinate = new Coordinate(i, i);
            assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
        }

        for (int i = 4; i >= 0; i--) {
            Coordinate coordinate = new Coordinate(i, i);
            assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
        }

        for (int i = 6, j = 4; i < 8; i++, j--) {
            Coordinate coordinate1 = new Coordinate(i, j);
            assertThat(whiteRook.canMove(coordinate1)).isEqualTo(false);
            Coordinate coordinate2 = new Coordinate(j, i);
            assertThat(whiteRook.canMove(coordinate2)).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("흰색 룩이 앞으로 가는 동안 다른 말이 존재하면 안됨")
    public void moveWhiteRookForwardWithoutPieceOnWay() {
        Piece piece = new Rook(4, 5, Color.WHITE, board);
        board.setPieceOnBoard(4, 5, piece);
        Coordinate coordinate = new Coordinate(3, 5);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 룩이 뒤로 가는 동안 다른 말이 존재하면 안됨")
    public void moveWhiteRookBackwardWithoutPieceOnWay() {
        Piece piece = new Rook(6, 5, Color.WHITE, board);
        board.setPieceOnBoard(6, 5, piece);
        Coordinate coordinate = new Coordinate(7, 5);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 룩이 오른쪽으로 가는 동안 다른 말이 존재하면 안됨")
    public void moveWhiteRookRightSideWithoutPieceOnWay() {
        Piece piece = new Rook(5, 6, Color.WHITE, board);
        board.setPieceOnBoard(5, 6, piece);
        Coordinate coordinate = new Coordinate(5, 7);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 룩이 왼쪽으로 가는 동안 다른 말이 존재하면 안됨")
    public void moveWhiteRookLeftSideWithoutPieceOnWay() {
        Piece piece = new Rook(5, 1, Color.WHITE, board);
        board.setPieceOnBoard(5, 1, piece);
        Coordinate coordinate = new Coordinate(5, 0);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
    }


    @Test
    @DisplayName("흰색 룩이 앞에 있는 상대방 말까지 갈 수 있음")
    public void moveWhiteRookForwardToEnemy() {
        Piece piece = new Rook(4, 5, Color.BLACK, board);
        board.setPieceOnBoard(4, 5, piece);

        Coordinate coordinate = new Coordinate(4, 5);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 룩이 뒤에 있는 상대방 말까지 갈 수 있음")
    public void moveWhiteRookBackwardToEnemy() {
        Piece piece = new Rook(6, 5, Color.BLACK, board);
        board.setPieceOnBoard(6, 5, piece);

        Coordinate coordinate = new Coordinate(6, 5);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 룩이 오른쪽에 있는 상대방 말까지 갈 수 있음")
    public void moveWhiteRookRightSideToEnemy() {
        Piece piece = new Rook(5, 7, Color.BLACK, board);
        board.setPieceOnBoard(5, 7, piece);

        Coordinate coordinate = new Coordinate(5, 7);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 룩이 왼쪽에 있는 상대방 말까지 갈 수 있음")
    public void moveWhiteRookLeftSideToEnemy() {
        Piece piece = new Rook(5, 0, Color.BLACK, board);
        board.setPieceOnBoard(5, 0, piece);

        Coordinate coordinate = new Coordinate(5, 0);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 룩이 앞에 있는 같은 팀 말까지 갈 수 없음")
    public void moveWhiteRookForwardToAlliance() {
        Piece piece = new Rook(4, 5, Color.WHITE, board);
        board.setPieceOnBoard(4, 5, piece);

        Coordinate coordinate = new Coordinate(4, 5);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 룩이 뒤에 있는 같은 팀 말까지 갈 수 없음")
    public void moveWhiteRookBackwardToAlliance() {
        Piece piece = new Rook(7, 5, Color.WHITE, board);
        board.setPieceOnBoard(7, 5, piece);

        Coordinate coordinate = new Coordinate(7, 5);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 룩이 오른쪽에 있는 같은 팀 말까지 갈 수 없음")
    public void moveWhiteRookRightSideToAlliance() {
        Piece piece = new Rook(5, 7, Color.WHITE, board);
        board.setPieceOnBoard(5, 7, piece);

        Coordinate coordinate = new Coordinate(5, 7);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 룩이 왼쪽에 있는 같은 팀 말까지 갈 수 없음")
    public void moveWhiteRookLeftSideToAlliance() {
        Piece piece = new Rook(5, 0, Color.WHITE, board);
        board.setPieceOnBoard(5, 0, piece);

        Coordinate coordinate = new Coordinate(5, 0);
        assertThat(whiteRook.canMove(coordinate)).isEqualTo(false);
    }
}
