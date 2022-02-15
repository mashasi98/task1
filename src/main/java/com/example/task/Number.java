package com.example.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Number extends Parent {

    @JsonProperty("number")
    protected String  shoryNumber;
    @JsonProperty("updated_at")
    protected String  dataUpdate;
    @JsonProperty("data_humans")
    protected String  dataHumans;
    @JsonProperty("full_number")
    protected String  fullNumber;
    @JsonProperty("maxdate")
    protected String  maxDate;
    @JsonProperty("status")
    protected String  status;

    protected String getFullNumber() {
        return fullNumber;
    }


}
