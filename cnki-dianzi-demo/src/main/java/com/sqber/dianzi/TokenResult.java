package com.sqber.dianzi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResult {

    private String access_token;
    /**
     * 过期时间（单位 秒）
     */
    private int expires_in;
    private String token_type;

    private String scope;
    private String client_id;
    private String jti;
    private String code;
    private String error_description;



}
