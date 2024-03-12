package practice.tdd.chess.game.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practice.tdd.chess.exceptions.InvalidUserRequestException;
import practice.tdd.chess.game.domain.board.Coordinate;
import practice.tdd.chess.game.domain.board.CoordinateDTO;
import practice.tdd.chess.game.domain.game.Game;
import practice.tdd.chess.game.domain.game.GameDTO;
import practice.tdd.chess.game.domain.piece.Color;
import practice.tdd.chess.game.domain.player.Player;
import practice.tdd.chess.game.repository.GameRepository;
import practice.tdd.chess.game.service.Convertor;
import practice.tdd.chess.game.service.GameService;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final Convertor convertor;
    private final GameRepository gameRepository;

    public GameController(GameService gameService, Convertor convertor, GameRepository gameRepository) {
        this.gameService = gameService;
        this.convertor = convertor;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/start")
    public String startGameForm() {
        return "game-start";
    }

    @PostMapping("/start")
    public String startGame(@RequestParam String user1_id,
                            @RequestParam String user2_id,
                            @RequestParam String user1_name,
                            @RequestParam String user2_name,
                            Model model) {

        System.out.println("GameController.startGame");
        Long user1Id = Long.parseLong(user1_id);
        Long user2Id = Long.parseLong(user2_id);

        Player player1 = new Player(user1Id, user1_name, 0, Color.BLACK);
        Player player2 = new Player(user2Id, user2_name, 0, Color.WHITE);
        Game game = gameService.initializeGame(player1, player2);
        GameDTO gameDTO = convertor.convertGameToGameDTO(game);
        model.addAttribute("gameDTO", gameDTO);
        return "chess-board";
    }

    @GetMapping("/request")
    public String getGameInstance(@RequestParam String gameId, Model model) {
        Game game;
        Optional<Game> gameValue = gameRepository.findById(Long.parseLong(gameId));
        if (gameValue.isPresent()) {
            game = gameValue.get();
            GameDTO gameDTO = convertor.convertGameToGameDTO(game);
            model.addAttribute("gameDTO", gameDTO);
        } else {
            model.addAttribute("error", false);
            return "error";
        }

        return "chess-board";
    }

    @PostMapping("/movable-position")
    public ResponseEntity<?> movablePosition(@RequestParam Long gameId,
                                             @RequestBody CoordinateDTO coordinateDTO,
                                              HttpServletRequest request) {
        System.out.println("coordinateDTO = " + coordinateDTO);
        Game game = gameRepository.findById(gameId).get();

        Coordinate coordinate = new Coordinate(coordinateDTO.getRow(), coordinateDTO.getCol());
        gameService.getMovableCoordinates(game, coordinate);
        return null;
    }

    @PostMapping("/request")
    public String userMovePiece(@RequestBody Map<String, Object> params,
                                Model model) {
        Long gameId = Long.parseLong(params.get("game_id").toString());
        Integer startRow = Integer.parseInt(params.get("start_row").toString());
        Integer startCol = Integer.parseInt(params.get("start_col").toString());
        Integer finishRow = Integer.parseInt(params.get("finish_row").toString());
        Integer finishCol = Integer.parseInt(params.get("finish_col").toString());

        Game game;
        Optional<Game> gameValue = gameRepository.findById(gameId);
        if (gameValue.isPresent()) {
            game = gameValue.get();
        } else {
            model.addAttribute("error", false);
            return "error";
        }
        Coordinate start = new Coordinate(startRow, startCol);
        Coordinate finish = new Coordinate(finishRow, finishCol);
        try {
            gameService.movePiece(game, start, finish);
        } catch (InvalidUserRequestException e) {
            //잘못된 움직임
        }

        return "chess-board";

    }
}
