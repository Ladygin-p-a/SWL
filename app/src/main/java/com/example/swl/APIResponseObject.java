package com.example.swl;

public class APIResponseObject {

    int responseCode;
    String response;

    APIResponseObject(int responseCode, String response)
    {
        this.responseCode = responseCode;
        this.response = response;
    }
}
