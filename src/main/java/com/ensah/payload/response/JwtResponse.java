package com.ensah.payload.response;
/**
 * @author med_Aziz
 * @version 1.0
 */


public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;

    private String email;
    private String role;

    public JwtResponse(String accessToken, String email, String  role) {
        this.token = accessToken;
        this.email = email;
        this.role = role;
    }

    public String getAccessToken() {
        return token;
    }
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }
    public String getTokenType() {
        return type;
    }
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String  getRole() {
        return role;
    }
}