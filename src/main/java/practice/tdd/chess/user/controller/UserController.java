package practice.tdd.chess.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import practice.tdd.chess.exceptions.NullValueException;
import practice.tdd.chess.user.domain.JoinUser;
import practice.tdd.chess.user.domain.JoinUserDTO;
import practice.tdd.chess.user.exception.UserJoinException;
import practice.tdd.chess.user.service.UserJoinService;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserJoinService userJoinService;

    @Autowired
    public UserController(UserJoinService userJoinService) {
        this.userJoinService = userJoinService;
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
