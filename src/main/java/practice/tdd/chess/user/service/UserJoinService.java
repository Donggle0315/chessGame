package practice.tdd.chess.user.service;

import practice.tdd.chess.exceptions.NullValueException;
import practice.tdd.chess.user.domain.JoinUser;
import practice.tdd.chess.user.domain.JoinUserDTO;
import practice.tdd.chess.user.exception.UserJoinException;

public interface UserJoinService {
    JoinUser convertDTOToJoinUser(JoinUserDTO joinUserDTO) throws NullValueException;

    void join(JoinUser joinUser) throws UserJoinException;
}
