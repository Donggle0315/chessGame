package practice.tdd.chess.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.tdd.chess.user.domain.JoinUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<JoinUser, Long> {
    Optional<JoinUser> findByName(String name);
}
