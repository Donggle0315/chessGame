package practice.tdd.chess.user;

import practice.tdd.chess.user.domain.JoinUser;
import practice.tdd.chess.user.domain.JoinUserDTO;

public class JoinDataGiver {
    public JoinUserDTO getJoinUserDTOWithNullNameValue() {
        JoinUserDTO joinUserDTO = new JoinUserDTO();
        joinUserDTO.setPassword("1234");

        return joinUserDTO;
    }

    public JoinUserDTO getJoinUserDTOWithNullPasswordValue() {
        JoinUserDTO joinUserDTO = new JoinUserDTO();
        joinUserDTO.setName("kim");

        return joinUserDTO;
    }

    public JoinUserDTO getJoinUserDTONormal() {
        JoinUserDTO joinUserDTO = new JoinUserDTO();
        joinUserDTO.setName("kim");
        joinUserDTO.setPassword("1234");

        return joinUserDTO;
    }

    public JoinUserDTO getJoinUserDTOWithEmptyNameValue() {
        JoinUserDTO joinUserDTO = new JoinUserDTO();
        joinUserDTO.setName("");
        joinUserDTO.setPassword("1234");

        return joinUserDTO;
    }

    public JoinUserDTO getJoinUserDTOWithEmptyPasswordValue() {
        JoinUserDTO joinUserDTO = new JoinUserDTO();
        joinUserDTO.setName("kim");
        joinUserDTO.setPassword("");

        return joinUserDTO;
    }

    public JoinUser getJoinUserNormal() {
        JoinUser joinUser = new JoinUser();
        joinUser.setName("Kim");
        joinUser.setPassword("1234");
        return joinUser;
    }
}
