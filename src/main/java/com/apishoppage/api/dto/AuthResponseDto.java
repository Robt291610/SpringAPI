package com.apishoppage.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {


    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token_expiry")
    private int accessTokenExpiry;

    @JsonProperty("token_type")
    private TokenType tokenType;

    @JsonProperty("user_name")
    private String userName;


    /*@Builder
    public AuthResponseDto(String accessToken,
                           int accessTokenExpiry,
                           TokenType tokenType,
                           String userName){
        this.accessToken = accessToken;
        this.accessTokenExpiry = accessTokenExpiry;
        this.tokenType = tokenType;
        this.userName = userName;
    }

    public String getAccessToken() {return this.accessToken;}
    public int getAccessTokenExpiry() {return this.accessTokenExpiry;}
    public TokenType  getTokenType(){return this.tokenType;}
    public String getUsername() {return this.userName;}

    public void setUsername(String username) {this.userName = userName;}
    public void setAccessToken(String accessToken) {this.accessToken = accessToken;}
    public void setAccessTokenExpiry(int accessTokenExpiry) {this.accessTokenExpiry = accessTokenExpiry;}
    public void setTokenType(TokenType tokenType){this.tokenType = tokenType;}*/
}
