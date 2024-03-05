package practice.tdd.chess.game.domain.piece;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PieceDTO {
    private String name;
    private Color color;

    public PieceDTO() {
    }

    public PieceDTO(String name, Color color) {
        this.name = name;
        this.color = color;
    }
}
