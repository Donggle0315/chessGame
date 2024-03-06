package practice.tdd.chess.user.service;

import practice.tdd.chess.user.domain.LoginUserDTO;
import practice.tdd.chess.user.exception.UserLoginException;

public interface LoginService {
    void login(LoginUserDTO loginUserDTO) throws UserLoginException;
}
