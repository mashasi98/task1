package com.example.task;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Parent {
    @JsonProperty("country")
    protected int countryId;
    @JsonProperty("country_text")
    protected String countryText;
}
