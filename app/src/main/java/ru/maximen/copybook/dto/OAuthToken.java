package ru.maximen.copybook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OAuthToken {

    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
    private String jti;
}
