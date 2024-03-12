package practice.tdd.chess.game.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.tdd.chess.exceptions.InvalidUserRequestException;
import practice.tdd.chess.game.domain.game.Game;
import practice.tdd.chess.game.domain.board.Coordinate;
import practice.tdd.chess.game.domain.piece.Color;
import practice.tdd.chess.game.domain.piece.EmptyPiece;
import practice.tdd.chess.game.domain.piece.Pawn;
import practice.tdd.chess.game.domain.player.Turn;
import practice.tdd.chess.user.domain.User;
import practice.tdd.chess.game.domain.UserRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTest {
    private final GameService gameService;
    private Game game;
    private User user1;
    private User user2;

    @Autowired
    public GameServiceTest(GameService gameService) {
        this.gameService = gameService;
    }

    @BeforeEach
    public void init() {
        user1 = new User(1L, "BLACK");
        user2 = new User(2L, "WHITE");
        game = gameService.initializeGame(user1, user2);
    }

    @Test
    @DisplayName("게임이 정상적으로 만들어지는지 테스트")
    public void makeGame() {
        User testUser1 = new User(1L, "Kim");
        User testUser2 = new User(2L, "Lee");
        Game gameResult = gameService.initializeGame(testUser1, testUser2);

        assertThat(gameResult.getP1().getGameId()).isEqualTo(testUser1.getGameId());
        assertThat(gameResult.getP1().getName()).isEqualTo(testUser1.getName());
        assertThat(gameResult.getP1().getColor()).isEqualTo(Color.BLACK);
        assertThat(gameResult.getP1().getTurn()).isEqualTo(0);

        assertThat(gameResult.getP2().getGameId()).isEqualTo(testUser2.getGameId());
        assertThat(gameResult.getP2().getName()).isEqualTo(testUser2.getName());
        assertThat(gameResult.getP2().getColor()).isEqualTo(Color.WHITE);
        assertThat(gameResult.getP2().getTurn()).isEqualTo(0);

        assertThat(gameResult.getTurn().getTurn()).isEqualTo(0);
        assertThat(gameResult.getTurn().getColor()).isEqualTo(Color.BLACK);

        assertThat(gameResult.getBoard()).isNotNull();
    }

    @Test
    @DisplayName("유저의 요청이 현재 턴 중 턴 수가 맞지 않다면 예외 발생")
    public void doesNotMatchUserRequestTurnAndGameTurn() {
        UserRequest userRequest = new UserRequest();
        userRequest.setTurn(new Turn(1, Color.BLACK));
        assertThrows(InvalidUserRequestException.class,
                () -> gameService.matchTurn(userRequest.getTurn(), game.getTurn()));
    }

    @Test
    @DisplayName("유저의 요청이 현재 턴의 컬러와 맞지 않다면 예외 발생")
    public void doesNotMatchUserRequestColorAndGameColor() {
        UserRequest userRequest = new UserRequest();
        userRequest.setTurn(new Turn(0, Color.WHITE));
        assertThrows(InvalidUserRequestException.class,
                () -> gameService.matchTurn(userRequest.getTurn(), game.getTurn()));
    }

    @Test
    @DisplayName("유저의 요청과 현재 턴이 일치한다면 예외가 발생하지 않음")
    public void matchUserRequestAndGameTurn() {
        UserRequest userRequest = new UserRequest();
        userRequest.setTurn(new Turn(0,Color.BLACK));

        try {
            gameService.matchTurn(userRequest.getTurn(), game.getTurn());
        } catch (InvalidUserRequestException e) {
            fail();
        }
    }

    @Test
    @DisplayName("유저의 요청에 따라 말을 옮기지만 유효하지 않아서 false 리턴 - 남의 말을 옮김")
    public void movePieceServiceInvalidSelectEnemyPiece() {
        UserRequest userRequest = new UserRequest();
        userRequest.setStartCoordinate(new Coordinate(6,0));
        userRequest.setFinishCoordinate(new Coordinate(5, 0));

        assertThrows(InvalidUserRequestException.class,
                () -> gameService.movePiece(game, userRequest.getStartCoordinate(), userRequest.getFinishCoordinate()));
    }

    @Test
    @DisplayName("유저의 요청에 따라 말을 옮기지만 유효하지 않아서 false 리턴 - 옮길 수 없는 곳으로 옮김")
    public void movePieceServiceInvalidSelectWrongDestination() {
        UserRequest userRequest = new UserRequest();
        userRequest.setStartCoordinate(new Coordinate(1,0));
        userRequest.setFinishCoordinate(new Coordinate(4, 0));

        assertThrows(InvalidUserRequestException.class,
                () -> gameService.movePiece(game, userRequest.getStartCoordinate(), userRequest.getFinishCoordinate()));
    }

    @Test
    @DisplayName("유저의 요청이 유효할 경우 해당 말을 이동시킴 - BLACK")
    public void movePieceServiceValidMoveBlack() {
        UserRequest userRequest = new UserRequest();
        userRequest.setStartCoordinate(new Coordinate(1, 0));
        userRequest.setFinishCoordinate(new Coordinate(2, 0));

        try {
            gameService.movePiece(game, userRequest.getStartCoordinate(), userRequest.getFinishCoordinate());
        } catch (InvalidUserRequestException e) {
            fail();
        }

        assertThat(game.getBoard().getPiece(1,0)).isInstanceOf(EmptyPiece.class);
        assertThat(game.getBoard().getPiece(2, 0)).isInstanceOf(Pawn.class);
    }

    @Test
    @DisplayName("유저의 요청이 유효할 경우 해당 말을 이동시킴 - WHITE")
    public void movePieceServiceValidMoveWhite() {
        game.getTurn().nextTurn();
        UserRequest userRequest = new UserRequest();
        userRequest.setStartCoordinate(new Coordinate(6, 0));
        userRequest.setFinishCoordinate(new Coordinate(5, 0));

        try {
            gameService.movePiece(game, userRequest.getStartCoordinate(), userRequest.getFinishCoordinate());
        } catch (InvalidUserRequestException e) {
            fail();
        }

        assertThat(game.getBoard().getPiece(6,0)).isInstanceOf(EmptyPiece.class);
        assertThat(game.getBoard().getPiece(5, 0)).isInstanceOf(Pawn.class);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - BLACK PAWN")
    public void userSelectCoordinateMovableBlackPawn() {
        Coordinate coordinate = new Coordinate(1, 0);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);

        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(2, 0));
        expect.add(new Coordinate(3, 0));
        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - BLACK ROOK")
    public void userSelectCoordinateMovableBlackRook() {
        Coordinate coordinate = new Coordinate(0, 0);
        deletePieceOnBoard(1,0);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);

        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(1, 0));
        expect.add(new Coordinate(2, 0));
        expect.add(new Coordinate(3, 0));
        expect.add(new Coordinate(4, 0));
        expect.add(new Coordinate(5, 0));
        expect.add(new Coordinate(6, 0));

        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - BLACK KNIGHT")
    public void userSelectCoordinateMovableBlackKnight() {
        Coordinate coordinate = new Coordinate(0, 1);
        deletePieceOnBoard(1,3);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);

        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(1, 3));
        expect.add(new Coordinate(2, 0));
        expect.add(new Coordinate(2, 2));


        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - BLACK BISHOP")
    public void userSelectCoordinateMovableBlackBishop() {
        Coordinate coordinate = new Coordinate(0, 2);
        deletePieceOnBoard(1,1);
        deletePieceOnBoard(1,3);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);

        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(1, 1));
        expect.add(new Coordinate(1, 3));
        expect.add(new Coordinate(2, 0));
        expect.add(new Coordinate(2, 4));
        expect.add(new Coordinate(3, 5));
        expect.add(new Coordinate(4, 6));
        expect.add(new Coordinate(5, 7));

        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - BLACK KING")
    public void userSelectCoordinateMovableBlackKing() {
        Coordinate coordinate = new Coordinate(0, 3);
        deletePieceOnBoard(0,2);
        deletePieceOnBoard(0,4);
        deletePieceOnBoard(1,2);
        deletePieceOnBoard(1,3);
        deletePieceOnBoard(1,4);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);
        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(0, 2));
        expect.add(new Coordinate(0, 4));
        expect.add(new Coordinate(1, 2));
        expect.add(new Coordinate(1, 3));
        expect.add(new Coordinate(1, 4));

        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - BLACK QUEEN")
    public void userSelectCoordinateMovableBlackQueen() {
        Coordinate coordinate = new Coordinate(0, 4);

        deletePieceOnBoard(0,3);
        deletePieceOnBoard(0,5);
        deletePieceOnBoard(1,3);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);
        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(0, 3));
        expect.add(new Coordinate(0, 5));
        expect.add(new Coordinate(1, 3));
        expect.add(new Coordinate(2, 2));
        expect.add(new Coordinate(3, 1));
        expect.add(new Coordinate(4, 0));

        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - WHITE PAWN")
    public void userSelectCoordinateMovableWhitePawn() {
        Coordinate coordinate = new Coordinate(6, 0);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);

        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(4, 0));
        expect.add(new Coordinate(5, 0));
        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - WHITE ROOK")
    public void userSelectCoordinateMovableWhiteRook() {
        Coordinate coordinate = new Coordinate(7, 0);
        deletePieceOnBoard(6,0);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);

        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(1, 0));
        expect.add(new Coordinate(2, 0));
        expect.add(new Coordinate(3, 0));
        expect.add(new Coordinate(4, 0));
        expect.add(new Coordinate(5, 0));
        expect.add(new Coordinate(6, 0));

        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - WHITE KNIGHT")
    public void userSelectCoordinateMovableWhiteKnight() {
        Coordinate coordinate = new Coordinate(7, 1);
        deletePieceOnBoard(6,3);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);

        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(5, 0));
        expect.add(new Coordinate(5, 2));
        expect.add(new Coordinate(6, 3));


        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - WHITE BISHOP")
    public void userSelectCoordinateMovableWhiteBishop() {
        Coordinate coordinate = new Coordinate(7, 2);
        deletePieceOnBoard(6,1);
        deletePieceOnBoard(6,3);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);

        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(2, 7));
        expect.add(new Coordinate(3, 6));
        expect.add(new Coordinate(4, 5));
        expect.add(new Coordinate(5, 0));
        expect.add(new Coordinate(5, 4));
        expect.add(new Coordinate(6, 1));
        expect.add(new Coordinate(6, 3));

        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - WHITE KING")
    public void userSelectCoordinateMovableWhiteKing() {
        Coordinate coordinate = new Coordinate(7, 3);
        deletePieceOnBoard(7,2);
        deletePieceOnBoard(7,4);
        deletePieceOnBoard(6,2);
        deletePieceOnBoard(6,3);
        deletePieceOnBoard(6,4);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);
        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(6, 2));
        expect.add(new Coordinate(6, 3));
        expect.add(new Coordinate(6, 4));
        expect.add(new Coordinate(7, 2));
        expect.add(new Coordinate(7, 4));

        assertEquals(expect, movableCoordinates);
    }

    @Test
    @DisplayName("유저가 선택한 좌표에 말이 움직일 수 있는 경우 - WHITE QUEEN")
    public void userSelectCoordinateMovableWhiteQueen() {
        Coordinate coordinate = new Coordinate(7, 4);

        deletePieceOnBoard(7,3);
        deletePieceOnBoard(7,5);
        deletePieceOnBoard(6,3);

        List<Coordinate> movableCoordinates = gameService.getMovableCoordinates(game, coordinate);
        List<Coordinate> expect = new ArrayList<>();
        expect.add(new Coordinate(3, 0));
        expect.add(new Coordinate(4, 1));
        expect.add(new Coordinate(5, 2));
        expect.add(new Coordinate(6, 3));
        expect.add(new Coordinate(7, 3));
        expect.add(new Coordinate(7, 5));

        assertEquals(expect, movableCoordinates);
    }

    private void deletePieceOnBoard(int row, int col) {
        game.getBoard().setPieceOnBoard(row, col, new EmptyPiece(row, col, game.getBoard()));
    }
}
