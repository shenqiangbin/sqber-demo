package com.sqber.dianzi.result;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DsLoginResult extends DsBaseResult {

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

    public String getIdenId() {
        return idenId;
    }

    public void setIdenId(String idenId) {
        this.idenId = idenId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonUserName() {
        return personUserName;
    }

    public void setPersonUserName(String personUserName) {
        this.personUserName = personUserName;
    }

    public String getInstUserName() {
        return instUserName;
    }

    public void setInstUserName(String instUserName) {
        this.instUserName = instUserName;
    }

    public String getInstShowName() {
        return instShowName;
    }

    public void setInstShowName(String instShowName) {
        this.instShowName = instShowName;
    }
}
