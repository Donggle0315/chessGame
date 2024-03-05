package practice.tdd.chess.game.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.tdd.chess.game.domain.board.Board;
import practice.tdd.chess.game.service.BoardBuilder;
import practice.tdd.chess.game.domain.board.Coordinate;
import practice.tdd.chess.game.domain.piece.Color;
import practice.tdd.chess.game.domain.piece.EmptyPiece;
import practice.tdd.chess.game.domain.piece.Pawn;
import practice.tdd.chess.game.domain.piece.Piece;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnMoveTest {
    private BoardBuilder builder;
    private Board board;

    private Piece blackPawn;
    private Piece whitePawn;

    @BeforeEach
    public void init() {
        builder = new BoardBuilder();
        board = builder.buildChessBoard();
        blackPawn = new Pawn(1, 1, Color.BLACK, board);
        whitePawn = new Pawn(6, 6, Color.WHITE, board);
        board.setPieceOnBoard(1, 1, blackPawn);
        board.setPieceOnBoard(6, 6, whitePawn);
    }
    /* 공통 테스트 */
    @Test
    @DisplayName("보드 범위 밖으로 나가면 예외 발생")
    public void moveOutBoardMakeException() {
        Pawn pawn1 = new Pawn(0, 0, Color.BLACK, board);
        board.setPieceOnBoard(0, 0, pawn1);
        Pawn pawn2 = new Pawn(7, 7, Color.BLACK, board);
        board.setPieceOnBoard(7, 7, pawn2);

        Coordinate coordinate1 = new Coordinate(-1, 0);
        Coordinate coordinate2 = new Coordinate(0, -1);
        Coordinate coordinate3 = new Coordinate(8, 7);
        Coordinate coordinate4 = new Coordinate(7, 8);

        assertThat(board.getPiece(0, 0).canMove(coordinate1)).isEqualTo(false);
        assertThat(board.getPiece(0, 0).canMove(coordinate2)).isEqualTo(false);
        assertThat(board.getPiece(7, 7).canMove(coordinate3)).isEqualTo(false);
        assertThat(board.getPiece(7, 7).canMove(coordinate4)).isEqualTo(false);

    }

    /* 검은색 말 테스트 */
    @Test
    @DisplayName("검은색 폰이 앞 빈으로 한칸 이동 가능 여부 확인")
    public void moveBlackOneForwardEmptyPoint() {
        Coordinate coordinate = new Coordinate(2, 1);

        assertThat(board.getPiece(1, 1).canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("검은색 폰 앞에 같은 팀 말이 있다면 이동할 수 없음")
    public void moveBlackOneForwardPiecePoint() {
        Coordinate coordinate = new Coordinate(2, 1);
        board.setPieceOnBoard(2,1,new Pawn(2,1,Color.BLACK, board));

        assertThat(board.getPiece(1, 1).canMove(coordinate)).isEqualTo(false);
    }


    @Test
    @DisplayName("검은색 폰이 처음에 두 칸 이동 가능 여부 확인")
    public void moveBlackFirstTwoForwardEmptyPoint() {
        Coordinate coordinate = new Coordinate(3, 1);

        assertThat(board.getPiece(1, 1).canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("검은색 폰 두 칸 앞에 같은 팀 말이 있다면 두 칸 이동할 수 없음")
    public void moveBlackFirstTwoForwardPiecePoint() {
        Coordinate coordinate = new Coordinate(3, 1);
        board.setPieceOnBoard(3,1,new Pawn(3,1,Color.BLACK, board));

        assertThat(board.getPiece(1, 1).canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 폰 한 칸 앞에 같은 팀 말이 있다면 두 칸 이동할 수 없음")
    public void moveBlackFirstTwoForwardPieceBetweenPoint() {
        Coordinate coordinate = new Coordinate(3, 1);
        board.setPieceOnBoard(2, 1, new Pawn(2, 1, Color.BLACK, board));

        assertThat(board.getPiece(1, 1).canMove(coordinate)).isEqualTo(false);

    }

    @Test
    @DisplayName("검은색 폰이 좌로 움직일 수 없음 여부 확인")
    public void moveBlackOneLeft() {
        Coordinate coordinate = new Coordinate(1, 0);

        assertThat(board.getPiece(1, 1).canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 폰이 우로 움직일 수 없음 여부 확인")
    public void moveBlackOneRight() {
        Coordinate coordinate = new Coordinate(1, 3);

        assertThat(board.getPiece(1, 1).canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 폰이 뒤로 움직일 수 없음 여부 확인")
    public void moveBlackOneBack() {
        Coordinate coordinate = new Coordinate(0, 1);

        assertThat(board.getPiece(1, 1).canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 폰이 대각선에 상대방 말이 없는 경우 움직일 수 없음")
    public void moveBlackDiagonalNoEnemyExist() {
        Coordinate coordinate1 = new Coordinate(2, 2);
        Coordinate coordinate2 = new Coordinate(0, 2);
        Coordinate coordinate3 = new Coordinate(0, 0);
        Coordinate coordinate4 = new Coordinate(2, 0);

        board.setPieceOnBoard(2, 2, new EmptyPiece(2, 2, board));
        board.setPieceOnBoard(2, 2, new EmptyPiece(0, 2, board));
        board.setPieceOnBoard(2, 2, new EmptyPiece(0, 0, board));
        board.setPieceOnBoard(2, 2, new EmptyPiece(2, 0, board));


        assertThat(board.getPiece(1, 1).canMove(coordinate1)).isEqualTo(false);
        assertThat(board.getPiece(1, 1).canMove(coordinate2)).isEqualTo(false);
        assertThat(board.getPiece(1, 1).canMove(coordinate3)).isEqualTo(false);
        assertThat(board.getPiece(1, 1).canMove(coordinate4)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 폰이 대각선에 같은팀 말이 없는 경우 움직일 수 없음")
    public void moveBlackDiagonalTeamPieceExist() {
        Coordinate coordinate1 = new Coordinate(2, 2);
        Coordinate coordinate2 = new Coordinate(0, 2);
        Coordinate coordinate3 = new Coordinate(0, 0);
        Coordinate coordinate4 = new Coordinate(2, 0);

        board.setPieceOnBoard(2, 2, new Pawn(2,2,Color.BLACK, board));
        board.setPieceOnBoard(2, 2, new Pawn(2,0,Color.BLACK, board));
        board.setPieceOnBoard(2, 2, new Pawn(0,0,Color.BLACK, board));
        board.setPieceOnBoard(2, 2, new Pawn(0,2,Color.BLACK, board));


        assertThat(board.getPiece(1, 1).canMove(coordinate1)).isEqualTo(false);
        assertThat(board.getPiece(1, 1).canMove(coordinate2)).isEqualTo(false);
        assertThat(board.getPiece(1, 1).canMove(coordinate3)).isEqualTo(false);
        assertThat(board.getPiece(1, 1).canMove(coordinate4)).isEqualTo(false);
    }

    @Test
    @DisplayName("검은색 폰이 대각선에 상대방 말이 있는 경우 움직일 수 있음")
    public void moveBlackDiagonalEnemyExist() {
        Coordinate coordinate1 = new Coordinate(2, 2);
        Coordinate coordinate2 = new Coordinate(0, 2);
        Coordinate coordinate3 = new Coordinate(0, 0);
        Coordinate coordinate4 = new Coordinate(2, 0);

        board.setPieceOnBoard(2, 2, new Pawn(2, 2, Color.WHITE, board));
        board.setPieceOnBoard(0, 2, new Pawn(0, 2, Color.WHITE, board));
        board.setPieceOnBoard(0, 0, new Pawn(0, 0, Color.WHITE, board));
        board.setPieceOnBoard(2, 0, new Pawn(2, 0, Color.WHITE, board));

        assertThat(board.getPiece(1, 1).canMove(coordinate1)).isEqualTo(true);
        assertThat(board.getPiece(1, 1).canMove(coordinate2)).isEqualTo(true);
        assertThat(board.getPiece(1, 1).canMove(coordinate3)).isEqualTo(true);
        assertThat(board.getPiece(1, 1).canMove(coordinate4)).isEqualTo(true);
    }

    /* 흰색 말 테스트 */
    @Test
    @DisplayName("흰색 폰이 앞 빈으로 한칸 이동 가능 여부 확인")
    public void moveWhiteOneForwardEmptyPoint() {
        Coordinate coordinate = new Coordinate(5, 6);

        assertThat(board.getPiece(6, 6).canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 폰 앞에 같은 팀 말이 있다면 이동할 수 없음")
    public void moveWhiteOneForwardPiecePoint() {
        Coordinate coordinate = new Coordinate(5, 6);
        board.setPieceOnBoard(5,6,new Pawn(5,6,Color.WHITE, board));

        assertThat(board.getPiece(6, 6).canMove(coordinate)).isEqualTo(false);
    }


    @Test
    @DisplayName("흰색 폰이 처음에 두 칸 이동 가능 여부 확인")
    public void moveWhiteFirstTwoForwardEmptyPoint() {
        Coordinate coordinate = new Coordinate(4, 6);

        assertThat(board.getPiece(6, 6).canMove(coordinate)).isEqualTo(true);
    }

    @Test
    @DisplayName("흰색 폰 앞에 같은 팀 말이 있다면 두 칸 이동할 수 없음")
    public void moveWhiteFirstTwoForwardPiecePoint() {
        Coordinate coordinate = new Coordinate(4, 6);
        board.setPieceOnBoard(4,6,new Pawn(4,6,Color.WHITE, board));

        assertThat(board.getPiece(6, 6).canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 폰이 좌로 움직일 수 없음 여부 확인")
    public void moveWhiteOneLeft() {
        Coordinate coordinate = new Coordinate(6, 5);

        assertThat(board.getPiece(6, 6).canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 폰이 우로 움직일 수 없음 여부 확인")
    public void moveWhiteOneRight() {
        Coordinate coordinate = new Coordinate(6, 7);

        assertThat(board.getPiece(6, 6).canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 폰이 뒤로 움직일 수 없음 여부 확인")
    public void moveWhiteOneBack() {
        Coordinate coordinate = new Coordinate(7, 6);

        assertThat(board.getPiece(6, 6).canMove(coordinate)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 폰이 대각선에 상대방 말이 없는 경우 움직일 수 없음")
    public void moveWhiteDiagonalNoEnemyExist() {
        Coordinate coordinate1 = new Coordinate(5, 5);
        Coordinate coordinate2 = new Coordinate(5, 7);
        Coordinate coordinate3 = new Coordinate(7, 5);
        Coordinate coordinate4 = new Coordinate(7, 7);

        board.setPieceOnBoard(6, 6, new EmptyPiece(5, 5, board));
        board.setPieceOnBoard(6, 6, new EmptyPiece(5, 7, board));
        board.setPieceOnBoard(6, 6, new EmptyPiece(7, 5, board));
        board.setPieceOnBoard(6, 6, new EmptyPiece(7, 7, board));


        assertThat(board.getPiece(6, 6).canMove(coordinate1)).isEqualTo(false);
        assertThat(board.getPiece(6, 6).canMove(coordinate2)).isEqualTo(false);
        assertThat(board.getPiece(6, 6).canMove(coordinate3)).isEqualTo(false);
        assertThat(board.getPiece(6, 6).canMove(coordinate4)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 폰이 대각선에 같은팀 말이 없는 경우 움직일 수 없음")
    public void moveWhiteDiagonalTeamPieceExist() {
        Coordinate coordinate1 = new Coordinate(5, 5);
        Coordinate coordinate2 = new Coordinate(5, 7);
        Coordinate coordinate3 = new Coordinate(7, 5);
        Coordinate coordinate4 = new Coordinate(7, 7);

        board.setPieceOnBoard(5, 5, new Pawn(5,5,Color.WHITE, board));
        board.setPieceOnBoard(5, 7, new Pawn(5,7,Color.WHITE, board));
        board.setPieceOnBoard(7, 5, new Pawn(7,5,Color.WHITE, board));
        board.setPieceOnBoard(7, 7, new Pawn(7,7,Color.WHITE, board));


        assertThat(board.getPiece(6, 6).canMove(coordinate1)).isEqualTo(false);
        assertThat(board.getPiece(6, 6).canMove(coordinate2)).isEqualTo(false);
        assertThat(board.getPiece(6, 6).canMove(coordinate3)).isEqualTo(false);
        assertThat(board.getPiece(6, 6).canMove(coordinate4)).isEqualTo(false);
    }

    @Test
    @DisplayName("흰색 폰이 대각선에 상대방 말이 있는 경우 움직일 수 있음")
    public void moveWhiteDiagonalEnemyExist() {
        Coordinate coordinate1 = new Coordinate(5, 5);
        Coordinate coordinate2 = new Coordinate(5, 7);
        Coordinate coordinate3 = new Coordinate(7, 5);
        Coordinate coordinate4 = new Coordinate(7, 7);

        board.setPieceOnBoard(5, 5, new Pawn(5, 5, Color.BLACK, board));
        board.setPieceOnBoard(5, 7, new Pawn(5, 7, Color.BLACK, board));
        board.setPieceOnBoard(7, 5, new Pawn(7, 5, Color.BLACK, board));
        board.setPieceOnBoard(7, 7, new Pawn(7, 7, Color.BLACK, board));

        assertThat(board.getPiece(6, 6).canMove(coordinate1)).isEqualTo(true);
        assertThat(board.getPiece(6, 6).canMove(coordinate2)).isEqualTo(true);
        assertThat(board.getPiece(6, 6).canMove(coordinate3)).isEqualTo(true);
        assertThat(board.getPiece(6, 6).canMove(coordinate4)).isEqualTo(true);
    }

}
