package com.chtrembl.petstoreapp.security;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JWTDecoder {

    public String getNameFromToken(String response) {

        try {
            String jwtToken = new JSONObject(response).getString("id_token");

            String[] chunks = jwtToken.split("\\.");

            Base64.Decoder decoder = Base64.getUrlDecoder();

            String payload = new String(decoder.decode(chunks[1]));

            JSONObject jsonObject = new JSONObject(payload);

            return jsonObject.getString("given_name") + " " + jsonObject.getString("family_name");
        } catch (Exception e) {
            return null;
        }
    }
}

