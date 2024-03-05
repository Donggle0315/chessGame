package practice.tdd.chess.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class JoinUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    public JoinUser() {
    }

    public JoinUser(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public boolean hasNull() {
        return this.getName() == null || this.getPassword() == null;
    }

    public boolean hasEmpty() {
        return this.getName().isEmpty() || this.getPassword().isEmpty();
    }
}
