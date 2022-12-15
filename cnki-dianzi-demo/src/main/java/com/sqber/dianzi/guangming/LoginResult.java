package com.sqber.dianzi.guangming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginResult {
    @JsonProperty(value = "Code")
    private String code;

    @JsonProperty(value = "Message")
    private String message;

    @JsonProperty(value = "Token")
    private String token;
}
