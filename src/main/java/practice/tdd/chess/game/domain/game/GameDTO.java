package practice.tdd.chess.game.domain.game;

import lombok.Getter;
import lombok.Setter;
import practice.tdd.chess.game.domain.piece.PieceDTO;
import practice.tdd.chess.game.domain.player.Player;
import practice.tdd.chess.game.domain.player.Turn;

@Getter @Setter
public class GameDTO {
    private Long Id;
    private Player player1;
    private Player player2;
    private Turn turn;
    private PieceDTO[][] board = new PieceDTO[8][8];

    public GameDTO() {
    }

    public GameDTO(Player player1, Player player2, Turn turn, PieceDTO[][] board) {
        this.player1 = player1;
        this.player2 = player2;
        this.turn = turn;
        this.board = board;
    }

    public void setPieceDTO(int row, int col, PieceDTO pieceDTO) {
        this.board[row][col] = pieceDTO;
    }
}
