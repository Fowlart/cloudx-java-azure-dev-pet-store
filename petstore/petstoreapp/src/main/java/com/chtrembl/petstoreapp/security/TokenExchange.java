package com.chtrembl.petstoreapp.security;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenExchange {



    public String getGwtToken(String code,
                              String client_id,
                              String client_secret,
                              String redirect_uri) {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        try {
            response = Unirest.post("https://fowlart.b2clogin.com/fowlart.onmicrosoft.com/B2C_1_pet-store-user-flow/oauth2/v2.0/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Cookie", "x-ms-cpim-geo=EU")
                    .field("grant_type", "authorization_code")
                    .field("code", code)
                    .field("client_id", client_id)
                    .field("client_secret", client_secret)
                    .field("redirect_uri", redirect_uri)
                    .field("scope", "openid offline_access").asString();

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return response != null ? response.getBody() : null;
    }
}



