package com.sqber.dianzi.qichacha;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class QueryResult {
    @JsonProperty(value = "Status")
    private String Status;

    @JsonProperty(value = "Message")
    private String Message;

    @JsonProperty(value = "Result")
    private InfoData Result;

    @JsonProperty(value = "OrderNumber")
    private String OrderNumber;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public InfoData getResult() {
        return Result;
    }

    public void setResult(InfoData result) {
        Result = result;
    }
}
