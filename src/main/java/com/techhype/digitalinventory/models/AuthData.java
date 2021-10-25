package com.techhype.digitalinventory.models;

import org.springframework.http.ResponseEntity;

public class AuthData {
    private ResponseEntity<BaseResponse> response;
    private TokenData tokenData;
    private boolean auth;

    public ResponseEntity<BaseResponse> getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity<BaseResponse> response) {
        this.response = response;
    }

    public TokenData getTokenData() {
        return tokenData;
    }

    public void setTokenData(TokenData tokenData) {
        this.tokenData = tokenData;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

}
