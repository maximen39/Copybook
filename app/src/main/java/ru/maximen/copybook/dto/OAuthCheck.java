package ru.maximen.copybook.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
public class OAuthCheck {

    private List<String> aud;
    private String user_name;
    private List<String> scope;
    private boolean active;
    private int exp;
    private String jti;
    private String client_id;
    @Accessors(fluent = true)
    private String access_token;

}
