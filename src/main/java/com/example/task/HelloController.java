package com.example.task;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;



public class HelloController implements Initializable {

    @FXML
    private ListView<String> countyCodeList;
    @FXML
    private ListView<String> phoneNumbersList;

    String currentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        HttpClient client = HttpClient.newHttpClient();

        countyCodeList.getItems()
                .addAll(getAllInformation(client));

        countyCodeList.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                phoneNumbersList.getItems().clear();
                currentId=countyCodeList.getSelectionModel().getSelectedItem();
                phoneNumbersList.getItems()
                        .addAll(  getNumberRromRequest(currentId,client));

            }

        });

    }

    public void exitProgramm(){

    }
    public ArrayList<String> getNumberRromRequest(String countryId,HttpClient client){
        String uriGetNumbers = "https://onlinesim.ru/api/getFreePhoneList?country=" + currentId;

        return   client.sendAsync(getClientRequest(uriGetNumbers), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(HelloController::parserNumbersToArray)
                .join();
    }

    public ArrayList<String> getAllInformation(HttpClient client) {

        String uriGetCountry = "https://onlinesim.ru/api/getFreeCountryList";

        return client.sendAsync(getClientRequest(uriGetCountry), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(HelloController::parserCountryToArray)
                .join();

    }

    public HttpRequest getClientRequest(String uri) {

        return HttpRequest
                .newBuilder()
                .uri(URI.create(uri))
                .build();
    }

    public static ArrayList<String> parserCountryToArray(String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> countryIdFromRequest = new ArrayList<>();
        try {
            Response responseToClass = mapper.readValue(responseBody, Response.class);
            ArrayList<Country> countries = responseToClass.getCountryList();
            for (Country country : countries) {
                countryIdFromRequest.add(Integer.toString(country.getCountryId()));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return countryIdFromRequest;
    }

    public static ArrayList<String> parserNumbersToArray(String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> numbersFromRequest = new ArrayList<>();
        try {
            Response responseToClass = mapper.readValue(responseBody, Response.class);
            ArrayList<Number> numbers = responseToClass.getNumbersList();
            for (Number number : numbers) {
                numbersFromRequest.add(number.getFullNumber());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return numbersFromRequest;
    }





}