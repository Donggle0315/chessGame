package practice.tdd.chess.game.service;

import org.springframework.stereotype.Service;
import practice.tdd.chess.game.domain.game.Game;
import practice.tdd.chess.game.domain.game.GameDTO;
import practice.tdd.chess.game.domain.piece.*;


@Service
public class Convertor {
    public GameDTO convertGameToGameDTO(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(game.getId());
        gameDTO.setPlayer1(game.getP1());
        gameDTO.setPlayer2(game.getP2());
        gameDTO.setTurn(game.getTurn());

        for (Piece[] pieces : game.getBoard().getBoard()) {
            for (Piece piece : pieces) {
                PieceDTO pieceDTO = convertPieceToPieceDTO(piece);
                gameDTO.setPieceDTO(piece.getCoordinate().getRow(), piece.getCoordinate().getCol(), pieceDTO);
            }
        }

        return gameDTO;
    }

    public PieceDTO convertPieceToPieceDTO(Piece piece) {
        PieceDTO pieceDTO = new PieceDTO();
        if (piece instanceof Pawn) {
            pieceDTO.setName("PAWN");
        } else if (piece instanceof Rook) {
            pieceDTO.setName("ROOK");
        } else if (piece instanceof Knight) {
            pieceDTO.setName("KNIGHT");
        } else if (piece instanceof Bishop) {
            pieceDTO.setName("BISHOP");
        } else if (piece instanceof Queen) {
            pieceDTO.setName("QUEEN");
        } else if (piece instanceof King) {
            pieceDTO.setName("KING");
        } else {
            pieceDTO.setName("EMPTY");
        }

        pieceDTO.setColor(piece.getColor());
        return pieceDTO;
    }
}
