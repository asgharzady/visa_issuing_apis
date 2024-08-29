package com.appopay.visa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwillioRequestDTO {

    private String body;
    private String from;
    private String to;
    private String accountSid;



}
