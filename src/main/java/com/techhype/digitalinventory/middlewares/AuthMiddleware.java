package com.techhype.digitalinventory.middlewares;

import java.io.IOException;

import com.techhype.digitalinventory.constants.ServerMessage;
import com.techhype.digitalinventory.constants.ServerStatus;
import com.techhype.digitalinventory.models.AuthData;
import com.techhype.digitalinventory.models.BaseResponse;
import com.techhype.digitalinventory.utils.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthMiddleware {
   private JwtTokenUtil jwt;

   @Autowired
   public AuthMiddleware(JwtTokenUtil jwt) {
      this.jwt = jwt;
   }

   public AuthData checkToken(String authorization) throws IOException {
      var token = authorization.replaceAll("Bearer ", "");
      var tokenData = jwt.decodeToken(token);
      var authData = new AuthData();
      if (!tokenData.isPresent()) {
         authData.setAuth(false);
         authData.setResponse(new ResponseEntity<>(
               new BaseResponse(ServerStatus.UNAUTHORIZED, ServerMessage.UNAUTHORIZED, null), HttpStatus.UNAUTHORIZED));
      } else {
         authData.setAuth(true);
         authData.setTokenData(tokenData.get());
      }
      return authData;
   }
}
