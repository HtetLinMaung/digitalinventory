package com.techhype.digitalinventory.auth;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techhype.digitalinventory.configs.Environment;
import com.techhype.digitalinventory.constants.ServerMessage;
import com.techhype.digitalinventory.constants.ServerStatus;
import com.techhype.digitalinventory.models.BaseResponse;
import com.techhype.digitalinventory.models.TokenData;
import com.techhype.digitalinventory.shop.ShopService;
import com.techhype.digitalinventory.utils.JwtTokenUtil;
import com.techhype.digitalinventory.utils.RestClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {
    private JwtTokenUtil jwt;
    private ShopService sService;
    private Environment env;

    @Autowired
    public AuthController(JwtTokenUtil jwt, ShopService sService, Environment env) {
        this.jwt = jwt;
        this.sService = sService;
        this.env = env;
    }

    @PostMapping(path = "get-app-token")
    public ResponseEntity<BaseResponse> getApplicationToken(@RequestBody TokenData tokenData) {
        try {
            var body = new HashMap<String, Object>();
            body.put("token", tokenData.getIamtoken());
            var response = RestClient.post(env.getProxyhost() + "/iam/auth/check-token", body, new HashMap<>());

            if (!response.get("code").equals(200)) {
                return new ResponseEntity<BaseResponse>(
                        new BaseResponse(ServerStatus.UNAUTHORIZED, ServerMessage.UNAUTHORIZED, null),
                        HttpStatus.UNAUTHORIZED);
            }
            var json = new ObjectMapper().writeValueAsString(response.get("data"));
            var tData = new ObjectMapper().readValue(json, TokenData.class);
            var shopmap = sService.getShopMapByUserid(tData.getUserid(), tData);
            if (shopmap.isPresent()) {
                var sm = shopmap.get();
                tData.setShopid(sm.getShopid());
                tData.setShopname(sm.getShopname());
            }
            var token = jwt.generateToken(tData);
            tData.setToken(token);
            return ResponseEntity.ok().body(BaseResponse.ok(tData));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }
}
