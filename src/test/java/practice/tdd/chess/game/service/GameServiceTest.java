package practice.tdd.chess.game.service;

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
import practice.tdd.chess.user.domain.UserRequest;

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
    
}
