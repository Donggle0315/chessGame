package practice.tdd.chess.user;

import practice.tdd.chess.user.domain.LoginUserDTO;

public class LoginDataGiver {
    public LoginUserDTO getLoginDataNormal() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setName("kimTest");
        loginUserDTO.setPassword("1234");

        return loginUserDTO;
    }

    public LoginUserDTO getLoginDataWithoutName() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setPassword("1234");

        return loginUserDTO;
    }

    public LoginUserDTO getLoginDataWithoutPassword() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setName("kimTest");

        return loginUserDTO;
    }

    public LoginUserDTO getLoginDataNothing() {
        return new LoginUserDTO();
    }
}
