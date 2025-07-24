package de.brunokrams.lichessclient.domain.lichessapi;

public class PlayerDto {
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
