package practice.tdd.chess.game.domain.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter @Setter @ToString
public class CoordinateDTO {
    private Integer row;
    private Integer col;
}
