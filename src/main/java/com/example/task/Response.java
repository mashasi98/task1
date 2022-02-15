package com.example.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Response {

    @JsonProperty("response")
    private int responseId;

    @JsonProperty("countries")
    private ArrayList<Country> countryList;

    @JsonProperty("numbers")
    private ArrayList<Number> numbersList;

    public int getResponseId() {
        return responseId;
    }

    public ArrayList<Number> getNumbersList() {
        return numbersList;
    }

    public ArrayList<Country> getCountryList() {
        return countryList;
    }

}
