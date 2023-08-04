package com.sqber.dianzi.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DsLoginResult {

    @JsonProperty("Success")
    private boolean success;

    @JsonProperty("Code")
    private Integer code;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("IdenId")
    private String idenId;

    @JsonProperty("Username")
    private String username;

    @JsonProperty("PersonUserName")
    private String personUserName;

    @JsonProperty("InstUserName")
    private String instUserName;

    @JsonProperty("InstShowName")
    private String instShowName;

    @JsonProperty("MemberLoginLimit")
    private Integer memberLoginLimit;

}
