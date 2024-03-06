package practice.tdd.chess.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practice.tdd.chess.exceptions.InvalidSessionException;
import practice.tdd.chess.exceptions.NullValueException;
import practice.tdd.chess.global.SessionHandler;
import practice.tdd.chess.user.domain.JoinUser;
import practice.tdd.chess.user.domain.JoinUserDTO;
import practice.tdd.chess.user.domain.LoginUserDTO;
import practice.tdd.chess.user.exception.UserJoinException;
import practice.tdd.chess.user.exception.UserLoginException;
import practice.tdd.chess.user.service.LoginService;
import practice.tdd.chess.user.service.UserJoinService;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserJoinService userJoinService;
    private final LoginService loginService;
    private final SessionHandler sessionHandler;

    @Autowired
    public UserController(UserJoinService userJoinService, LoginService loginService, SessionHandler sessionHandler) {
        this.userJoinService = userJoinService;
        this.loginService = loginService;
        this.sessionHandler = sessionHandler;
    }

    @GetMapping
    public String userPage(HttpServletRequest request,
                           Model model) {
        try {
            JoinUser user = sessionHandler.validateSession(request.getSession());
            model.addAttribute("userName", user.getName());
            log.info("[user] user page request {}", user.getName());
            return "user/user-page";
        } catch (InvalidSessionException e) {
            log.info("[user] not logged user try to go userpage");
            return "redirect:/user/login";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/user-login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginUserDTO loginUserDTO,
                            HttpServletRequest request) {
        try {
            loginService.login(loginUserDTO);
            sessionHandler.generateSession(request, loginUserDTO.getName());
            log.info("[user] logged in {}", loginUserDTO.getName());
            return "redirect:/";
        } catch (UserLoginException e) {
            log.info("[user] login fail {}", loginUserDTO.getName());
            return "redirect:/user/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            String userName = sessionHandler.validateSession(session).getName();
            session.invalidate();
            log.info("[user] logged out {}", userName);
            return "redirect:/user/login";
        } catch (InvalidSessionException e) {
            return "redirect:/user/login";
        }
    }

    @GetMapping("/join")
    public String joinPage() {
        return "user/user-join";
    }

    @PostMapping("join")
    public String joinUser(@ModelAttribute JoinUserDTO joinUserDTO) {
        try {
            JoinUser joinUser = userJoinService.convertDTOToJoinUser(joinUserDTO);
            userJoinService.join(joinUser);
            log.info("[user] join {}", joinUser.getName());
            return "user/join-success";
        } catch (NullValueException | UserJoinException e) {
            log.error("[user] join error {}", e.getMessage());
            return "user/join-fail";
        }
    }
}
