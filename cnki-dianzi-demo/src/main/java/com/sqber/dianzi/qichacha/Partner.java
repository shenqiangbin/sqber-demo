package com.sqber.dianzi.qichacha;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class Partner {
    @JsonProperty(value = "KeyNo")
    private String KeyNo;
    @JsonProperty(value = "StockName")
    private String StockName;
    @JsonProperty(value = "StockType")
    private String StockType;
}
