package practice.tdd.chess.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.tdd.chess.game.domain.game.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
