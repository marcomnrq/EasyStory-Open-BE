package com.dystopiastudios.easystory.domain.service.communication;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private String username;
    private String token;

    public AuthenticationResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
