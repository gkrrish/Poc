package com.poc.response;

import lombok.Data;

import java.util.List;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Data
@DefaultSerializer
@JsonSerialize
@JsonDeserialize
public class WelcomeResponse {
    private String message;
    private List<String> languages;

    public WelcomeResponse(String message) {
        this.message = message;
    }
}
