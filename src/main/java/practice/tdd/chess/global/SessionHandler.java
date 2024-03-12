package practice.tdd.chess.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.tdd.chess.exceptions.InvalidSessionException;
import practice.tdd.chess.user.domain.JoinUser;
import practice.tdd.chess.user.repository.UserRepository;

@Service
public class SessionHandler {
    private final UserRepository userRepository;

    @Autowired
    public SessionHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void generateSession(HttpServletRequest request,
                                String userName) {

        HttpSession httpSession = request.getSession(true);

        httpSession.setAttribute("user", userName);
        httpSession.setMaxInactiveInterval(1800);
    }

    public JoinUser validateSession(HttpSession httpSession) throws InvalidSessionException {
        if (httpSession == null) {
            throw new InvalidSessionException("null session");
        }
        String userName = (String) httpSession.getAttribute("user");

        if (userName == null) {
            throw new InvalidSessionException("invalid user logged in");
        }

        return userRepository.findByName(userName).orElseThrow(
                () -> new InvalidSessionException("there is a such user")
        );

    }

}
